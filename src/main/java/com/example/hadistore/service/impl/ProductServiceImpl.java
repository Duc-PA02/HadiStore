package com.example.hadistore.service.impl;

import com.example.hadistore.dtos.request.ProductRequest;
import com.example.hadistore.entity.Category;
import com.example.hadistore.entity.Product;
import com.example.hadistore.repository.CategoryRepository;
import com.example.hadistore.repository.ProductRepository;
import com.example.hadistore.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findByStatusTrue();
    }

    @Override
    public List<Product> getProductBestSeller() {
        return productRepository.findByStatusTrueOrderBySoldDesc();
    }

    @Override
    public List<Product> getRated() {
        return productRepository.findProductRated();
    }

    @Override
    public List<Product> getLasted() {
        return productRepository.findByStatusTrueOrderByEnteredDateDesc();
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(()->new EntityNotFoundException("Product not found with ID: " + productId));
    }

    @Override
    public List<Product> getProductByCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new EntityNotFoundException("Category not found with ID: " + categoryId));
        return productRepository.findByCategory(category);
    }

    @Override
    public Product createProduct(ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(()->new EntityNotFoundException("Category not found with ID: " + productRequest.getCategoryId()));
        Product product = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .quantity(productRequest.getQuantity())
                .discount(productRequest.getDiscount())
                .status(true)
                .category(category)
                .build();
        productRepository.save(product);
        return product;
    }
}
