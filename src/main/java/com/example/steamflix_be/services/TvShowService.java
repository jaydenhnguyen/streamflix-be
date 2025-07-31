package com.example.steamflix_be.services;

import com.example.steamflix_be.models.TvShow;
import com.example.steamflix_be.repositories.TvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TvShowService {

    @Autowired
    private TvShowRepository tvShowRepo;

    public List<TvShow> getAllTvShows() {
        return tvShowRepo.findAll();
    }

    public TvShow addNewTvShow(TvShow tvShow) {
        return tvShowRepo.save(tvShow);
    }

    public List<TvShow> findByTitleContains(String title) {
        return tvShowRepo.findByTitleContainingIgnoreCase(title);
    }
}
