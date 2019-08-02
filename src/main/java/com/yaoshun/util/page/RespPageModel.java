package com.yaoshun.util.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author fizz
 * @since 2019/7/25 10:59
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class RespPageModel<T> {

	/**
	 * 分页数据
	 */
	@JsonProperty("rows")
	private List<T> pageDatas;

	/**
	 * 当前第几页
	 */
	@JsonProperty("curPage")
	private long curPage;

	/**
	 * 每页条数
	 */
	@JsonProperty("page")
	private long pageSize;

	/**
	 * 共多少页
	 */
	@JsonProperty("total")
	private long countPages;

	/**
	 * 共多少条
	 */
	@JsonProperty("records")
	private long countDatas;

	public RespPageModel(ReqPageModel reqPageModel, IPage<T> page) {
		this.setPageDatas(page.getRecords()).setCurPage(reqPageModel.getCurPage()).setPageSize(reqPageModel.getPageSize())
				.setCountPages(page.getPages()).setCountDatas(page.getTotal());
	}
}
