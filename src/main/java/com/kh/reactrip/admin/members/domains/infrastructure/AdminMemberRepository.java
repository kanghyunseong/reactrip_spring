//package com.kh.reactrip.admin.members.infrastructure;
//
//import java.util.List;
//import java.util.Optional;
//
//import com.kh.reactrip.admin.members.domain.AdminMember;
//import com.kh.reactrip.util.PageInfo;
//
//public interface AdminMemberRepository {
//	
//	int getTotalCount();
//
//	List<AdminMember> findAllMembers(PageInfo pageInfo);
//
//	List<AdminMember> findByMembers(String keyword);
//
//	Optional<AdminMember> findMemberByNo(Long memberNo);
//
//	void updateMemberRole(Long memberNo, String memberRole);
//
//	void deleteMember(Long memberNo);
//}
