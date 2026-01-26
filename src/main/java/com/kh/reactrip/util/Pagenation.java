package com.kh.reactrip.util;

<<<<<<< HEAD
import org.apache.ibatis.session.RowBounds;
=======

import org.apache.ibatis.session.RowBounds;

>>>>>>> 5d3ff508e0166528a5bd964850cf83acd006253c
import org.springframework.stereotype.Component;

@Component //클래스를 빈으로 등록하는것이다. 즉 spring제어하게 만드는 것객체를 생성안해도됨 
public class Pagenation {

	private static final int DEFAULT_BOARD_LIMIT = 10;
    private static final int DEFAULT_PAGE_LIMIT = 5;


	public PageInfo getPageInfo(int listCount
							  , int currentPage
							  , int boardLimit
							  , int pageLimit) {
		int maxPage = (int)Math.ceil((double)listCount/ boardLimit);
		int startPage =(currentPage-1)/pageLimit*pageLimit +1;
		int endPage =startPage + pageLimit -1 ;
		if(endPage>maxPage) endPage = maxPage;
		return new PageInfo(listCount,currentPage,boardLimit,pageLimit,maxPage,
				startPage,endPage);
	}
	
	public RowBounds createRowBounds(PageInfo pi) {
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
        return new RowBounds(offset, pi.getBoardLimit());
	}
	
	public PageInfo getPageInfo(int listCount, int currentPage) {
        return getPageInfo(listCount, currentPage, DEFAULT_BOARD_LIMIT, DEFAULT_PAGE_LIMIT);
    }
		
}
