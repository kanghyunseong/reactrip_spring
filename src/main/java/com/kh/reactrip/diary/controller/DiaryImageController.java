package com.kh.reactrip.diary.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.reactrip.file.service.S3Service;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/diarys")
@RequiredArgsConstructor
public class DiaryImageController {

	private final S3Service s3Service;
	
	
@PostMapping(value = "/upload/diary-image", consumes = "multipart/form-data")
public ResponseEntity<List<String>> uploadDiaryImages(@RequestParam("images") List<MultipartFile> images) {
	
	System.out.println("images size = " + images.size());
	
	List<String> imageUrls = new ArrayList<>();
	
	for (MultipartFile file : images) {
		if(file == null || file.isEmpty()) continue;
		
        String imageUrl = s3Service.fileSave(file);
        imageUrls.add(imageUrl);
    }

    return ResponseEntity.ok(imageUrls);
	
	}
}
