package com.yaoshun.util.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yaoshun.constant.ResponseEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 新封装的消息返回类
 */
@Data
@Accessors(chain = true)
public class ResponseMessage<T> {

    @JsonProperty("code")
    private String code;

    @JsonProperty("msg")
    private String message;

    @JsonProperty("body")
    private T data;

    public ResponseMessage<T> set(ResponseEnum responseEnum){
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
        return this;
    }
}
