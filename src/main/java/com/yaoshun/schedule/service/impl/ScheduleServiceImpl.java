package com.yaoshun.schedule.service.impl;

import com.yaoshun.constant.TriggerStateEnum;
import com.yaoshun.schedule.job.PushJob;
import com.yaoshun.schedule.model.JobModel;
import com.yaoshun.schedule.service.JobModelService;
import com.yaoshun.schedule.service.ScheduleService;
import com.yaoshun.util.collection.MapUtils;
import org.quartz.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author fizz
 * @since 2019/7/23 18:02
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

	@Resource
	private Scheduler scheduler;

	@Resource
	private JobModelService jobModelService;

	public static JobKey getJobKey(JobModel jobModel){
		return JobKey.jobKey(jobModel.getJobName(), jobModel.getJobGroup());
	}

	public static TriggerKey getTriggerKey(JobModel jobModel){
		return TriggerKey.triggerKey(jobModel.getJobName(), jobModel.getJobGroup());
	}

	private JobDetail getJobDetail(JobModel jobModel) throws SchedulerException {
		return scheduler.getJobDetail(getJobKey(jobModel));
	}

	private Trigger getTrigger(JobModel jobModel) throws SchedulerException {
		return scheduler.getTrigger(getTriggerKey(jobModel));
	}

	/**
	 * 启动
	 */
	public void start(JobModel jobModel) throws IllegalAccessException, SchedulerException {
		String jobName = jobModel.getJobName();
		String jobGroup = jobModel.getJobGroup();
		if (scheduler.checkExists(getJobKey(jobModel)) || scheduler.checkExists(getTriggerKey(jobModel))) {
			return;
		}
		JobDetail jobDetail = JobBuilder.newJob(PushJob.class)
				.withIdentity(jobName, jobGroup)
				.setJobData(new JobDataMap(MapUtils.toMap(jobModel)))
				.withDescription(jobModel.getJobDesc())
				.build();
		CronTrigger cronTrigger = TriggerBuilder.newTrigger()
				.withIdentity(jobName, jobGroup)
				.withSchedule(CronScheduleBuilder.cronSchedule(jobModel.getCronExpression()))
				.build();
		scheduler.scheduleJob(jobDetail, cronTrigger);
		jobModelService.updateJobStatus(jobModel);
	}

	/**
	 * 暂停
	 */
	public void pauseJob(JobModel jobModel) throws SchedulerException {
		scheduler.pauseJob(getJobKey(jobModel));
		jobModelService.updateJobStatus(jobModel);
	}

	/**
	 * 恢复
	 */
	public void resumeJob(JobModel jobModel) throws SchedulerException {
		scheduler.resumeJob(getJobKey(jobModel));
		jobModelService.updateJobStatus(jobModel);
	}

	/**
	 * 运行一次
	 */
	public void triggerJob(JobModel jobModel) throws SchedulerException {
		scheduler.triggerJob(getJobKey(jobModel));
		jobModelService.updateJobStatus(jobModel);
	}

	/**
	 * 修改
	 */
	public void updateJobCron(JobModel jobModel) throws SchedulerException {
		TriggerKey triggerKey = TriggerKey.triggerKey(jobModel.getJobName(), jobModel.getJobGroup());
		CronTrigger cronTrigger = TriggerBuilder.newTrigger()
				.withIdentity(triggerKey)
				.withSchedule(CronScheduleBuilder.cronSchedule(jobModel.getCronExpression()))
				.build();
		scheduler.rescheduleJob(triggerKey, cronTrigger);
	}

	/**
	 * 删除
	 */
	public void deleteJob(JobModel jobModel) throws SchedulerException {
		if (Objects.isNull(jobModel) || StringUtils.isEmpty(jobModel.getJobName()) || StringUtils.isEmpty(jobModel.getJobGroup())) return;
		scheduler.deleteJob(getJobKey(jobModel));
		jobModelService.updateJobStatus(jobModel);
	}

	/**
	 * 获取trigger状态
	 */
	public String getTriggerState(JobModel jobModel) throws SchedulerException {
		return TriggerStateEnum.getValueByKey(
				scheduler.getTriggerState(getTriggerKey(jobModel)).name());
	}

}
