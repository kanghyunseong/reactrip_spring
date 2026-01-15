package com.kh.reactrip.diary.model.dto;


import java.sql.Clob;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DiaryDTO {

	private int diaryNo;			// 게시글 고유번호
	private String diaryTitle; 		// 게시글 제목
	private Clob diaryContent;	    // 게시글 내용
	private char diaryStatus; 		// 게시글 상태
	private Date createdDate; 		// 게시글 등록일
	private Date updatedDate; 		// 게시글 수정일
	private int count;				// 조회수
	private int memberNo;			// 회원 고유번호
	private int scheduleNo;			// 일정 번호
	private int travelNo;			// 여행지 번호
	
	private int totalCount;
	
}
