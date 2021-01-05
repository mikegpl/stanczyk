package agh.sm.prediction.estimators;


import agh.sm.prediction.params.DeviceParameters;
import agh.sm.prediction.params.TaskParameters;

/*
 * Predict how hard given task is
 */
public class DeviceComputeEstimator {

    public double getDeviceComputeTime(TaskParameters taskParameters, DeviceParameters deviceParameters) {
        return taskParameters.getTaskHardnessLevel() / deviceParameters.getDevicePerformanceLevel();
    }
}
