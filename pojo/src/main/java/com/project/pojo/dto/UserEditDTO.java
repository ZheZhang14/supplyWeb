package com.project.pojo.dto;


import com.project.pojo.entities.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEditDTO {
    private Integer id;
    private String password;
    private String email;
    private String contactName;
    private String phone;
    private String address;
    private LocalDateTime dateCreated;
    private Long updateUser;
}
