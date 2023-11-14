package com.project.controller.api;

import com.project.constant.JwtClaimsConstant;
import com.project.context.BaseContext;
import com.project.pojo.dto.*;
import com.project.pojo.entities.*;
import com.project.pojo.vo.*;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;


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
    public Result signup(@RequestBody UserRegisterDTO userRegisterDTO) throws Exception {
        User newUser = new User();
        String originalFilename = userRegisterDTO.getImage().getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String extname = originalFilename.substring(index);
        String newFileName = UUID.randomUUID().toString() +extname;
        String filePath = "G:\\develop\\image\\" + newFileName;
        userRegisterDTO.getImage().transferTo(new File("G:\\develop\\image\\"+newFileName));
        BeanUtils.copyProperties(userRegisterDTO,newUser);
        newUser.setImagePath(filePath);
        newUser.setDateCreated(LocalDateTime.now());
        newUser.setPassword(DigestUtils.md5DigestAsHex(userRegisterDTO.getPassword().getBytes()));
        userService.register(newUser);
        return Result.success();
    }


    @PostMapping("/users/{id}/edit")
    public Result editUser(@RequestBody UserEditDTO userEditDTO) throws Exception {
        if (userEditDTO != null) {
            if (userEditDTO.getPassword() != null && !userEditDTO.getPassword().isEmpty()) {
                String hashedPassword = DigestUtils.md5DigestAsHex(userEditDTO.getPassword().getBytes());
                userEditDTO.setPassword(hashedPassword);
            }
            UserByIdVO existingUser = userService.getUserById(userEditDTO.getId());
            if (!existingUser.getUsername().equals(userEditDTO.getUsername())) {
                return Result.error("Username cannot be changed");
            }
            if (existingUser.getUserRole() != userEditDTO.getUserRole()) {
                return Result.error("User role cannot be changed");
            }
            String originalFilename = userEditDTO.getImage().getOriginalFilename();
            int index = originalFilename.lastIndexOf(".");
            String extname = originalFilename.substring(index);
            String newFileName = UUID.randomUUID().toString() +extname;
            String filePath = "G:\\develop\\image\\" + newFileName;
            userEditDTO.getImage().transferTo(new File("G:\\develop\\image\\"+newFileName));
            userEditDTO.setImagePath(filePath);
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
    public Result<UserByIdVO> getUserById(@PathVariable Integer userId) {
        UserByIdVO userById = userService.getUserById(userId);
        if (userById.getImagePath() != null && Files.exists(Paths.get(userById.getImagePath()))) {
            String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/users/")
                    .path(userById.getId().toString())
                    .toUriString();
            userById.setImagePath(imageUrl); // 设置图片的URL
        }
        return Result.success(userById);
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

    @PostMapping("/inventory/{id}/update")
    public Result<String> updateInventory(@PathVariable(value="id") Integer id, @RequestBody InventoryDTO inventoryDTO){
        inventoryService.updateInventory(id,inventoryDTO);
        return Result.success();
    }
}