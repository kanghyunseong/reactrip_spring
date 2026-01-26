package com.kh.reactrip.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseData<T> {

	private String message;
	private T data;
	private String success;

	private ResponseData(String message, T data, String success) {
		this.message = message;
		this.data = data;
		this.success = success;

	}

	// 성공 응답
	public static <T> ResponseEntity<ResponseData<T>> ok(T data) {
		return ResponseEntity.ok(new ResponseData<T>(null, data, "요청성공"));
	}

	public static <T> ResponseEntity<ResponseData<T>> ok(T data, String message) {
		return ResponseEntity.ok(new ResponseData<T>(message, data, "등록 성공"));
	}

	// 실패응답
	public static <T> ResponseEntity<ResponseData<T>> failure(String message, HttpStatus status) {
		return ResponseEntity.status(status).body(new ResponseData<T>(message, null, "요청실패"));
	}

	// 3. 데이터 없이 상태 코드만 보내는 경우 (204 No Content)
	public static <T> ResponseEntity<ResponseData<T>> noContent() {
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	// 4. 생성 성공 (201 Created)
	public static <T> ResponseEntity<ResponseData<T>> created(T data) {
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseData<>("생성되었습니다.", data, "요청 성공"));
	}


}
