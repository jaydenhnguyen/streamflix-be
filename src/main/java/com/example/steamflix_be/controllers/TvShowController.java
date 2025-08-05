package com.example.steamflix_be.controllers;

import com.example.steamflix_be.annotations.SecureRoute;
import com.example.steamflix_be.models.TvShow;
import com.example.steamflix_be.services.TvShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/tvshows")
public class TvShowController {

    @Autowired
    private TvShowService tvShowService;

    @PostMapping
    @SecureRoute
    public ResponseEntity<TvShow> createTvShow(@RequestBody TvShow tvShow) {
        TvShow saved = tvShowService.addNewTvShow(tvShow);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<TvShow>> getTvShows(@RequestParam(value = "featured", required = false) Boolean featured) {
        List<TvShow> shows;
        if (Boolean.TRUE.equals(featured)) {
            shows = tvShowService.findFeaturedTvShows();
        } else {
            shows = tvShowService.getAllTvShows();
        }
        return ResponseEntity.ok(shows);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TvShow> getTvShowById(@PathVariable String id) {
        TvShow tvShow = tvShowService.getTvShowById(id);
        return ResponseEntity.ok(tvShow);
    }

    @PutMapping("/{id}")
    @SecureRoute
    public ResponseEntity<TvShow> updateTvShowById(@PathVariable String id, @RequestBody TvShow updatedTvShow) {
        TvShow tvShow = tvShowService.updateTvShowById(id, updatedTvShow);
        return ResponseEntity.ok(tvShow);
    }

    @DeleteMapping("/{id}")
    @SecureRoute
    public ResponseEntity<Map<String, String>> deleteTvShowById(@PathVariable String id) {
        tvShowService.deleteTvShowById(id);
        return ResponseEntity.ok(Map.of("message", "TV Show with ID " + id + " deleted successfully."));
    }


    @GetMapping("/search")
    public ResponseEntity<List<TvShow>> searchTvShowsByTitle(@RequestParam("title") String title) {
        List<TvShow> tvShows = tvShowService.findByTitleContains(title);
        return ResponseEntity.ok(tvShows);
    }
}
