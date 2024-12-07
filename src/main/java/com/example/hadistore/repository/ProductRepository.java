package com.example.hadistore.repository;

import com.example.hadistore.entity.Category;
import com.example.hadistore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @Query(value = "SELECT p " +
            "FROM Product p " +
            "LEFT JOIN Favorite f ON p.productId = f.product.productId " +
            "WHERE p.category.categoryId = :categoryId AND p.productId != :productId " +
            "GROUP BY p.productId " +
            "ORDER BY COUNT(f.favoriteId) DESC")
    List<Product> findSuggestedProducts(@Param("categoryId") Long categoryId, @Param("productId") Long productId);

}
