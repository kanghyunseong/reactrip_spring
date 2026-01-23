package com.kh.reactrip.member.model.service;

import org.springframework.web.multipart.MultipartFile;

import com.kh.reactrip.member.model.dto.SignupRequest;

public interface MemberService {
	void signUp(SignupRequest request);

	void registerMember(SignupRequest sign);

	//void updateProfileImage(Long memberId, MultipartFile profileImage);
}
