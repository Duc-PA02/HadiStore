package com.example.hadistore.repository;

import com.example.hadistore.entity.Cart;
import com.example.hadistore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}
