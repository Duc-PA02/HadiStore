package com.example.hadistore.controller;

import com.example.hadistore.dtos.request.CartRequest;
import com.example.hadistore.entity.Order;
import com.example.hadistore.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrderController {
    OrderService orderService;
    @GetMapping("user/{email}")
    public ResponseEntity<List<Order>> getByEmail(@PathVariable String email){
        return ResponseEntity.ok(orderService.findByEmail(email));
    }
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrder(){
        return ResponseEntity.ok(orderService.findAllOrder());
    }
    @GetMapping("{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.findById(id));
    }
    @PostMapping("/{email}")
    public ResponseEntity<Order> checkout(@PathVariable String email, @RequestBody CartRequest cartRequest){
        return ResponseEntity.ok(orderService.checkout(email, cartRequest));
    }
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long id, @RequestParam Integer status){
        orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok().build();
    }
}
