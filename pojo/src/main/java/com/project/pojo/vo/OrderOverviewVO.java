package com.project.pojo.vo;


import com.project.pojo.entities.OrderType;
import com.project.pojo.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderOverviewVO {
    private Integer id;
    private String productName;
    private BigDecimal price;
    private BigDecimal totalAmount;
    private Integer count;
    private String contactName;
    private OrderType orderType;
    private Status status;
}
