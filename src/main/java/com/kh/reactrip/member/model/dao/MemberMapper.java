package com.kh.reactrip.member.model.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.reactrip.auth.model.dto.MemberLoginDTO;
import com.kh.reactrip.member.model.dto.SignupRequest;
import com.kh.reactrip.member.model.vo.AuthMember;
import com.kh.reactrip.member.model.vo.Member;
import com.kh.reactrip.member.model.vo.DeleteMember;

@Mapper
public interface MemberMapper {
	// 아이디, 이메일, 번호 중복 확인 메소드
    int countById(String memberId);
    int countByEmail(String email);
    int countByPhone(String phone);
    
    Member findById(String memberId);
    
    // 회원가입 관련 메소드들
    void insertMemberInfo(Member member); 
    void insertAuthMember(AuthMember auth);
    void insertDeleteMember(Long memberNo);
    void deleteMember(Long memberNo);
    
	void insertMember(SignupRequest sign);
	
	Member selectMemberNo(Long memberNo);
	
	// 로그인 메소드
	MemberLoginDTO loadUser(String username);
	// 
	AuthMember selectByMemberId(String memberId);
	void updateMemberName(@Param("memberNo") Long memberNo, @Param("newName") String newName);
	void updateMemberEmail(@Param("memberNo") Long memberNo, @Param("newEmail") String newEmail);
	void updateMemberPhone(@Param("memberNo") Long memberNo, @Param("newPhone") String newPhone);
	void updateMemberBirthday(@Param("memberNo") Long memberNo, @Param("newBirthDay") String newBirthDay);
	void updateMemberPassword(@Param("memberNo") Long memberNo, @Param("encryptedPassword") String encryptedPassword);
}
