package agh.sm.exchange;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import stanczyk.Stanczyk;
import stanczyk.StanczykKnowledgeExchangeServiceGrpc.StanczykKnowledgeExchangeServiceFutureStub;

public class StanczykExchangeService {

    private final KnowledgeExchangeStrategy strategy;
    private final StanczykKnowledgeExchangeServiceFutureStub knowledgeExchangeService;
    private final KnowledgeStore knowledgeStore;

    public StanczykExchangeService(KnowledgeExchangeStrategy strategy, StanczykKnowledgeExchangeServiceFutureStub knowledgeExchangeService, KnowledgeStore knowledgeStore) {
        this.strategy = strategy;
        this.knowledgeExchangeService = knowledgeExchangeService;
        this.knowledgeStore = knowledgeStore;

        if (strategy == KnowledgeExchangeStrategy.AT_INTERVALS) {
            new ScheduledThreadPoolExecutor(1).schedule(this::exchangeKnowledge, 5, TimeUnit.MINUTES); // todo - decide about interval
        }
    }

    public KnowledgeExchangeStrategy getStrategy() {
        return strategy;
    }

    public void exchangeKnowledge() {
        try {
            Stanczyk.KnowledgeBatch batch = knowledgeExchangeService.exchangeKnowledge(knowledgeStore.getLocalKnowledge()).get(); // this needs to be blocking
            updateExternalKnowledge(batch);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateExternalKnowledge(Stanczyk.KnowledgeBatch knowledgeBatch) {
        knowledgeStore.insertExternalExecutions(knowledgeBatch.getDevicesKnowledge().getDataList(), knowledgeBatch.getCloudKnowledge().getDataList());
        System.out.println(knowledgeBatch);
    }

    public void updateLocalKnowledge(Stanczyk.DeviceExecutionMetadata localExecutionMeta) {
        knowledgeStore.insertLocalExecution(localExecutionMeta);
    }

    public Stanczyk.DevicesKnowledge getLocalKnowledge() {
        return knowledgeStore.getLocalKnowledge();
    }
}
