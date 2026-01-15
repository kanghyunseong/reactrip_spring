package com.kh.reactrip.admin.notices.model.dto;


import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

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
	
}
