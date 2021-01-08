package agh.sm.prediction.params;

import stanczyk.Stanczyk;

public class DeviceParameters {

    private final long availMem;
    private final long totalMem;
    private final int sdkScore;
    private final double batteryPercentage;

    public enum Cpu {
        FAST(10),
        SLOW(1);

        private final int rating;

        Cpu(int rating) {
            this.rating = rating;
        }

        public int getRating() {
            return rating;
        }
    }

    public enum NetworkSpeed {
        FAST(10),
        SLOW(1);

        private final int rating;

        NetworkSpeed(int rating) {
            this.rating = rating;
        }

        public int getRating() {
            return rating;
        }
    }

    /*
     * TODO :
     *  Replace or fix enums
     *  Add more metrics (SDK lvl, memory, ...)
     */
    private final int cpuCount;
    private final int networkSpeed;

    public DeviceParameters(int cpuCount, int networkSpeed, long availMem, long totalMem, int sdkScore, double batteryPercentage) {
        this.cpuCount = cpuCount;
        this.networkSpeed = networkSpeed;
        this.availMem = availMem;
        this.totalMem = totalMem;
        this.sdkScore = sdkScore;
        this.batteryPercentage = batteryPercentage;
    }

    /*
     * Use this to map device parameters to proto when sending knowledge to the server
     * Possibly change return type
     */
    public Stanczyk.DeviceExecutorMetadata toDto() {
        return Stanczyk.DeviceExecutorMetadata.newBuilder()
                .setCpuRating(cpuCount)
                .setNetworkRating(networkSpeed)
                .setMemoryAvailable(availMem)
                .setTotalMemory(totalMem)
                .setSdkScore(sdkScore)
                .setBatteryPercentage(batteryPercentage)
                .build();
    }
}