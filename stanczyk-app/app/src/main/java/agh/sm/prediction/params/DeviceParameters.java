package agh.sm.prediction.params;

import stanczyk.Stanczyk;

public class DeviceParameters {

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
    private final Cpu cpu;
    private final NetworkSpeed networkSpeed;

    public DeviceParameters(Cpu cpu, NetworkSpeed networkSpeed) {
        this.cpu = cpu;
        this.networkSpeed = networkSpeed;
    }

    /*
     * Use this to map device parameters to proto when sending knowledge to the server
     * Possibly change return type
     */
    public Stanczyk.DeviceExecutorMetadata toStanczykALALALALChangeName() {
        // TODO : Implement
        return Stanczyk.DeviceExecutorMetadata.newBuilder()
                .build();
    }

    /*
     * Used by estimators and when sending knowledge to Stanczyk
     */
    public int getDevicePerformanceLevel() {
        return cpu.getRating() + networkSpeed.getRating();
    }
}
