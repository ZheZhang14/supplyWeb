package com.project.pojo.vo;

import com.project.pojo.entities.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserByIdVO {
    private Integer id;
    private String imagePath;
    private String username;
    private UserType userRole;
    private String contactName;
    private String email;
    private String phone;
    private String address;
}
