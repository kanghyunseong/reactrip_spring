package com.kh.reactrip.admin.notices.model.vo;

import java.time.LocalDateTime;
import lombok.Value;

@Value
public class AdminNoticeVO {

	Long noticeNo;
	String memberName; 
	String noticeTitle;
	String noticeContent;
	LocalDateTime createDate;
	String noticeStatus;
	int count;
	LocalDateTime updateDate;
	String image;
	
}
