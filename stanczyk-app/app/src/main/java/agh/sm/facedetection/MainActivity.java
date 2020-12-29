package agh.sm.facedetection;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.face.Face;
import com.google.protobuf.ByteString;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import agh.sm.estimator.TaskParameters;
import agh.sm.metrics.MetricCollector;
import agh.sm.estimator.DeviceParameters;
import agh.sm.estimator.ExecutionPredictor;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import stanczyk.Stanczyk;
import stanczyk.StanczykTaskExecutionServiceGrpc;


public class MainActivity extends AppCompatActivity {

    private static final String SIZE_SCREEN = "w:screen"; // Match screen width
    private static final String SIZE_1024_768 = "w:1024"; // ~1024*768 in a normal ratio
    private static final String SIZE_640_480 = "w:640"; // ~640*480 in a normal ratio

    private static final int REQUEST_CHOOSE_IMAGE = 1002;
    private static final String TAG = "MainActivity";

    private final String selectedSize = SIZE_SCREEN;

    private ImageView preview;
    private GraphicOverlay graphicOverlay;

    private boolean isLandScape = false;
    private Uri imageUri;
    private int imageMaxWidth;
    private int imageMaxHeight;
    private VisionImageProcessor<List<Face>> imageProcessor;

    private ExecutorService executor;
    private ManagedChannel grpcChannel;

    private ExecutionPredictor executionPredictor;
    private StanczykTaskExecutionServiceGrpc.StanczykTaskExecutionServiceFutureStub taskService;
    private MetricCollector metricCollector;


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

        imageProcessor = new FaceDetectorProcessor(this);
    }

    private void initializeServices() {
        executionPredictor = new ExecutionPredictor();
        metricCollector = new MetricCollector(getApplicationContext());
        executor = Executors.newFixedThreadPool(4);

        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder
                .forAddress("10.0.2.2", 50051)
                .usePlaintext();

        grpcChannel = channelBuilder.build();
        taskService = StanczykTaskExecutionServiceGrpc.newFutureStub(grpcChannel);

        Log.i(TAG, "Initialized Services");
    }


    private View.OnClickListener getRunDetectionOnClickListener() {
        return v -> {
            Bitmap imageBitmap = ((BitmapDrawable) preview.getDrawable()).getBitmap();
            TaskParameters taskParameters = new TaskParameters();
            DeviceParameters deviceDeviceParameters = metricCollector.getDeviceKnowledge();

            if (executionPredictor.predict(taskParameters, deviceDeviceParameters).equals(ExecutionPredictor.ExecutionTarget.CLOUD) && false) {
                cloudExecution(imageBitmap);
            } else {
                localExecution(imageBitmap); //313, 118, 802, 608
            }
        };
    }

    private void cloudExecution(Bitmap imageBitmap) {
        Stanczyk.FindRequest request = Stanczyk.FindRequest.newBuilder()
                .setFileName("a")
                .setBase64ImageBytes(ByteString.copyFromUtf8(BitmapUtils.convert(imageBitmap)))
                .build();

        ListenableFuture<Stanczyk.FindResult> findResult = taskService.findFaces(request);

        Futures.addCallback(findResult, new FutureCallback<Stanczyk.FindResult>() {
            @Override
            public void onSuccess(@NullableDecl Stanczyk.FindResult result) {
                Optional.ofNullable(result.getDataList())
                        .ifPresent(list -> list.forEach(System.out::println));
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "Failed in the future", t);
            }
        }, executor);
    }

    private void localExecution(Bitmap imageBitmap) {
        long startEnergy = metricCollector.getBatteryEnergy();
        long startTime = SystemClock.elapsedRealtime();
        Task<List<Face>> task = imageProcessor.processBitmap(imageBitmap, graphicOverlay);
        task.addOnCompleteListener(task1 -> {
            long endTime = SystemClock.elapsedRealtime();
            long endEnergy = metricCollector.getBatteryEnergy();
            executionPredictor.teachFromDevice(endTime - startTime, endEnergy - startEnergy);

            task1.getResult().stream()
                    .map(Face::getBoundingBox)
                    .forEach(System.out::println);
        });
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

            if (SIZE_SCREEN.equals(selectedSize) && imageMaxWidth == 0) {
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

        switch (selectedSize) {
            case SIZE_SCREEN:
                targetWidth = imageMaxWidth;
                targetHeight = imageMaxHeight;
                break;
            case SIZE_640_480:
                targetWidth = isLandScape ? 640 : 480;
                targetHeight = isLandScape ? 480 : 640;
                break;
            case SIZE_1024_768:
                targetWidth = isLandScape ? 1024 : 768;
                targetHeight = isLandScape ? 768 : 1024;
                break;
            default:
                throw new IllegalStateException("Unknown size");
        }

        return new Pair<>(targetWidth, targetHeight);
    }
}