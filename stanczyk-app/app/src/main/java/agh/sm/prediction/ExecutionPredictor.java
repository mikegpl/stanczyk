package agh.sm.prediction;

import android.util.Log;

import agh.sm.metrics.MetricCollector;
import agh.sm.prediction.estimators.CloudComputeEstimator;
import agh.sm.prediction.estimators.DeviceComputeEstimator;
import agh.sm.prediction.estimators.TransmissionEstimator;
import agh.sm.prediction.params.DeviceParameters;
import agh.sm.prediction.params.KnnX;
import agh.sm.prediction.params.TaskParameters;
import smile.classification.KNN;

public class ExecutionPredictor {

    private final String TAG = "ExecutionPredictor";

    private final TransmissionEstimator transmissionEstimator;
    private final DeviceComputeEstimator deviceComputeEstimator;
    private final CloudComputeEstimator cloudComputeEstimator;

    private KNN<KnnX> knn;

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


    public ExecutionTarget predictKNN(TaskParameters taskParameters, DeviceParameters deviceParameters) {
        ExecutionTarget executionTarget = knn.predict(new KnnX(taskParameters, deviceParameters)) == 1 ? ExecutionTarget.CLOUD : ExecutionTarget.DEVICE;
        Log.i(TAG, "Prediction : " + executionTarget + " for TP: " + taskParameters + " , DP: " + deviceParameters);
        return executionTarget;
    }

    // TODO - implement learning (simplest one would be "learn after every knowledge exchange"... and I'm simple man)

    /*
     * TODO - Call this form ExecutionService, depeding on KnowledgeExchaneStrategy
     *  Supply Knowledge from KnowledgeStore
     */
    public void learn(KnnX[] x, int[] y) {
        knn = KNN.fit(x, y, 3, KnnX.getDistance());
    }


    public enum ExecutionTarget {
        DEVICE,
        CLOUD;
    }
}
