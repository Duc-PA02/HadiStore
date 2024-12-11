package com.example.hadistore.service;

import com.example.hadistore.dtos.request.ProductRequest;
import com.example.hadistore.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    List<Product> getProductBestSeller();
    List<Product> findTop10ByOrderBySoldDesc();

    List<Product> getRated();

    List<Product> getLasted();

    Product getProductById(Long productId);

    List<Product> getProductByCategory(Long categoryId);
    List<Product> findProductSuggest(Long categoryId, Long productId);

    Product createProduct(ProductRequest productRequest);
    Product updateProduct(Long id, ProductRequest productRequest);
    void deleteProduct(Long id);
}
