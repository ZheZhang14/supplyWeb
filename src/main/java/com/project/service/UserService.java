package com.project.service;

import com.project.pojo.dto.UserEditDTO;
import com.project.pojo.dto.UserLoginDTO;
import com.project.pojo.entities.User;
import com.project.pojo.entities.UserVO;
import com.project.pojo.vo.OrderOverviewVO;
import com.project.pojo.vo.OverviewVO;

import java.util.List;


public interface UserService {
    User login(UserLoginDTO userLoginDTO);

    void register(User newUser);

    User getUserById(Integer userId);

    List<UserVO> list();

    void update(UserEditDTO userEditDTO);

    OverviewVO getOverview();

    List<OrderOverviewVO> getOverviewByUserId(Integer id);
}
