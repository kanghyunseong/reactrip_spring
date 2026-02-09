package com.kh.reactrip.schedule.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.kh.reactrip.schedule.model.vo.Schedule;

@Mapper
public interface ScheduleMapper {

	// 스케줄 생성
	int insertSchedule(Schedule schedule);
	
	// 내 스케줄 전체 개수
	int selectMyScheduleCount(@Param("memberNo") Long memberNo);
	
	// 내 스케줄 목록 조회
	List<Schedule> selectMyScheduleList(@Param("memberNo") Long memberNo, RowBounds rowBounds);

	// 스케줄 상세 조회
	Schedule selectMyScheduleByNo(@Param("memberNo") Long memberNo, @Param("scheduleNo") Long scheduleNo);

	// 스케줄 수정
	int updateSchedule(Schedule schedule);

	// 스케줄 삭제
	int deleteSchedule(Long scheduleNo);



		

}
