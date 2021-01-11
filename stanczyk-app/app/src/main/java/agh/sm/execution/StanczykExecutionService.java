package agh.sm.execution;

import android.graphics.Bitmap;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import agh.sm.exchange.StanczykExchangeService;
import agh.sm.execution.executors.CloudExecutor;
import agh.sm.execution.executors.LocalExecutor;
import agh.sm.detection.FaceDetectionTask;
import agh.sm.metrics.MetricCollector;
import agh.sm.prediction.ExecutionPredictor;
import agh.sm.exchange.KnowledgeExchangeStrategy;
import agh.sm.prediction.params.DeviceParameters;
import agh.sm.prediction.params.KnnX;
import agh.sm.prediction.params.TaskParametersMetadata;
import stanczyk.Stanczyk;

public class StanczykExecutionService {

    private final StanczykExchangeService stanczykService;
    private final ExecutionPredictor executionPredictor;
    private final MetricCollector metricsCollector;
    private final CloudExecutor cloudExecutor;
    private final LocalExecutor localExecutor;

    public StanczykExecutionService(StanczykExchangeService stanczykService, ExecutionPredictor executionPredictor, MetricCollector metricsCollector,
                                    CloudExecutor cloudExecutor, LocalExecutor localExecutor) {
        this.stanczykService = stanczykService;
        this.executionPredictor = executionPredictor;
        this.metricsCollector = metricsCollector;
        this.cloudExecutor = cloudExecutor;
        this.localExecutor = localExecutor;
    }

    /*
     * TODO :
     *  Metadata for estimators
     *  Synchronize with Stanczyk (proto)
     *  Pass these to cloud/localExecution() methods
     */
    public void executeFor(Bitmap image) {
        FaceDetectionTask faceDetectionTask = new FaceDetectionTask(image);
        TaskParametersMetadata taskParametersMetadata = new TaskParametersMetadata(faceDetectionTask);

        if (stanczykService.getStrategy() == KnowledgeExchangeStrategy.ALWAYS_BEFORE_REQUEST) {
            stanczykService.exchangeKnowledge();

            Stanczyk.DevicesKnowledge localKnowledge = stanczykService.getLocalKnowledge();
            /*
             * TODO : Mock Local Knowledge
             */
            executionPredictor.learnDeviceComputeEstimator(localKnowledge);
//
//            KnnX[] labels = localKnowledge.getDataList().stream()
//                    .map(deviceExecutionMetadata -> {
//                        DeviceParameters deviceParameters = DeviceParameters.fromStanczykDto(deviceExecutionMetadata.getDeviceExecutorMetadata());
//                        TaskParametersMetadata taskMetadata = new TaskParametersMetadata(deviceExecutionMetadata.getTaskMetadata().getProblemSize());
//                        return new KnnX(taskMetadata, deviceParameters);
//                    })
//                    .toArray(KnnX[]::new);
//
//            int[] features = localKnowledge.getDataList().stream()
//                    .map((Function<Stanczyk.DeviceExecutionMetadata, Long>) Stanczyk.DeviceExecutionMetadata::getExecutionTimeMs)
//                    .mapToInt(Long::intValue)
//                    .toArray();

//            executionPredictor.learn(labels, features);
        }

        if (executionPredictor.predict(taskParametersMetadata, metricsCollector.getDeviceParams()).equals(ExecutionPredictor.ExecutionTarget.CLOUD)) {
            cloudExecutor.executeFor(image);
        } else {
            localExecutor.executeFor(image);//313, 118, 802, 608
        }
    }
}
