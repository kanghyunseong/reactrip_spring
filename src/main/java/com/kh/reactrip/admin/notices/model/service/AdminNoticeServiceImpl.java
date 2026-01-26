package com.kh.reactrip.admin.notices.model.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.reactrip.admin.notices.model.dto.AdminNoticeDTO;
import com.kh.reactrip.admin.notices.model.mapper.AdminNoticeMapper;
import com.kh.reactrip.auth.model.vo.CustomUserDetails;
import com.kh.reactrip.common.PageResponseDTO;
import com.kh.reactrip.exception.NoticeNotFoundException;
import com.kh.reactrip.file.service.FileService;
import com.kh.reactrip.util.PageInfo;
import com.kh.reactrip.util.Pagenation;
import com.kh.reactrip.util.Validator;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class AdminNoticeServiceImpl implements AdminNoticeService {

    private final Validator validator;

    private final Pagenation pagenation;
	
	private final AdminNoticeMapper adminNoticeMapper;
	private final FileService fileService;
	

	@Override
	@Transactional
	public void insertNotice(AdminNoticeDTO adminNoticeDTO, MultipartFile file, CustomUserDetails user) {
		
		adminNoticeDTO.setMemberNo(adminNoticeDTO.getMemberNo());
		
		Validator.ValidateNoticeinsert(adminNoticeDTO);
		
		if (user != null && user.getMemberNo() != null) {
			adminNoticeDTO.setMemberNo(user.getMemberNo());
		}
		if (adminNoticeDTO.getMemberNo() == null) {
			throw new IllegalArgumentException("공지 등록을 위해 로그인 정보(memberNo)가 필요합니다.");
		}
		
		try {
			if(file != null && !file.isEmpty()) {
				String imgUrl = fileService.store(file);
				adminNoticeDTO.setImage(imgUrl);;
			}
			adminNoticeMapper.insertNotice(adminNoticeDTO);
		} catch (IOException e) {
	        log.error("공지사항 등록 중 S3 업로드 실패 : {}", e.getMessage());
	        throw new RuntimeException("이미지 서버 업로드에 실패하여 공지사항 등록이 취소되었습니다.", e);
	    } catch (Exception e) {
	        log.error("공지사항 중 예상치 못한 오류 발생 : {}", e.getMessage());
	        throw new RuntimeException("공지사항 처리 중 시스템 오류가 발생했습니다.", e);
	    }
	}

	@Override
	@Transactional
	public PageResponseDTO<AdminNoticeDTO> findAllNotice(int page) {
		
		int totalCount = adminNoticeMapper.getTotalCount();
		
		PageInfo pi = pagenation.getPageInfo(totalCount, page);
		
		List<AdminNoticeDTO> notice = adminNoticeMapper.findAllNotice(pagenation.createRowBounds(pi));
		
		return new PageResponseDTO<>(pi, notice);
	}

	@Override
	@Transactional
	public void updateNotice(Long noticeNo, MultipartFile file, AdminNoticeDTO adminNoticeDTO) {
		
		Validator.validateNo(noticeNo, "수정할 게시글 번호가 올바르지 않습니다.");
		
		AdminNoticeDTO origin = adminNoticeMapper.selectNoticeDetail(noticeNo);
		
		Validator.validateExist(origin.getNoticeNo(), "수정할 게시글을 찾을 수 없습니다.");
		
		String finalImgUrl = fileService.updateFile(file, origin.getImage());
		
		adminNoticeDTO.setNoticeNo(noticeNo);
		adminNoticeDTO.setImage(finalImgUrl);
		
		int result = adminNoticeMapper.updateNotice(adminNoticeDTO);
		
		Validator.validateResult(result, "공지사항 수정 실패");

	}
	
	@Override
	@Transactional
	public void deleteNotice(Long noticeNo) {
		
		Validator.validateNo(noticeNo, "삭제할 번호가 올바르지 않습니다. ");
		
		AdminNoticeDTO origin = adminNoticeMapper.selectNoticeDetail(noticeNo);
		
		if(origin == null ) {
			throw new RuntimeException("삭제할 게시글을 찾지 못함");
		}
		
		int result = adminNoticeMapper.deleteNotice(noticeNo);
		
		if(result > 0) {
			if(origin.getImage() != null) {
				fileService.delete(origin.getImage());
			}
		} else {
			throw new RuntimeException("삭제 처리 실패");
		}
	}

	@Override
	public PageResponseDTO<AdminNoticeDTO> findByNotice(String keyword, int page) {

		if(keyword == null || keyword.trim().isEmpty()) {
			return new PageResponseDTO<>(new PageInfo(), new ArrayList<>());
		}
		
		int totalCount = adminNoticeMapper.getSearchCount(keyword);
		
		// 검색 결과가 0건이면 예외를 던지지 말고 "빈 리스트"를 정상 응답으로 내려준다.
		if(totalCount == 0) {
			PageInfo pi = pagenation.getPageInfo(0, page);
			return new PageResponseDTO<>(pi, new ArrayList<>());
		}
 		
 		PageInfo pi = pagenation.getPageInfo(totalCount, page);
 		
 		List<AdminNoticeDTO> list = adminNoticeMapper.findByNotice(keyword, pagenation.createRowBounds(pi));
		
		return new PageResponseDTO<>(pi, list);
	}
}
