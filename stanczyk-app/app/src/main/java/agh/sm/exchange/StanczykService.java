package agh.sm.exchange;

import java.util.concurrent.ExecutionException;

import agh.sm.prediction.ExecutionPredictor;
import agh.sm.prediction.KnowledgeExchangeStrategy;
import stanczyk.Stanczyk;
import stanczyk.StanczykKnowledgeExchangeServiceGrpc.StanczykKnowledgeExchangeServiceFutureStub;

public class StanczykService {

    private final KnowledgeExchangeStrategy strategy;
    private final StanczykKnowledgeExchangeServiceFutureStub knowledgeService;
    private ExecutionPredictor predictor;

    public StanczykService(KnowledgeExchangeStrategy strategy, ExecutionPredictor predictor, StanczykKnowledgeExchangeServiceFutureStub knowledgeService) {
        this.strategy = strategy;
        this.predictor = predictor;
        this.knowledgeService = knowledgeService;
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
            // todo mikegpl - insert new knowledge into predictor
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
