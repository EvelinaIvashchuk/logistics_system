package com.logistics.shipping.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipments")
public class Shipment {
    @Id
    private String shipmentId;
    private String orderId;
    private String status;
    private String address;
    private String carrier;
    private LocalDateTime shippedDate;
    private LocalDateTime estimatedDeliveryDate;

    public Shipment() {}

    public Shipment(String shipmentId, String orderId, String status, String address, String carrier, LocalDateTime shippedDate, LocalDateTime estimatedDeliveryDate) {
        this.shipmentId = shipmentId;
        this.orderId = orderId;
        this.status = status;
        this.address = address;
        this.carrier = carrier;
        this.shippedDate = shippedDate;
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    // Getters and Setters
    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public LocalDateTime getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(LocalDateTime shippedDate) {
        this.shippedDate = shippedDate;
    }

    public LocalDateTime getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(LocalDateTime estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }
}
