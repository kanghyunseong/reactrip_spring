package com.kh.reactrip.schedule.controller;

import org.apache.ibatis.annotations.Delete;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.reactrip.common.PageResponseDTO;
import com.kh.reactrip.common.ResponseData;
import com.kh.reactrip.schedule.model.dto.ScheduleListResponse;
import com.kh.reactrip.schedule.model.dto.ScheduleRequest;
import com.kh.reactrip.schedule.model.dto.ScheduleResponse;
import com.kh.reactrip.schedule.model.service.ScheduleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {
	
	private final ScheduleService scheduleService;
	
	/**
	 * @param userDetails
	 * @param request
	 * 스케줄 생성
	 * @return
	 */
	@PostMapping
	public ResponseEntity<ResponseData<Long>> createSchedule(
		@AuthenticationPrincipal UserDetails userDetails, 
		@Valid @RequestBody ScheduleRequest request) {
		String memberId = userDetails.getUsername();
		Long scheduleNo = scheduleService.createSchedule(memberId, request);
		return ResponseData.created(scheduleNo);
	}
	
	
	/**
	 * @param page
	 * 스케줄 목록 조회 (페이징)
	 * @return
	 */
	@GetMapping
	public ResponseEntity<ResponseData<ScheduleListResponse>> getScheduleList(
		@AuthenticationPrincipal UserDetails userDetails,
		@RequestParam(name = "page", defaultValue = "1") int page) {
		String memberId = userDetails.getUsername();
		ScheduleListResponse response = scheduleService.getMyScheduleList(memberId, page);
		return ResponseData.ok(response);
		
	}
	
	/**
	 * @param scheduleNo
	 * 스케줄 상세 조회
	 * @return
	 */
	@GetMapping("/{scheduleNo}")
	public ResponseEntity<ResponseData<ScheduleResponse>> getScheduleDetail(
		@AuthenticationPrincipal UserDetails userDetails,
		@PathVariable("scheduleNo") Long scheduleNo) {
		String memberId = userDetails.getUsername();
		ScheduleResponse response = scheduleService.getScheduleDetail(memberId, scheduleNo);
		return ResponseData.ok(response);
	}
	
	/**
	 * @param scheduleNo
	 * 스케줄 수정
	 * @return
	 */
	@PutMapping("/{scheduleNo}")
	public ResponseEntity<ResponseData<Void>> updateSchedule(
		@AuthenticationPrincipal UserDetails userDetails,
		@PathVariable("scheduleNo") Long scheduleNo,
		@Valid @RequestBody ScheduleRequest request) {
		String memberId = userDetails.getUsername();
		scheduleService.updateSchedule(memberId, scheduleNo, request);
		return ResponseData.ok(null, "스케줄이 수정되었습니다.");
	}
	
	@DeleteMapping("/{scheduleNo}")
	public ResponseEntity<ResponseData<Void>> deleteSchedule(
		@AuthenticationPrincipal UserDetails userDetails,
		@PathVariable("scheduleNo") Long scheduleNo) {
		String memberId = userDetails.getUsername();
		scheduleService.deleteSchedule(memberId, scheduleNo);
		return ResponseData.ok(null, "스케줄이 삭제되었습니다.");
	}
}
