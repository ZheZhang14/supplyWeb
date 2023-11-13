package com.project.mapper;

import com.project.pojo.dto.ProductCreatedDTO;
import com.project.pojo.entities.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Select("SELECT product.*, u.contact_name FROM product Join user u on product.user_id = u.id;")
    List<Product> getAll();


    @Select("select product.*,u.contact_name from product " +
            "join user u on u.id = product.user_id where product.id=#{id};")
    Product getProductById(Integer id);


    @Insert("INSERT INTO product (product_name, description, price, user_id) " +
            "VALUES (#{productName}, #{description}, #{price}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(ProductCreatedDTO productCreatedDTO);

    void update(Product product);
}
