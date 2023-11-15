package com.project.service.impl;

import com.project.mapper.InventoryMapper;
import com.project.mapper.OrderMapper;
import com.project.mapper.ProductMapper;
import com.project.pojo.dto.OrderCreatedDTO;
import com.project.pojo.entities.Order;
import com.project.pojo.entities.Product;
import com.project.pojo.entities.Status;
import com.project.pojo.vo.OrderVO;
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
        orderMapper.createOrder(orderCreatedDTO);
    }

    @Override
    public void updateOrder(Integer id, Status status) {
        Order order = Order.builder()
                .id(id)
                .status(status)
                .build();
        OrderVO order1 = orderMapper.getOrderById(id);
        Integer productId = order1.getProductId();
        if(status.equals(Status.Done)){
            List<Integer> productIds = inventoryMapper.getProductId();
            boolean flag = false;
            for(Integer productId1 : productIds){
                if(id == productId1){
                    flag = true;
                    break;
                }
            }
            if(flag){
                Integer count = order1.getCount();
                Integer stock = inventoryMapper.getstock(productId);
                Integer totalStock = count+stock;
                inventoryMapper.updateStock(totalStock,productId);
            }else{
                Integer count = order1.getCount();
                inventoryMapper.insertStock(count,productId);
            }
        }
        if(status.equals(Status.Return)){
            List<Integer> productIds = inventoryMapper.getProductId();
            for(Integer productId1 : productIds){
                if(id == productId1){
                    Integer count = order1.getCount();
                    Integer stock = inventoryMapper.getstock(productId);
                    Integer totalCount = count-stock;
                    inventoryMapper.updateStock(totalCount,productId);
                    break;
                }
            }
        }
        orderMapper.updateOrder(order);
    }
}
