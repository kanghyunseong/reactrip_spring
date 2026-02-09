package com.kh.reactrip.schedule.model.service;


import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.session.RowBounds;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.kh.reactrip.common.PageResponseDTO;
import com.kh.reactrip.member.model.dao.MemberMapper;
import com.kh.reactrip.member.model.vo.AuthMember;
import com.kh.reactrip.schedule.model.dao.ScheduleMapper;
import com.kh.reactrip.schedule.model.dto.ScheduleListResponse;
import com.kh.reactrip.schedule.model.dto.ScheduleRequest;
import com.kh.reactrip.schedule.model.dto.ScheduleResponse;
import com.kh.reactrip.schedule.model.vo.Schedule;
import com.kh.reactrip.util.PageInfo;
import com.kh.reactrip.util.Pagenation;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService{
	
	private final ScheduleMapper scheduleMapper;
	private final MemberMapper memberMapper;
	private final Pagenation pagenation;
	
	@Override
	@Transactional
	public Long createSchedule(String memberId, @Valid ScheduleRequest request) {
		// 날짜 유효성 검사
		validateTravelDates(request.getTravelStart(), request.getTravelEnd());
		
		// memberId로 memberNo 조회
		Long memberNo = getMemberNoByMemberId(memberId);
		
		// Schedule 객체 생성
		Schedule schedule = Schedule.builder()
				.memberNo(memberNo)
				.scheduleName(request.getScheduleName())
				.headCount(request.getHeadCount())
				.description(request.getDescription())
				.travelStart(request.getTravelStart())
				.travelEnd(request.getTravelEnd())
				.build();
		
		scheduleMapper.insertSchedule(schedule);
		
		return schedule.getScheduleNo();
	}

	@Override
	public ScheduleListResponse getMyScheduleList(String memberId, int currentPage) {
		
		Long memberNo = getMemberNoByMemberId(memberId);
		
		// 내 스케줄 전체 개수 조회
		int listCount = scheduleMapper.selectMyScheduleCount(memberNo);
		
		// PageInfo 생성
		PageInfo pageInfo = pagenation.getPageInfo(listCount, currentPage, 10, 10);
		
		// RowBounds 생성
		RowBounds rowBounds = pagenation.createRowBounds(pageInfo);
		
		// 내 스케줄 목록 조회
		List<Schedule> schedules = scheduleMapper.selectMyScheduleList(memberNo, rowBounds);
		
		// VO -> DTO 변환
		List<ScheduleResponse> scheduleResponse = schedules.stream()
				.map(this::convertToResponse)
				.collect(Collectors.toList());
		
		// Response 생성
		ScheduleListResponse response = new ScheduleListResponse();
		response.setPageInfo(pageInfo);
		response.setSchedules(scheduleResponse);
		
		return response;
	}
	
	@Override
	@Transactional
	public ScheduleResponse getScheduleDetail(String memberId, Long scheduleNo) {
		
		// memberNo 조회
		Long memberNo = getMemberNoByMemberId(memberId);
		
        // 스케줄 조회 및 권한 확인
        Schedule schedule = getMyScheduleWithPermission(memberNo, scheduleNo);
        
		return convertToResponse(schedule);
	}
	
	@Override
	public void updateSchedule(String memberId, Long scheduleNo, @Valid ScheduleRequest request) {
		// 날짜 유효성 검증
		validateTravelDates(request.getTravelStart(), request.getTravelEnd());
		
		// memberNo 조회
		Long memberNo = getMemberNoByMemberId(memberId);
		
        // 스케줄 조회 및 권한 확인
        Schedule schedule = getMyScheduleWithPermission(memberNo, scheduleNo);
        
		schedule.setScheduleName(request.getScheduleName());
		schedule.setDescription(request.getDescription());
		schedule.setHeadCount(request.getHeadCount());
		schedule.setTravelStart(request.getTravelStart());
		schedule.setTravelEnd(request.getTravelEnd());
		
		scheduleMapper.updateSchedule(schedule);
	}

	@Override
	public void deleteSchedule(String memberId, Long scheduleNo) {
		// memberNo 조회
		Long memberNo = getMemberNoByMemberId(memberId);
		
        // 스케줄 조회 및 권한 확인
        getMyScheduleWithPermission(memberNo, scheduleNo);
		
		scheduleMapper.deleteSchedule(scheduleNo);
	}
	
	// ========== 공통 메서드 (private) ==========
	
	/**
	 * memberId로 memberNo 조회
	 */
	private Long getMemberNoByMemberId(String memberId) {
		AuthMember authMember = memberMapper.selectByMemberId(memberId);
		if (authMember == null) {
			throw new RuntimeException("사용자를 찾을 수 없습니다.");
		}
		return authMember.getMemberNo();
	}
	
	/**
	 * @param memberNo
	 * @param scheduleNo
	 * 본인 스케줄 조회 및 권한 검증
	 * @return
	 */
	private Schedule getMyScheduleWithPermission(Long memberNo, Long scheduleNo) {
		Schedule schedule = scheduleMapper.selectMyScheduleByNo(memberNo, scheduleNo);
		if (schedule == null) {
            throw new RuntimeException("스케줄을 찾을 수 없거나 접근 권한이 없습니다.");
        }
        return schedule;
	}
	
	private void validateTravelDates(java.sql.Date startDate, java.sql.Date endDate) {
		if (endDate != null && endDate.before(endDate)) {
			throw new IllegalArgumentException("종료일은 시작일보다 이후여야 합니다.");
		}
	}
	
	// VO -> DTO 변환
	private ScheduleResponse convertToResponse(Schedule schedule) {
		return ScheduleResponse.builder()
				.scheduleNo(schedule.getScheduleNo())
				.scheduleName(schedule.getScheduleName())
				.description(schedule.getDescription())
				.headCount(schedule.getHeadCount())
				.travelStart(schedule.getTravelStart())
				.travelEnd(schedule.getTravelEnd())
				.createdDate(schedule.getCreatedDate())  // 추가
				.scheduleStatus(schedule.getScheduleStatus())
				.memberNo(schedule.getMemberNo())
				.memberName(schedule.getMemberName())  // 추가
				.memberId(schedule.getMemberId())      // 추가
				.build();
	}

}
