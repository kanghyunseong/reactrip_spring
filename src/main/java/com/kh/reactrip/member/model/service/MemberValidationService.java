package com.kh.reactrip.member.model.service;

import org.springframework.stereotype.Service;
import com.kh.reactrip.member.model.dao.MemberMapper;
import com.kh.reactrip.member.model.dto.SignupRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberValidationService {
	
	private final MemberMapper memberMapper;
	
	/**
	 * 회원가입 시 중복 검증
	 */
	public void validateDuplicateMember(SignupRequest request) {
		if (memberMapper.countById(request.getMemberId()) > 0) {
			throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
		}
		
		if (memberMapper.countByEmail(request.getEmail()) > 0) {
			throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
		}
		
		if (memberMapper.countByPhone(request.getPhone()) > 0) {
			throw new IllegalArgumentException("이미 존재하는 번호입니다.");
		}
	}
	
	/**
	 * 아이디 중복 확인
	 */
	public boolean isIdDuplicate(String memberId) {
		return memberMapper.countById(memberId) > 0;
	}
	
	/**
	 * 이메일 중복 확인
	 */
	public boolean isEmailDuplicate(String email) {
		return memberMapper.countByEmail(email) > 0;
	}
	
	/**
	 * 전화번호 중복 확인
	 */
	public boolean isPhoneDuplicate(String phone) {
		return memberMapper.countByPhone(phone) > 0;
	}
}