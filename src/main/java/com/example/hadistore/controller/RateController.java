package com.example.hadistore.controller;

import com.example.hadistore.entity.Rate;
import com.example.hadistore.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/rates")
@RequiredArgsConstructor
public class RateController {
    private final RateService rateService;
    @GetMapping
    public ResponseEntity<List<Rate>> findAll() {
        return ResponseEntity.ok(rateService.findAll());
    }
}
