package agh.sm.exchange;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import agh.sm.metrics.MetricCollector;
import stanczyk.Stanczyk;
import stanczyk.StanczykKnowledgeExchangeServiceGrpc.StanczykKnowledgeExchangeServiceFutureStub;

public class StanczykExchangeService {

    private final KnowledgeExchangeStrategy strategy;
    private final StanczykKnowledgeExchangeServiceFutureStub knowledgeExchangeService;
    private final MetricCollector metricCollector;

    public StanczykExchangeService(KnowledgeExchangeStrategy strategy, StanczykKnowledgeExchangeServiceFutureStub knowledgeExchangeService, MetricCollector metricCollector) {
        this.strategy = strategy;
        this.knowledgeExchangeService = knowledgeExchangeService;
        this.metricCollector = metricCollector;

        if (strategy == KnowledgeExchangeStrategy.AT_INTERVALS) {
            new ScheduledThreadPoolExecutor(1).schedule(this::exchangeKnowledge, 5, TimeUnit.MINUTES); // todo mikegpl - decide on interval
        }
    }

    public KnowledgeExchangeStrategy getStrategy() {
        return strategy;
    }

    public void exchangeKnowledge() {
        Stanczyk.DevicesKnowledge localKnowledge = Stanczyk.DevicesKnowledge.newBuilder().build(); // todo mikgepl - retrieve knowledge from store (probably filter out knowledge, that did not originate from device)
        try {
            Stanczyk.KnowledgeBatch batch = knowledgeExchangeService.exchangeKnowledge(localKnowledge).get(); // this needs to be blocking
            updateKnowledge(batch);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateKnowledge(Stanczyk.KnowledgeBatch knowledgeBatch) {
        // todo mikegpl:
        // 1. store (with overwriting) knowledge about devices in KnowledgeStore
        // 2. store (with overwriting) knowledge about cloud in KnowledgeStore
        System.out.println(knowledgeBatch);
    }
}
