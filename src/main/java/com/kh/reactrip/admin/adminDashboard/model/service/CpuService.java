package com.kh.reactrip.admin.adminDashboard.model.service;

import org.springframework.stereotype.Service;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.NetworkIF;
import oshi.hardware.Sensors;
import oshi.software.os.FileSystem;
import oshi.software.os.NetworkParams;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

@Service
public class CpuService {
    private final SystemInfo si = new SystemInfo();
    private final CentralProcessor processor = si.getHardware().getProcessor();
    private final GlobalMemory memory = si.getHardware().getMemory();
    private final Sensors sensors = si.getHardware().getSensors();
    private final OperatingSystem os = si.getOperatingSystem();
    private long[] prevTicks = new long[CentralProcessor.TickType.values().length];
    private volatile NetworkIF primaryNif;
    private volatile long prevRxBytes = -1L;
    private volatile long prevTxBytes = -1L;
    private volatile long prevNetAtMs = -1L;

    // 기기 고유 MAC 주소 가져오기
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

    private NetworkIF getPrimaryNetworkIF() {
        if (primaryNif != null) return primaryNif;
        try {
            for (NetworkIF nif : si.getHardware().getNetworkIFs()) {
                if (nif == null) continue;
                String name = nif.getName();
                String mac = nif.getMacaddr();
                if (name != null && name.equalsIgnoreCase("lo")) continue;
                if (mac == null || mac.isBlank()) continue;
                if ("00:00:00:00:00:00".equals(mac)) continue;
                primaryNif = nif;
                return nif;
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    public double getCpuUsage() {
        double usage = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
        prevTicks = processor.getSystemCpuLoadTicks();
        return Math.round(usage * 10) / 10.0;
    }

    public double getCpuTemperatureC() {
        double temp = sensors.getCpuTemperature();
        return Math.round(temp * 10) / 10.0;
    }

    public long getRamTotalBytes() {
        return memory.getTotal();
    }

    public long getRamUsedBytes() {
        long total = memory.getTotal();
        long available = memory.getAvailable();
        long used = total - available;
        return Math.max(used, 0L);
    }

    public double getRamUsagePercent() {
        long total = getRamTotalBytes();
        if (total <= 0) return 0.0;
        double pct = (getRamUsedBytes() * 100.0) / total;
        return Math.round(pct * 10) / 10.0;
    }

    private OSFileStore getPrimaryFileStore() {
        try {
            FileSystem fs = os.getFileSystem();
            for (OSFileStore store : fs.getFileStores()) {
                if (store == null) continue;
                String mount = store.getMount();
                if (mount != null && (mount.equals("/") || mount.equalsIgnoreCase("/root") || mount.startsWith("/"))) {
                    return store;
                }
            }
            for (OSFileStore store : fs.getFileStores()) {
                if (store != null) return store;
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    public long getDiskTotalBytes() {
        OSFileStore store = getPrimaryFileStore();
        return store == null ? 0L : store.getTotalSpace();
    }

    public long getDiskUsedBytes() {
        OSFileStore store = getPrimaryFileStore();
        if (store == null) return 0L;
        long total = store.getTotalSpace();
        long usable = store.getUsableSpace();
        long used = total - usable;
        return Math.max(used, 0L);
    }

    public double getDiskUsagePercent() {
        long total = getDiskTotalBytes();
        if (total <= 0) return 0.0;
        double pct = (getDiskUsedBytes() * 100.0) / total;
        return Math.round(pct * 10) / 10.0;
    }

    public long getUptimeSeconds() {
        try {
            return os.getSystemUptime();
        } catch (Exception ignored) {
            return 0L;
        }
    }

    public double getLoad1() {
        double[] la = processor.getSystemLoadAverage(3);
        return (la == null || la.length < 1 || la[0] < 0) ? 0.0 : Math.round(la[0] * 100) / 100.0;
    }

    public double getLoad5() {
        double[] la = processor.getSystemLoadAverage(3);
        return (la == null || la.length < 2 || la[1] < 0) ? 0.0 : Math.round(la[1] * 100) / 100.0;
    }

    public double getLoad15() {
        double[] la = processor.getSystemLoadAverage(3);
        return (la == null || la.length < 3 || la[2] < 0) ? 0.0 : Math.round(la[2] * 100) / 100.0;
    }

    public long getNetRxBps() {
        updateNetCounters();
        return lastRxBps;
    }

    public long getNetTxBps() {
        updateNetCounters();
        return lastTxBps;
    }

    private volatile long lastRxBps = 0L;
    private volatile long lastTxBps = 0L;

    private void updateNetCounters() {
        NetworkIF nif = getPrimaryNetworkIF();
        if (nif == null) return;
        try {
            nif.updateAttributes();
            long rx = nif.getBytesRecv();
            long tx = nif.getBytesSent();
            long now = System.currentTimeMillis();

            if (prevRxBytes >= 0 && prevTxBytes >= 0 && prevNetAtMs > 0) {
                long dtMs = now - prevNetAtMs;
                if (dtMs > 0) {
                    long dRx = rx - prevRxBytes;
                    long dTx = tx - prevTxBytes;
                    lastRxBps = (long) ((dRx * 1000.0) / dtMs);
                    lastTxBps = (long) ((dTx * 1000.0) / dtMs);
                }
            }

            prevRxBytes = rx;
            prevTxBytes = tx;
            prevNetAtMs = now;
        } catch (Exception ignored) {
        }
    }

    public String getHostname() {
        try {
            NetworkParams np = os.getNetworkParams();
            String hn = np.getHostName();
            return (hn == null || hn.isBlank()) ? "unknown" : hn;
        } catch (Exception ignored) {
            return "unknown";
        }
    }
}
