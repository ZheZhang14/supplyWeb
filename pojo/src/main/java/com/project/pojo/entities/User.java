package com.project.pojo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Slf4j
public class User {
    public User() {}
    private Integer id;
    private String username;
    private String password;

    private String email;
    private UserType userRole;

    private String contactName;

    private LocalDateTime dateCreated;

    private String phone;

    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getROLE() {
        return userRole;
    }

    public void setROLE(UserType role) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + userRole +
                '}';
    }
}
