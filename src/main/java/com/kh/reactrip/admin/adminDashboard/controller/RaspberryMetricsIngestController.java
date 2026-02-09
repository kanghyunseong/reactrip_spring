package com.kh.reactrip.admin.adminDashboard.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/monitoring")
public class RaspberryMetricsIngestController {

    private final SimpMessagingTemplate messagingTemplate;

    /**
     * 라즈베리파이(또는 기타 디바이스)가 측정한 실시간 메트릭을 수신해,
     * 프론트에서 구독 중인 토픽(/topic/cpu)으로 그대로 브로드캐스트합니다.
     */
    @PostMapping("/raspberry")
    public ResponseEntity<Map<String, Object>> ingest(@RequestBody Map<String, Object> payload) {
        Map<String, Object> data = (payload == null) ? new HashMap<>() : new HashMap<>(payload);
        data.putIfAbsent("timestamp", System.currentTimeMillis());
        // 프론트 구독 토픽
        messagingTemplate.convertAndSend("/topic/cpu", data);
        return ResponseEntity.ok(Map.of("ok", true));
    }
}

