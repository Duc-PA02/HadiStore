package com.example.hadistore.service.impl;

import com.example.hadistore.dtos.request.ProductRequest;
import com.example.hadistore.entity.Category;
import com.example.hadistore.entity.Product;
import com.example.hadistore.exceptions.DataNotFoundException;
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
    public List<Product> getProductByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new EntityNotFoundException("Category not found with ID: " + categoryId));
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> findProductSuggest(Long categoryId, Long productId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new DataNotFoundException("Category not found"));
        productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        return productRepository.findSuggestedProducts(categoryId, productId);
    }

    @Override
    public Product createProduct(ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(()->new EntityNotFoundException("Category not found with ID: " + productRequest.getCategoryId()));
        Product product = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .quantity(productRequest.getQuantity())
                .image(productRequest.getImage())
                .discount(productRequest.getDiscount())
                .status(true)
                .category(category)
                .build();
        productRepository.save(product);
        return product;
    }

    @Override
    public Product updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                        .orElseThrow(()->new DataNotFoundException("Category not found"));
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setDiscount(productRequest.getDiscount());
        product.setCategory(category);
        product.setDescription(productRequest.getDescription());
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Product not found"));
        product.setStatus(false);
        productRepository.save(product);
    }
}
