package com.kh.reactrip.auth.model.dto;

import java.sql.Date;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class MemberLoginDTO {
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "아이디는 영어/숫자만 사용 가능합니다.")
	@Size(min = 6, max = 20, message = "아이디 값은 6글자 이상 20글자 이하만 사용할 수 있습니다.")
	@NotBlank(message = "아이디는 필수 입력사항입니다.")
	private String memberId;

	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "비밀번호값은 영어 / 숫자만 사용가능합니다.")
	@Size(min = 5, max = 20, message = "비밀번호 값은 5글자 이상 20글자 이하만 사용할 수 있습니다.")
	@NotBlank(message = "비밀번호 값은 비어있을 수 없습니다.")
	private String memberPwd;
	private Long memberNo;
	private Long authNo;
	private String memberName;
	private String birthDay;
	private String phone;
	private String email;
	private String image;
	private String memberRole;
	private String deletedDate;
	private Date enrollDate;
	private char deleteStatus;

}