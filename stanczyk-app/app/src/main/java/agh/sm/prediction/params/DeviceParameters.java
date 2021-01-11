package agh.sm.prediction.params;

import stanczyk.Stanczyk;

public class DeviceParameters {

    /*
     * TODO :
     *  Replace or fix enums
     *  Add more metrics (SDK lvl, memory, ...)
     */
    private final long cpuCount;
    private final long networkSpeed;
    private final long availMem;
    private final long totalMem;
    private final long sdkScore;
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



    public DeviceParameters(long cpuCount, long networkSpeed, long availMem, long totalMem, long sdkScore, double batteryPercentage) {
        this.cpuCount = cpuCount;
        this.networkSpeed = networkSpeed;
        this.availMem = availMem;
        this.totalMem = totalMem;
        this.sdkScore = sdkScore;
        this.batteryPercentage = batteryPercentage;
    }

    public static DeviceParameters fromStanczykDto(Stanczyk.DeviceExecutorMetadata dto) {
        return new DeviceParameters(dto.getCpuRating(),
                dto.getNetworkRating(),
                dto.getMemoryAvailable(),
                dto.getTotalMemory(),
                dto.getSdkScore(),
                dto.getBatteryPercentage());
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

    public long getCpuCount() {
        return cpuCount;
    }

    public long getNetworkSpeed() {
        return networkSpeed;
    }

    public long getAvailMem() {
        return availMem;
    }

    public long getTotalMem() {
        return totalMem;
    }

    public long getSdkScore() {
        return sdkScore;
    }

    public double getBatteryPercentage() {
        return batteryPercentage;
    }
}