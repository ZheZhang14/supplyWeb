package com.project.pojo.vo;

import com.project.pojo.entities.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEditVO {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private UserType userRole;
    private String contactName;
    private LocalDateTime dateCreated;
    private String phone;
    private String address;
    private String imagePath;
}
