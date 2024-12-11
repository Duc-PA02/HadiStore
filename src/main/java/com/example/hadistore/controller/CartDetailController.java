package com.example.hadistore.controller;

import com.example.hadistore.dtos.request.CartDetailRequest;
import com.example.hadistore.entity.CartDetail;
import com.example.hadistore.service.CartDetailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cart-detail")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CartDetailController {
    CartDetailService cartDetailService;
    @GetMapping("cart/{cartId}")
    public ResponseEntity<List<CartDetail>> getCartDetailByUser(@PathVariable Long cartId){
        return ResponseEntity.ok(cartDetailService.findAllCartDetailByCart(cartId));
    }
    @GetMapping("{id}")
    public ResponseEntity<CartDetail> getCartDetail(@PathVariable Long id){
        return ResponseEntity.ok(cartDetailService.findById(id));
    }
    @PostMapping
    public ResponseEntity<CartDetail> addItem(@RequestBody CartDetail cartDetail){
        return ResponseEntity.ok(cartDetailService.addItem(cartDetail));
    }
    @PutMapping
    public ResponseEntity<CartDetail> updateItem(@RequestBody CartDetailRequest cartDetailRequest){
        return ResponseEntity.ok(cartDetailService.updateDetail(cartDetailRequest));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id){
        cartDetailService.deleteItem(id);
        return ResponseEntity.ok().build();
    }
}
