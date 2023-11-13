package com.project.service.impl;

import com.project.mapper.InventoryMapper;
import com.project.mapper.OrderMapper;
import com.project.mapper.ProductMapper;
import com.project.pojo.dto.OrderCreatedDTO;
import com.project.pojo.entities.Order;
import com.project.pojo.entities.OrderType;
import com.project.pojo.entities.Product;
import com.project.pojo.entities.Status;
import com.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    public List<Order> getAllOrders() {
        List<Order> list = orderMapper.getAllOrders();
        return list;
    }

    @Override
    public List<Order> getOrderByUserId(Integer id) {
        List<Order> list1 = orderMapper.getOrderByUserId(id);
        return list1;
    }

    @Override
    public void createOrder(OrderCreatedDTO orderCreatedDTO) {
        Product product = productMapper.getProductById(orderCreatedDTO.getProductId());
        if (product != null) {
            if (product.getStage().equals("Off_Market")) {
                throw new RuntimeException("This product has been off market");
            }
            BigDecimal price = product.getPrice();
            BigDecimal count = new BigDecimal(orderCreatedDTO.getCount());
            BigDecimal totalPrice = price.multiply(count);
            orderCreatedDTO.setTotalAmount(totalPrice);
        } else {
            throw new RuntimeException("This product does not exist");
        }
        if (orderCreatedDTO.getCount() > inventoryMapper.getstock(orderCreatedDTO.getProductId())) {
            throw new RuntimeException("Inventory Shortage");
        }

        if (orderCreatedDTO.getOrderType().equals(OrderType.Return)) {
            List<Integer> productIds = inventoryMapper.getProductId();
            boolean flag = false;
            for (Integer productId : productIds) {
                if (orderCreatedDTO.getProductId() == productId) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                Integer count = orderMapper.getCountByOrderType(orderCreatedDTO.getOrderType());
                Integer count1 = inventoryMapper.getDamagedCount(orderCreatedDTO.getProductId());
                Integer totalCount = count1 + count;
                inventoryMapper.updateDamagedCount(totalCount);
            } else {
                Integer count = orderMapper.getCountByOrderType(orderCreatedDTO.getOrderType());
                inventoryMapper.insertDamagedCount(count);
            }
        }
        Integer count1 = orderCreatedDTO.getCount();
        Integer updateCount = inventoryMapper.getstock(orderCreatedDTO.getProductId()) - count1;
        Integer productId = orderCreatedDTO.getProductId();
        inventoryMapper.update(updateCount, productId);
        orderMapper.createOrder(orderCreatedDTO);
    }

    @Override
    public void updateOrder(Integer id, Status status) {
        Order order = Order.builder()
                .id(id)
                .status(status)
                .build();
        orderMapper.updateOrder(order);
    }
}
