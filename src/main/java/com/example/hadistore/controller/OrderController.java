package com.example.hadistore.controller;

import com.example.hadistore.entity.Order;
import com.example.hadistore.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
