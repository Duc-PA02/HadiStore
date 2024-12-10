package com.example.hadistore.service.impl;

import com.example.hadistore.entity.Order;
import com.example.hadistore.entity.OrderDetail;
import com.example.hadistore.exceptions.DataNotFoundException;
import com.example.hadistore.repository.OrderDetailRepository;
import com.example.hadistore.repository.OrderRepository;
import com.example.hadistore.service.OrderDetailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrderDetailServiceImpl implements OrderDetailService {
    OrderDetailRepository orderDetailRepository;
    OrderRepository orderRepository;
    @Override
    public List<OrderDetail> findByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order not found"));
        return orderDetailRepository.findByOrder(order);
    }
}
