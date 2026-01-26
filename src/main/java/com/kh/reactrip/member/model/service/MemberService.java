package com.kh.reactrip.member.model.service;

import org.springframework.web.multipart.MultipartFile;

import com.kh.reactrip.auth.model.dto.MemberLoginDTO;
import com.kh.reactrip.member.model.dto.SignupRequest;
import com.kh.reactrip.member.model.dto.UpdatePasswordRequest;

import jakarta.validation.Valid;

public interface MemberService {
	void signUp(SignupRequest request);

	MemberLoginDTO getMemberInfo(String memberId);

	void deleteMember(Long memberNo);

	void updateMemberName(String memberId, String newName);

	void updateMemberEmail(String memberId, String newEmail);
	//void updateProfileImage(Long memberId, MultipartFile profileImage);

	void updateMemberPhone(String memberId, String newPhone);

	void updateMemberBirthday(String memberId, String newBirthDay);

	void updateMemberPassword(String memberId, UpdatePasswordRequest memberPwd);
}
