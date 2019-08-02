package com.yaoshun.log.service.impl;

import com.yaoshun.log.model.DataPushLog;
import com.yaoshun.log.service.IDataPushLogService;
import com.yaoshun.util.page.ReqPageModel;
import com.yaoshun.util.page.RespPageModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @author Fizz
 * @since 2019/8/1 9:20
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DataPushLogServiceImplTest {

    @Resource
    private IDataPushLogService iDataPushLogService;

    @Test
    public void listPage() {
        ReqPageModel reqPageModel = new ReqPageModel(1, 10, "{\"jobModelId\":\"1\",\"startTime\":\"2019-07-31 18:07:43\",\"endTime\":\"2019-08-01 09:05:35\"}");
        RespPageModel<DataPushLog> dataPushLogRespPageModel = iDataPushLogService.listPage(reqPageModel);
        System.out.println(dataPushLogRespPageModel);
    }
}