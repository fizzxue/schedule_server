package com.yaoshun.schedule.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yaoshun.constant.JobConstant;
import com.yaoshun.log.model.DataPushLog;
import com.yaoshun.log.service.IDataPushLogService;
import com.yaoshun.schedule.dto.IparamDto;
import com.yaoshun.util.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author fizz
 * @since 2019/7/23 18:05
 */
@Slf4j
@Component
public class PushJob implements Job {

	@Resource
	private IDataPushLogService iDataPushLogService;

	/**
	 * 存储tableName和推送偏移量的map
	 */
	private static ConcurrentMap<String, Long> tablePushTotal = new ConcurrentHashMap<>();

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		try {
			JobDataMap mergedJobDataMap = jobExecutionContext.getMergedJobDataMap();
			String iaddr = (String) mergedJobDataMap.get(JobConstant.IADDR);
			String iparam = (String) mergedJobDataMap.get(JobConstant.IPARAM);
			Long jobModelId = (Long) mergedJobDataMap.get(JobConstant.JOB_MODEL_ID);
			log.info("jobName:{}, jobGroup:{}",mergedJobDataMap.get("jobName"), mergedJobDataMap.get("jobGroup"));

			//增加推送偏移量
			IparamDto iparamDto = JSON.parseObject(iparam, IparamDto.class);
			String tableName = iparamDto.getTableName();
			Long singlePushTotal = iparamDto.getSinglePushTotal();
			iparamDto.setPushTotal(getPushTotal(tableName));
			iparam = JSON.toJSONString(iparamDto);

			//推送开始时间
			String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			String resp = HttpUtils.reqPost(iaddr, iparam);

			//插入日志
			if (!StringUtils.isEmpty(resp)) {
				iDataPushLogService.save(getDataPushLog(mergedJobDataMap, resp, now));
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new JobExecutionException(e.getMessage());
		}

	}

	/**
	 * 生成DataPushLog对象
	 * @param mergedJobDataMap mergedJobDataMap
	 * @return DataPushLog对象
	 */
	private DataPushLog getDataPushLog(JobDataMap mergedJobDataMap, String resp, String localDateTime) {
		//resp格式 {code message data}
		JSONObject respJo = JSON.parseObject(resp);

		//data格式 {systemName pushStatus successTotal failureTotal returnContent}
		DataPushLog dataPushLog = respJo.getObject(JobConstant.DATA, DataPushLog.class);

		//设置 jobModelId tableName pushDateTime singlePushTotal pushTotal
		Long jobModelId = mergedJobDataMap.getLong(JobConstant.JOB_MODEL_ID);

		//iparamJo格式 {tableId catalogId tableName optype ignoreField singlePushTotal pushTotal}
		JSONObject iparamJo = JSON.parseObject(mergedJobDataMap.getString(JobConstant.IPARAM));
		String tableName = iparamJo.getString(JobConstant.TABLE_NAME);
		Long singlePushTotal = iparamJo.getLong(JobConstant.SINGLE_PUSH_TOTAL);

		//更新推送偏移量
		updatePushTotal(tableName, dataPushLog.getSuccessTotal());

		Long pushTotal = tablePushTotal.get(tableName);

		return dataPushLog.setJobModelId(jobModelId).setTableName(tableName).setPushDateTime(localDateTime)
				.setSinglePushTotal(singlePushTotal).setPushTotal(pushTotal);
	}

	private static long getPushTotal(String tableName){
		if (tablePushTotal.containsKey(tableName)) {
			return tablePushTotal.getOrDefault(tableName, 0L);
		} else {
			tablePushTotal.put(tableName, 0L);
			return 0L;
		}
	}

	private static void updatePushTotal(String tableName, Long successTotal){
		if (tablePushTotal.containsKey(tableName)) {
			tablePushTotal.put(tableName, tablePushTotal.get(tableName) + successTotal);
		} else {
			tablePushTotal.put(tableName, successTotal);
		}
	}

}
