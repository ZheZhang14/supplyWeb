package com.project.controller.api;

import com.project.constant.JwtClaimsConstant;
import com.project.context.BaseContext;
import com.project.pojo.dto.*;
import com.project.pojo.entities.*;
import com.project.pojo.vo.OrderOverviewVO;
import com.project.pojo.vo.OverviewVO;
import com.project.pojo.vo.UserLoginVO;
import com.project.properties.JwtProperties;
import com.project.result.Result;
import com.project.service.InventoryService;
import com.project.service.OrderService;
import com.project.service.ProductService;
import com.project.service.UserService;
import com.project.utils.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v2")
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private InventoryService inventoryService;

    /**
     * User related, including sign in, signup, edit info, get all users, get user by id
     */
    @PostMapping("/users/signin")
    public Result<UserLoginVO> signin(@RequestBody UserLoginDTO userLoginDTO) {
        User user = userService.login(userLoginDTO);

        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .token(token)
                .build();

        return Result.success(userLoginVO);
    }

    @PostMapping("/users/signup")
    public Result signup(@RequestBody UserRegisterDTO userRegisterDTO){
        User newUser = new User();
        BeanUtils.copyProperties(userRegisterDTO,newUser);
        newUser.setDateCreated(LocalDateTime.now());
        newUser.setPassword(DigestUtils.md5DigestAsHex(userRegisterDTO.getPassword().getBytes()));
        userService.register(newUser);
        return Result.success();
    }

    @PostMapping("/users/{id}/edit")
    public Result editUser(@RequestBody UserEditDTO userEditDTO) {
        if (userEditDTO != null) {
            if (userEditDTO.getPassword() != null && !userEditDTO.getPassword().isEmpty()) {
                String hashedPassword = DigestUtils.md5DigestAsHex(userEditDTO.getPassword().getBytes());
                userEditDTO.setPassword(hashedPassword);
            }
            userService.update(userEditDTO);
            return Result.success();
        } else {
            // Handle the case where userEditDTO is null
            return Result.error("Invalid request body");
        }
    }


    @GetMapping("/users")
    public Result<List<UserVO>> getAllUsers() {
        List<UserVO> list = userService.list();
        return Result.success(list);
    }

    @GetMapping("/users/{userId}")
    public Result<User> getUserById(@PathVariable Integer userId) {
        User user = userService.getUserById(userId);
        return Result.success(user);
    }

    /**
     * Overview, including get overview of all users, get self overview
     */
    @GetMapping("/overview")
    public Result<OverviewVO> getOverview(){
       OverviewVO overviewVO = userService.getOverview();
        return Result.success(overviewVO);
    }

    @GetMapping("/overview/{id}")
    public Result<List<OrderOverviewVO>> getOverviewByUserId(@PathVariable Integer id) {
        List<OrderOverviewVO> list = userService.getOverviewByUserId(id);
        return Result.success(list);
    }

    /**
     * Product related, including get all products, get self products(products provided by the vendor)
     * create product and product stage change
     */
    @GetMapping("/products")
    public Result<List<Product>> getAllProducts() {
        return Result.success(productService.getAllProducts());
    }

    @GetMapping("/products/{id}")
    public Result<Product> getProductByUserId(@PathVariable(value = "id") Integer id){
       return Result.success(productService.getProductByUserId(id));
    }

    @PostMapping("/products/create")
    public Result createProduct(@RequestBody ProductCreatedDTO productCreatedDTO){
        productCreatedDTO.setUserId(BaseContext.getCurrentId());
        productCreatedDTO.setStock(1000);
        productService.createProduct(productCreatedDTO);
        return Result.success();
    }

    @PostMapping("/products/{id}/update/stage")
    public Result<String> updateProductStage(@PathVariable(value = "id") Integer id, Stage stage){
        productService.updateProductsStage(id,stage);
        return Result.success();
    }

    /**
     * Order related, including get all orders, get self orders(orders to the vendor)
     * create order and update order status
     */
    @GetMapping("/orders")
    public Result<List<Order>> getAllOrders() {
        List<Order> list = orderService.getAllOrders();
        return Result.success(list);
    }

    @GetMapping("/orders/{id}")
    public Result <List<Order>> getOrderByUserId(@PathVariable(value = "id") Integer id){
       List<Order> list = orderService.getOrderByUserId(id);
        return Result.success(list);
    }

    @PostMapping("/orders/create")
    public Result createOrder(@RequestBody OrderCreatedDTO orderCreatedDTO){
        orderCreatedDTO.setUserId(BaseContext.getCurrentId());
        orderService.createOrder(orderCreatedDTO);
        return Result.success();
    }

    @PostMapping("/orders/{id}/update/status")
    public Result<String> updateOrder(@PathVariable(value="id") Integer id, Status status){
        orderService.updateOrder(id,status);
        return Result.success();
    }

    @GetMapping("/inventory")
    public Result<List<Inventory>> getInventory(){
        List<Inventory> list = inventoryService.getAllInventory();
        return Result.success(list);
    }
}