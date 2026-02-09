package com.kh.reactrip.admin.community.diary.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDiaryImageVO {
	private Long imageNo;
	private String imageUrl;
	private String originalName;
	private int sortOrder;
	private Long diaryNo;
}
