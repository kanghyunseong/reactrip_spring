package com.kh.reactrip.diary.model.dto;

import java.util.List;

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
public class DiaryImageDTO {
	
	private int imageNo;
	private String imageUrl;
	private String originalName;
	private int sortOrder;  
	private String createDate;
	private int diaryNo;
	private List<String> imageUrls;
	

}
