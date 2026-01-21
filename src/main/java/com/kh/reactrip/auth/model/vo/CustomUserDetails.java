package com.kh.reactrip.auth.model.vo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class CustomUserDetails implements UserDetails {

	private Collection<? extends GrantedAuthority> authorities;
	private Long memberNo;
	private String username;
	private String password;
	private String birthday;
	private String email;
	private String phone;
}
