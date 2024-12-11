package com.example.hadistore.service.impl;

import com.example.hadistore.dtos.request.CartRequest;
import com.example.hadistore.entity.*;
import com.example.hadistore.enums.OrderStatus;
import com.example.hadistore.exceptions.DataNotFoundException;
import com.example.hadistore.repository.*;
import com.example.hadistore.service.OrderService;
import com.example.hadistore.service.SendMailService;
import com.sun.jdi.request.InvalidRequestStateException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jmx.export.metadata.InvalidMetadataException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.List;
import java.util.prefs.InvalidPreferencesFormatException;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    UserRepository userRepository;
    CartRepository cartRepository;
    ProductRepository productRepository;
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
    public List<Order> findAllOrder() {
        return orderRepository.findAllByOrderByOrdersIdDesc();
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Order not found"));
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, Integer status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order not found"));
        if (OrderStatus.fromValue(order.getStatus()) == OrderStatus.CANCELLED){
            throw new InvalidRequestStateException("Cannot change status of cancelled order");
        }
        switch (status){
            case 1:
                statusDeliver(order, status);
                break;
            case 2:
                statusSuccess(order, status);
                break;
            case 3:
                statusCancel(order, status);
                break;
            default:
                throw new IllegalArgumentException("Invalid status value!");
        }

    }
    private void statusDeliver(Order order, Integer status){
        updateProduct(order);
        order.setStatus(status);
        sendMailService.sendMailOrderDeliver(order);
    }
    private void statusSuccess(Order order, Integer status){
        order.setStatus(status);
        sendMailService.sendMailOrderSuccess(order);
    }
    private void statusCancel(Order order, Integer status){
        order.setStatus(status);
        sendMailService.sendMailOrderCancel(order);
    }
    private void updateProduct(Order order){
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrder(order);
        for (OrderDetail orderDetail : orderDetailList){
            Product product = productRepository.findById(orderDetail.getProduct().getProductId())
                    .orElseThrow(() -> new DataNotFoundException("Product not found"));
            if (product.getQuantity() - orderDetail.getQuantity() >= 0){
                product.setQuantity(product.getQuantity() - orderDetail.getQuantity());
                product.setSold(product.getSold() + orderDetail.getQuantity());
            } else {
                throw new InvalidParameterException("Product quantity is not enough");
            }
        }
    }
}
