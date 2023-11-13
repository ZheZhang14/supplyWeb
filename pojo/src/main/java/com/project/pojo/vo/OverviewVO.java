package com.project.pojo.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OverviewVO {
    private Integer supplierCount;
    private Integer distributorCount;
    private Integer manufacturerCount;
    private Integer orderCreatedCount;
    private Integer inProgressCount;
    private Integer returnCount;
    private Integer rejectedCount;
    private Integer doneCount;
    private Integer onMarketCount;
    private Integer offMarketCount;
}
