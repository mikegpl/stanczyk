package agh.sm.estimator;

public class DeviceParameters {

    public enum Cpu {
        FAST(10),
        SLOW(1);

        Cpu(int rating) {
        }
    }

    public enum NetworkSpeed {
        FAST(10),
        SLOW(1);

        NetworkSpeed(int rating) {
        }
    }

    private final Cpu cpu;
    private final NetworkSpeed networkSpeed;

    public DeviceParameters(Cpu cpu, NetworkSpeed networkSpeed) {
        this.cpu = cpu;
        this.networkSpeed = networkSpeed;
    }

    public Cpu getCpu() {
        return cpu;
    }

    public NetworkSpeed getNetworkSpeed() {
        return networkSpeed;
    }
}
