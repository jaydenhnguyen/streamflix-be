package com.example.steamflix_be.controllers;

import com.example.steamflix_be.annotations.SecureRoute;
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
    @SecureRoute
    public ResponseEntity<?> createTvShow(@RequestBody TvShow tvShow) {
        try {
            TvShow saved = tvShowService.addNewTvShow(tvShow);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body("Failed to create TV show: " + e.getMessage());
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
            return ResponseEntity
                    .internalServerError()
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
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body("Error retrieving TV show: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @SecureRoute
    public ResponseEntity<?> updateTvShowById(@PathVariable String id, @RequestBody TvShow updatedTvShow) {
        try {
            TvShow tvShow = tvShowService.updateTvShowById(id, updatedTvShow);
            return ResponseEntity.ok(tvShow);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body("Failed to update tv show: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @SecureRoute
    public ResponseEntity<?> deleteTvShowById(@PathVariable String id) {
        try {
            tvShowService.deleteTvShowById(id);
            return ResponseEntity.ok("TV Show with ID " + id + " deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body("Failed to delete TV show: " + e.getMessage());
        }
    }


    @GetMapping("/search")
    public ResponseEntity<?> searchTvShowsByTitle(@RequestParam("title") String title) {
        try {
            List<TvShow> tvShows = tvShowService.findByTitleContains(title);
            return ResponseEntity.ok(tvShows);
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body("Failed to search TV shows: " + e.getMessage());
        }
    }
}
