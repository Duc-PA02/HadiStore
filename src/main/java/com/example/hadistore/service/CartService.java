package com.example.hadistore.service;

import com.example.hadistore.entity.Cart;

public interface CartService {
    Cart findCartByUser(String email);
}
