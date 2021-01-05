package agh.sm.metrics;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.BatteryManager;
import android.os.Build;

import java.io.File;
import java.util.Arrays;
import java.util.regex.Pattern;

import agh.sm.estimator.DeviceParameters;

public class MetricCollector {

    private final ActivityManager activityManager;
    private final BatteryManager batteryManager;
    private final ConnectivityManager connectivityManager;


    public MetricCollector(Context context) {
        this.activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        this.batteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
        this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /*
     * TODO : Add more metrics to DeviceParameters
     */
    public DeviceParameters getDeviceKnowledge() {
        DeviceParameters.Cpu cpuSpeed = getCpuCount() > 2 ?
                DeviceParameters.Cpu.FAST : DeviceParameters.Cpu.SLOW;
        DeviceParameters.NetworkSpeed networkSpeed = getNetworkDownloadSpeed() > 1024 * 8 * 1024 ?
                DeviceParameters.NetworkSpeed.FAST : DeviceParameters.NetworkSpeed.SLOW;
        return new DeviceParameters(cpuSpeed, networkSpeed);
    }

    public int getCpuCount() {
        Pattern pattern = Pattern.compile("cpu[0-9]+");
        int count = (int) Arrays.stream(new File("/sys/devices/system/cpu/").list())
                .filter(n -> pattern.matcher(n).matches())
                .count();
        return Math.max(count, Runtime.getRuntime().availableProcessors());
    }

    public long getTotalMemory() {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.totalMem;
    }

    public long getAvailableMemory() {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    /*
     * Max Score - 10
     * Min Score - 0
     */
    public int getSDKScore() {
        return Math.min(10 - (30 - Build.VERSION.SDK_INT), 0);
    }

    public NetworkCapabilities getActiveNetworkCapabilities() {
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
        return networkCapabilities;
    }

    /*
     * G GPRS ~26.8 Kbps
     * E EDGE ~108.8 Kbps
     * 3G UMTS ~128 Kbps
     * H HSPA ~3.6 Mbps
     * H+ HSPA+ ~14.4 Mbps-23.0 Mbps
     * 4G LTE ~50 Mbps
     */
    public int getNetworkDownloadSpeed() {
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        return networkCapabilities.getLinkDownstreamBandwidthKbps();

    }

    public int getNetworkUploadSpeed() {
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        return networkCapabilities.getLinkUpstreamBandwidthKbps();

    }

    public boolean isNetworkCellular() {
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
    }

    /*
     * Battery remaining energy in nanowatt-hours, as a long integer
     */
    public long getBatteryEnergy() {
        return batteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER);
    }

    /*
     * Instantaneous battery current in microamperes, as an integer.
     */
    public int getBatteryCurrent() {
        return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
    }
}
