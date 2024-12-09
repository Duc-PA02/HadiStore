package com.example.hadistore.controller;

import com.example.hadistore.dtos.request.CartRequest;
import com.example.hadistore.entity.Cart;
import com.example.hadistore.service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CartController {
    CartService cartService;
    @GetMapping("user/{email}")
    public ResponseEntity<Cart> getCartByUser(@PathVariable String email){
        return ResponseEntity.ok(cartService.findCartByUser(email));
    }
    @PutMapping("user/{email}")
    public ResponseEntity<Cart> updateCart(@PathVariable String email, @RequestBody CartRequest cartRequest){
        return ResponseEntity.ok(cartService.updateCart(email, cartRequest));
    }
}
