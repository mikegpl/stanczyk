package agh.sm.prediction.params;

import stanczyk.Stanczyk;

public class TaskExecutionParameters{

    private final TaskParametersMetadata taskParametersMetadata;
    private final Long totalExecutionTime;
    private final Long usedEnergy;

    public TaskExecutionParameters(TaskParametersMetadata taskParametersMetadata, Long totalExecutionTime, Long usedEnergy) {
        this.taskParametersMetadata = taskParametersMetadata;
        this.totalExecutionTime = totalExecutionTime;
        this.usedEnergy = usedEnergy;
    }

    /*
     * Use this when receiving knowledge from server
     */
    public static TaskExecutionParameters fromStanczyk(Stanczyk.FindAndExchangeResult stanczykTaskResult) {
        // TODO : Implement
        return new TaskExecutionParameters(null, null, null);
    }

    /*
     * Use this to map device task execution metadata to proto when sending to the server
     */
    public Stanczyk.DeviceExecutorMetadata toStanczykALALALALChangeName() {
        // TODO : Implement
        return Stanczyk.DeviceExecutorMetadata.newBuilder()
                .build();
    }
}
