package com.logistics.order.controller;

import com.logistics.common.inventory.InventoryServiceGrpc;
import com.logistics.common.inventory.StockRequest;
import com.logistics.common.inventory.StockResponse;
import com.logistics.common.shipping.ShipmentRequest;
import com.logistics.common.shipping.ShipmentResponse;
import com.logistics.common.shipping.ShippingServiceGrpc;
import com.logistics.order.model.Order;
import com.logistics.order.service.OrderDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order Controller", description = "Управління замовленнями")
public class OrderController {

    private final RestTemplate restTemplate;
    private final StreamBridge streamBridge;
    private final OrderDataService orderDataService;
    private final ShippingServiceGrpc.ShippingServiceBlockingStub shippingStub;
    private final InventoryServiceGrpc.InventoryServiceBlockingStub inventoryStub;

    @Value("${services.inventory.url}")
    private String inventoryServiceUrl;

    @Value("${services.shipping.url}")
    private String shippingServiceUrl;

    public OrderController(RestTemplate restTemplate, 
                           StreamBridge streamBridge, 
                           OrderDataService orderDataService,
                           @GrpcClient("shipping-service") ShippingServiceGrpc.ShippingServiceBlockingStub shippingStub,
                           @GrpcClient("inventory-service") InventoryServiceGrpc.InventoryServiceBlockingStub inventoryStub) {
        this.restTemplate = restTemplate;
        this.streamBridge = streamBridge;
        this.orderDataService = orderDataService;
        this.shippingStub = shippingStub;
        this.inventoryStub = inventoryStub;
    }

    @GetMapping
    @Operation(summary = "Отримати всі замовлення")
    public List<Order> getAllOrders() {
        return orderDataService.getAllOrders();
    }

    @PostMapping
    @Operation(summary = "Створити замовлення (за замовчуванням)", description = "Перевіряє stock через REST, створює доставку через gRPC (аналог mixed-1)")
    public String createOrder(@RequestParam String productId, @RequestParam int quantity, @RequestParam String address) {
        return createOrderMixed1(productId, quantity, address);
    }

    @PostMapping("/mixed-1")
    @Operation(summary = "Створити замовлення (REST + gRPC)", description = "Перевіряє stock через REST, створює доставку через gRPC")
    public String createOrderMixed1(@RequestParam String productId, @RequestParam int quantity, @RequestParam String address) {
        // 1. Check stock via REST
        Integer availableStock = restTemplate.getForObject(inventoryServiceUrl + "/" + productId, Integer.class);
        
        if (availableStock == null || availableStock < quantity) {
            return "Order failed: Not enough stock (Available: " + (availableStock != null ? availableStock : 0) + ")";
        }

        // 2. Create order
        String orderId = UUID.randomUUID().toString();
        Order order = new Order(orderId, productId, quantity, address, "PENDING");
        orderDataService.saveOrder(order);

        // 3. Initiate shipping via gRPC
        ShipmentRequest shipmentRequest = ShipmentRequest.newBuilder()
                .setOrderId(orderId)
                .setAddress(address)
                .build();
        
        ShipmentResponse shipmentResponse = shippingStub.createShipment(shipmentRequest);

        // 4. Send event to RabbitMQ (Data circulation)
        streamBridge.send("order-out-0", "Order created for " + productId + " quantity " + quantity + " to " + address);

        return "Order " + orderId + " created successfully. " +
                "Stock checked via REST. " +
                "Shipment status: " + shipmentResponse.getStatus() + ", ID: " + shipmentResponse.getShipmentId() + " (via gRPC)";
    }

    @PostMapping("/mixed-2")
    @Operation(summary = "Створити замовлення (gRPC + REST)", description = "Перевіряє stock через gRPC, створює доставку через REST")
    public String createOrderMixed2(@RequestParam String productId, @RequestParam int quantity, @RequestParam String address) {
        // 1. Check stock via gRPC
        StockRequest stockRequest = StockRequest.newBuilder()
                .setProductId(productId)
                .setQuantity(quantity)
                .build();
        
        StockResponse stockResponse = inventoryStub.checkStock(stockRequest);
        
        if (!stockResponse.getAvailable()) {
            return "Order failed: Not enough stock (via gRPC)";
        }

        // 2. Create order
        String orderId = UUID.randomUUID().toString();
        Order order = new Order(orderId, productId, quantity, address, "PENDING");
        orderDataService.saveOrder(order);

        // 3. Initiate shipping via REST
        // ShippingController.createShipment expects orderId and address as RequestParam
        String url = shippingServiceUrl + "/create?orderId=" + orderId + "&address=" + address;
        String restResponse = restTemplate.postForObject(url, null, String.class);

        return "Order " + orderId + " created successfully. " +
                "Stock checked via gRPC. " +
                "Shipment status: " + restResponse + " (via REST)";
    }
}
