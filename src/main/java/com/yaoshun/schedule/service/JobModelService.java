package com.yaoshun.schedule.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaoshun.schedule.model.JobModel;
import com.yaoshun.util.page.ReqPageModel;
import com.yaoshun.util.page.RespPageModel;
import org.quartz.SchedulerException;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author fizz
 * @since 2019/7/23 15:40
 */
public interface JobModelService extends IService<JobModel> {

	boolean add(JobModel jobModel) throws SchedulerException;

	boolean deleteByIds(Collection<? extends Serializable> idList);

	boolean updateByIdSelf(JobModel jobModel) throws SchedulerException;

	/**
	 * 更新任务状态
	 */
	void updateJobStatus(JobModel jobModel) throws SchedulerException;

	RespPageModel<JobModel> listPage(ReqPageModel reqPageModel);
}
