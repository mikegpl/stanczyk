package agh.sm.exchange;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import agh.sm.prediction.KnowledgeExchangeStrategy;
import stanczyk.Stanczyk;
import stanczyk.StanczykKnowledgeExchangeServiceGrpc.StanczykKnowledgeExchangeServiceFutureStub;

public class StanczykExchangeService {

    private final KnowledgeExchangeStrategy strategy;
    private final StanczykKnowledgeExchangeServiceFutureStub knowledgeService;

    public StanczykExchangeService(KnowledgeExchangeStrategy strategy, StanczykKnowledgeExchangeServiceFutureStub knowledgeService) {
        this.strategy = strategy;
        this.knowledgeService = knowledgeService;

        if (strategy == KnowledgeExchangeStrategy.AT_INTERVALS) {
            new ScheduledThreadPoolExecutor(1).schedule(this::exchangeKnowledge, 5, TimeUnit.MINUTES); // todo mikegpl - decide on interval
        }
    }

    public KnowledgeExchangeStrategy getStrategy() {
        return strategy;
    }

    public void exchangeKnowledge() {
        Stanczyk.DeviceExecutorMetadata deviceMeta = Stanczyk.DeviceExecutorMetadata.newBuilder()
                .setData("xD") // todo mikegpl - decide what is device's knowledge and send it
                .build();
        try {
            Stanczyk.KnowledgeBatch batch = knowledgeService.exchangeKnowledge(deviceMeta).get(); // this needs to be blocking
            System.out.println(batch);
            // todo mikegpl - insert new knowledge into predictor
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
