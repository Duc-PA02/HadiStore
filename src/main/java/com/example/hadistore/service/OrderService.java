package com.example.hadistore.service;

import com.example.hadistore.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findByEmail(String email);
}
