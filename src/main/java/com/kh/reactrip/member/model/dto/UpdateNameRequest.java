package com.kh.reactrip.member.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateNameRequest {
	@Pattern(regexp = "^[a-z가-힣]*$", message = "이름은 영어, 한글만 사용 가능합니다.")
	@Size(min = 2, max = 40, message = "이름은 2글자 이상 40글자 이하만 사용할 수 있습니다.")
	@NotBlank(message = "이름은 필수 입력사항입니다.")
	private String memberName;
	
}
