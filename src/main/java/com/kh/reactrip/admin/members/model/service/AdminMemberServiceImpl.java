package com.kh.reactrip.admin.members.model.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.kh.reactrip.admin.members.model.dto.AdminMemberDTO;
import com.kh.reactrip.admin.members.model.dto.AdminPageResponseDTO;
import com.kh.reactrip.admin.members.model.mapper.AdminMemberMapper;
import com.kh.reactrip.exception.UserNotFoundException;
import com.kh.reactrip.util.PageInfo;
import com.kh.reactrip.util.Pagenation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminMemberServiceImpl implements AdminMemberService {

	private final Pagenation pagenation;
	private final AdminMemberMapper adminMemberMapper;

	@Override
	public AdminPageResponseDTO findAllMember(int page) {

		int totalCount = adminMemberMapper.getTotalCount();

		int boardList = 10;
		int pageLimit = 5;

		PageInfo pi = pagenation.getPageInfo(totalCount, page, boardList, pageLimit);
		
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		
		List<AdminMemberDTO> members = adminMemberMapper.findAllMembers(rowBounds);

		return new AdminPageResponseDTO(pi, members);
	}

	@Override
	public List<AdminMemberDTO> findByMembers(String keyword) {
		
		List<AdminMemberDTO> findByMembers = adminMemberMapper.findByMembers(keyword);
		
		if(findByMembers == null || findByMembers.isEmpty()) {
			log.error("User Not Found Exception : {} ", keyword );
			throw new UserNotFoundException("검색어 : " + keyword + "에 대한 정보가 없습니다.");
		}
		
		return findByMembers;
	}

	@Override
	public void updateMemberRole(Long memberNo, String memberRole) {
		
		int updateMemberRole = adminMemberMapper.updateMemberRole(memberNo, memberRole);
		
		if(updateMemberRole == 0) {
			log.error("User Not Found Exception : {} ", memberNo);
			throw new UserNotFoundException("권한 부여 실패" + memberNo + "에 해당하는 사용자가 없습니다.");
		}
		
	}

}
