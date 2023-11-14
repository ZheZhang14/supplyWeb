package com.project.pojo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryDTO {
    private Integer damagedGoodsCount;
    private Integer expiredGoodsCount;
    private String valuation;
}
