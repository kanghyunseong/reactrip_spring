package com.kh.reactrip.schedule.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleResponse {
	private Long scheduleNo;
	private String scheduleName;
	private String description;
	private Integer headCount;
	private Date travelStart;
	private Date travelEnd;
	private Date createdDate;
	private char scheduleStatus;
	private Long memberNo;
	
	private String memberName;
	private String memberId;
	
}
