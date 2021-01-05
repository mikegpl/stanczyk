package agh.sm.prediction;

import android.util.Log;
import android.util.Pair;

import java.util.HashMap;
import java.util.Map;

import agh.sm.prediction.estimators.CloudComputeEstimator;
import agh.sm.prediction.estimators.DeviceComputeEstimator;
import agh.sm.prediction.params.DeviceParameters;
import agh.sm.prediction.params.TaskExecutionParameters;
import agh.sm.prediction.params.TaskParameters;
import agh.sm.prediction.estimators.TransmissionEstimator;
import agh.sm.metrics.MetricCollector;

public class ExecutionPredictor {

    private final String TAG = "Predictor";

    private final TransmissionEstimator transmissionEstimator;
    private final DeviceComputeEstimator deviceComputeEstimator;
    private final CloudComputeEstimator cloudComputeEstimator;
    private Map<Pair<DeviceParameters, TaskParameters>, TaskExecutionParameters> knowledge;


    public ExecutionPredictor(MetricCollector metricCollector) {
        this.transmissionEstimator = new TransmissionEstimator(metricCollector);
        this.deviceComputeEstimator = new DeviceComputeEstimator();
        this.cloudComputeEstimator = new CloudComputeEstimator();
        this.knowledge = new HashMap<>();
    }

    public ExecutionTarget predict(TaskParameters taskParameters, DeviceParameters deviceParameters) {
        double totalCloudExecutionTime = cloudComputeEstimator.getCloudComputeTime(taskParameters) +
                2 * transmissionEstimator.getTransmissionTime(taskParameters, deviceParameters);

        double deviceExecutionTime = deviceComputeEstimator.getDeviceComputeTime(taskParameters, deviceParameters);

        return totalCloudExecutionTime < deviceExecutionTime ? ExecutionTarget.CLOUD : ExecutionTarget.DEVICE;
    }

    /*
     * Update predictor with Stanczyk knowledge
     */
    public void teachFromCloud(Pair<DeviceParameters, TaskParameters> taskInputStanczykKnowledge, TaskExecutionParameters taskResultStanczykKnowledge) {
        // TODO : implement
        knowledge.put(taskInputStanczykKnowledge, taskResultStanczykKnowledge);
    }

    /*
     * Save results from locally executed task
     */
    public void teachFromDevice(long executionTime, long energyDelta) {
        // TODO : implement
        Log.i(TAG, "Teaching Local Model time : " + executionTime + " | energy : " + energyDelta);
        knowledge.put(null, null);
        return;
    }

    public enum ExecutionTarget {
        DEVICE,
        CLOUD;
    }
}
