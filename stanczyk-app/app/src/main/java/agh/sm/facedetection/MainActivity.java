package agh.sm.facedetection;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
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

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.security.auth.x500.X500Principal;
import javax.security.cert.X509Certificate;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.okhttp.OkHttpChannelBuilder;
import stanczyk.Stanczyk;
import stanczyk.StanczykKnowledgeExchangeServiceGrpc;


public class MainActivity extends AppCompatActivity {

    private static final String SIZE_SCREEN = "w:screen"; // Match screen width
    private static final String SIZE_1024_768 = "w:1024"; // ~1024*768 in a normal ratio
    private static final String SIZE_640_480 = "w:640"; // ~640*480 in a normal ratio
    private static final String FACE_DETECTION = "Face Detection";

    private static final int REQUEST_CHOOSE_IMAGE = 1002;
    private static final String TAG = "MainActivity";

    private final String selectedMode = FACE_DETECTION;
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

    private StanczykKnowledgeExchangeServiceGrpc.StanczykKnowledgeExchangeServiceFutureStub knowledgeExchangeService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initializeGRPC();

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

    private void initializeGRPC() {
        executor = Executors.newFixedThreadPool(4);


        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forTarget("10.0.2.2:50051")
                .usePlaintext();

        grpcChannel = channelBuilder.build();

        knowledgeExchangeService = StanczykKnowledgeExchangeServiceGrpc.newFutureStub(grpcChannel);

        Log.i(TAG, "Initialized GRPC connections");
    }



    /*
     * https://source.android.com/devices/tech/power/device
     *
     */
    private View.OnClickListener getRunDetectionOnClickListener() {
        return v -> {
            Bitmap imageBitmap = ((BitmapDrawable) preview.getDrawable()).getBitmap();
            if (imageProcessor != null) {
                BatteryManager mBatteryManager = (BatteryManager) getApplicationContext().getSystemService(Context.BATTERY_SERVICE);
                Long energyStart = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER);

                Task<List<Face>> task = imageProcessor.processBitmap(imageBitmap, graphicOverlay);
                task.addOnCompleteListener(task1 -> {
                    Long energyEnd = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER);
                    int batteryCurrent = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
                    int capacity = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

                    System.out.println("End energy : " + energyEnd);
                    System.out.println("Bat current : " + batteryCurrent);
                    System.out.println("Bat capac : " + capacity);
                    System.out.println("Energy delta : " + (energyEnd - energyStart));

                    Stanczyk.DeviceExecutorMetadata message = Stanczyk.DeviceExecutorMetadata.newBuilder()
                            .setData("Test Data")
                            .build();

                    ListenableFuture<Stanczyk.KnowledgeBatch> knowledgeBatch = knowledgeExchangeService.exchangeKnowledge(message);

                    Futures.addCallback(knowledgeBatch, new FutureCallback<Stanczyk.KnowledgeBatch>() {
                        @Override
                        public void onSuccess(@NullableDecl Stanczyk.KnowledgeBatch result) {
                            System.out.println(result);
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e(TAG, "Failed in future", t);
                        }
                    }, executor);

                    task1.getResult().stream().map(Face::getBoundingBox).forEach(System.out::println);
                });


            } else {
                Log.e(TAG, "Null imageProcessor");
            }
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