package com.yaoshun.exception;

/**
 * @author fizz
 * @since 2019/7/24 11:58
 */
public class EnumExcption extends RuntimeException {

	private static final long serialVersionUID = -8426100052737103784L;

	public EnumExcption(String message) {
		super(message);
	}
}
