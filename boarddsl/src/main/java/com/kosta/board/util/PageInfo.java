package com.kosta.board.util;

import lombok.Setter;
import lombok.Builder;
import lombok.Getter;

@Getter
@Setter
@Builder
public class PageInfo {
	private Integer allPage;
	private Integer curPage;
	private Integer startPage;
	private Integer endPage;
}
