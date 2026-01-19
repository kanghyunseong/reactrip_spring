package com.kh.reactrip.member.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.reactrip.member.model.vo.AuthMember;

@Mapper
public interface AuthMemberMapper {
	int insertAuthMember(AuthMember authMember);
	AuthMember findByMemberNo(Long memberNo);
	AuthMember selectByMemberId(String memberId);
}
