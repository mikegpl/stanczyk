package agh.sm.execution.executors;

import android.graphics.Bitmap;
import android.os.SystemClock;

import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.face.Face;

import java.util.List;

import agh.sm.detection.GraphicOverlay;
import agh.sm.detection.VisionImageProcessor;
import agh.sm.exchange.StanczykExchangeService;
import agh.sm.metrics.MetricCollector;
import agh.sm.prediction.params.DeviceParameters;
import stanczyk.Stanczyk;

public class LocalExecutor {

    private final VisionImageProcessor<List<Face>> processor;
    private final GraphicOverlay overlay;
    private final MetricCollector metricCollector;
    private final StanczykExchangeService stanczykExchangeService;

    public LocalExecutor(GraphicOverlay overlay, MetricCollector metricCollector, VisionImageProcessor<List<Face>> processor, StanczykExchangeService stanczykExchangeService) {
        this.overlay = overlay;
        this.metricCollector = metricCollector;
        this.processor = processor;
        this.stanczykExchangeService = stanczykExchangeService;
    }

    public void executeFor(Bitmap image) {
        DeviceParameters deviceParams = metricCollector.getDeviceParams();
        int problemSize = image.getHeight() * image.getWidth();
        long startTime = SystemClock.elapsedRealtime();
        Task<List<Face>> task = processor.processBitmap(image, overlay);
        task.addOnCompleteListener(taskWrapper -> {
            long endTime = SystemClock.elapsedRealtime();
            long timeElapsed = endTime - startTime;

            stanczykExchangeService.updateLocalKnowledge(
                    Stanczyk.DeviceExecutionMetadata.newBuilder()
                            .setExecutionTimeMs(timeElapsed)
                            .setTaskMetadata(Stanczyk.TaskMetadata.newBuilder().setProblemSize(problemSize).build())
                            .setDeviceExecutorMetadata(deviceParams.toDto())
                            .build()
            );
            taskWrapper.getResult().stream()
                    .map(Face::getBoundingBox)
                    .forEach(System.out::println);
        });
    }
}