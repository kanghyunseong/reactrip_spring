package com.kh.reactrip.admin.members.model.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.reactrip.admin.members.model.dto.AdminMemberDTO;

@Mapper
public interface AdminMemberMapper {

	//List<AdminMemberDTO> findAllMembers(RowBounds, rowBounds);
	
	int getTotalCount();

	List<AdminMemberDTO> findAllMembers(RowBounds rowBounds);

	AdminMemberDTO searchMember(Long memberNo);
}
