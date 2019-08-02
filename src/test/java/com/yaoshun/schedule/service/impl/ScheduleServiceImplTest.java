package com.yaoshun.schedule.service.impl;

import com.yaoshun.schedule.model.JobModel;
import com.yaoshun.schedule.service.ScheduleService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author fizz
 * @since 2019/7/24 12:01
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ScheduleServiceImplTest {

	@Resource
	private ScheduleService scheduleService;

	private JobModel jobModel;

	@Resource
	private Scheduler scheduler;

	@Before
	public void createModel(){
		jobModel = Optional.ofNullable(jobModel).orElseGet(() -> new JobModel(1L,"name6","group6"
				,"0/1 * * * * ?"
		, "http://192.168.199.143:8585/pushtools/pushOnce"
				, "{\"tableId\":\"2\",\"catalogId\":\"WEB1452\",\"tableName\":\"XXZX_LIXIANWEIYIJL1\",\"optype\":\"add\"}"
				, "a interesting job"));
	}

	@After
	public void active() throws InterruptedException {
		Thread.sleep(1000000);
	}

	@Test
	public void start() throws IllegalAccessException, InterruptedException, SchedulerException {
		scheduleService.start(jobModel);
	}

	@Test
	public void pauseJob() throws SchedulerException {
		scheduleService.pauseJob(jobModel);
	}

	@Test
	public void resumeJob() throws SchedulerException {
		scheduleService.resumeJob(jobModel);
	}

	@Test
	public void runOnce() throws SchedulerException, InterruptedException {
		scheduleService.triggerJob(jobModel);
		Thread.sleep(10*1000);
	}

	@Test
	public void update() throws SchedulerException, IllegalAccessException, InterruptedException {
		Thread.sleep(10*1000);
		scheduleService.updateJobCron(jobModel);
	}

	@Test
	public void delete() throws SchedulerException {
		scheduleService.deleteJob(jobModel);
	}

	@Test
	public void getTriggerState() throws SchedulerException {
		Assert.assertNotNull(scheduleService.getTriggerState(jobModel));
	}


}