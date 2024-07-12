package com.example.shetuanlianmeng.service;

import com.example.shetuanlianmeng.entity.Favorite;
import com.example.shetuanlianmeng.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Transactional
    public Favorite saveFavorite(Favorite favorite) {
        favorite.setDate(LocalDateTime.now());
        return favoriteRepository.save(favorite);
    }

    @Transactional(readOnly = true)
    public List<Favorite> getFavoritesByUserId(Long userId) {
        return favoriteRepository.findByUserId(userId);
    }
}
