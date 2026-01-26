package com.kh.reactrip.member.model.service;

import java.sql.Date;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.kh.reactrip.auth.model.dto.MemberLoginDTO;
import com.kh.reactrip.auth.model.vo.CustomUserDetails;
import com.kh.reactrip.member.model.dao.AuthMemberMapper;
import com.kh.reactrip.member.model.dao.MemberMapper;
import com.kh.reactrip.member.model.dto.SignupRequest;
import com.kh.reactrip.member.model.dto.UpdateNameRequest;
import com.kh.reactrip.member.model.dto.UpdatePasswordRequest;
import com.kh.reactrip.member.model.vo.AuthMember;
import com.kh.reactrip.member.model.vo.Member;
import com.kh.reactrip.member.model.vo.DeleteMember;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	private final MemberMapper memberMapper;
	private final AuthMemberMapper authMemberMapper;
	private final PasswordEncoder passwordEncoder;
	private final MemberValidationService validationService;  // 추가
	// private final FileService fileService;
	
	@Override
	@Transactional
	public void signUp(SignupRequest request) {
		// 중복 검증 (분리된 서비스 사용)
		validationService.validateDuplicateMember(request);
		
		// 비밀번호 암호화
		String encryptedPassword = passwordEncoder.encode(request.getMemberPwd());
		
		// Member 생성 및 저장
		Member member = createMember(request);
		memberMapper.insertMemberInfo(member);
		
		// AuthMember 생성 및 저장
		Long generatedMemberNo = member.getMemberNo();
		AuthMember authMember = createAuthMember(generatedMemberNo, request.getMemberId(), encryptedPassword);
		memberMapper.insertAuthMember(authMember);
		
		// DeleteMember 초기화 (필요시)
		DeleteMember deleteMember = createDeleteMember(generatedMemberNo);
	}
	
//	@Override
//	@Transactional
//	public void deleteMember(Long memberNo) {
//		memberMapper.insertDeleteMember(memberNo);
//		if (memberMapper.countById(request.getMemberId()) > 0) {
//			throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
//		}
//		
//		if (memberMapper.countByEmail(request.getEmail()) > 0) {
//			throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
//		}
//		
//		if (memberMapper.countByPhone(request.getPhone()) > 0) {
//			throw new IllegalArgumentException("이미 존재하는 번호입니다.");
//		}
//		
//		String encryptedPassword = passwordEncoder.encode(request.getMemberPwd());
//		
//		Date currentDate = new Date(System.currentTimeMillis());
//		
//		Member member = Member.builder()
//					          .memberName(request.getMemberName())
//					          .birthDay(request.getBirthDay())
//					          .phone(request.getPhone())
//					          .email(request.getEmail())
//					          .memberRole("ROLE_USER")
//					          .enrollDate(currentDate)
//					          .image(request.getImage())
//					          .build();
//	
//		memberMapper.insertMemberInfo(member);
//		
//		Long generatedMemberNo = member.getMemberNo();
//
//		AuthMember authMember = AuthMember.builder()
//										  .memberNo(generatedMemberNo)
//										  .memberId(request.getMemberId())
//									      .memberPwd(encryptedPassword)
//									      .build();
//		
//		memberMapper.insertAuthMember(authMember);
//
//		DeleteMember deleteMember = DeleteMember.builder()
//													.memberNo(generatedMemberNo)
//													.deleteStatus('N')
//													.build();
//															
//	}
	
	@Transactional
	public void deleteMember(Long memberNo) {
		// 삭제 레코드 생성
		memberMapper.insertDeleteMember(memberNo);
		// 삭제일 업데이트
		memberMapper.deleteMember(memberNo);
	}
	
	@Override
	public MemberLoginDTO getMemberInfo(String memberId) {
		return memberMapper.loadUser(memberId);
	}
	
	@Override
	@Transactional
	public void updateMemberName(String memberId, String newName) {
		AuthMember authMember = getAuthMemberByMemberId(memberId);
		memberMapper.updateMemberName(authMember.getMemberNo(), newName);
	}

	@Override
	@Transactional
	public void updateMemberBirthday(String memberId, String newBirthDay) {
		AuthMember authMember = getAuthMemberByMemberId(memberId);
		memberMapper.updateMemberBirthday(authMember.getMemberNo(), newBirthDay);
	}
	@Override
	@Transactional
	public void updateMemberEmail(String memberId, String newEmail) {
		
		if(validationService.isEmailDuplicate(newEmail)) {
			throw new IllegalArgumentException("이미 사용중인 이메일입니다");
		}
		
		AuthMember authMember = getAuthMemberByMemberId(memberId);
		memberMapper.updateMemberEmail(authMember.getMemberNo(), newEmail);
	}
	
	@Override
	@Transactional
	public void updateMemberPhone(String memberId, String newPhone) {
		
		if(validationService.isPhoneDuplicate(newPhone)) {
			throw new IllegalArgumentException("이미 사용중인 전화번호입니다");
		};
		
		AuthMember authMember = getAuthMemberByMemberId(memberId);
		memberMapper.updateMemberPhone(authMember.getMemberNo(), newPhone);
	}
	
	@Override
	@Transactional
	public void updateMemberPassword(String memberId, UpdatePasswordRequest request) {
		
		if(!request.getNewMemberPwd().equals(request.getConfirmPassword())) {
			throw new IllegalArgumentException("새 비밀번호가 일치하지 않습니다");
		}
		
		AuthMember authMember = getAuthMemberByMemberId(memberId);
		if(!checkPassword(request.getCurrentPassword(), authMember.getMemberPwd())) {
			throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
		}
		String encryptedPassword = passwordEncoder.encode(request.getNewMemberPwd());
		
		memberMapper.updateMemberPassword(authMember.getMemberNo(), encryptedPassword);
	}
	
	// ========== 공통 메서드 (private) ==========
	
	/**
	 * memberId로 AuthMember 조회
	 */
	private AuthMember getAuthMemberByMemberId(String memberId) {
		AuthMember authMember = memberMapper.selectByMemberId(memberId);
		if (authMember == null) {
			throw new RuntimeException("사용자를 찾을 수 없습니다.");
		}
		return authMember;
	}
	
	/**
	 * Member 객체 생성
	 */
	private Member createMember(SignupRequest request) {
		Date currentDate = new Date(System.currentTimeMillis());
		return Member.builder()
				.memberName(request.getMemberName())
				.birthDay(request.getBirthDay())
				.phone(request.getPhone())
				.email(request.getEmail())
				.memberRole("ROLE_USER")
				.enrollDate(currentDate)
				.image(request.getImage())
				.build();
	}
	
	/**
	 * AuthMember 객체 생성
	 */
	private AuthMember createAuthMember(Long memberNo, String memberId, String encryptedPassword) {
		return AuthMember.builder()
				.memberNo(memberNo)
				.memberId(memberId)
				.memberPwd(encryptedPassword)
				.build();
	}
	
	/**
	 * DeleteMember 객체 생성
	 */
	private DeleteMember createDeleteMember(Long memberNo) {
		return DeleteMember.builder()
				.memberNo(memberNo)
				.deleteStatus('N')
				.build();
	}
	
	/**
	 * 비밀번호 검증
	 */
	private boolean checkPassword(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

}
