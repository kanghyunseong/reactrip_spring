//package com.kh.reactrip.admin.members.application;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.kh.reactrip.admin.members.domain.AdminMember;
//import com.kh.reactrip.admin.members.domain.MemberImageStore;
//import com.kh.reactrip.admin.members.infrastructure.AdminMemberRepository;
//import com.kh.reactrip.exception.UserNotFoundException;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class AdminMemberCommandServiceImpl implements AdminMemberCommandService {
//
//    private final AdminMemberRepository memberRepository;
//    private final MemberImageStore imageStore;
//
//    @Override
//    @Transactional
//    public void updateMemberRole(Long memberNo, String memberRole) {
//        memberRepository.updateMemberRole(memberNo, memberRole);
//    }
//
//    @Override
//    @Transactional // 삭제니까 트랜잭션 필수
//    public void deleteMember(Long memberNo) {
//        
//        // 1. 도메인 객체 조회 (없으면 바로 에러)
//        AdminMember member = memberRepository.findMemberByNo(memberNo)
//                .orElseThrow(() -> new UserNotFoundException("삭제할 회원을 찾을 수 없습니다."));
//
//        // 2. 이미지 삭제 처리
//        // 설명: "멤버야, 너 커스텀 이미지 가지고 있어?" (도메인에게 물어봄)
//        if (member.hasCustomImage()) {
//            // "이미지 스토어장, 그 이미지 좀 지워줘." (인터페이스 호출)
//            imageStore.deleteImage(member.getImage());
//        }
//
//        // 3. DB 데이터 삭제
//        memberRepository.deleteMember(memberNo);
//    }
//}
//
