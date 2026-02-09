package com.kh.reactrip.admin.adminDashboard.model.service;

import org.springframework.stereotype.Service;

import oshi.SystemInfo;
import oshi.hardware.NetworkIF;
import oshi.hardware.Sensors;

@Service
public class TempService {
	
	private final SystemInfo si = new SystemInfo();
	private final Sensors sensors = si.getHardware().getSensors();
	
	public String getMacAddress() {
		try {
			for (NetworkIF nif : si.getHardware().getNetworkIFs()) {
				if (nif == null) continue;
				String mac = nif.getMacaddr();
				if (mac != null && !mac.isBlank()) return mac;
			}
		} catch (Exception ignored) {
		}
		return "unknown";
	}

	// CPU 온도(°C). 지원하지 않는 환경이면 0.0 반환될 수 있음
	public double getCpuTemperatureC() {
		double temp = sensors.getCpuTemperature();
		return Math.round(temp * 10) / 10.0;
	}

}
