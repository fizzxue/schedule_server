package com.yaoshun.util.page;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author fizz
 * @since 2019/7/25 10:52
 * 分页请求对象封装
 */
@Data
@AllArgsConstructor
public class ReqPageModel {

	/**
	 * 当前第几页
	 */
	private long curPage;

	/**
	 * 每页条数
	 */
	private long pageSize;

	private String body;
}
