package com.example.hadistore.service;

import com.example.hadistore.dtos.request.CartRequest;
import com.example.hadistore.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findByEmail(String email);
    Order checkout(String email, CartRequest cartRequest);
}
