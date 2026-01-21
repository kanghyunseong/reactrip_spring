package com.kh.reactrip.admin.notices.model.service;


import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.reactrip.admin.notices.model.dto.AdminNoticeDTO;
import com.kh.reactrip.admin.notices.model.mapper.AdminNoticeMapper;
import com.kh.reactrip.common.PageResponseDTO;
import com.kh.reactrip.file.service.FileService;
import com.kh.reactrip.util.PageInfo;
import com.kh.reactrip.util.Pagenation;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class AdminNoticeServiceImpl implements AdminNoticeService {

    private final Pagenation pagenation;
	
	private final AdminNoticeMapper adminNoticeMapper;
	private final FileService fileService;
	
	private static final int BOARD_LIMIT = 10;
	private static final int PAGE_LIMIT = 5;

	@Override
	public void insertNotice(AdminNoticeDTO adminNoticeDTO, MultipartFile file) {
		
		if(adminNoticeDTO.getNoticeTitle() == null || adminNoticeDTO.getNoticeTitle().isEmpty()) {
			throw new IllegalArgumentException("공지사항 제목은 필수입니다. ");
		}
		
		if(adminNoticeDTO.getNoticeContent() == null || adminNoticeDTO.getNoticeContent().isEmpty()) {
			throw new IllegalArgumentException("공지사항 내용은 필수 입니다.");
		}
		
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
	public PageResponseDTO<AdminNoticeDTO> findAllNotice(int page) {
		
		int totalCount = adminNoticeMapper.getTotalCount();
		
		
		PageInfo pi = pagenation.getPageInfo(page, page, page, page);
		RowBounds rowBounds = createRowBounds(pi);
		
		List<AdminNoticeDTO> notice = adminNoticeMapper.findAllNotice(rowBounds);
		
		return new PageResponseDTO<>(pi, notice);
	}
	
	private RowBounds createRowBounds(PageInfo pi) {
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		return new RowBounds(offset, pi.getBoardLimit());
	}
}
