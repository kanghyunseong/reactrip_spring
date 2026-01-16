package com.kh.reactrip.util;

import org.springframework.stereotype.Component;

import com.kh.reactrip.admin.notices.model.dto.AdminNoticeDTO;
import com.kh.reactrip.exception.PageNotFoundException;

@Component
public class Validator {
	

	private Validator() {}
	
	public static void validateNo(Long number, String message) {
		if(number <= 0) {
			throw new PageNotFoundException(message);
		}
	}
	
	public static void ValidateNoticeinsert(AdminNoticeDTO adminNoticeDTO) {
		
		if(adminNoticeDTO.getNoticeTitle() == null || adminNoticeDTO.getNoticeTitle().trim().isEmpty()) {
			throw new IllegalArgumentException("공지사항 제목은 필수입니다. ");
		}
		
		if(adminNoticeDTO.getNoticeContent() == null || adminNoticeDTO.getNoticeContent().trim().isEmpty()) {
			throw new IllegalArgumentException("공지사항 내용은 필수 입니다.");
		}
		
	}
	
	public static void validateExist(Object entity, String message) {
		if(entity == null) {
			throw new RuntimeException(message);
		}
	}
	
	public static void validateResult(int number, String message) {
		if(number <= 0) {
			throw new RuntimeException(message);
			
		}
	}
}
