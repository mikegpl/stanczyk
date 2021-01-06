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

import agh.sm.prediction.params.DeviceParameters;

public class MetricCollector {

    private final ActivityManager activityManager;
    private final BatteryManager batteryManager;
    private final ConnectivityManager connectivityManager;


    public MetricCollector(Context context) {
        this.activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        this.batteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
        this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public DeviceParameters getDeviceParams() {
        ActivityManager.MemoryInfo memoryInfo = getMemory();
        return new DeviceParameters(getCpuCount(), getNetworkDownloadSpeed(), memoryInfo.availMem, memoryInfo.totalMem, getSDKScore(), getBatteryPercentage());
    }

    private int getCpuCount() {
        Pattern pattern = Pattern.compile("cpu[0-9]+");
        int count = (int) Arrays.stream(new File("/sys/devices/system/cpu/").list())
                .filter(n -> pattern.matcher(n).matches())
                .count();
        return Math.max(count, Runtime.getRuntime().availableProcessors());
    }

    private ActivityManager.MemoryInfo getMemory() {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo;
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

    public double getBatteryPercentage() {
        return batteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY) / (double) batteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER);
    }

    /*
     * Instantaneous battery current in microamperes, as an integer.
     */
    public int getBatteryCurrent() {
        return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
    }
}
