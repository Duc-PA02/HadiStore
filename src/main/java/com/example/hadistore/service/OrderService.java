package com.example.hadistore.service;

import com.example.hadistore.dtos.request.CartRequest;
import com.example.hadistore.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findByEmail(String email);
    List<Order> findAllOrder();
    Order findById(Long id);
    Order checkout(String email, CartRequest cartRequest);
    void updateOrderStatus(Long orderId, Integer status);
}
