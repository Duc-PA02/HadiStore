package com.example.hadistore.service.impl;

import com.example.hadistore.entity.Order;
import com.example.hadistore.entity.User;
import com.example.hadistore.exceptions.DataNotFoundException;
import com.example.hadistore.repository.OrderRepository;
import com.example.hadistore.repository.UserRepository;
import com.example.hadistore.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    UserRepository userRepository;
    @Override
    public List<Order> findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        return orderRepository.findByUserOrderByOrdersIdDesc(user);
    }
}
