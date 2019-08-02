package com.yaoshun;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import javax.annotation.Resource;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author fizz
 * @since 2019/7/23 15:23
 */
//@Component
@Slf4j
public class SchedulerRunner implements ApplicationRunner {

	@Resource
	private Scheduler scheduler;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("enter SchedulerRunner.run() ");
		try {
			JobDetail job = newJob(SchedulerRunner.HelloJob.class)
					.withIdentity("job1", "group1")
					.build();

			// Trigger the job to run now, and then repeat every 40 seconds
			Trigger trigger = newTrigger()
					.withIdentity("trigger1", "group1")
					.startNow()
					.withSchedule(simpleSchedule()
							.withIntervalInSeconds(1)
							.repeatForever())
					.build();

			// Tell quartz to schedule the job using our trigger
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		log.info("exit SchedulerRunner.run() ");
	}

	static class HelloJob implements Job {
		@Override
		public void execute(JobExecutionContext ctx) throws JobExecutionException {
			JobDetail jobDetail = ctx.getJobDetail();
			String fireInstanceId = ctx.getFireInstanceId();
			Job jobInstance = ctx.getJobInstance();
			JobDataMap mergedJobDataMap = ctx.getMergedJobDataMap();
			JobDataMap jobDataMap = jobDetail.getJobDataMap();
			Trigger trigger = ctx.getTrigger();
			JobDataMap jobDataMap1 = trigger.getJobDataMap();
			log.info("ID = {}, Name = {}, job execute end!", Thread.currentThread().getId(), Thread.currentThread().getName());
		}
	}
}
