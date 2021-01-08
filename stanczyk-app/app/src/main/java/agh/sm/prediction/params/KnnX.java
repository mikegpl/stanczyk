package agh.sm.prediction.params;

import agh.sm.prediction.estimators.DeviceComputeEstimator;
import smile.math.distance.Distance;


public class KnnX {
    private final TaskParameters taskParameters;
    private final DeviceParameters deviceParameters;

    public KnnX(TaskParameters taskParameters, DeviceParameters deviceParameters) {
        this.taskParameters = taskParameters;
        this.deviceParameters = deviceParameters;
    }

    public TaskParameters getTaskParameters() {
        return taskParameters;
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
            distance *= (double) x.taskParameters.getTaskSize() / y.taskParameters.getTaskSize();
            return distance;
        };
    }
}
