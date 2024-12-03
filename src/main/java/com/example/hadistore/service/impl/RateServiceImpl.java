package com.example.hadistore.service.impl;

import com.example.hadistore.entity.Rate;
import com.example.hadistore.exceptions.DataNotFoundException;
import com.example.hadistore.repository.RateRepository;
import com.example.hadistore.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {
    private final RateRepository rateRepository;
    @Override
    public List<Rate> findAll() {
        return rateRepository.findAllByOrderByIdDesc();
    }
}
