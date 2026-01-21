//// 예: com.kh.reactrip.admin.members.domain.AdminMember
//package com.kh.reactrip.admin.members.domain;
//
//import java.time.LocalDate;
//
//import lombok.Value;
//
//@Value
//public class AdminMember {
//    Long memberNo;
//    String memberName;
//    String birthday;
//    String phone;
//    String email;
//    LocalDate enrollDate;
//    String memberRole;
//    String image;
//    
//    public boolean hasCustomImage() {
//    	return this.image != null 
//    			&& !this.image.isEmpty()
//    			&& !this.image.contains("default");
//    }
//}