package com.example.steamflix_be.controllers;

import com.example.steamflix_be.models.TvShow;
import com.example.steamflix_be.services.TvShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> getAllTvShows() {
        try {
            List<TvShow> tvShows = tvShowService.getAllTvShows();
            return ResponseEntity.ok(tvShows);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve TV shows: " + e.getMessage());
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
