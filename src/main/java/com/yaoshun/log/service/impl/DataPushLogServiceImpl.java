package com.yaoshun.log.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaoshun.log.mapper.DataPushLogMapper;
import com.yaoshun.log.model.DataPushLog;
import com.yaoshun.log.service.IDataPushLogService;
import com.yaoshun.util.page.ReqPageModel;
import com.yaoshun.util.page.RespPageModel;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @author fizz
 * @since 2019-07-30
 */
@Service
public class DataPushLogServiceImpl extends ServiceImpl<DataPushLogMapper, DataPushLog> implements IDataPushLogService {

    @Override
    public RespPageModel<DataPushLog> listPage(ReqPageModel reqPageModel) {
        String body = reqPageModel.getBody();
        DataPushLog dataPushLog = new DataPushLog();
        Page<DataPushLog> page = new Page<>(reqPageModel.getCurPage(), reqPageModel.getPageSize());
        if (!StringUtils.isEmpty(body)) {
            dataPushLog = JSON.parseObject(body, DataPushLog.class);
        }
        QueryWrapper<DataPushLog> qw = new QueryWrapper<>();
        qw.orderByDesc("push_date_time");
        String startTime = dataPushLog.getStartTime();
        String endTime = dataPushLog.getEndTime();
        Long jobModelId = dataPushLog.getJobModelId();
        if (!StringUtils.isEmpty(startTime)) {
            qw.ge("push_date_time", startTime);
        }
        if (!StringUtils.isEmpty(endTime)) {
            qw.le("push_date_time", endTime);
        }
        Optional.ofNullable(jobModelId).ifPresent(aLong -> qw.eq("job_model_id", jobModelId));
        IPage<DataPushLog> dataPushLogIPage = this.page(page, qw);
        return new RespPageModel<>(reqPageModel, dataPushLogIPage);
    }

    public static void main(String[] args) {
        DataPushLog dataPushLog = new DataPushLog();
        String s = "{\"jobModelId\":1,\"startTime\":\"2019-08-01 00:00:00\",\"endTime\":\"2019-08-01 11:56:54\"}";
        dataPushLog = JSON.parseObject(s, DataPushLog.class);
        System.out.println(dataPushLog);
    }

}
