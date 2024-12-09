package com.example.hadistore.service.impl;

import com.example.hadistore.dtos.request.CartRequest;
import com.example.hadistore.entity.*;
import com.example.hadistore.enums.OrderStatus;
import com.example.hadistore.exceptions.DataNotFoundException;
import com.example.hadistore.repository.*;
import com.example.hadistore.service.OrderService;
import com.example.hadistore.service.SendMailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    UserRepository userRepository;
    CartRepository cartRepository;
    CartDetailRepository cartDetailRepository;
    OrderDetailRepository orderDetailRepository;
    SendMailService sendMailService;
    @Override
    public List<Order> findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        return orderRepository.findByUserOrderByOrdersIdDesc(user);
    }

    @Override
    public Order checkout(String email, CartRequest cartRequest) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        Cart cart = cartRepository.findByUser(user);
        List<CartDetail> cartDetailList = cartDetailRepository.findByCart(cart);
        Order order = Order.builder()
                .orderDate(new Date())
                .amount(cart.getAmount())
                .address(cartRequest.getAddress())
                .phone(cartRequest.getPhone())
                .status(OrderStatus.PENDING.getValue())
                .user(user)
                .build();
        orderRepository.save(order);
        for (CartDetail cartDetail : cartDetailList){
            OrderDetail orderDetail = new OrderDetail(0L, cartDetail.getQuantity(), cartDetail.getPrice(), cartDetail.getProduct(), order);
            orderDetailRepository.save(orderDetail);
            cartDetailRepository.delete(cartDetail);
        }
        cart.setAmount(0.0);
        cartRepository.save(cart);
        sendMailService.sendMailOrder(order);
        return order;
    }
}
