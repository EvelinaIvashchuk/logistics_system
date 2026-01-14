package com.logistics.inventory.controller;

import com.logistics.inventory.service.InventoryDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/inventory")
@Tag(name = "Inventory Controller", description = "Управління запасами товарів")
public class InventoryController {

    private final InventoryDataService inventoryDataService;

    public InventoryController(InventoryDataService inventoryDataService) {
        this.inventoryDataService = inventoryDataService;
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Отримати кількість товару", description = "Повертає доступну кількість товару за його ID")
    public Integer getStock(@PathVariable String productId) {
        return inventoryDataService.getStock(productId);
    }

    @PostMapping("/{productId}/add")
    @Operation(summary = "Додати товар на склад", description = "Збільшує кількість товару на складі")
    public String addStock(@PathVariable String productId, @RequestParam int quantity) {
        inventoryDataService.addStock(productId, quantity);
        return "Stock updated for " + productId + ". New quantity: " + inventoryDataService.getStock(productId);
    }

    @GetMapping
    @Operation(summary = "Отримати весь інвентар", description = "Повертає список усіх товарів та їх кількість")
    public Map<String, Integer> getAllInventory() {
        return inventoryDataService.getAllInventory();
    }
}
