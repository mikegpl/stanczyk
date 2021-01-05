package agh.sm.exchange;

import agh.sm.prediction.ExecutionPredictor;
import agh.sm.prediction.KnowledgeExchangeStrategy;

public class KnowledgeService {

    private final KnowledgeExchangeStrategy strategy;
    private ExecutionPredictor predictor;

    public KnowledgeService(KnowledgeExchangeStrategy strategy, ExecutionPredictor predictor) {
        this.strategy = strategy;
        this.predictor = predictor;
    }

    public void setPredictor(ExecutionPredictor predictor) {
        this.predictor = predictor;
    }


}
