package com.logistics.shipping.service;

import com.logistics.common.shipping.ShipmentRequest;
import com.logistics.common.shipping.ShipmentResponse;
import com.logistics.common.shipping.ShippingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class ShippingGrpcService extends ShippingServiceGrpc.ShippingServiceImplBase {

    private final ShippingDataService shippingDataService;

    public ShippingGrpcService(ShippingDataService shippingDataService) {
        this.shippingDataService = shippingDataService;
    }

    @Override
    public void createShipment(ShipmentRequest request, StreamObserver<ShipmentResponse> responseObserver) {
        String shipmentId = shippingDataService.createShipment(request.getOrderId(), request.getAddress());
        
        System.out.println("Creating shipment (via gRPC) for order: " + request.getOrderId() + " to " + request.getAddress());

        ShipmentResponse response = ShipmentResponse.newBuilder()
                .setShipmentId(shipmentId)
                .setStatus("SHIPPED")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
