package com.kh.reactrip.diary.model.service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kh.reactrip.diary.model.dao.DiaryMapper;
import com.kh.reactrip.diary.model.dto.DiaryCommentDTO;
import com.kh.reactrip.diary.model.dto.DiaryDTO;
import com.kh.reactrip.diary.model.dto.DiaryDetailDTO;
import com.kh.reactrip.diary.model.vo.DiaryComListVO;
import com.kh.reactrip.util.PageInfo;
import com.kh.reactrip.util.Pagenation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DiaryServiceImpl implements DiaryService {

	@Autowired
	private final DiaryMapper diaryMapper;
	private final Pagenation pagenation;
	
	
	// 전체 목록 조회
	@Override
	public Map<String, Object> findAllDiary(int page, int size) {
		
		// 페이지 처리 유효성 검사
		if(page < 1) {
			throw new InvalidParameterException("잘못된 페이지 요청입니다.");
		} 
		
		int offset = (page - 1) * size;

		List<DiaryDTO> diaryList = diaryMapper.findAllDiary(size, offset);
		
		int totalCount = diaryMapper.findDiaryCount();

		log.info("전제페이지 :  " + totalCount);
		
		Map<String, Object> map = new HashMap();
		
		map.put("diary", diaryList);
		map.put("totalCnt", totalCount);
		map.put("limitPage", size);
		map.put("currentPage", page);
		
		return map;
	}
 

	// 상세 조회
	@Override
	public DiaryDetailDTO findByDiaryNo(int diaryNo) {
		
		if(diaryNo <= 0) {
			throw new InvalidParameterException("잘못된 다이어리 번호입니다.");
		}
		
		DiaryDetailDTO ddDTO = diaryMapper.findByDiaryNo(diaryNo);
		
		log.info("상세조회 : {}", ddDTO);
		
		
		return diaryMapper.findByDiaryNo(diaryNo);
	}


	// 댓글 목록 조회
	@Override
	public List<DiaryComListVO> findByComments(int diaryNo, int page) {
		log.info("개시글 번호ㅣ: " + diaryNo);
		
		List<DiaryComListVO> listVo = new ArrayList<DiaryComListVO>(); 
		
		List<DiaryCommentDTO> comList = diaryMapper.findByComments(diaryNo);
		
		if(comList != null && comList.size() > 0 ) {
			for( DiaryCommentDTO items :  comList) {
				DiaryComListVO setItem = new DiaryComListVO();
				setItem.setCommentNo(items.getCommentNo()); // 댓글 key번호
				setItem.setCommentContent(items.getCommentContent()); //댓글내용
				setItem.setCreatedDate(items.getCreatedDate());  // 댓글작성일자
				setItem.setCommentWriteName(items.getMemberName()); // 댓글 작성자명
				listVo.add(setItem);
			}
		} 
		
		return listVo;
	}







}
