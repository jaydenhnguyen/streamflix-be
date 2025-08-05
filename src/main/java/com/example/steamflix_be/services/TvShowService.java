package com.example.steamflix_be.services;

import com.example.steamflix_be.exceptions.AppException;
import com.example.steamflix_be.models.TvShow;
import com.example.steamflix_be.repositories.TvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Date;
import java.util.NoSuchElementException;

@Service
public class TvShowService {
    @Autowired private TvShowRepository tvShowRepo;

    public TvShow addNewTvShow(TvShow tvShow) {
        try {
            return tvShowRepo.save(tvShow);
        } catch (Exception e) {
            throw new AppException(
                    "TVSHOW_CREATION_FAILED",
                    "Failed to create TV show: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public List<TvShow> getAllTvShows() {
        try {
            return tvShowRepo.findAll();
        } catch (Exception e) {
            throw new AppException(
                    "TVSHOWS_FETCH_FAILED",
                    "Failed to retrieve TV shows: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public List<TvShow> findFeaturedTvShows() {
        try {
            return tvShowRepo.findByFeaturedTrue();
        } catch (Exception e) {
            throw new AppException(
                    "TVSHOWS_FETCH_FAILED",
                    "Failed to retrieve featured TV shows: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public TvShow getTvShowById(String id) {
        try {
            return tvShowRepo.findById(id)
                    .orElseThrow(() -> new AppException(
                            "NOT_FOUND",
                            "TV Show with ID " + id + " not found.",
                            HttpStatus.NOT_FOUND
                    ));
        } catch (IllegalArgumentException e) {
            throw new AppException(
                    "INVALID_ID_FORMAT",
                    "Invalid TV Show ID format: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    public TvShow updateTvShowById(String id, TvShow updatedTvShow) {
        if (id == null || id.isBlank()) {
            throw new AppException("INVALID_ID", "TV Show ID must not be empty", HttpStatus.BAD_REQUEST);
        }
        if (updatedTvShow == null) {
            throw new AppException("INVALID_DATA", "Updated TV show data is required", HttpStatus.BAD_REQUEST);
        }

        TvShow existingTvShow = tvShowRepo.findById(id)
                .orElseThrow(() -> new AppException(
                        "NOT_FOUND",
                        "TV Show with ID " + id + " not found",
                        HttpStatus.NOT_FOUND));

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
            throw new AppException("INVALID_ID", "TV Show ID must not be empty", HttpStatus.BAD_REQUEST);
        }

        boolean exists = tvShowRepo.existsById(id);
        if (!exists) {
            throw new AppException("NOT_FOUND", "TV Show with ID " + id + " not found", HttpStatus.NOT_FOUND);
        }

        tvShowRepo.deleteById(id);
    }

    public List<TvShow> findByTitleContains(String title) {
        try {
            return tvShowRepo.findByTitleContainingIgnoreCase(title);
        } catch (Exception e) {
            throw new AppException(
                    "TVSHOWS_SEARCH_FAILED",
                    "Failed to search TV shows by title: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
