package com.project.service.impl;

import com.project.mapper.InventoryMapper;
import com.project.mapper.ProductMapper;
import com.project.pojo.dto.ProductCreatedDTO;
import com.project.pojo.entities.Product;
import com.project.pojo.entities.Stage;
import com.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    public List<Product> getAllProducts() {
        return productMapper.getAll();
    }

    @Override
    public Product getProductByUserId(Integer id) {
        return productMapper.getProductById(id);
    }

    @Override
    public void createProduct(ProductCreatedDTO productCreatedDTO) {
        productMapper.insert(productCreatedDTO);
    }

    @Override
    public void updateProductsStage(Integer id, Stage stage) {
        Product product = Product.builder()
                .id(id)
                .stage(stage)
                .build();

        productMapper.update(product);
    }
}
