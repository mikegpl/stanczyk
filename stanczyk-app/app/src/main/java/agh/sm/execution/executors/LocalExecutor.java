package agh.sm.execution.executors;

import android.graphics.Bitmap;
import android.os.SystemClock;

import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.face.Face;

import java.util.List;

import agh.sm.detection.GraphicOverlay;
import agh.sm.detection.VisionImageProcessor;
import agh.sm.metrics.MetricCollector;
import agh.sm.prediction.ExecutionPredictor;

public class LocalExecutor {

    private final VisionImageProcessor<List<Face>> processor;
    private final GraphicOverlay overlay;
    private final MetricCollector metricCollector;
    private final ExecutionPredictor predictor;

    public LocalExecutor(GraphicOverlay overlay, MetricCollector metricCollector, VisionImageProcessor<List<Face>> processor, ExecutionPredictor predictor) {
        this.overlay = overlay;
        this.metricCollector = metricCollector;
        this.processor = processor;
        this.predictor = predictor;
    }

    public void executeFor(Bitmap image) {
        // todo mikegpl - collect knowledge
        long startEnergy = metricCollector.getBatteryEnergy();
        long startTime = SystemClock.elapsedRealtime();
        Task<List<Face>> task = processor.processBitmap(image, overlay);
        task.addOnCompleteListener(task1 -> {
            long endTime = SystemClock.elapsedRealtime();
            long endEnergy = metricCollector.getBatteryEnergy();
            predictor.teachFromDevice(endTime - startTime, endEnergy - startEnergy);

            task1.getResult().stream()
                    .map(Face::getBoundingBox)
                    .forEach(System.out::println);
        });
    }
}