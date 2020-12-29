package agh.sm.estimator;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExecutionPredictor {

    private final String TAG = "Predictor";

    private final TransmisionEstimator transmisionEstimator;
    private final DeviceComputeEstimator deviceComputeEstimator;
    private final CloudComputeEstimator cloudComputeEstimator;
    private Map<Pair<DeviceParameters, TaskParameters>, Long> pastPredictions;


    public ExecutionPredictor() {
        this.transmisionEstimator = new TransmisionEstimator();
        this.deviceComputeEstimator = new DeviceComputeEstimator();
        this.cloudComputeEstimator = new CloudComputeEstimator();
        this.pastPredictions = new HashMap<>();
    }

    public ExecutionTarget predict(TaskParameters taskParameters, DeviceParameters deviceParameters) {
        long totalCloudExecutionTime = cloudComputeEstimator.getCloudComputeTime(taskParameters) +
                2 * transmisionEstimator.getTransmisionTime(taskParameters, deviceParameters);

        long deviceExecutionTime = deviceComputeEstimator.getDeviceComputeTime(taskParameters, deviceParameters);

        return totalCloudExecutionTime < deviceExecutionTime ? ExecutionTarget.CLOUD : ExecutionTarget.DEVICE;
    }

    /*
     * Update predictor with cloud knowledge
     */
    public void teachFromCloud() {
        return;
    }

    /*
     * Save results from locally executed task
     */
    public void teachFromDevice(long executionTime, long energyDelta) {
        Log.i(TAG, "Teaching Local Model time : " + executionTime + " | energy : " + energyDelta);
        return;
    }

    public enum ExecutionTarget {
        DEVICE,
        CLOUD;
    }
}
