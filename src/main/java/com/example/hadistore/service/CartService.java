package com.example.hadistore.service;

import com.example.hadistore.dtos.request.CartRequest;
import com.example.hadistore.entity.Cart;

public interface CartService {
    Cart findCartByUser(String email);
    Cart updateCart(String email, CartRequest cartRequest);
}
