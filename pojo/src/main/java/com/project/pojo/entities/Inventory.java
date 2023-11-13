package com.project.pojo.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory{
    private Integer id;
    private Integer productId;
    private Integer stockQuantity;
    private Integer damagedGoodsCount;
    private Valuation Good;
}
