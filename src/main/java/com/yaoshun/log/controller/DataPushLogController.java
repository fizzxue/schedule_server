package com.yaoshun.test.controller;


import com.yaoshun.constant.ResponseEnum;
import com.yaoshun.log.model.DataPushLog;
import com.yaoshun.log.service.IDataPushLogService;
import com.yaoshun.util.page.ReqPageModel;
import com.yaoshun.util.page.RespPageModel;
import com.yaoshun.util.response.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author fizz
 * @since 2019-07-30
 */
@RestController
@RequestMapping("/pushDataLog")
@Slf4j
public class DataPushLogController {

    @Resource
    private IDataPushLogService iDataPushLogService;

    @GetMapping("/listPage")
    @ResponseBody
    public ResponseMessage listPage(ReqPageModel reqPageModel){
        ResponseMessage<RespPageModel> rm = new ResponseMessage<>();
        try {
            RespPageModel<DataPushLog> respPageModel = iDataPushLogService.listPage(reqPageModel);
            return rm.set(ResponseEnum.SUCCESS).setData(respPageModel);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return rm.set(ResponseEnum.FAILURE).setMessage(e.getMessage());
        }
    }

}

