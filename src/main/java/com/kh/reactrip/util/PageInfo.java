package com.kh.reactrip.util;

import lombok.AllArgsConstructor; //모든 필드를 매개변수로 하는 생성자
import lombok.Getter;
import lombok.NoArgsConstructor; //기본생성자 만드는 에노테이션
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PageInfo {

		private int listCount;
		private int currentPage;
		private int boardLimit;
		private int pageLimit;
		
		private int maxPage;
		private int startPage;
		private int endPage;
		
		
}
