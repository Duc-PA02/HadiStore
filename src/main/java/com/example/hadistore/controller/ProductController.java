package com.example.hadistore.controller;

import com.example.hadistore.dtos.request.ProductRequest;
import com.example.hadistore.entity.Product;
import com.example.hadistore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(productService.getAllProducts());
    }
    @GetMapping("bestseller")
    public ResponseEntity<?> getBestSeller() {
        return ResponseEntity.ok(productService.getProductBestSeller());
    }
    @GetMapping("latest")
    public ResponseEntity<?> getLasted() {
        return ResponseEntity.ok(productService.getLasted());
    }
    @GetMapping("rated")
    public ResponseEntity<?> getRated() {
        return ResponseEntity.ok(productService.getRated());
    }
    @GetMapping("category/{id}")
    public ResponseEntity<?> getByCategory(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(productService.getProductByCategory(id));
    }
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
    @PostMapping
    public ResponseEntity<Product> post(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.createProduct(productRequest));
    }
}
