package com.kh.reactrip.schedule.model.service;

import com.kh.reactrip.schedule.model.dto.ScheduleListResponse;
import com.kh.reactrip.schedule.model.dto.ScheduleRequest;
import com.kh.reactrip.schedule.model.dto.ScheduleResponse;

public interface ScheduleService {
	
	// 스케줄 생성
	Long createSchedule(String memberId, ScheduleRequest request);

	// 스케줄 목록 조회 (페이징)
	ScheduleListResponse getMyScheduleList(String memberId, int currentPage);

	// 스케줄 상세 조회
	ScheduleResponse getScheduleDetail(String memberId, Long scheduleNo);
	
	// 스케줄 수정
	void updateSchedule(String memberId, Long scheduleNo, ScheduleRequest request);

	// 스케줄 삭제
	void deleteSchedule(String memberId, Long scheduleNo);
}
