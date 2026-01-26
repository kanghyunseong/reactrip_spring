package com.kh.reactrip.admin.members.model.service;


import com.kh.reactrip.admin.members.model.dto.AdminMemberDTO;
import com.kh.reactrip.common.PageResponseDTO;


public interface AdminMemberService {
	
	PageResponseDTO<AdminMemberDTO> findAllMember(int page);

	PageResponseDTO<AdminMemberDTO> findByMembers(String keyword, int page);

	void updateMemberRole(Long memberNo, String memberRole);

	void deleteMember(Long memberNo);
	
	

}
