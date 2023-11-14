package com.project.service;

import com.project.pojo.dto.InventoryDTO;
import com.project.pojo.entities.Inventory;

import java.util.List;

public interface InventoryService {
    List<Inventory> getAllInventory();

    void updateInventory(Integer id, InventoryDTO inventoryDTO);
}
