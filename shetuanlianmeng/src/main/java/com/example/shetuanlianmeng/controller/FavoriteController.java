package com.example.shetuanlianmeng.controller;

import com.example.shetuanlianmeng.entity.Favorite;
import com.example.shetuanlianmeng.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @PostMapping
    public Favorite createFavorite(@RequestBody Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    @DeleteMapping("/{id}")
    public void deleteFavorite(@PathVariable Long id, @RequestBody Long userId) {
        Favorite favorite = favoriteRepository.findById(id).orElse(null);
        if (favorite != null && favorite.getUserId().equals(userId)) {
            favoriteRepository.delete(favorite);
        }
    }

    @GetMapping("/user/{userId}")
    public List<Favorite> getFavoritesByUserId(@PathVariable Long userId) {
        return favoriteRepository.findByUserId(userId);
    }
}
