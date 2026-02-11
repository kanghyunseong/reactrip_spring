package com.kh.reactrip.admin.travel.runner;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.kh.reactrip.admin.travel.model.service.AdminTravelService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * API 동기화를 비동기로 실행합니다.
 * HTTP 요청은 즉시 202로 반환하고, 실제 동기화는 백그라운드에서 수행되어
 * 게이트웨이 타임아웃(504)을 방지합니다.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AdminTravelSyncRunner {

	private final AdminTravelService adminTravelService;

	@Async
	public void runSync() {
		try {
			adminTravelService.fetchAndSaveApiData();
			log.info("[Admin] API 동기화 백그라운드 작업 완료");
		} catch (Exception e) {
			log.error("[Admin] API 동기화 백그라운드 작업 실패", e);
		}
	}
}
