package com.kh.reactrip.member.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
@Data
public class UpdateBirthdayRequest {
	@Pattern(regexp = "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$", message = "생년월일 형식이 올바르지 않습니다.")
	@NotBlank(message = "생년월일는 필수 입력사항입니다.")
	private String birthDay;
}
