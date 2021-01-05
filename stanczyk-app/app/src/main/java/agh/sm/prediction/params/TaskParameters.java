package agh.sm.prediction.params;

import agh.sm.facedetection.FaceDetectionTask;

public class TaskParameters {

    private final FaceDetectionTask task;

    public TaskParameters(FaceDetectionTask task) {
        this.task = task;
    }

    /*
     * Used by estimators and when sending knowledge to Stanczyk
     */
    public int getTaskHardnessLevel() {
        return -1;
    }

    public int getTaskSize() {
        return -1;
    }
}
