package agh.sm.facedetection;

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

import java.io.IOException;


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
    private VisionImageProcessor imageProcessor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.select_image_button)
                .setOnClickListener(view -> startChooseImageIntentForResult());

        findViewById(R.id.run_detection_button)
                .setOnClickListener(getRunDetectionOnClickListener());

        graphicOverlay = findViewById(R.id.graphic_overlay);
        preview = findViewById(R.id.preview);

        View rootView = findViewById(R.id.root);
        rootView
                .getViewTreeObserver()
                .addOnGlobalLayoutListener(
                        new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                imageMaxWidth = rootView.getWidth();
                                imageMaxHeight = rootView.getHeight() - findViewById(R.id.control).getHeight();
//                                if (SIZE_SCREEN.equals(selectedSize)) {
//                                    loadImage();
//                                }
                            }
                        });

        imageProcessor = new FaceDetectorProcessor(this);

    }

    private View.OnClickListener getRunDetectionOnClickListener() {
        return v -> {
            Bitmap imageBitmap = ((BitmapDrawable) preview.getDrawable()).getBitmap();
            if (imageProcessor != null) {
                imageProcessor.processBitmap(imageBitmap, graphicOverlay);
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