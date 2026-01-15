package com.logistics.order.service;

import com.logistics.order.model.Order;
import com.logistics.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;

@Service
public class OrderDataService {
    private final OrderRepository orderRepository;

    public OrderDataService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostConstruct
    public void init() {
        if (orderRepository.count() == 0) {
            orderRepository.save(new Order("ORD-1001", "prod-001", 1, "Kyiv, Khreschatyk 1", "COMPLETED", LocalDateTime.now().minusDays(2), 1200.0, "Ivan Ivanov"));
            orderRepository.save(new Order("ORD-1002", "prod-002", 2, "Lviv, Svobody Ave 10", "SHIPPED", LocalDateTime.now().minusDays(1), 1600.0, "Maria Petrenko"));
            orderRepository.save(new Order("ORD-1003", "prod-003", 5, "Odesa, Deribasivska 5", "PENDING", LocalDateTime.now(), 125.0, "Oleksandr Sydorenko"));
        }
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Optional<Order> getOrder(String orderId) {
        return orderRepository.findById(orderId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
