package com.kh.reactrip.admin.notices.model.dto;


import java.time.LocalDateTime;

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
public class AdminNoticeDTO {

	private Long noticeNo;
	private Long memberNo;
	private String noticeTitle;
	private String noticeContent;
	private String image;
	private String noticeStatus;
	private Integer count;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	private String memberName;
	
}
