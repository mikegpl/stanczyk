package agh.sm.execution;

import android.graphics.Bitmap;

import java.util.stream.Collectors;

import agh.sm.exchange.StanczykExchangeService;
import agh.sm.execution.executors.CloudExecutor;
import agh.sm.execution.executors.LocalExecutor;
import agh.sm.detection.FaceDetectionTask;
import agh.sm.metrics.MetricCollector;
import agh.sm.prediction.ExecutionPredictor;
import agh.sm.exchange.KnowledgeExchangeStrategy;
import agh.sm.prediction.params.KnnX;
import agh.sm.prediction.params.TaskParameters;
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
        TaskParameters taskParameters = new TaskParameters(faceDetectionTask);

        if (stanczykService.getStrategy() == KnowledgeExchangeStrategy.ALWAYS_BEFORE_REQUEST) {
            stanczykService.exchangeKnowledge();
            // TODO : map local knowledge to KnnX and learn
            Stanczyk.DevicesKnowledge localKnowledge = stanczykService.getLocalKnowledge();
            KnnX[] localKnowdlegeForKnnX = Arrays.stream(localKnowledge).map(KnnX::fromStanczykGetX).collect(Collectors.toList());
            int[] localKnowdlegeForKnnY = Arrays.stream(localKnowledge).map(KnnX::fromStanczykGetY).collect(Collectors.toList());
            executionPredictor.learn(localKnowdlegeForKnnX, localKnowdlegeForKnnY);
        }

        if (executionPredictor.predict(taskParameters, metricsCollector.getDeviceParams()).equals(ExecutionPredictor.ExecutionTarget.CLOUD)) {
            cloudExecutor.executeFor(image);
        } else {
            localExecutor.executeFor(image);//313, 118, 802, 608
        }
    }
}
