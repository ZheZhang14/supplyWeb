package com.project.service;

import com.project.pojo.dto.OrderCreatedDTO;
import com.project.pojo.entities.Order;
import com.project.pojo.entities.Status;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();

    List<Order> getOrderByUserId(Integer id);

    void createOrder(OrderCreatedDTO orderCreatedDTO);

    void updateOrder(Integer id, Status status);
}
