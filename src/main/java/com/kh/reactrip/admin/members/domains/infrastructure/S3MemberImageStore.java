//package com.kh.reactrip.admin.members.infrastructure;
//
//import org.springframework.stereotype.Component;
//
//import com.kh.reactrip.admin.members.domain.MemberImageStore;
//import com.kh.reactrip.file.service.S3Service;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class S3MemberImageStore implements MemberImageStore {
//
//	private final S3Service s3Service;
//	
//	@Override
//    public void deleteImage(String imageUrl) {
//        try {
//            // S3 삭제 로직을 여기에 격리시킴
//            s3Service.deleteFile(imageUrl);
//            log.info("S3 이미지 삭제 성공 : {}", imageUrl);
//        } catch (Exception e) {
//            // 에러가 나도 DB 삭제는 진행해야 하므로 로그만 찍고 넘어감 (비즈니스 정책에 따라 다름)
//            log.error("S3 삭제 중 오류 발생 (DB 삭제 진행): {}", e.getMessage());
//        }
//    }
//
//}
