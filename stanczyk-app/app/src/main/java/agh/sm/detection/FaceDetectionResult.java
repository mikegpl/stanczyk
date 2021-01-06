package agh.sm.detection;

public class FaceDetectionResult {
    private final int time;
    private final int batteryDelta;
    private final int x;
    private final int y;
    private final int w;
    private final int h;

    public FaceDetectionResult(int time, int batteryDelta, int x, int y, int w, int h) {
        this.time = time;
        this.batteryDelta = batteryDelta;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public int getTime() {
        return time;
    }

    public int getBatteryDelta() {
        return batteryDelta;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    @Override
    public String toString() {
        return "FaceDetectionResult{" +
                "time=" + time +
                ", batteryDelta=" + batteryDelta +
                ", x=" + x +
                ", y=" + y +
                ", w=" + w +
                ", h=" + h +
                '}';
    }
}
