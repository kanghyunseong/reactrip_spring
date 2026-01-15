package com.kh.reactrip.admin.members.model.service;

import java.util.List;

import com.kh.reactrip.admin.members.model.dto.AdminMemberDTO;
import com.kh.reactrip.common.PageResponseDTO;

public interface AdminMemberService {
	
	PageResponseDTO<AdminMemberDTO> findAllMember(int page);

	List<AdminMemberDTO> findByMembers(String keyword);

	void updateMemberRole(Long memberNo, String memberRole);

	void deleteMember(Long memberNo);
	
	

}
