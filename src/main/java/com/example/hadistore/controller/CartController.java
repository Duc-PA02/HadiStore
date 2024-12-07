package com.example.hadistore.controller;

import com.example.hadistore.entity.Cart;
import com.example.hadistore.service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
