package com.project.mapper;

import com.project.pojo.dto.OrderCreatedDTO;
import com.project.pojo.entities.Order;
import com.project.pojo.entities.OrderType;
import com.project.pojo.vo.OrderOverviewVO;
import com.project.pojo.vo.OrderVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Select("SELECT o.id, o.total_amount AS totalAmount, o.order_type AS orderType, o.count, o.status, u.contact_name " +
            "AS contactName, p.product_name AS productName, p.price " +
            "FROM `order` o INNER JOIN user u ON o.user_id = u.id " +
            "INNER JOIN product p ON o.product_id = p.id WHERE o.user_id = #{userId} ")
    List<OrderOverviewVO> getOverviewByUserId(Integer id);

    @Select("SELECT o.id, o.total_amount AS totalAmount, o.order_type AS orderType, o.count, o.status, u.contact_name " +
            "AS contactName, p.product_name AS productName, p.price " +
            "FROM `order` o INNER JOIN user u ON o.user_id = u.id " +
            "INNER JOIN product p ON o.product_id = p.id ")
    List<Order> getAllOrders();

    @Select("SELECT o.id, o.total_amount AS totalAmount, o.order_type AS orderType, o.count, o.status, u.contact_name " +
            "AS contactName, p.product_name AS productName, p.price " +
            "FROM `order` o INNER JOIN user u ON o.user_id = u.id " +
            "INNER JOIN product p ON o.product_id = p.id WHERE o.id = #{id} ")
    List<Order> getOrderByUserId(Integer id);
    @Insert("INSERT INTO `order`(product_id,user_id,total_amount,count,order_type,status) " +
            "VALUES(#{productId}, #{userId}, #{totalAmount}, #{count}, #{orderType},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createOrder(OrderCreatedDTO orderCreatedDTO);

    void updateOrder(Order order);

    @Select("SELECT COUNT(*) FROM `order` WHERE order_type =  #{orderType}")
    Integer getCountByOrderType(OrderType orderType);

    @Select("SELECT product_id,count from `order` where id=#{id}")
    OrderVO getOrderById(Integer id);
}

