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

import agh.sm.exchange.StanczykExchangeService;
import agh.sm.facedetection.BitmapUtils;
import agh.sm.prediction.KnowledgeExchangeStrategy;
import stanczyk.Stanczyk;
import stanczyk.StanczykTaskExecutionServiceGrpc.StanczykTaskExecutionServiceFutureStub;

public class CloudExecutor {
    public static final String TAG = "CloudExecutor";
    private final StanczykTaskExecutionServiceFutureStub taskService;
    private final StanczykExchangeService stanczyk;
    private final ExecutorService executor;

    public CloudExecutor(StanczykExchangeService stanczyk, StanczykTaskExecutionServiceFutureStub taskService) {
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
            Stanczyk.FindAndExchangeRequest requestWrapper = Stanczyk.FindAndExchangeRequest.newBuilder()
                    .setRequest(request)
                    .setMeta((Stanczyk.DeviceExecutorMetadata) null) // todo mikegpl - get metadata from device executor
                    .build();
            ListenableFuture<Stanczyk.FindAndExchangeResult> findResult = taskService.findFacesAndExchangeKnowledge(requestWrapper);
            Futures.addCallback(findResult, new FutureCallback<Stanczyk.FindAndExchangeResult>() {
                @Override
                public void onSuccess(@NullableDecl Stanczyk.FindAndExchangeResult result) {
                    Optional.ofNullable(result.getResult().getDataList())
                            .ifPresent(list -> list.forEach(System.out::println));
//                    result.getData() - todo mikegpl - insert new knowledge into predictor
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e(TAG, "Failed in the future", t);
                }
            }, executor);
        } else {
            ListenableFuture<Stanczyk.FindResult> findResult = taskService.findFaces(request);
            Futures.addCallback(findResult, new FutureCallback<Stanczyk.FindResult>() {
                @Override
                public void onSuccess(@NullableDecl Stanczyk.FindResult result) {
                    Optional.ofNullable(result.getDataList())
                            .ifPresent(list -> list.forEach(System.out::println));
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e(TAG, "Failed in the future", t);
                }
            }, executor);
        }
    }
}
