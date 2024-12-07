package com.example.hadistore.service.impl;

import com.example.hadistore.entity.Cart;
import com.example.hadistore.entity.User;
import com.example.hadistore.exceptions.DataNotFoundException;
import com.example.hadistore.repository.CartRepository;
import com.example.hadistore.repository.UserRepository;
import com.example.hadistore.service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CartServiceImpl implements CartService {
    CartRepository cartRepository;
    UserRepository userRepository;
    @Override
    public Cart findCartByUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        return cartRepository.findByUser(user);
    }
}
