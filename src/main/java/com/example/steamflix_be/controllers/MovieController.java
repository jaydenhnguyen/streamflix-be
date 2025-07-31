package com.example.steamflix_be.controllers;

import com.example.steamflix_be.models.Movie;
import com.example.steamflix_be.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping
    public ResponseEntity<?> createMovie(@RequestBody Movie movie) {
        try {
            Movie saved = movieService.addNewMovie(movie);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create movie: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getMovies(@RequestParam(value = "featured", required = false) Boolean featured) {
        try {
            List<Movie> movies;
            if (featured != null && featured) {
                movies = movieService.findFeaturedMovies();
            } else {
                movies = movieService.getAllMovies();
            }
            return ResponseEntity.ok(movies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve movies: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchMoviesByTitle(@RequestParam("title") String title) {
        try {
            List<Movie> movies = movieService.findByTitleContains(title);
            return ResponseEntity.ok(movies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to search movies: " + e.getMessage());
        }
    }
}

