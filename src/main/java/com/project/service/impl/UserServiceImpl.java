package com.project.service.impl;

import com.project.constant.MessageConstant;
import com.project.exception.PasswordErrorException;
import com.project.mapper.OrderMapper;
import com.project.mapper.UserMapper;
import com.project.pojo.dto.UserEditDTO;
import com.project.pojo.dto.UserLoginDTO;
import com.project.pojo.entities.User;
import com.project.pojo.entities.UserVO;
import com.project.pojo.vo.OrderOverviewVO;
import com.project.pojo.vo.OverviewVO;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import com.project.exception.AccountNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.pojo.entities.Stage.Off_Market;
import static com.project.pojo.entities.Stage.On_Market;
import static com.project.pojo.entities.Status.*;
import static com.project.pojo.entities.UserType.*;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;


    public User login(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        User user = userMapper.getByUsername(username);
        if (user == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //对前端传过来的明文密码进行md5加密处理
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        //3、返回实体对象
        return user;
    }

    public void register(User newUser) {
        User user = userMapper.getByUserId(newUser.getId());
        if(user.getContactName() == newUser.getContactName()){
            throw new RuntimeException("This contactName has already exist");
        }
        userMapper.register(newUser);
    }

    public User getUserById(Integer userId) {
        User user = userMapper.getByUserId(userId);
        return user;
    }

    public List<UserVO> list() {
        List<UserVO> list = userMapper.getAll();
        return list;
    }

    public void update(UserEditDTO userEditDTO) {
        userMapper.updateDetails(userEditDTO);
    }


    public OverviewVO getOverview() {
        Map map = new HashMap<>();
        map.put("user_role",supplier);
        Integer supplierCount = userMapper.countByMap(map);

        map.put("user_role",manufacturer);
        Integer manufacturerCount = userMapper.countByMap(map);

        map.put("user_role",distributor);
        Integer distributorCount = userMapper.countByMap(map);

        map.put("status",Done);
        Integer orderDoneCount= userMapper.countByMap1(map);

        map.put("status",In_Progress);
        Integer inProgressCount= userMapper.countByMap1(map);

        map.put("status",Return);
        Integer returnCount= userMapper.countByMap1(map);

        map.put("status",Rejected);
        Integer rejectedCount= userMapper.countByMap1(map);

        map.put("stage",On_Market);
        Integer onMarket = userMapper.countByMap2(map);

        map.put("stage",Off_Market);
        Integer offMarket = userMapper.countByMap2(map);


        return OverviewVO.builder()
                .supplierCount(supplierCount)
                .distributorCount(distributorCount)
                .manufacturerCount(manufacturerCount)
                .inProgressCount(inProgressCount)
                .doneCount(orderDoneCount)
                .offMarketCount(offMarket)
                .onMarketCount(onMarket)
                .rejectedCount(rejectedCount)
                .returnCount(returnCount)
                .build();
    }

    @Override
    public List<OrderOverviewVO> getOverviewByUserId(Integer id) {
        List<OrderOverviewVO> list = orderMapper.getOverviewByUserId(id);
        return list;
    }
}
