package com.kh.reactrip.member.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class UpdatePasswordRequest {
	
	@NotBlank(message = "현재 비밀번호를 입력해주세요")
	private String currentPassword;
	
	@Size(min = 5, max = 20, message = "비밀번호 값은 5글자 이상 20글자 이하만 사용할 수 있습니다.")
	@NotBlank(message = "비밀번호 값은 비어있을 수 없습니다.")
	private String newMemberPwd;
	
	@NotBlank(message = "비밀번호 확인을 입력해주세요")
    private String confirmPassword;
}
