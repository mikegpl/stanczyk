package agh.sm.exchange;

import com.google.common.collect.EvictingQueue;

import java.util.ArrayList;
import java.util.List;

import static stanczyk.Stanczyk.CloudExecutionMetadata;
import static stanczyk.Stanczyk.DeviceExecutionMetadata;
import static stanczyk.Stanczyk.DevicesKnowledge;

@SuppressWarnings("ALL")
public class KnowledgeStore {

    private EvictingQueue<DeviceExecutionMetadata> localDeviceKnowledge = EvictingQueue.create(1024);
    private EvictingQueue<DeviceExecutionMetadata> externalDeviceKnowledge = EvictingQueue.create(1024);
    private EvictingQueue<CloudExecutionMetadata> cloudKnowledge = EvictingQueue.create(1024);

    public DevicesKnowledge getLocalKnowledge() {
        List<DeviceExecutionMetadata> localMetadata = new ArrayList(localDeviceKnowledge);
        return DevicesKnowledge.newBuilder().addAllData(localMetadata).build();
    }

    public void insertLocalExecution(DeviceExecutionMetadata metadata) {
        localDeviceKnowledge.add(metadata);
    }

    public void insertExternalExecutions(List<DeviceExecutionMetadata> deviceExecutionMetadata, List<CloudExecutionMetadata> cloudExecutionMetadata) {
        externalDeviceKnowledge.addAll(deviceExecutionMetadata);
        cloudKnowledge.addAll(cloudExecutionMetadata);
    }
}
