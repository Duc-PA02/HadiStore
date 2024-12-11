package com.example.hadistore.service.impl;

import com.example.hadistore.dtos.request.RateRequest;
import com.example.hadistore.entity.OrderDetail;
import com.example.hadistore.entity.Product;
import com.example.hadistore.entity.Rate;
import com.example.hadistore.entity.User;
import com.example.hadistore.exceptions.DataNotFoundException;
import com.example.hadistore.repository.OrderDetailRepository;
import com.example.hadistore.repository.ProductRepository;
import com.example.hadistore.repository.RateRepository;
import com.example.hadistore.repository.UserRepository;
import com.example.hadistore.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {
    private final RateRepository rateRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    @Override
    public List<Rate> findAll() {
        return rateRepository.findAllByOrderByIdDesc();
    }

    @Override
    public Rate findByOrderDetail(Long orderDetailId) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(() -> new DataNotFoundException("Order detail not found"));
        return rateRepository.findByOrderDetail(orderDetail);
    }

    @Override
    public List<Rate> findByProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        return rateRepository.findByProductOrderByIdDesc(product);
    }

    @Override
    public Rate createRate(RateRequest rateRequest) {
        User user = userRepository.findById(rateRequest.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        Product product = productRepository.findById(rateRequest.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        OrderDetail orderDetail = orderDetailRepository.findById(rateRequest.getOrderDetailId())
                .orElseThrow(() -> new DataNotFoundException("Order detail not found"));
        Rate rate = new Rate(rateRequest.getRating(), rateRequest.getComment(), rateRequest.getRateDate(), user, product, orderDetail);
        return rateRepository.save(rate);
    }

    @Override
    public Rate updateRate(RateRequest rateRequest) {
        Rate rate = rateRepository.findById(rateRequest.getId())
                .orElseThrow(() -> new DataNotFoundException("Rate not found"));
        rate.setRating(rate.getRating());
        rate.setComment(rateRequest.getComment());
        return rateRepository.save(rate);
    }

    @Override
    public void deleteRate(Long rateId) {
        Rate rate = rateRepository.findById(rateId)
                .orElseThrow(() -> new DataNotFoundException("Rate not found"));
        rateRepository.delete(rate);
    }
}
