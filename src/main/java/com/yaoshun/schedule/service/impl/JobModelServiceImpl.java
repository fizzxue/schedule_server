package com.yaoshun.schedule.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaoshun.constant.TriggerStateEnum;
import com.yaoshun.schedule.mapper.JobModelMapper;
import com.yaoshun.schedule.model.JobModel;
import com.yaoshun.schedule.service.JobModelService;
import com.yaoshun.schedule.service.ScheduleService;
import com.yaoshun.util.page.ReqPageModel;
import com.yaoshun.util.page.RespPageModel;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

/**
 * @author fizz
 * @since 2019/7/23 15:41
 */
@Service
public class JobModelServiceImpl extends ServiceImpl<JobModelMapper, JobModel> implements JobModelService {

	@Resource
	private Scheduler scheduler;

	@Resource
	private ScheduleService scheduleService;

	public boolean add(JobModel entity) throws SchedulerException {
		String triggerState = scheduleService.getTriggerState(entity);
		entity.setJobStatus(triggerState);
		return super.save(entity);
	}

	@Override
	public boolean deleteByIds(Collection<? extends Serializable> idList) {
		Collection<JobModel> jobModels = this.listByIds(idList);
		jobModels.forEach(jobModel -> {
			try {
				scheduleService.deleteJob(jobModel);
				this.updateJobStatus(jobModel);
			} catch (SchedulerException e) {
				log.error(e.getMessage(), e);
			}
		});
		return super.removeByIds(idList);
	}

	@Override
	public boolean updateByIdSelf(JobModel jobModel) throws SchedulerException {
		//更新jobModel
		this.updateById(jobModel);
		//只能更新NORMAL状态的作业
		if (!TriggerStateEnum.NORMAL.getKey().equals(TriggerStateEnum.getKeyByValue(jobModel.getJobStatus())))
			return false;
		scheduleService.updateJobCron(jobModel);
		this.updateJobStatus(jobModel);
		return super.updateById(jobModel);
	}

	@Override
	public void updateJobStatus(JobModel jobModel) throws SchedulerException {
		String triggerState = scheduleService.getTriggerState(jobModel);
		jobModel.setJobStatus(triggerState);
		this.updateById(jobModel);
	}

	@Override
	public RespPageModel<JobModel> listPage(ReqPageModel reqPageModel) {
		String body = reqPageModel.getBody();
		JobModel jobModel = new JobModel();
		if (!StringUtils.isEmpty(body)) {
			jobModel = JSON.parseObject(body, JobModel.class);
		}
		Page<JobModel> page = new Page<>(reqPageModel.getCurPage(), reqPageModel.getPageSize());
		QueryWrapper<JobModel> qw = new QueryWrapper<>();
		qw.orderByDesc("id");
		String jobName = jobModel.getJobName();
		if (!StringUtils.isEmpty(jobName)) {
			qw.like("job_name", jobName);
		}
		IPage<JobModel> jobModelIPage = this.page(page, qw);
		return new RespPageModel<>(reqPageModel, jobModelIPage);
	}

}
