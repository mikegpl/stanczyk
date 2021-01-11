package agh.sm.prediction;

import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import agh.sm.metrics.MetricCollector;
import agh.sm.prediction.estimators.CloudComputeEstimator;
import agh.sm.prediction.estimators.DeviceComputeEstimator;
import agh.sm.prediction.estimators.TransmissionEstimator;
import agh.sm.prediction.params.DeviceParameters;
import agh.sm.prediction.params.KnnX;
import agh.sm.prediction.params.TaskParametersMetadata;
import smile.classification.KNN;
import stanczyk.Stanczyk;

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

    public ExecutionTarget predict(TaskParametersMetadata taskParametersMetadata, DeviceParameters deviceParameters) {
        double totalTransmissionTime = transmissionEstimator.getTransmissionTime(taskParametersMetadata, deviceParameters);
        long cloudComputeTime = cloudComputeEstimator.getCloudComputeTime(taskParametersMetadata);
        Log.i(TAG, "TTT = " + totalTransmissionTime + " CCT = " + cloudComputeTime);

        double totalCloudExecutionTime = cloudComputeTime + 2 * totalTransmissionTime;

        double deviceExecutionTime = deviceComputeEstimator.getDeviceComputeTime(taskParametersMetadata, deviceParameters);
        double deviceExecutionTime2 = deviceComputeEstimator.predictDeviceComputeTime(taskParametersMetadata, deviceParameters);

        return totalCloudExecutionTime < deviceExecutionTime ? ExecutionTarget.DEVICE : ExecutionTarget.DEVICE;
    }


    public ExecutionTarget predictKNN(TaskParametersMetadata taskParametersMetadata, DeviceParameters deviceParameters) {
        ExecutionTarget executionTarget = knn.predict(new KnnX(taskParametersMetadata, deviceParameters)) == 1 ? ExecutionTarget.CLOUD : ExecutionTarget.DEVICE;
        Log.i(TAG, "Prediction : " + executionTarget + " for TP: " + taskParametersMetadata + " , DP: " + deviceParameters);
        return executionTarget;
    }

    /*
     * TODO - Call this form ExecutionService, depeding on KnowledgeExchaneStrategy
     *  Supply Knowledge from KnowledgeStore - group local / cloud
     */
    public void learnExecutionPredictor(KnnX[] executionParameters, int[] executionTarget) {
        knn = KNN.fit(executionParameters, executionTarget, 3, KnnX.getDistance());
    }

    public void learnDeviceComputeEstimator(Stanczyk.DevicesKnowledge localKnowledge) {
        List<KnnX> labels = localKnowledge.getDataList().stream()
                .map(deviceExecutionMetadata -> {
                    DeviceParameters deviceParameters = DeviceParameters.fromStanczykDto(deviceExecutionMetadata.getDeviceExecutorMetadata());
                    TaskParametersMetadata taskMetadata = new TaskParametersMetadata(deviceExecutionMetadata.getTaskMetadata().getProblemSize());
                    return new KnnX(taskMetadata, deviceParameters);
                })
                .collect(Collectors.toList());

        long[] features = localKnowledge.getDataList().stream()
                .map((Function<Stanczyk.DeviceExecutionMetadata, Long>) Stanczyk.DeviceExecutionMetadata::getExecutionTimeMs)
                .mapToLong(Long::longValue)
                .toArray();

        List<KnnX> labelsMock = Arrays.asList(
                new KnnX(new TaskParametersMetadata(100), new DeviceParameters(4, 10000, 8000, 16000, 8, 99)),
                new KnnX(new TaskParametersMetadata(600), new DeviceParameters(8, 10000, 8000, 16000, 10, 99)),
                new KnnX(new TaskParametersMetadata(100), new DeviceParameters(2, 100, 1000, 2000, 5, 99)));

        long[] featuresMock = {150, 200, 600};

        deviceComputeEstimator.learn(labelsMock, featuresMock);
    }

    public enum ExecutionTarget {
        DEVICE,
        CLOUD;
    }
}
