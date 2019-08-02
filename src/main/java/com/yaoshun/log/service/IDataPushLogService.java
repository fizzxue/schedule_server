package com.yaoshun.log.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yaoshun.log.model.DataPushLog;
import com.yaoshun.util.page.ReqPageModel;
import com.yaoshun.util.page.RespPageModel;

/**
 * @author fizz
 * @since 2019-07-30
 */
public interface IDataPushLogService extends IService<DataPushLog> {

    RespPageModel<DataPushLog> listPage(ReqPageModel reqPageModel);
}
