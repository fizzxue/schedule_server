package com.yaoshun.schedule.service;

import com.yaoshun.schedule.model.JobModel;
import org.quartz.SchedulerException;

/**
 * @author fizz
 * @since 2019/7/23 18:02
 */
public interface ScheduleService {

	/**
	 * 启动
	 */
	void start(JobModel jobModel) throws IllegalAccessException, SchedulerException;

	/**
	 * 暂停
	 */
	void pauseJob(JobModel jobModel) throws SchedulerException;

	/**
	 * 恢复
	 */
	void resumeJob(JobModel jobModel) throws SchedulerException;

	/**
	 * 运行一次
	 */
	void triggerJob(JobModel jobModel) throws SchedulerException;

	/**
	 * 修改
	 */
	void updateJobCron(JobModel jobModel) throws SchedulerException;

	/**
	 * 删除
	 */
	void deleteJob(JobModel jobModel) throws SchedulerException;

	/**
	 * 获取trigger状态
	 */
	String getTriggerState(JobModel jobModel) throws SchedulerException;
}
