package com.kh.reactrip.schedule.model.dto;

import lombok.Data;
import java.util.List;

import com.kh.reactrip.util.PageInfo;
@Data
public class ScheduleListResponse {
	private PageInfo pageInfo;
	private List<ScheduleResponse> schedules;
}
