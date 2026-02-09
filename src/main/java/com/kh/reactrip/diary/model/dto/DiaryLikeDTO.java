package com.kh.reactrip.diary.model.dto;

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
public class DiaryLikeDTO {

	private int likeNo;
	private String createdDate;
	private int memberNo;
	private int diaryNo;  
	
}
