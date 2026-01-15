package com.logistics.inventory.service;

import com.logistics.inventory.model.InventoryItem;
import com.logistics.inventory.repository.InventoryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
            inventoryRepository.save(new InventoryItem("prod-001", "Laptop Pro", "High-performance laptop", 1200.0, "Electronics", 15));
            inventoryRepository.save(new InventoryItem("prod-002", "Smartphone X", "Latest smartphone model", 800.0, "Electronics", 30));
            inventoryRepository.save(new InventoryItem("prod-003", "Wireless Mouse", "Ergonomic wireless mouse", 25.0, "Accessories", 50));
            inventoryRepository.save(new InventoryItem("prod-004", "Mechanical Keyboard", "RGB mechanical keyboard", 100.0, "Accessories", 20));
            inventoryRepository.save(new InventoryItem("prod-005", "Office Chair", "Comfortable office chair", 250.0, "Furniture", 10));
        }
    }

    public int getStock(String productId) {
        return inventoryRepository.findById(productId)
                .map(InventoryItem::getQuantity)
                .orElse(0);
    }

    public void addStock(String productId, int quantity) {
        InventoryItem item = inventoryRepository.findById(productId)
                .orElse(new InventoryItem(productId, "New Product", "Description", 0.0, "General", 0));
        item.setQuantity(item.getQuantity() + quantity);
        inventoryRepository.save(item);
    }

    public List<InventoryItem> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Optional<InventoryItem> getProduct(String productId) {
        return inventoryRepository.findById(productId);
    }

    public boolean checkStock(String productId, int quantity) {
        return getStock(productId) >= quantity;
    }
}
