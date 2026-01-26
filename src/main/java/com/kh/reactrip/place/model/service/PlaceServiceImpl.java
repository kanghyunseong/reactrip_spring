package com.kh.reactrip.place.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.kh.reactrip.exception.PlaceNotFoundException;
import com.kh.reactrip.place.model.dao.PlaceMapper;
import com.kh.reactrip.place.model.dto.PlaceDTO;
import com.kh.reactrip.util.PageInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Primary
public class PlaceServiceImpl implements PlaceService {
	
	private final PlaceMapper placeMapper;
	
	// RowBounds용 상수
	private static final Integer OFFSET = 10;
	
	public List<PlaceDTO> findAllPlace(String keyword, Long themeNo, Long regionNo, Integer page, Integer size, String sort) {
		
		PageInfo pi = new PageInfo();
		pi.setCurrentPage(page);
		pi.setBoardLimit(size);
		
		RowBounds rb = getRowBounds(pi);
		
		Map<String, Object> query = new HashMap();
		query.put("keyword", keyword);
		query.put("themeNo", themeNo);
		query.put("regionNo", regionNo);
		query.put("sort", sort);
		
		List<PlaceDTO> places = placeMapper.findAllPlace(query, rb);
		
		if(places == null) {
			throw new PlaceNotFoundException("조회된 여행지가 없습니다.");
		}
		
		return places;
		
	}
	
	// RowBounds
	private RowBounds getRowBounds(PageInfo pi) {
		
		RowBounds rb = new RowBounds(OFFSET * pi.getCurrentPage(), pi.getBoardLimit());
		return rb;
		
	}
	
	public PlaceDTO findByTravelNo(Long travelNo) {
		
		PlaceDTO place = placeMapper.findByTravelNo(travelNo);
		
		if(place == null) {
			throw new PlaceNotFoundException("조회된 여행지가 없습니다.");
		}
		
		return place;
		
	}

}
