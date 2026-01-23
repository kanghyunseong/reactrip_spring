package com.kh.reactrip.member.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateEmailRequest {
	@NotBlank
	@Email(message = "이메일 형식이 올바르지 않습니다")
	private String email;
}
