package com.kh.reactrip.schedule.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
	private Long scheduleNo;
	private String scheduleName;
	private Integer headCount;
	private String description;
	private Date travelStart;
	private Date travelEnd;
	private Date createdDate;
	private char scheduleStatus;
	private Long memberNo;
	
	// 조인용
	private String memberName;
	private String memberId;
}
