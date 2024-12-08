package com.example.hadistore.repository;

import com.example.hadistore.entity.Order;
import com.example.hadistore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByOrdersIdDesc(User user);
    List<Order> findByStatus(int status);
}
