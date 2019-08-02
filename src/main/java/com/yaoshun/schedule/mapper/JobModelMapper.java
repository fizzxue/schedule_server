package com.yaoshun.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaoshun.schedule.model.JobModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @author fizz
 * @since 2019/7/23 15:49
 */
@Mapper
public interface JobModelMapper extends BaseMapper<JobModel> {

    @Select("SELECT * FROM job_model")
    @Results({
            @Result(column = "id", property = "dataPushLogs",
                    many = @Many(select = "com.yaoshun.log.mapper.DataPushLogMapper.listByJobModelId",
                    fetchType = FetchType.LAZY))
    })
    List<JobModel> pageListAssociation();

}
