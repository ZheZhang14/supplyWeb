package com.project.service.impl;

import com.project.mapper.InventoryMapper;
import com.project.mapper.OrderMapper;
import com.project.pojo.dto.InventoryDTO;
import com.project.pojo.entities.Inventory;
import com.project.pojo.entities.Order;
import com.project.pojo.entities.Status;
import com.project.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    public List<Inventory> getAllInventory() {
        List<Inventory> list1 = inventoryMapper.getAll();
        return list1;
    }

    @Override
    public void updateInventory(Integer id, InventoryDTO inventoryDTO) {
        Integer getstock = inventoryMapper.getstock(id);
        if(inventoryDTO.getExpiredGoodsCount() == null){
            Integer totalStock = getstock-inventoryDTO.getExpiredGoodsCount();
            inventoryMapper.updateById(id,totalStock);
        }else if(inventoryDTO.getDamagedGoodsCount() == null){
            Integer totalStock = getstock-inventoryDTO.getDamagedGoodsCount();
            inventoryMapper.updateById(id,totalStock);
        }else{
            Integer totoalStock = getstock-inventoryDTO.getDamagedGoodsCount()- inventoryDTO.getExpiredGoodsCount();
            inventoryMapper.updateById(id,totoalStock);
        }
        inventoryMapper.updateDamagedOrExpiredCount(id,inventoryDTO);
    }
}
