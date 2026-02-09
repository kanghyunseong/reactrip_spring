package com.kh.reactrip.diary.model.vo;

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
public class DiaryComListVO{

	
	private int commentNo;
	private char commentStatus;
	private String commentContent;
	private String createdDate;
	private String updatedDate;
	private int diaryNo;
	private int memberNo;
	private String commentWriteName; //댓글작성자명
}
  