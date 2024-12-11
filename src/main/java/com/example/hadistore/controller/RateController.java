package com.example.hadistore.controller;

import com.example.hadistore.dtos.request.RateRequest;
import com.example.hadistore.entity.Rate;
import com.example.hadistore.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("order-detail/{orderDetailId}")
    public ResponseEntity<Rate> getByOrderDetail(@PathVariable Long orderDetailId){
        return ResponseEntity.ok(rateService.findByOrderDetail(orderDetailId));
    }
    @GetMapping("product/{productId}")
    public ResponseEntity<List<Rate>> getByProduct(@PathVariable Long productId){
        return ResponseEntity.ok(rateService.findByProduct(productId));
    }
    @PostMapping
    public ResponseEntity<Rate> createRate(@RequestBody RateRequest rateRequest){
        return ResponseEntity.ok(rateService.createRate(rateRequest));
    }
    @PutMapping
    public ResponseEntity<Rate> updateRate(@RequestBody RateRequest rateRequest){
        return ResponseEntity.ok(rateService.updateRate(rateRequest));
    }
    @DeleteMapping("/{rateId}")
    public ResponseEntity<Void> deleteRate(@PathVariable Long rateId){
        rateService.deleteRate(rateId);
        return ResponseEntity.ok().build();
    }
}
