package com.yaoshun.constant;

/**
 * @author fizz
 * @since 2019/7/23 16:15
 */
public enum ResponseEnum {
	SUCCESS("200", "操作成功"),
	FAILURE("500", "操作失败");

	private String code;

	private String message;

	ResponseEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
