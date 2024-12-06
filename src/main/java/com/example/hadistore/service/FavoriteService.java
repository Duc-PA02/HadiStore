package com.example.hadistore.service;

import com.example.hadistore.entity.Favorite;

import java.util.List;

public interface FavoriteService {
    List<Favorite> findByEmail(String email);
    Favorite findByProductAndUser(Long productId, String email);
    Long countFavoriteByProduct(Long productId);
    Favorite createFavorite(Favorite favorite);
    void deleteFavorite(Long id);
}
