package com.logistics.order.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    private String orderId;
    private String productId;
    private int quantity;
    private String address;
    private String status;
    private LocalDateTime orderDate;
    private double totalAmount;
    private String customerName;

    public Order() {}

    public Order(String orderId, String productId, int quantity, String address, String status, LocalDateTime orderDate, double totalAmount, String customerName) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.address = address;
        this.status = status;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.customerName = customerName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
