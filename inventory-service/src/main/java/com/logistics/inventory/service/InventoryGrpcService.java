package com.logistics.inventory.service;

import com.logistics.common.inventory.InventoryServiceGrpc;
import com.logistics.common.inventory.StockRequest;
import com.logistics.common.inventory.StockResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class InventoryGrpcService extends InventoryServiceGrpc.InventoryServiceImplBase {

    private final InventoryDataService inventoryDataService;

    public InventoryGrpcService(InventoryDataService inventoryDataService) {
        this.inventoryDataService = inventoryDataService;
    }

    @Override
    public void checkStock(StockRequest request, StreamObserver<StockResponse> responseObserver) {
        String productId = request.getProductId();
        int requestedQuantity = request.getQuantity();

        boolean available = inventoryDataService.checkStock(productId, requestedQuantity);

        StockResponse response = StockResponse.newBuilder()
                .setAvailable(available)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
