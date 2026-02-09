package com.kh.reactrip.schedule.model.dto;

import java.sql.Date;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ScheduleRequest {
	@NotBlank(message = "제목을 입력해주세요")
	@Size(min = 2, max = 100, message = "제목은 2~100자로 입력해주세요")
	private String scheduleName;
	
	@Size(max = 2000, message = "설명은 2000자로 이하로 입력해주세요")
	private String description;
	
	@NotNull(message = "인원을 입력해주세요")
	@Min(value = 1, message = "인원은 최소 1명 이상이어야 합니다")
	@Max(value = 100, message = "인원은 최대 100명까지 가능합니다")
	private int headCount;
	
	@NotNull(message = "시작일을 입력해주세요")
	private Date travelStart;
	private Date travelEnd;
	
}
