package com.example.hadistore.controller;

import com.example.hadistore.dtos.request.ProductRequest;
import com.example.hadistore.entity.Product;
import com.example.hadistore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAll(){
        return ResponseEntity.ok(productService.getAllProducts());
    }
    @GetMapping("bestseller")
    public ResponseEntity<List<Product>> getBestSeller() {
        return ResponseEntity.ok(productService.getProductBestSeller());
    }
    @GetMapping("latest")
    public ResponseEntity<List<Product>> getLasted() {
        return ResponseEntity.ok(productService.getLasted());
    }
    @GetMapping("rated")
    public ResponseEntity<List<Product>> getRated() {
        return ResponseEntity.ok(productService.getRated());
    }
    @GetMapping("category/{id}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(productService.getProductByCategory(id));
    }
    @GetMapping("{id}")
    public ResponseEntity<Product> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
    @PostMapping
    public ResponseEntity<Product> post(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.createProduct(productRequest));
    }
}
