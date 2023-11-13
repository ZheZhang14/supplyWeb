package com.project.pojo.dto;

import com.project.pojo.entities.OrderType;
import com.project.pojo.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedDTO {
    private Integer id;
    private Integer productId;
    private Integer userId;
    private Integer count;
    private OrderType orderType;
    private BigDecimal totalAmount;
    private Status status;
}
