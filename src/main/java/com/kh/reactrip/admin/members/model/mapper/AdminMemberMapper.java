package com.kh.reactrip.admin.members.model.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.kh.reactrip.admin.members.model.dto.AdminMemberDTO;

@Mapper
public interface AdminMemberMapper {

	//List<AdminMemberDTO> findAllMembers(RowBounds, rowBounds);
	
	int getTotalCount();

	List<AdminMemberDTO> findAllMembers(RowBounds rowBounds);

	List<AdminMemberDTO> findByMembers(@Param("keyword")String keyword);

	int updateMemberRole(@Param("memberNo") Long memberNo, @Param("memberRole") String memberRole);

	int deleteMember(Long memberNo);
	
	AdminMemberDTO findMemberByNo(Long MemberNo);
}
