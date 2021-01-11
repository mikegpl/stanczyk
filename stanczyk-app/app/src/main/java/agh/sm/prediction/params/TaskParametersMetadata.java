package agh.sm.prediction.params;

import agh.sm.detection.FaceDetectionTask;

public class TaskParametersMetadata {

    private final int taskSize;

    public TaskParametersMetadata(FaceDetectionTask task) {
        this.taskSize = task.getTaskSize();
    }

    public TaskParametersMetadata(long taskSize) {
        this.taskSize = (int) taskSize;
    }

    public int getTaskSize() {
        return taskSize;
    }
}
