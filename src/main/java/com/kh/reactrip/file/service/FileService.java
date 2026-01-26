package com.kh.reactrip.file.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class FileService {

    private final S3Service s3Service;

    public String store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        return s3Service.fileSave(file);
    }

    public void delete(String fileUrl) {
        if (fileUrl != null && !fileUrl.isEmpty()) {
            s3Service.deleteFile(fileUrl);
        }
    }
    
    public String updateFile(MultipartFile newFile, String oldFileurl) {
    	if(newFile == null || newFile.isEmpty()) {
    		return oldFileurl;
    	}
    	
    	if(oldFileurl != null && !oldFileurl.isEmpty()) {
    		this.delete(oldFileurl);
    	}
    	
    	return this.store(newFile);
    }
}
