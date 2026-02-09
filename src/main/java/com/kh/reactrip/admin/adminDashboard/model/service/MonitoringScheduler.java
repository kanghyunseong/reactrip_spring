package com.kh.reactrip.admin.adminDashboard.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Profile("local")
public class MonitoringScheduler {
    private final SimpMessagingTemplate messagingTemplate;
    private final CpuService cpuService;

    @Scheduled(fixedRate = 1000)
    public void broadcastStatus() {
        Map<String, Object> data = new HashMap<>();
        data.put("deviceId", cpuService.getMacAddress());
        data.put("hostname", cpuService.getHostname());
        data.put("cpuUsage", cpuService.getCpuUsage());
        data.put("cpuTempC", cpuService.getCpuTemperatureC());
        data.put("ramTotalBytes", cpuService.getRamTotalBytes());
        data.put("ramUsedBytes", cpuService.getRamUsedBytes());
        data.put("ramUsage", cpuService.getRamUsagePercent());
        data.put("diskTotalBytes", cpuService.getDiskTotalBytes());
        data.put("diskUsedBytes", cpuService.getDiskUsedBytes());
        data.put("diskUsage", cpuService.getDiskUsagePercent());
        data.put("netRxBps", cpuService.getNetRxBps());
        data.put("netTxBps", cpuService.getNetTxBps());
        data.put("load1", cpuService.getLoad1());
        data.put("load5", cpuService.getLoad5());
        data.put("load15", cpuService.getLoad15());
        data.put("uptimeSec", cpuService.getUptimeSeconds());
        data.put("timestamp", System.currentTimeMillis());
        messagingTemplate.convertAndSend("/topic/cpu", data);
    }
}

