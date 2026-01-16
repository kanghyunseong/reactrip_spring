package com.kh.reactrip.admin.notices.model.service;


import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.reactrip.admin.notices.model.dto.AdminNoticeDTO;
import com.kh.reactrip.admin.notices.model.mapper.AdminNoticeMapper;
import com.kh.reactrip.auth.model.vo.CustomUserDetails;
import com.kh.reactrip.common.PageResponseDTO;
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
	
	private static final int BOARD_LIMIT = 10;
	private static final int PAGE_LIMIT = 5;

	@Override
	@Transactional
	public void insertNotice(AdminNoticeDTO adminNoticeDTO, MultipartFile file, CustomUserDetails user) {
		
		adminNoticeDTO.setMemberNo(user.getMemberNo());
		
		Validator.ValidateNoticeinsert(adminNoticeDTO);
		
		
		try {
			if(file != null && !file.isEmpty()) {
				String imgUrl = fileService.store(file);
				adminNoticeDTO.setImage(imgUrl);;
			}
			int result = adminNoticeMapper.insertNotice(adminNoticeDTO);
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
		
		
		PageInfo pi = pagenation.getPageInfo(totalCount, page, BOARD_LIMIT, PAGE_LIMIT);
		RowBounds rowBounds = createRowBounds(pi);
		
		List<AdminNoticeDTO> notice = adminNoticeMapper.findAllNotice(rowBounds);
		
		return new PageResponseDTO<>(pi, notice);
	}
	
	private RowBounds createRowBounds(PageInfo pi) {
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		return new RowBounds(offset, pi.getBoardLimit());
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
}
