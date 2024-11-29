package com.example.hadistore.repository;

import com.example.hadistore.entity.Category;
import com.example.hadistore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatusTrue();
    List<Product> findByStatusTrueOrderBySoldDesc();
    @Query(value = "Select p.* From product p \r\n"
            + "left join rate r on p.product_id = r.product_id\r\n"
            + "group by p.product_id , p.name\r\n"
            + "Order by  avg(r.rating) desc, RAND()", nativeQuery = true)
    List<Product> findProductRated();
    List<Product> findByStatusTrueOrderByEnteredDateDesc();
    List<Product> findByCategory(Category category);
    List<Product> findByStatusTrueOrderByQuantityDesc();
}
