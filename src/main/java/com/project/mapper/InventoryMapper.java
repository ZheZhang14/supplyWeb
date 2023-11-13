package com.project.mapper;

import com.project.pojo.entities.Inventory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface InventoryMapper {

    @Insert("insert into inventory (stock,product_id) VALUES (#{stock},#{id})")
    void insertStock(Integer stock,Integer id);

    @Select("select stock from inventory where product_id=#{productId}")
    Integer getstock(Integer productId);

    @Update("update inventory set stock=#{updateCount} where product_id=#{productId}")
    void update(Integer updateCount, Integer productId);

    @Select("select product_id from inventory;")
    List<Integer> getProductId();

    @Select("SELECT COUNT(*) FROM inventory WHERE product_id=#{productId}")
    Integer getDamagedCount(Integer productId);

    @Update("update inventory set damagedGoods_count=#{totalCount}")
    void updateDamagedCount(Integer totalCount);

    @Insert("insert into inventory (damagedGoods_count) VALUES (#{count})")
    void insertDamagedCount(Integer count);

    @Select("SELECT inventory.*, product.product_name " +
            "FROM inventory " +
            "JOIN product ON inventory.product_id = product.id")
    List<Inventory> getAll();
}
