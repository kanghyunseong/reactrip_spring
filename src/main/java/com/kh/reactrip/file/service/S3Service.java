package com.kh.reactrip.file.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
@RequiredArgsConstructor
public class S3Service {

   // 데이터 마이그레이션 작업

   private final S3Client s3Client;
   @Value("${cloud.aws.s3.bucket}")
   private String bucketName;

   @Value("${cloud.aws.region.static}")
   private String region;

   // 업로드 메소드
   public String fileSave(MultipartFile file) {

      // 파일 이름 바꿔주기 중복 x
      String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
      String contentType = file.getContentType();
      if (contentType == null || contentType.isEmpty()) {
         contentType = "application/octet-stream";
      }

      // s3에 업로드
      PutObjectRequest request = PutObjectRequest.builder().bucket(bucketName).key(fileName)
            .contentType(contentType).build();

      try {
         s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
      } catch (S3Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (AwsServiceException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (SdkClientException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      String filePath = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;

      return filePath;
   }

   public void deleteFile(String filePath) {
      // https://butcket-name.s3.region.amazonaws.com/넘겨받은filePath
      String objectKey = getObjectKeyFromUrl(filePath);

      try {
         DeleteObjectRequest request = DeleteObjectRequest.builder().bucket(bucketName).key(objectKey).build();

         s3Client.deleteObject(request);
      } catch (Exception e) {
         e.printStackTrace();
         throw new RuntimeException("S3파일 삭제 실패 " + e.getMessage());
      }
   }

   private String getObjectKeyFromUrl(String filePath) {

      if (filePath == null || filePath.isEmpty()) {
         return null;
      }

      try {
         URL url = new URL(filePath);
         String path = url.getPath();
         System.out.println(path);

         return path.substring(1);
      } catch (MalformedURLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return "";
   }

   public List<String> upload(List<MultipartFile> images) {
		List<String> outList = new ArrayList<String>();
		if (images != null && images.size() > 0 ) {
			for (MultipartFile multipartFile : images) {
				String urlStr = fileSave(multipartFile);
				outList.add(urlStr);
			}
		}
		return outList;
	}

}
