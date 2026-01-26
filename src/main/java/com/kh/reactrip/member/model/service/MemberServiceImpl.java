package com.kh.reactrip.member.model.service;

import java.sql.Date;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.kh.reactrip.auth.model.dto.MemberLoginDTO;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.reactrip.file.service.FileService;
import com.kh.reactrip.file.service.S3Service;
import com.kh.reactrip.member.model.dao.AuthMemberMapper;
import com.kh.reactrip.member.model.dao.MemberMapper;
import com.kh.reactrip.member.model.dto.SignupRequest;
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
	public void updateMemberName(String memberId, String memberName) {
		// AuthMember 조회 (공통 메서드 사용)
		AuthMember authMember = getAuthMemberByMemberId(memberId);
		
		log.info("회원 이름 변경: memberNo={}, newName={}", authMember.getMemberNo(), memberName);
		memberMapper.updateMemberName(authMember.getMemberNo(), memberName);
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

	public void registerMember(SignupRequest sign) {
		// 비밀번호 암호화
		String encryptedPassword = passwordEncoder.encode(sign.getMemberPwd());
		sign.setMemberPwd(encryptedPassword);
		
		memberMapper.insertMember(sign);
		
	}
	
//	private boolean checkPassword(String rawPassword, String encodedPassword) {
//		// 비밀번호 검증
//		return passwordEncoder.matches(rawPassword, encodedPassword);
//	}
	
	
//	public void updateProfileImage(Long memberId, MultipartFile profileImage) {
//		
//		String profileImageUrl = null;
//		
//		String defaultImageUrl = "https://www.google.com/url?sa=i&url=https%3A%2F%2Funknownblog.tistory.com%2F343&psig=AOvVaw1L0tJupKrwUuzY4b2x4Wq-&ust=1763535249259000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCNCBj_WO-5ADFQAAAAAdAAAAABAL";
//
//		if(profileImage != null && !profileImage.isEmpty()) {
//			String savePath = "/";
//		}
//		String profileImageUrl = S3Service.uploadFile(request.getProfileUrl(), "profiles");
//	}
}