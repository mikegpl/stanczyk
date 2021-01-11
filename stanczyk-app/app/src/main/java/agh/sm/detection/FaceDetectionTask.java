package agh.sm.detection;

import android.graphics.Bitmap;

public class FaceDetectionTask {

    private final Bitmap image;

    public FaceDetectionTask(Bitmap image) {
        this.image = image;
    }

    public int getTaskSize() {
        return image.getRowBytes() * image.getHeight();
    }
}
