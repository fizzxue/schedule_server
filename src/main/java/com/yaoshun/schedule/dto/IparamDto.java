package com.yaoshun.schedule.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Fizz
 * @since 2019/7/30 15:36
 */
@Data
public class IparamDto implements Serializable {

    private static final long serialVersionUID = 7680880976202160370L;

    private String tableId;

    private String catalogId;

    private String tableName;

    private String optype;

    private String ignoreField;

    private Long singlePushTotal;//单次推送总量

    private Long pushTotal;//历史推送总量
}
