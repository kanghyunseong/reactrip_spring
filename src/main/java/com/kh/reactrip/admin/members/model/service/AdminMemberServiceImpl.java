package com.kh.reactrip.admin.members.model.service;

import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
=======
import org.apache.ibatis.session.RowBounds;
import org.springframework.context.annotation.Primary;
>>>>>>> 5d3ff508e0166528a5bd964850cf83acd006253c
import org.springframework.stereotype.Service;

import com.kh.reactrip.admin.members.model.dto.AdminMemberDTO;
import com.kh.reactrip.admin.members.model.mapper.AdminMemberMapper;
import com.kh.reactrip.common.PageResponseDTO;
import com.kh.reactrip.exception.UserNotFoundException;
import com.kh.reactrip.file.service.S3Service;
import com.kh.reactrip.util.PageInfo;
import com.kh.reactrip.util.Pagenation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Primary
public class AdminMemberServiceImpl implements AdminMemberService {

	private final Pagenation pagenation;
	private final AdminMemberMapper adminMemberMapper;
	private final S3Service s3Service;
	

	@Override
	public PageResponseDTO<AdminMemberDTO> findAllMember(int page) {

		int totalCount = adminMemberMapper.getTotalCount();

		PageInfo pi = pagenation.getPageInfo(totalCount, page);
		
		List<AdminMemberDTO> members = adminMemberMapper.findAllMembers(pagenation.createRowBounds(pi));

		return new PageResponseDTO<>(pi, members);
	}

	@Override
	public PageResponseDTO<AdminMemberDTO> findByMembers(String keyword, int page) {
		
		if(keyword == null || keyword.trim().isEmpty()) {
			return new PageResponseDTO<>(new PageInfo(), new ArrayList<>());
		}
		
		int totalCount = adminMemberMapper.getSearchCount(keyword);
		
		// 검색 결과가 0건이면 예외를 던지지 말고 "빈 리스트"를 정상 응답으로 내려준다.
		// (프론트에서 404로 인식되어 실패 처리되는 문제 방지)
		if(totalCount == 0) {
			PageInfo pi = pagenation.getPageInfo(0, page);
			return new PageResponseDTO<>(pi, new ArrayList<>());
		}
		
		PageInfo pi = pagenation.getPageInfo(totalCount, page);
		
		List<AdminMemberDTO> members = adminMemberMapper.findByMembers(keyword, pagenation.createRowBounds(pi));
		
		return new PageResponseDTO<>(pi, members);
	}

	@Override
	public void updateMemberRole(Long memberNo, String memberRole) {
		int updateMemberRole = adminMemberMapper.updateMemberRole(memberNo, memberRole);
		
		if(updateMemberRole == 0) {
			log.error("User Not Found Exception : {} ", memberNo);
			throw new UserNotFoundException("권한 부여 실패" + memberNo + "에 해당하는 사용자가 없습니다.");
		}
		
	}

	@Override
	public void deleteMember(Long memberNo) {
		
		AdminMemberDTO members = adminMemberMapper.findMemberByNo(memberNo);
		
		if(members == null) {
			throw new UserNotFoundException("삭제할 회원을 찾을 수 없습니다.");
		}
		
		String imageUrl = members.getImage();
		
		if(imageUrl != null && !imageUrl.isEmpty() && !imageUrl.contains("default")) {
            try {
                s3Service.deleteFile(imageUrl);
                log.info("S3 이미지 삭제 성공 : {} ", imageUrl);
            } catch (Exception e) {
                log.error("S3 삭제 중 오류 발생 (DB 삭제 진행): {}", e.getMessage());
            }
        }
		
		int result = adminMemberMapper.deleteMember(memberNo);
		
		if(result == 0 || result < 0) {
			log.error("정보 삭제 실패 : {} ", memberNo );
			throw new UserNotFoundException("사용자 삭제 실패 " + memberNo + "에 해당하는 사용자를 찾지 못했습니다.");
		}
	}
}
