package agh.sm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.mlkit.vision.face.Face;

import java.io.IOException;
import java.util.List;

import agh.sm.exchange.KnowledgeStore;
import agh.sm.exchange.StanczykExchangeService;
import agh.sm.execution.StanczykExecutionService;
import agh.sm.execution.executors.CloudExecutor;
import agh.sm.execution.executors.LocalExecutor;
import agh.sm.detection.BitmapUtils;
import agh.sm.detection.FaceDetectorProcessor;
import agh.sm.detection.GraphicOverlay;
import agh.sm.detection.R;
import agh.sm.detection.VisionImageProcessor;
import agh.sm.metrics.MetricCollector;
import agh.sm.prediction.ExecutionPredictor;
import agh.sm.exchange.KnowledgeExchangeStrategy;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import stanczyk.StanczykKnowledgeExchangeServiceGrpc;
import stanczyk.StanczykTaskExecutionServiceGrpc;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CHOOSE_IMAGE = 1002;
    private static final String TAG = "MainActivity";

    private ImageView preview;
    private GraphicOverlay graphicOverlay;

    private Uri imageUri;
    private int imageMaxWidth;
    private int imageMaxHeight;

    private StanczykExecutionService stanczykExecutionService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initializeServices();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.select_image_button)
                .setOnClickListener(view -> startChooseImageIntentForResult());

        findViewById(R.id.run_detection_button)
                .setOnClickListener(getRunDetectionOnClickListener());

        graphicOverlay = findViewById(R.id.graphic_overlay);
        preview = findViewById(R.id.preview);

        View rootView = findViewById(R.id.root);
        rootView.getViewTreeObserver()
                .addOnGlobalLayoutListener(
                        new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                imageMaxWidth = rootView.getWidth();
                                imageMaxHeight = rootView.getHeight() - findViewById(R.id.control).getHeight();
                            }
                        });
    }

    private void initializeServices() {
        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder
                .forAddress("10.0.2.2", 50051)
                .usePlaintext();
        ManagedChannel grpcChannel = channelBuilder.build();
        StanczykTaskExecutionServiceGrpc.StanczykTaskExecutionServiceFutureStub taskService = StanczykTaskExecutionServiceGrpc.newFutureStub(grpcChannel);
        StanczykKnowledgeExchangeServiceGrpc.StanczykKnowledgeExchangeServiceFutureStub knowledgeService = StanczykKnowledgeExchangeServiceGrpc.newFutureStub(grpcChannel);

        MetricCollector metricCollector = new MetricCollector(getApplicationContext());
        StanczykExchangeService exchangeService = new StanczykExchangeService(KnowledgeExchangeStrategy.ALWAYS_BEFORE_REQUEST, knowledgeService, metricCollector);
        ExecutionPredictor executionPredictor = new ExecutionPredictor(metricCollector);
        KnowledgeStore knowledgeStore = new KnowledgeStore();

        VisionImageProcessor<List<Face>> imageProcessor = new FaceDetectorProcessor(this);
        LocalExecutor localExecutor = new LocalExecutor(graphicOverlay, metricCollector, imageProcessor, executionPredictor);
        CloudExecutor cloudExecutor = new CloudExecutor(exchangeService, taskService, knowledgeStore);

        stanczykExecutionService = new StanczykExecutionService(exchangeService, executionPredictor, metricCollector, cloudExecutor, localExecutor);

        Log.i(TAG, "Initialized Services");
    }


    private View.OnClickListener getRunDetectionOnClickListener() {
        return v -> {
            Bitmap imageBitmap = ((BitmapDrawable) preview.getDrawable()).getBitmap();
            stanczykExecutionService.executeFor(imageBitmap);
        };
    }

    private void startChooseImageIntentForResult() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CHOOSE_IMAGE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            loadImage();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void loadImage() {
        Log.d(TAG, "Load image");
        try {
            if (imageUri == null) {
                return;
            }

            if (imageMaxWidth == 0) {
                // UI layout has not finished yet, will reload once it's ready.
                return;
            }

            Bitmap imageBitmap = BitmapUtils.getBitmapFromContentUri(getContentResolver(), imageUri);
            if (imageBitmap == null) {
                return;
            }

            // Clear the overlay first
            graphicOverlay.clear();

            // Get the dimensions of the image view
            Pair<Integer, Integer> targetedSize = getTargetedWidthHeight();

            // Determine how much to scale down the image
            float scaleFactor =
                    Math.max(
                            (float) imageBitmap.getWidth() / (float) targetedSize.first,
                            (float) imageBitmap.getHeight() / (float) targetedSize.second);

            Bitmap resizedBitmap =
                    Bitmap.createScaledBitmap(
                            imageBitmap,
                            (int) (imageBitmap.getWidth() / scaleFactor),
                            (int) (imageBitmap.getHeight() / scaleFactor),
                            true);

            preview.setImageBitmap(resizedBitmap);

        } catch (IOException e) {
            Log.e(TAG, "Error retrieving saved image");
            imageUri = null;
        }
    }

    private Pair<Integer, Integer> getTargetedWidthHeight() {
        int targetWidth;
        int targetHeight;

        targetWidth = imageMaxWidth;
        targetHeight = imageMaxHeight;

        return new Pair<>(targetWidth, targetHeight);
    }
}