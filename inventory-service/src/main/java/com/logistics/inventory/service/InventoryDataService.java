package com.logistics.inventory.service;

import com.logistics.inventory.model.InventoryItem;
import com.logistics.inventory.repository.InventoryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InventoryDataService {
    private final InventoryRepository inventoryRepository;

    public InventoryDataService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @PostConstruct
    public void init() {
        if (inventoryRepository.count() == 0) {
            inventoryRepository.save(new InventoryItem("product-1", 10));
            inventoryRepository.save(new InventoryItem("product-2", 5));
            inventoryRepository.save(new InventoryItem("product-3", 0));
        }
    }

    public int getStock(String productId) {
        return inventoryRepository.findById(productId)
                .map(InventoryItem::getQuantity)
                .orElse(0);
    }

    public void addStock(String productId, int quantity) {
        InventoryItem item = inventoryRepository.findById(productId)
                .orElse(new InventoryItem(productId, 0));
        item.setQuantity(item.getQuantity() + quantity);
        inventoryRepository.save(item);
    }

    public Map<String, Integer> getAllInventory() {
        return inventoryRepository.findAll().stream()
                .collect(Collectors.toMap(InventoryItem::getProductId, InventoryItem::getQuantity));
    }

    public boolean checkStock(String productId, int quantity) {
        return getStock(productId) >= quantity;
    }
}
