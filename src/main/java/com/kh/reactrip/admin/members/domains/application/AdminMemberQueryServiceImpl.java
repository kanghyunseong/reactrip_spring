//package com.kh.reactrip.admin.members.application;
//
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.kh.reactrip.admin.members.domain.AdminMember;
//import com.kh.reactrip.admin.members.infrastructure.AdminMemberRepository;
//import com.kh.reactrip.admin.members.model.dto.AdminMemberDTO;
//import com.kh.reactrip.admin.members.model.dto.AdminPageResponseDTO;
//import com.kh.reactrip.exception.UserNotFoundException;
//import com.kh.reactrip.util.PageInfo;
//import com.kh.reactrip.util.Pagenation;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class AdminMemberQueryServiceImpl implements AdminMemberQueryService {
//
//    private final Pagenation pagenation;                 // 페이징 계산 유틸
//    private final AdminMemberRepository memberRepository; // 도메인 리포지토리
//
//    private static final int BOARD_LIMIT = 10;
//    private static final int PAGE_LIMIT = 5;
//
//    @Override
//    public AdminPageResponseDTO getMembers(int page) {
//
//        int totalCount = memberRepository.getTotalCount();
//
//        PageInfo pi = pagenation.getPageInfo(totalCount, page, BOARD_LIMIT, PAGE_LIMIT);
//
//        // 도메인 기준 회원 목록 조회
//        List<AdminMember> members = memberRepository.findAllMembers(pi);
//
//        // 도메인 -> DTO 변환
//        List<AdminMemberDTO> memberDTOs = members.stream()
//                .map(this::toDto)
//                .toList();
//
//        return new AdminPageResponseDTO(pi, memberDTOs);
//    }
//
//    @Override
//    public List<AdminMemberDTO> searchMembers(String keyword) {
//
//        List<AdminMember> members = memberRepository.findByMembers(keyword);
//
//        if (members == null || members.isEmpty()) {
//            log.error("User Not Found Exception : {} ", keyword);
//            throw new UserNotFoundException("검색어 : " + keyword + "에 대한 정보가 없습니다.");
//        }
//
//        return members.stream()
//                .map(this::toDto)
//                .toList();
//    }
//
//    // 도메인 -> DTO 변환
//    private AdminMemberDTO toDto(AdminMember member) {
//        AdminMemberDTO dto = new AdminMemberDTO();
//        dto.setMemberNo(member.getMemberNo());
//        dto.setMemberName(member.getMemberName());
//        dto.setBirthday(member.getBirthday());
//        dto.setPhone(member.getPhone());
//        dto.setEmail(member.getEmail());
//        dto.setEnrollDate(member.getEnrollDate() != null
//                ? java.sql.Date.valueOf(member.getEnrollDate())
//                : null);
//        dto.setMemberRole(member.getMemberRole());
//        dto.setImage(member.getImage());
//        return dto;
//    }
//}