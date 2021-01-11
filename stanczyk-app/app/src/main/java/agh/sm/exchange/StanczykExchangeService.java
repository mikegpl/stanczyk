package agh.sm.exchange;

import android.util.Log;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import stanczyk.Stanczyk;
import stanczyk.StanczykKnowledgeExchangeServiceGrpc.StanczykKnowledgeExchangeServiceFutureStub;

public class StanczykExchangeService {

    private static final String TAG = "StanczykExchangeService";

    private final KnowledgeExchangeStrategy strategy;
    private final StanczykKnowledgeExchangeServiceFutureStub knowledgeExchangeService;
    private final KnowledgeStore knowledgeStore;
    private final ExecutorService executor;


    public StanczykExchangeService(KnowledgeExchangeStrategy strategy, StanczykKnowledgeExchangeServiceFutureStub knowledgeExchangeService, KnowledgeStore knowledgeStore) {
        this.strategy = strategy;
        this.knowledgeExchangeService = knowledgeExchangeService;
        this.knowledgeStore = knowledgeStore;
        this.executor = Executors.newFixedThreadPool(4);

        if (strategy == KnowledgeExchangeStrategy.AT_INTERVALS) {
            new ScheduledThreadPoolExecutor(1).schedule(this::exchangeKnowledge, 5, TimeUnit.MINUTES); // todo - decide about interval
        }
    }

    public KnowledgeExchangeStrategy getStrategy() {
        return strategy;
    }

    public void exchangeKnowledge() {
        Stanczyk.DevicesKnowledge localKnowledge = knowledgeStore.getLocalKnowledge();
        ListenableFuture<Stanczyk.KnowledgeBatch> knowledgeBatchFuture = knowledgeExchangeService.exchangeKnowledge(localKnowledge);
        /*
         * TODO: Removed comment about blocking call, async code
         */
        Futures.addCallback(knowledgeBatchFuture, new FutureCallback<Stanczyk.KnowledgeBatch>() {
            @Override
            public void onSuccess(@NullableDecl Stanczyk.KnowledgeBatch result) {
                Optional.ofNullable(result).ifPresent(knowledgeBatch -> updateExternalKnowledge(knowledgeBatch));
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "Failed in the future", t);
            }
        }, executor);

    }

    public void updateExternalKnowledge(Stanczyk.KnowledgeBatch knowledgeBatch) {
        Log.i(TAG, "Update external knowledge");
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
