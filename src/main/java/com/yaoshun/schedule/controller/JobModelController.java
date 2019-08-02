package com.yaoshun.schedule.controller;

import com.yaoshun.constant.ResponseEnum;
import com.yaoshun.schedule.model.JobModel;
import com.yaoshun.schedule.service.JobModelService;
import com.yaoshun.schedule.service.ScheduleService;
import com.yaoshun.util.page.ReqPageModel;
import com.yaoshun.util.page.RespPageModel;
import com.yaoshun.util.response.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author fizz
 * @since 2019/7/23 15:58
 */
@Controller
@RequestMapping("/schedule")
@Slf4j
public class JobModelController {

	@Resource
	private JobModelService jobModelService;

	@Resource
	private ScheduleService scheduleService;

	@PostMapping("/add")
	@ResponseBody
	public ResponseMessage add(@RequestBody JobModel jobModel){
		ResponseMessage rm = new ResponseMessage<>();
		rm.set(ResponseEnum.FAILURE);
		try {
			return jobModelService.add(jobModel)?rm.set(ResponseEnum.SUCCESS):
					rm;
		} catch (Exception e) {
		    log.error(e.getMessage(), e);
			return rm.set(ResponseEnum.FAILURE).setMessage(e.getMessage());
		}
	}

	@DeleteMapping("/del/{ids}")
	@ResponseBody
	public ResponseMessage delete(@PathVariable String ids){
		ResponseMessage rm = new ResponseMessage<>();
		rm.set(ResponseEnum.FAILURE);
		try {
			return jobModelService.deleteByIds(Arrays.asList(ids.split(",")))?rm.set(ResponseEnum.SUCCESS):
					rm;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return rm.set(ResponseEnum.FAILURE).setMessage(e.getMessage());
		}
	}

	/**
	 * 只支持更新cron表达式
	 */
	@PutMapping("/update")
	@ResponseBody
	public ResponseMessage update(@RequestBody JobModel jobModel){
		ResponseMessage<List> rm = new ResponseMessage<>();
		rm.set(ResponseEnum.FAILURE);
		try {
			return jobModelService.updateByIdSelf(jobModel)?rm.set(ResponseEnum.SUCCESS):
					rm;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return rm.set(ResponseEnum.FAILURE).setMessage(e.getMessage());
		}
	}

	@GetMapping("/list")
	@ResponseBody
	public ResponseMessage list(){
		ResponseMessage<List> rm = new ResponseMessage<>();
		List<JobModel> list = jobModelService.list();
		try {
			return rm.set(ResponseEnum.SUCCESS).setData(list);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return rm.set(ResponseEnum.FAILURE).setMessage(e.getMessage());
		}
	}

	@GetMapping("/listPage")
	@ResponseBody
	public ResponseMessage listPage(ReqPageModel reqPageModel){
		ResponseMessage<RespPageModel> rm = new ResponseMessage<>();
		try {
			RespPageModel<JobModel> respPageModel = jobModelService.listPage(reqPageModel);
			return rm.set(ResponseEnum.SUCCESS).setData(respPageModel);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return rm.set(ResponseEnum.FAILURE).setMessage(e.getMessage());
		}
	}

	@GetMapping("/{id}")
	@ResponseBody
	public ResponseMessage getById(@PathVariable Long id){
		ResponseMessage<JobModel> rm = new ResponseMessage<>();
		rm.set(ResponseEnum.FAILURE);
		try {
			return rm.set(ResponseEnum.SUCCESS).setData(jobModelService.getById(id));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return rm.set(ResponseEnum.FAILURE).setMessage(e.getMessage());
		}
	}

	@PutMapping("/start")
	@ResponseBody
	public ResponseMessage start(@RequestBody JobModel jobModel){
		ResponseMessage<List> rm = new ResponseMessage<>();
		try {
			scheduleService.start(jobModel);
			return rm.set(ResponseEnum.SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return rm.set(ResponseEnum.FAILURE).setMessage(e.getMessage());
		}
	}

	@PutMapping("/pauseJob")
	@ResponseBody
	public ResponseMessage pauseJob(@RequestBody JobModel jobModel){
		ResponseMessage<List> rm = new ResponseMessage<>();
		try {
			scheduleService.pauseJob(jobModel);
			return rm.set(ResponseEnum.SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return rm.set(ResponseEnum.FAILURE).setMessage(e.getMessage());
		}
	}

	@PutMapping("/resumeJob")
	@ResponseBody
	public ResponseMessage resumeJob(@RequestBody JobModel jobModel){
		ResponseMessage<List> rm = new ResponseMessage<>();
		try {
			scheduleService.resumeJob(jobModel);
			return rm.set(ResponseEnum.SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return rm.set(ResponseEnum.FAILURE).setMessage(e.getMessage());
		}
	}

	@PutMapping("/triggerJob")
	@ResponseBody
	public ResponseMessage triggerJob(@RequestBody JobModel jobModel){
		ResponseMessage<List> rm = new ResponseMessage<>();
		try {
			scheduleService.triggerJob(jobModel);
			return rm.set(ResponseEnum.SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return rm.set(ResponseEnum.FAILURE).setMessage(e.getMessage());
		}
	}

}
