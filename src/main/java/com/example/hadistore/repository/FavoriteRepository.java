package com.example.hadistore.repository;

import com.example.hadistore.entity.Favorite;
import com.example.hadistore.entity.Product;
import com.example.hadistore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUser(User user);
    Long countByProduct(Product product);

    Favorite findByProductAndUser(Product product, User user);
}
