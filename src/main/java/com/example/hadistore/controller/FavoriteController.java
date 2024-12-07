package com.example.hadistore.controller;

import com.example.hadistore.entity.Favorite;
import com.example.hadistore.service.FavoriteService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/favorites")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FavoriteController {
    FavoriteService favoriteService;
    @GetMapping("email/{email}")
    public ResponseEntity<List<Favorite>> findByEmail(@PathVariable("email") String email){
        return ResponseEntity.ok(favoriteService.findByEmail(email));
    }
    @GetMapping("product/{productId}")
    public ResponseEntity<Long> countFavoriteByProduct(@PathVariable Long productId){
        return ResponseEntity.ok(favoriteService.countFavoriteByProduct(productId));
    }
    @GetMapping("{productId}/{email}")
    public ResponseEntity<Favorite> findByProductAndUser(@PathVariable("productId") Long productId,
                                                         @PathVariable("email") String email) {
        return ResponseEntity.ok(favoriteService.findByProductAndUser(productId, email));
    }
    @PostMapping
    public ResponseEntity<Favorite> createFavorite(@RequestBody Favorite favorite) {
        return ResponseEntity.ok(favoriteService.createFavorite(favorite));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable("id") Long id) {
        favoriteService.deleteFavorite(id);
        return ResponseEntity.ok().build();
    }
}
