package agh.sm.prediction;

import agh.sm.metrics.MetricCollector;
import agh.sm.prediction.estimators.CloudComputeEstimator;
import agh.sm.prediction.estimators.DeviceComputeEstimator;
import agh.sm.prediction.estimators.TransmissionEstimator;
import agh.sm.prediction.params.DeviceParameters;
import agh.sm.prediction.params.TaskParameters;

public class ExecutionPredictor {

    private final String TAG = "Predictor";

    private final TransmissionEstimator transmissionEstimator;
    private final DeviceComputeEstimator deviceComputeEstimator;
    private final CloudComputeEstimator cloudComputeEstimator;


    public ExecutionPredictor(MetricCollector metricCollector) {
        this.transmissionEstimator = new TransmissionEstimator(metricCollector);
        this.deviceComputeEstimator = new DeviceComputeEstimator();
        this.cloudComputeEstimator = new CloudComputeEstimator();
    }

    public ExecutionTarget predict(TaskParameters taskParameters, DeviceParameters deviceParameters) {
        double totalCloudExecutionTime = cloudComputeEstimator.getCloudComputeTime(taskParameters) +
                2 * transmissionEstimator.getTransmissionTime(taskParameters, deviceParameters);

        double deviceExecutionTime = deviceComputeEstimator.getDeviceComputeTime(taskParameters, deviceParameters);

        return totalCloudExecutionTime < deviceExecutionTime ? ExecutionTarget.CLOUD : ExecutionTarget.DEVICE;
    }

    // TODO - implement learning (simplest one would be "learn after every knowledge exchange"... and I'm simple man)
    // in that case stanczyk would
//    /*
//     * Update predictor with Stanczyk knowledge
//     */
//    public void teachFromCloud(Pair<DeviceParameters, TaskParameters> taskInputStanczykKnowledge, TaskExecutionParameters taskResultStanczykKnowledge) {
//        // TODO : implement
//        knowledge.put(taskInputStanczykKnowledge, taskResultStanczykKnowledge);
//    }
//
//    /*
//     * Save results from locally executed task
//     */
//    public void teachFromDevice(long executionTime, long energyDelta) {
//        // TODO : implement
//        Log.i(TAG, "Teaching Local Model time : " + executionTime + " | energy : " + energyDelta);
//        knowledge.put(null, null);
//    }

    public enum ExecutionTarget {
        DEVICE,
        CLOUD;
    }
}
