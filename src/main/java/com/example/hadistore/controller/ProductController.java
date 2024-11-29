package com.example.hadistore.controller;

import com.example.hadistore.dtos.request.ProductRequest;
import com.example.hadistore.dtos.response.ResponseData;
import com.example.hadistore.entity.Product;
import com.example.hadistore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<Product>> getProductByBestSeller() {
        return ResponseEntity.ok(productService.getProductBestSeller());
    }
    @GetMapping("latest")
    public ResponseEntity<List<Product>> getProductByLasted() {
        return ResponseEntity.ok(productService.getLasted());
    }
    @GetMapping("rated")
    public ResponseEntity<List<Product>> getProductByRated() {
        return ResponseEntity.ok(productService.getRated());
    }
    @GetMapping("category/{id}")
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(productService.getProductByCategory(id));
    }
    @GetMapping("{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.createProduct(productRequest));
    }
    @PutMapping("{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest){
        return ResponseEntity.ok(productService.updateProduct(id, productRequest));
    }
    @DeleteMapping("{id}")
    public ResponseData<String> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return new ResponseData<>(HttpStatus.NO_CONTENT, "Delete product id = " + id + " successfully");
    }
}
