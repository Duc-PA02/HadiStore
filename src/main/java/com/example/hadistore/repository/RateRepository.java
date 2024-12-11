package com.example.hadistore.repository;

import com.example.hadistore.entity.OrderDetail;
import com.example.hadistore.entity.Product;
import com.example.hadistore.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
    List<Rate> findAllByOrderByIdDesc();
    Rate findByOrderDetail(OrderDetail orderDetail);
    List<Rate> findByProductOrderByIdDesc(Product product);
}
