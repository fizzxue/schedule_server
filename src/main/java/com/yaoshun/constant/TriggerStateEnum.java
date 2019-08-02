package com.yaoshun.constant;

import com.yaoshun.exception.EnumExcption;

/**
 * @author fizz
 * @since 2019/7/24 11:44
 * NONE, NORMAL, PAUSED, COMPLETE, ERROR, BLOCKED
 */
public enum TriggerStateEnum {
	NONE("NONE", "不存在"),
	NORMAL("NORMAL", "正常"),
	PAUSED("PAUSED", "暂停"),
	COMPLETE("COMPLETE", "完成"),
	ERROR("ERROR", "出错"),
	BLOCKED("BLOCKED", "阻塞");

	private final String key;

	private final String value;

	TriggerStateEnum(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public static String getKeyByValue(String value) throws EnumExcption {
		for (TriggerStateEnum triggerStateEnum : TriggerStateEnum.values()) {
			if (triggerStateEnum.getValue().equals(value)) {
				return triggerStateEnum.getKey();
			}
		}
		throw new EnumExcption("could not find");
	}

	public static String getValueByKey(String key) throws EnumExcption {
		for (TriggerStateEnum triggerStateEnum : TriggerStateEnum.values()) {
			if (triggerStateEnum.getKey().equals(key)) {
				return triggerStateEnum.getValue();
			}
		}
		throw new EnumExcption("could not find");
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
}
