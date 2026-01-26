package com.kh.reactrip.auth.model.service;

import java.util.Collections;

import javax.security.auth.login.LoginException;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kh.reactrip.auth.model.dto.MemberLoginDTO;
import com.kh.reactrip.auth.model.vo.CustomUserDetails;
import com.kh.reactrip.member.model.dao.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final MemberMapper mapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberLoginDTO user = mapper.loadUser(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("아이디가 존재하지 않습니다.");
		}
		
		if(user.getDeleteStatus() == 'Y') {
			throw new UsernameNotFoundException("정지된 계정입니다.");
			
		}
		
		return CustomUserDetails.builder().memberId(user.getMemberId())
										  .password(user.getMemberPwd())
										  .memberNo(user.getMemberNo())
										  .authNo(user.getAuthNo())
										  .memberName(user.getMemberName())
										  .birthDay(user.getBirthDay())
										  .phone(user.getPhone())
										  .email(user.getEmail())
										  .image(user.getImage())
										  .enrollDate(user.getEnrollDate())
										  .deletedDate(user.getDeletedDate())
										  .deleteStatus(user.getDeleteStatus())
										  .authorities(Collections.singletonList(new SimpleGrantedAuthority(user.getMemberRole())))
										  .build();
										  
	}
	// AuthenticationManger가 실질적으로 사용자의 정보를 조회할 때 메소드를 호출하는 클래스

	

}