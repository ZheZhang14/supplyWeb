package com.project.service;

import com.project.pojo.dto.ProductCreatedDTO;
import com.project.pojo.entities.Product;
import com.project.pojo.entities.Stage;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product getProductByUserId(Integer id);

    void createProduct(ProductCreatedDTO productCreatedDTO);

    void updateProductsStage(Integer id, Stage stage);
}
