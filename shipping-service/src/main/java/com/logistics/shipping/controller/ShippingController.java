package com.logistics.shipping.controller;

import com.logistics.shipping.service.ShippingDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/shipping")
@Tag(name = "Shipping Controller", description = "Управління доставкою")
public class ShippingController {

    private final ShippingDataService shippingDataService;

    public ShippingController(ShippingDataService shippingDataService) {
        this.shippingDataService = shippingDataService;
    }

    @PostMapping("/create")
    @Operation(summary = "Створити доставку", description = "Створює нову доставку для замовлення")
    public String createShipment(@RequestParam String orderId, @RequestParam String address) {
        String shipmentId = shippingDataService.createShipment(orderId, address);
        return "Shipment created with ID: " + shipmentId;
    }

    @GetMapping("/{shipmentId}")
    @Operation(summary = "Отримати статус доставки", description = "Повертає інформацію про доставку за її ID")
    public String getShipmentStatus(@PathVariable String shipmentId) {
        return shippingDataService.getShipmentStatus(shipmentId);
    }

    @GetMapping
    @Operation(summary = "Всі доставки", description = "Повертає список усіх доставок")
    public Map<String, String> getAllShipments() {
        return shippingDataService.getAllShipments();
    }
}
