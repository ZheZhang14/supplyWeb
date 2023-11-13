package com.project.pojo.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    private Integer id;
    private String productName;
    private Integer count;
    private Integer totalAmount;
    private String contactName;
    private OrderType orderType;
    private Status status;
}
