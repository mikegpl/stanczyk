package agh.sm.execution.executors;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.ByteString;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import agh.sm.detection.BitmapUtils;
import agh.sm.exchange.KnowledgeExchangeStrategy;
import agh.sm.exchange.KnowledgeStore;
import agh.sm.exchange.StanczykExchangeService;
import stanczyk.Stanczyk;
import stanczyk.StanczykTaskExecutionServiceGrpc.StanczykTaskExecutionServiceFutureStub;

public class CloudExecutor {
    public static final String TAG = "CloudExecutor";

    private final StanczykTaskExecutionServiceFutureStub taskService;
    private final StanczykExchangeService stanczyk;
    private final ExecutorService executor;

    public CloudExecutor(StanczykExchangeService stanczyk, StanczykTaskExecutionServiceFutureStub taskService, KnowledgeStore knowledgeStore) {
        this.stanczyk = stanczyk;
        this.taskService = taskService;
        this.executor = Executors.newFixedThreadPool(4);
    }

    public void executeFor(Bitmap image) {
        Stanczyk.FindRequest request = Stanczyk.FindRequest.newBuilder()
                .setFileName("a")
                .setBase64ImageBytes(ByteString.copyFromUtf8(BitmapUtils.convert(image)))
                .build();

        if (stanczyk.getStrategy() == KnowledgeExchangeStrategy.ON_DATA_TRANSMISSION) {
            executeAndExchange(request);
        } else {
            justExecute(request);
        }
    }

    private void justExecute(Stanczyk.FindRequest request) {
        Log.i(TAG, "Just Executing in the Cloud");
        ListenableFuture<Stanczyk.FindResult> findResult = taskService.findFaces(request);
        Futures.addCallback(findResult, new FutureCallback<Stanczyk.FindResult>() {
            @Override
            public void onSuccess(@NullableDecl Stanczyk.FindResult result) {
                Optional.ofNullable(result)
                        .ifPresent(success -> success.getDataList().forEach(System.out::println));
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "Failed in the future", t);
            }
        }, executor);
    }

    private void executeAndExchange(Stanczyk.FindRequest request) {
        Log.i(TAG, "Execute and Exchange in the Cloud");

        Stanczyk.FindAndExchangeRequest requestWrapper = Stanczyk.FindAndExchangeRequest.newBuilder()
                .setRequest(request)
                .setDevicesKnowledge(stanczyk.getLocalKnowledge())
                .build();
        ListenableFuture<Stanczyk.FindAndExchangeResult> findResult = taskService.findFacesAndExchangeKnowledge(requestWrapper);
        Futures.addCallback(findResult, new FutureCallback<Stanczyk.FindAndExchangeResult>() {
            @Override
            public void onSuccess(@NullableDecl Stanczyk.FindAndExchangeResult result) {
                Optional.ofNullable(result)
                        .ifPresent(success -> {
                            stanczyk.updateExternalKnowledge(success.getKnowledge());
                            success.getResult().getDataList().forEach(System.out::println);
                        });
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "Failed in the future", t);
            }
        }, executor);
    }
}
