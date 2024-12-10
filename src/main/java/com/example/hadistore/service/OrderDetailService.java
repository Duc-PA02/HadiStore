package com.example.hadistore.service;

import com.example.hadistore.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> findByOrderId(Long orderId);
}
