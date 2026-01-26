package com.kh.reactrip.auth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NaverProfileDTO {

	private Long userNo;
	private String memberId;
	private String provider;
	private String memberName;
	private String birthDay;
	private String email;
	private String phone;
	private String accessToken;
	private String refreshToken;
	private String licenseUrl;
	private String role;
}