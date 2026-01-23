package com.kh.reactrip.member.model.service;

import java.sql.Date;

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
public class MemberServiceImpl implements MemberService{
	
	private final MemberMapper memberMapper;
	private final AuthMemberMapper authMemberMapper;
	private final PasswordEncoder passwordEncoder;
	// private final FileService fileService;
	
	@Override
	@Transactional
	public void signUp(SignupRequest request) {
		if (memberMapper.countById(request.getMemberId()) > 0) {
			throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
		}
		
		if (memberMapper.countByEmail(request.getEmail()) > 0) {
			throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
		}
		
		if (memberMapper.countByPhone(request.getPhone()) > 0) {
			throw new IllegalArgumentException("이미 존재하는 번호입니다.");
		}
		
		String encryptedPassword = passwordEncoder.encode(request.getMemberPwd());
		
		Date currentDate = new Date(System.currentTimeMillis());
		
		Member member = Member.builder()
					          .memberName(request.getMemberName())
					          .birthDay(request.getBirthDay())
					          .phone(request.getPhone())
					          .email(request.getEmail())
					          .memberRole("ROLE_USER")
					          .enrollDate(currentDate)
					          .image(request.getImage())
					          .build();
	
		memberMapper.insertMemberInfo(member);
		
		Long generatedMemberNo = member.getMemberNo();

		AuthMember authMember = AuthMember.builder()
										  .memberNo(generatedMemberNo)
										  .memberId(request.getMemberId())
									      .memberPwd(encryptedPassword)
									      .build();
		
		memberMapper.insertAuthMember(authMember);

		DeleteMember deleteMember = DeleteMember.builder()
													.memberNo(generatedMemberNo)
													.deleteStatus('N')
													.build();
															
	}
	
	@Transactional
	public void deleteMember(Long memberNo) {
		// 삭제 레코드 생성
		memberMapper.insertDeleteMember(memberNo);
		// 삭제일 업데이트
		memberMapper.deleteMember(memberNo);
	}
	
	@Override
	public void registerMember(SignupRequest sign) {
		// 비밀번호 암호화
		String encryptedPassword = passwordEncoder.encode(sign.getMemberPwd());
		sign.setMemberPwd(encryptedPassword);
		
		memberMapper.insertMember(sign);
		
	}
	
	private boolean checkPassword(String rawPassword, String encodedPassword) {
		// 비밀번호 검증
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
	
	
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
