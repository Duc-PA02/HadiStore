package com.example.hadistore.repository;

import com.example.hadistore.entity.Category;
import com.example.hadistore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatusTrue();
    List<Product> findByStatusTrueOrderBySoldDesc();
    List<Product> findTop10ByOrderBySoldDesc();
    @Query(value = "SELECT p.* FROM product p " +
            "LEFT JOIN rate r ON p.product_id = r.product_id " +
            "WHERE p.status = TRUE " +
            "GROUP BY p.product_id, p.name " +
            "ORDER BY AVG(r.rating) DESC, RAND()", nativeQuery = true)
    List<Product> findProductRated();
    List<Product> findByStatusTrueOrderByEnteredDateDesc();
    List<Product> findByCategory(Category category);
    List<Product> findByStatusTrueOrderByQuantityDesc();
    Optional<Product> findByProductIdAndStatusTrue(Long id);
    @Query(value = "SELECT p " +
            "FROM Product p " +
            "LEFT JOIN Favorite f ON p.productId = f.product.productId " +
            "WHERE p.category.categoryId = :categoryId AND p.productId <> :productId " +
            "GROUP BY p.productId " +
            "ORDER BY COUNT(f.favoriteId) DESC")
    List<Product> findSuggestedProducts(@Param("categoryId") Long categoryId, @Param("productId") Long productId);
}
