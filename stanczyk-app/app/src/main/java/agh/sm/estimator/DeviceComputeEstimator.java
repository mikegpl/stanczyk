package agh.sm.estimator;


/*
 * Predict how hard given task is
 */
public class DeviceComputeEstimator {

    public double getDeviceComputeTime(TaskParameters taskParameters, DeviceParameters deviceParameters) {
        return taskParameters.getTaskHardnessLevel() / deviceParameters.getDevicePerformanceLevel();
    }
}
