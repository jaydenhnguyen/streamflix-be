package com.example.steamflix_be.services;

import com.example.steamflix_be.models.TvShow;
import com.example.steamflix_be.repositories.TvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Date;
import java.util.NoSuchElementException;

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

    public List<TvShow> findFeaturedTvShows() {
        return tvShowRepo.findByFeaturedTrue();
    }

    public TvShow getTvShowById(String id) {
        try {
            return tvShowRepo.findById(id).orElse(null);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid TV Show ID format: "+e.getMessage());
        }
    }

    public TvShow updateTvShowById(String id, TvShow updatedTvShow) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("TV Show ID must not be empty");
        }
        if (updatedTvShow == null) {
            throw new IllegalArgumentException("Updated TV show data is required");
        }

        TvShow existingTvShow = tvShowRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("TV Show with ID " + id + " not found"));

        if (updatedTvShow.getTitle() != null && !updatedTvShow.getTitle().isBlank())
            existingTvShow.setTitle(updatedTvShow.getTitle().trim());

        if (updatedTvShow.getType() != null && !updatedTvShow.getType().isBlank())
            existingTvShow.setType(updatedTvShow.getType().trim());

        if (updatedTvShow.getYear() > 1900)
            existingTvShow.setYear(updatedTvShow.getYear());

        if (updatedTvShow.getGenre() != null && !updatedTvShow.getGenre().isBlank())
            existingTvShow.setGenre(updatedTvShow.getGenre().trim());

        if (updatedTvShow.getSynopsis() != null && !updatedTvShow.getSynopsis().isBlank())
            existingTvShow.setSynopsis(updatedTvShow.getSynopsis().trim());

        if (updatedTvShow.getPoster() != null && !updatedTvShow.getPoster().isBlank())
            existingTvShow.setPoster(updatedTvShow.getPoster().trim());

        if (updatedTvShow.getLargePoster() != null && !updatedTvShow.getLargePoster().isBlank())
            existingTvShow.setLargePoster(updatedTvShow.getLargePoster().trim());

        if (updatedTvShow.getRentPrice() > 0)
            existingTvShow.setRentPrice(updatedTvShow.getRentPrice());

        if (updatedTvShow.getPurchasePrice() > 0)
            existingTvShow.setPurchasePrice(updatedTvShow.getPurchasePrice());

        existingTvShow.setFeatured(updatedTvShow.isFeatured());
        existingTvShow.setMostDemanded(updatedTvShow.isMostDemanded());

        if (updatedTvShow.getRating() >= 0)
            existingTvShow.setRating(updatedTvShow.getRating());

        if (updatedTvShow.getSeasons() > 0)
            existingTvShow.setSeasons(updatedTvShow.getSeasons());

        if (updatedTvShow.getEpisodes() > 0)
            existingTvShow.setEpisodes(updatedTvShow.getEpisodes());

        existingTvShow.setUpdatedAt(new Date());

        return tvShowRepo.save(existingTvShow);
    }

    public void deleteTvShowById(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("TV Show ID must not be empty");
        }

        boolean exists = tvShowRepo.existsById(id);
        if (!exists) {
            throw new NoSuchElementException("TV Show with ID " + id + " not found");
        }

        tvShowRepo.deleteById(id);
    }
}
