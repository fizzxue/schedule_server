package com.yaoshun.log.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Fizz
 * @since 2019/7/30 10:22
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"startTime", "endTime"})
public class DataPushLog implements Serializable {

    private static final long serialVersionUID = 5533529061407475769L;

    private Long id;

    private Long jobModelId;

    private String systemName;

    private String tableName;

    private String pushDateTime;

    private Long singlePushTotal;

    private Long pushTotal;

    private Float pushStatus;//1推送成功0推送失败

    private Long successTotal;

    private Long failureTotal;

    private String returnContent;

    @TableField(exist = false)
    private String startTime;

    @TableField(exist = false)
    private String endTime;

}
