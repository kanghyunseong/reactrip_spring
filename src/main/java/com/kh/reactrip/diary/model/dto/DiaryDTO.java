package com.kh.reactrip.diary.model.dto;


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

	private int diaryNo;
	private String diaryTitle;
	private String diaryContent;
	private char diaryStatus;
	private Date createdDate;
	private Date updatedDate;
	private int count;
	private int memberNo;
	private int scheduleNo;
	private int travelNo;
	
}
