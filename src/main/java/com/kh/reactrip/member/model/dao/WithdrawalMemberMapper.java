package com.kh.reactrip.member.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.reactrip.member.model.vo.DeleteMember;

@Mapper
public interface WithdrawalMemberMapper {
	int insertWithdrawalMember(DeleteMember withdrawalMember);
}
