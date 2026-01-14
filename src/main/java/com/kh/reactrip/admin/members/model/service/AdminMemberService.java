package com.kh.reactrip.admin.members.model.service;

import com.kh.reactrip.admin.members.model.dto.AdminMemberDTO;
import com.kh.reactrip.admin.members.model.dto.AdminPageResponseDTO;

public interface AdminMemberService {
	
	AdminPageResponseDTO findAllMember(int page);

	AdminMemberDTO searchMember(Long memberNo);
	
	

}
