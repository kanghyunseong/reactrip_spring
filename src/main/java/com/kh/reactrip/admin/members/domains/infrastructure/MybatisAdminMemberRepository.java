//package com.kh.reactrip.admin.members.infrastructure;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import org.apache.ibatis.session.RowBounds;
//import org.springframework.stereotype.Repository;
//
//import com.kh.reactrip.admin.members.domain.AdminMember;
//import com.kh.reactrip.admin.members.model.dto.AdminMemberDTO;
//import com.kh.reactrip.admin.members.model.mapper.AdminMemberMapper;
//import com.kh.reactrip.util.PageInfo;
//
//import lombok.RequiredArgsConstructor;
//
//@Repository
//@RequiredArgsConstructor
//public class MybatisAdminMemberRepository implements AdminMemberRepository {
//
//    private final AdminMemberMapper adminMemberMapper;
//
//    @Override
//    public int getTotalCount() {
//        return adminMemberMapper.getTotalCount();
//    }
//
//    @Override
//    public List<AdminMember> findAllMembers(PageInfo pageInfo) {
//        RowBounds rowBounds = createRowBounds(pageInfo);
//        List<AdminMemberDTO> dtos = adminMemberMapper.findAllMembers(rowBounds);
//        return dtos.stream()
//                .map(this::toDomain)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<AdminMember> findByMembers(String keyword) {
//        List<AdminMemberDTO> dtos = adminMemberMapper.findByMembers(keyword);
//        return dtos.stream()
//                .map(this::toDomain)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public Optional<AdminMember> findMemberByNo(Long memberNo) {
//        AdminMemberDTO dto = adminMemberMapper.findMemberByNo(memberNo);
//        return Optional.ofNullable(dto).map(this::toDomain);
//    }
//
//    @Override
//    public void updateMemberRole(Long memberNo, String memberRole) {
//        adminMemberMapper.updateMemberRole(memberNo, memberRole);
//    }
//
//    @Override
//    public void deleteMember(Long memberNo) {
//        adminMemberMapper.deleteMember(memberNo);
//    }
//
//    private RowBounds createRowBounds(PageInfo pi) {
//        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
//        return new RowBounds(offset, pi.getBoardLimit());
//    }
//
//    private AdminMember toDomain(AdminMemberDTO dto) {
//        return new AdminMember(
//                dto.getMemberNo(),
//                dto.getMemberName(),
//                dto.getBirthday(),
//                dto.getPhone(),
//                dto.getEmail(),
//                dto.getEnrollDate() != null ? dto.getEnrollDate().toLocalDate() : null,
//                dto.getMemberRole(),
//                dto.getImage()
//        );
//    }
//}