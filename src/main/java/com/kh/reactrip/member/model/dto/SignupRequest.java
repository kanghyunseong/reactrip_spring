package com.kh.reactrip.member.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignupRequest {
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "아이디는 영어/숫자만 사용 가능합니다.")
	@Size(min = 6, max = 20, message = "아이디 값은 6글자 이상 20글자 이하만 사용할 수 있습니다.")
	@NotBlank(message = "아이디는 필수 입력사항입니다.")
	private String memberId;
	@Pattern(regexp = "^[a-z가-힣]*$", message = "이름은 영어, 한글만 사용 가능합니다.")
	@Size(min = 2, max = 40, message = "이름은 2글자 이상 40글자 이하만 사용할 수 있습니다.")
	@NotBlank(message = "이름은 필수 입력사항입니다.")
	private String memberName;
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "비밀번호값은 영어 / 숫자만 사용가능합니다.")
	@Size(min = 5, max = 20, message = "비밀번호 값은 5글자 이상 20글자 이하만 사용할 수 있습니다.")
	@NotBlank(message = "비밀번호 값은 비어있을 수 없습니다.")
	private String memberPwd;
	@Pattern(regexp = "^(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])$", message = "생년월일 형식이 올바르지 않습니다.")
	@NotBlank(message = "전화번호는 필수 입력사항입니다.")
	private String birthDay;
	@NotBlank
	@Email(message = "이메일 형식이 올바르지 않습니다")
	private String email;
	@Pattern(regexp = "^0\\d{1,2}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
	@NotBlank(message = "전화번호는 필수 입력사항입니다.")
	private String phone;
	private String image;
}
