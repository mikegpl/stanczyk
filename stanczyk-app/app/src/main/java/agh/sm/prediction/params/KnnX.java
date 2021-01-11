package agh.sm.prediction.params;

import smile.math.distance.Distance;


public class KnnX {
    private final TaskParametersMetadata taskParametersMetadata;
    private final DeviceParameters deviceParameters;

    public KnnX(TaskParametersMetadata taskParametersMetadata, DeviceParameters deviceParameters) {
        this.taskParametersMetadata = taskParametersMetadata;
        this.deviceParameters = deviceParameters;
    }

    public TaskParametersMetadata getTaskParametersMetadata() {
        return taskParametersMetadata;
    }

    public DeviceParameters getDeviceParameters() {
        return deviceParameters;
    }

    public static Distance<KnnX> getDistance() {
        /*
         * TODO : Implemnt real distance metric
         */
        return (x, y) -> {
            double distance = 0;
            distance += Math.abs(x.deviceParameters.getCpuCount() - y.deviceParameters.getCpuCount())*10; // CPU distance
            distance *= (double) x.taskParametersMetadata.getTaskSize() / y.taskParametersMetadata.getTaskSize();
            return distance;
        };
    }
}
