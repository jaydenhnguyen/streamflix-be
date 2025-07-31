package com.example.steamflix_be.controllers;

import com.example.steamflix_be.models.Movie;
import com.example.steamflix_be.models.TvShow;
import com.example.steamflix_be.services.TvShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/tvshows")
public class TvShowController {

    @Autowired
    private TvShowService tvShowService;

    @PostMapping
    public ResponseEntity<?> createTvShow(@RequestBody TvShow tvShow) {
        try {
            TvShow saved = tvShowService.addNewTvShow(tvShow);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create TV show: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getTvShows(@RequestParam(value = "featured", required = false) Boolean featured) {
        try {
            List<TvShow> shows;
            if (featured != null && featured) {
                shows = tvShowService.findFeaturedTvShows();
            } else {
                shows = tvShowService.getAllTvShows();
            }
            return ResponseEntity.ok(shows);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve TV shows: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTvShowById(@PathVariable String id) {
        try {
            TvShow tvShow = tvShowService.getTvShowById(id);
            if (tvShow == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TV Show with ID " + id + " not found.");
            }
            return ResponseEntity.ok(tvShow);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving TV show: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTvShowById(@PathVariable String id, @RequestBody TvShow updatedTvShow) {
        try {
            TvShow tvShow = tvShowService.updateTvShowById(id, updatedTvShow);
            return ResponseEntity.ok(tvShow);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update tv show: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchTvShowsByTitle(@RequestParam("title") String title) {
        try {
            List<TvShow> tvShows = tvShowService.findByTitleContains(title);
            return ResponseEntity.ok(tvShows);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to search TV shows: " + e.getMessage());
        }
    }
}
