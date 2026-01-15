package com.kh.reactrip.admin.members.model.service;

import java.util.List;

import com.kh.reactrip.admin.members.model.dto.AdminMemberDTO;
import com.kh.reactrip.admin.members.model.dto.AdminPageResponseDTO;

public interface AdminMemberService {
	
	AdminPageResponseDTO findAllMember(int page);

	List<AdminMemberDTO> findByMembers(String keyword);

	void updateMemberRole(Long memberNo, String memberRole);

	void deleteMember(Long memberNo);
	
	

}
