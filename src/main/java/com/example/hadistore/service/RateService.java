package com.example.hadistore.service;

import com.example.hadistore.dtos.request.RateRequest;
import com.example.hadistore.entity.Rate;

import java.util.List;

public interface RateService {
    List<Rate> findAll();
    Rate findByOrderDetail(Long orderDetailId);
    List<Rate> findByProduct(Long productId);
    Rate createRate(RateRequest rateRequest);
    Rate updateRate(RateRequest rateRequest);
    void deleteRate(Long rateId);
}
