package com.project.pojo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreatedDTO {
    private String productName;
    private String description;
    private BigDecimal price;
    private Integer userId;
    private Integer id;
}
