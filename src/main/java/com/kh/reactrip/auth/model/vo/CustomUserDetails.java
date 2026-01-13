package com.kh.reactrip.auth.model.vo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CustomUserDetails implements UserDetails {

	private Long userNo;
	private String username; // ID
	private String password;
	private String realName; // 실명
	private String birthDay;
	private String email;
	private String phone;
	private String licenseUrl;
	private Collection<? extends GrantedAuthority> authorities;

}
