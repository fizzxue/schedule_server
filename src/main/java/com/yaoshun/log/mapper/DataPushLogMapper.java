package com.yaoshun.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaoshun.log.model.DataPushLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author fizz
 * @since 2019-07-30
 */
@Mapper
public interface DataPushLogMapper extends BaseMapper<DataPushLog> {

    @Select("select * from data_push_log where job_model_id = #{jobModelId}")
    public List<DataPushLog> listByJobModelId(Long jobModelId);

}
