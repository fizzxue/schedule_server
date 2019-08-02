package com.yaoshun;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author fizz
 * @since 2019/7/23 14:22
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ScheduleServerApplicationTest {

	@Resource
	private Scheduler scheduler;

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
			System.out.println("job execute end!");
		}
	}

	@Test
	public void test1() {
		try {
			JobDetail job = newJob(ScheduleServerApplicationTest.HelloJob.class)
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

			Thread.sleep(6000);
		} catch (SchedulerException | InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("test1");
	}

}