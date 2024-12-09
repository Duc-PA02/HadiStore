package com.example.hadistore.controller;

import com.example.hadistore.dtos.request.CartRequest;
import com.example.hadistore.entity.Order;
import com.example.hadistore.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/{email}")
    public ResponseEntity<Order> checkout(@PathVariable String email, @RequestBody CartRequest cartRequest){
        return ResponseEntity.ok(orderService.checkout(email, cartRequest));
    }
}
