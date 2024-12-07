package com.example.hadistore.repository;

import com.example.hadistore.entity.Cart;
import com.example.hadistore.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    List<CartDetail> findByCart(Cart cart);
}
