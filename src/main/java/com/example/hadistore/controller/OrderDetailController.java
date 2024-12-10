package com.example.hadistore.controller;

import com.example.hadistore.entity.OrderDetail;
import com.example.hadistore.service.OrderDetailService;
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
@RequestMapping("${api.prefix}/order-detail")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrderDetailController {
    OrderDetailService orderDetailService;
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderDetail>> getAllById(@PathVariable Long orderId){
        return ResponseEntity.ok(orderDetailService.findByOrderId(orderId));
    }
}
