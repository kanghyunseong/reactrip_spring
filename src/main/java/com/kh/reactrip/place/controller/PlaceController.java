package com.kh.reactrip.place.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.reactrip.place.model.dto.PlaceDTO;
import com.kh.reactrip.place.model.service.PlaceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j // 로그 확인용
@RestController
@Validated // 여행지 목록 전체 조회 시 전달값 검증
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {
	
	private final PlaceService placeService;
		
	@GetMapping
	public ResponseEntity<List<PlaceDTO>> findAllPlace(
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String theme,
			@RequestParam(required = false) String region,
			@RequestParam(defaultValue="0", required = false) Integer pageNo,
			@RequestParam(defaultValue="10", required = false) Integer size,
			@RequestParam(required = false) String sort
			) {
		
		
		
	}

}
