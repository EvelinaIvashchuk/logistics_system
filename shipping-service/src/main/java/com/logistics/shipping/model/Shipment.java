package com.logistics.shipping.model;

import com.logistics.shipping.repository.ShipmentRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "shipments")
public class Shipment {
    @Id
    private String shipmentId;
    private String status;

    private static ShipmentRepository repository;

    public static void setRepository(ShipmentRepository repo) {
        repository = repo;
    }

    public Shipment() {}

    public Shipment(String shipmentId, String status) {
        this.shipmentId = shipmentId;
        this.status = status;
    }

    // Active Record methods
    public void save() {
        repository.save(this);
    }

    public static Optional<Shipment> findById(String id) {
        return repository.findById(id);
    }

    public static List<Shipment> findAll() {
        return repository.findAll();
    }

    // Getters and Setters
    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Component
    public static class RepositoryInjector {
        @Autowired
        public RepositoryInjector(ShipmentRepository repository) {
            Shipment.setRepository(repository);
        }
    }
}
