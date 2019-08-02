package com.yaoshun.schedule.mapper;

import com.alibaba.fastjson.JSON;
import com.yaoshun.log.mapper.DataPushLogMapper;
import com.yaoshun.schedule.model.JobModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Fizz
 * @since 2019/7/31 11:01
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class JobModelMapperTest {

    @Resource
    private JobModelMapper jobModelMapper;

    @Resource
    private DataPushLogMapper dataPushLogMapper;

    @Test
    public void pageListAssociation() {
        List<JobModel> jobModels = jobModelMapper.pageListAssociation();
        System.out.println(JSON.toJSONString(jobModels));
    }
}