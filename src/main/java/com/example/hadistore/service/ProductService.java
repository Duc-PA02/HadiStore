package com.example.hadistore.service;

import com.example.hadistore.dtos.request.ProductRequest;
import com.example.hadistore.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    List<Product> getProductBestSeller();

    List<Product> getRated();

    List<Product> getLasted();

    Product getProductById(Long productId);

    List<Product> getProductByCategory(Integer categoryId);

    Product createProduct(ProductRequest productRequest);
}
