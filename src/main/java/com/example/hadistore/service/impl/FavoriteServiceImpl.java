package com.example.hadistore.service.impl;

import com.example.hadistore.entity.Favorite;
import com.example.hadistore.entity.Product;
import com.example.hadistore.entity.User;
import com.example.hadistore.exceptions.DataNotFoundException;
import com.example.hadistore.repository.FavoriteRepository;
import com.example.hadistore.repository.ProductRepository;
import com.example.hadistore.repository.UserRepository;
import com.example.hadistore.service.FavoriteService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FavoriteServiceImpl implements FavoriteService {
    FavoriteRepository favoriteRepository;
    ProductRepository productRepository;
    UserRepository userRepository;
    @Override
    public List<Favorite> findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        return favoriteRepository.findByUser(user);
    }

    @Override
    public Favorite findByProductAndUser(Long productId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        return favoriteRepository.findByProductAndUser(product, user);
    }

    @Override
    public Long countFavoriteByProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        return favoriteRepository.countByProduct(product);
    }

    @Override
    public Favorite createFavorite(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    @Override
    public void deleteFavorite(Long id) {
        Favorite favorite = favoriteRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Favorite not found"));
        favoriteRepository.delete(favorite);
    }
}
