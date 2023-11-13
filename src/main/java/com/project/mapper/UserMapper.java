package com.project.mapper;


import com.project.pojo.dto.UserEditDTO;
import com.project.pojo.entities.User;
import com.project.pojo.entities.UserVO;
import com.project.pojo.vo.OverviewVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


@Mapper
public interface UserMapper {

    @Select("select * from user where username=#{username};")
    User getByUsername(String username);

    @Insert("INSERT INTO user(username, password, email, contact_name, phone, user_role, date_created,address) " +
            "VALUES(#{username}, #{password}, #{email}, #{contactName}, #{phone}, #{userRole}, #{dateCreated},#{address})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void register(User newUser);

    @Select("select * from user where id=#{userId};")
    User getByUserId(Integer userId);

    @Select("SELECT id, contact_name, user_role FROM user")
    List<UserVO> getAll();

    void updateDetails(UserEditDTO userEditDTO);


    Integer countByMap(Map map);

    Integer countByMap1(Map map);

    Integer countByMap2(Map map);
}
