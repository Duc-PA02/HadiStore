package com.example.hadistore.service;

import com.example.hadistore.dtos.request.CartDetailRequest;
import com.example.hadistore.entity.CartDetail;

import java.util.List;

public interface CartDetailService {
    List<CartDetail> findAllCartDetailByCart(Long cartId);
    CartDetail findById(Long id);
    CartDetail addItem(CartDetail cartDetail);
    CartDetail updateDetail(CartDetailRequest cartDetail);
    void deleteItem(Long id);
}
