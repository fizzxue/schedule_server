package com.yaoshun.schedule.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yaoshun.log.model.DataPushLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author fizz
 * @since 2019/7/23 15:42
 */
@TableName("job_model")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class JobModel {

	private Long id;

	private String jobName;

	private String jobGroup;

	private String cronExpression;

	private String interfaceAddr;

	private String interfaceParam;

	private String jobDesc;

	private String jobStatus;

	@TableField(exist = false)
	private List<DataPushLog> dataPushLogs;

	public JobModel(Long id, String jobName, String jobGroup, String cronExpression, String interfaceAddr,
					String interfaceParam, String jobDesc) {
		this.setId(id).setJobName(jobName).setJobGroup(jobGroup).setCronExpression(cronExpression)
				.setInterfaceAddr(interfaceAddr).setInterfaceParam(interfaceParam).setJobDesc(jobDesc);
	}
}
