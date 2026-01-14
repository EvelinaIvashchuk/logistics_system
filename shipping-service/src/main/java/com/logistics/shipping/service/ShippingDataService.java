package com.logistics.shipping.service;

import com.logistics.shipping.model.Shipment;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShippingDataService {

    public String createShipment(String orderId, String address) {
        String shipmentId = UUID.randomUUID().toString();
        Shipment shipment = new Shipment(shipmentId, "Pending for Order: " + orderId + " at " + address);
        shipment.save();
        return shipmentId;
    }

    public String getShipmentStatus(String shipmentId) {
        return Shipment.findById(shipmentId)
                .map(Shipment::getStatus)
                .orElse("Shipment not found");
    }

    public Map<String, String> getAllShipments() {
        return Shipment.findAll().stream()
                .collect(Collectors.toMap(Shipment::getShipmentId, Shipment::getStatus));
    }
}
