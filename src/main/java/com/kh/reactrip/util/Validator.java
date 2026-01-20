package com.kh.reactrip.util;

import org.springframework.stereotype.Component;

import com.kh.reactrip.admin.notices.model.dto.AdminNoticeDTO;
import com.kh.reactrip.admin.travel.model.dto.AdminTravelDTO;
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

	public static void validatePage(int page, int maxPage) {
	    // 데이터가 하나도 없는 경우(maxPage 0) 1페이지 요청은 허용하거나, 
	    // 혹은 데이터가 있을 때만 엄격하게 검사하도록 설정
	    if (maxPage > 0 && (page <= 0 || page > maxPage)) {
	        throw new PageNotFoundException("존재하지 않는 페이지입니당.");
	    }
	    
	    // 페이지 번호 자체가 음수인 경우 방지
	    if (page <= 0) {
	        throw new PageNotFoundException("페이지 번호는 1보다 작을 수 없습니당.");
	    }
	}
	
public static void ValidateTravelInsert(AdminTravelDTO adminTravelDTO) {
		
		if(adminTravelDTO.getTravelName() == null || adminTravelDTO.getTravelName().trim().isEmpty()) {
			throw new IllegalArgumentException("여행지 제목은 필수입니다. ");
		}
		
		if(adminTravelDTO.getTravelContent() == null || adminTravelDTO.getTravelContent().trim().isEmpty()) {
			throw new IllegalArgumentException("여행지 내용은 필수 입니다.");
		}
		
	}
}
