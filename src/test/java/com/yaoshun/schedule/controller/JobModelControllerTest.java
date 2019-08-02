package com.yaoshun.schedule.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaoshun.schedule.mapper.JobModelMapper;
import com.yaoshun.schedule.model.JobModel;
import com.yaoshun.schedule.service.JobModelService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author fizz
 * @since 2019/7/23 16:30
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class JobModelControllerTest {

	@Resource
	private JobModelController jobModelController;

	@Resource
	private JobModelService JobModelService;

	@Resource
	private JobModelMapper jobModelMapper;

	@Test
	public void add() {
		JobModel jobModel = new JobModel(1L,"name1","group1"
				,"0/1 * * * * ?"
				, "http://192.168.199.143:8585/pushtools/pushOnce"
				, "{\"tableId\":\"2\",\"catalogId\":\"WEB1452\",\"tableName\":\"XXZX_LIXIANWEIYIJL1\",\"optype\":\"add\"}"
				, "a interesting job");
		System.out.println(jobModelController.add(jobModel));
	}

	@Test
	public void delete() {
		JobModel jobModel = new JobModel(1L,"name1","group1"
				,"0/1 * * * * ?"
				, "http://192.168.199.143:8585/pushtools/pushOnce"
				, "{\"tableId\":\"2\",\"catalogId\":\"WEB1452\",\"tableName\":\"XXZX_LIXIANWEIYIJL1\",\"optype\":\"add\"}"
				, "a interesting job");
		System.out.println(jobModelController.delete("1"));
	}

	@Test
	public void update() {
		JobModel jobModel = new JobModel(2L,"name2","group2"
				,"0/59 * * * * ?"
				, "http://192.168.199.143:8585/pushtools/pushOnce"
				, "{\"tableId\":\"2\",\"catalogId\":\"WEB1452\",\"tableName\":\"XXZX_LIXIANWEIYIJL1\",\"optype\":\"add\"}"
				, "a interesting job");
		System.out.println(jobModelController.update(jobModel));
	}

	@Test
	public void list() {
	}
	@After
	public void after1() throws InterruptedException {
		Thread.sleep(Integer.MAX_VALUE);
	}

	@Test
	public void start() {
		JobModel jobModel = new JobModel(2L,"name8","group8"
				,"0/10 * * * * ?"
				, "http://192.168.199.143:8585/pushtools/pushOnce"
				, "{\"tableId\":\"2\",\"catalogId\":\"WEB1452\",\"tableName\":\"XXZX_LIXIANWEIYIJL1\"," +
				"\"optype\":\"add\",\"singlePushTotal\":\"2\"}"
				, "a interesting job");
		System.out.println(jobModelController.start(jobModel));
	}

	@Test
	public void pauseJob() {
		JobModel jobModel = new JobModel(2L,"name2","group2"
				,"0/1 * * * * ?"
				, "http://192.168.199.143:8585/pushtools/pushOnce"
				, "{\"tableId\":\"2\",\"catalogId\":\"WEB1452\",\"tableName\":\"XXZX_LIXIANWEIYIJL1\",\"optype\":\"add\"}"
				, "a interesting job");
		System.out.println(jobModelController.pauseJob(jobModel));
	}

	@Test
	public void resumeJob() {
		JobModel jobModel = new JobModel(2L,"name2","group2"
				,"0/1 * * * * ?"
				, "http://192.168.199.143:8585/pushtools/pushOnce"
				, "{\"tableId\":\"2\",\"catalogId\":\"WEB1452\",\"tableName\":\"XXZX_LIXIANWEIYIJL1\",\"optype\":\"add\"}"
				, "a interesting job");
		System.out.println(jobModelController.resumeJob(jobModel));
	}

	@Test
	public void triggerJob() {
		JobModel jobModel = new JobModel(2L,"name2","group2"
				,"0/1 * * * * ?"
				, "http://192.168.199.143:8585/pushtools/pushOnce"
				, "{\"tableId\":\"2\",\"catalogId\":\"WEB1452\",\"tableName\":\"XXZX_LIXIANWEIYIJL1\",\"optype\":\"add\"}"
				, "a interesting job");
		System.out.println(jobModelController.triggerJob(jobModel));
	}

	@Test
	public void page() {
		JobModel jobModel = new JobModel();
		Page<JobModel> page = new Page<>(1, 5);
		QueryWrapper<JobModel> qw = new QueryWrapper<>(jobModel);
		qw.orderByAsc("id");
		IPage<JobModel> jobModelIPage = jobModelMapper.selectPage(page, qw);
		System.out.println(jobModelIPage);
	}

	@Test
	public void listPage() {
		JobModel jobModel = new JobModel();
//		jobModel.setId(1L);
	}
}