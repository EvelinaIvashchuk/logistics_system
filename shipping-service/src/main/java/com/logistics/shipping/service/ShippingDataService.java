package com.logistics.shipping.service;

import com.logistics.shipping.model.Shipment;
import com.logistics.shipping.repository.ShipmentRepository;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;

@Service
public class ShippingDataService {

    private final ShipmentRepository shipmentRepository;

    public ShippingDataService(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @PostConstruct
    public void init() {
        if (shipmentRepository.findAll().isEmpty()) {
            Shipment s1 = new Shipment("SHP-5001", "ORD-1001", "DELIVERED", "Kyiv, Khreschatyk 1", "DHL", LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1));
            shipmentRepository.save(s1);
            Shipment s2 = new Shipment("SHP-5002", "ORD-1002", "IN_TRANSIT", "Lviv, Svobody Ave 10", "Nova Poshta", LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
            shipmentRepository.save(s2);
        }
    }

    public String createShipment(String orderId, String address) {
        String shipmentId = UUID.randomUUID().toString();
        Shipment shipment = new Shipment(shipmentId, orderId, "PENDING", address, "Standard Carrier", LocalDateTime.now(), LocalDateTime.now().plusDays(3));
        shipmentRepository.save(shipment);
        return shipmentId;
    }

    public String getShipmentStatus(String shipmentId) {
        return shipmentRepository.findById(shipmentId)
                .map(Shipment::getStatus)
                .orElse("Shipment not found");
    }

    public Map<String, String> getAllShipments() {
        return shipmentRepository.findAll().stream()
                .collect(Collectors.toMap(Shipment::getShipmentId, Shipment::getStatus));
    }
}
