package com.kh.reactrip.auth.model.vo;

import java.sql.Date;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CustomUserDetails implements UserDetails {

	private String username;
	private String password;
	
	private Long memberNo;
	private Long authNo;
	private String memberId;
	private String memberName; 
	private String birthDay;
	private String phone;
	private String email;
	private String image;
	private String memberRole;
	private Date enrollDate;
	private String deletedDate;
	private char deleteStatus;
	private Collection<? extends GrantedAuthority> authorities;

	@Override
    public String getUsername() {
        return memberId;  // memberId를 username으로 사용
    }
	
	public Long getAuthNo() {  // getter가 있는지 확인
        return authNo;
    }

}
