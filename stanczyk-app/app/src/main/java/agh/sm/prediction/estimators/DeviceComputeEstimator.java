package agh.sm.prediction.estimators;


import agh.sm.prediction.params.DeviceParameters;
import agh.sm.prediction.params.TaskParameters;
import smile.classification.KNN;


/*
 * Predict how hard given task is
 */
public class DeviceComputeEstimator {


    public DeviceComputeEstimator() {
    }

    public double getDeviceComputeTime(TaskParameters taskParameters, DeviceParameters deviceParameters) {
        return 2137.0;
    }

}
