package com.example.steamflix_be.controllers;

import com.example.steamflix_be.annotations.SecureRoute;
import com.example.steamflix_be.models.Movie;
import com.example.steamflix_be.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping
    @SecureRoute
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        Movie saved = movieService.addNewMovie(movie);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getMovies(@RequestParam(value = "featured", required = false) Boolean featured) {
        List<Movie> movies;
        if (Boolean.TRUE.equals(featured)) {
            movies = movieService.findFeaturedMovies();
        } else {
            movies = movieService.getAllMovies();
        }
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable String id) {
        Movie movie = movieService.getMovieById(id);
        return ResponseEntity.ok(movie);
    }

    @PutMapping("/{id}")
    @SecureRoute
    public ResponseEntity<Movie> updateMovieById(@PathVariable String id, @RequestBody Movie updatedMovie) {
        Movie movie = movieService.updateMovieById(id, updatedMovie);
        return ResponseEntity.ok(movie);
    }

    @DeleteMapping("/{id}")
    @SecureRoute
    public ResponseEntity<Map<String, String>> deleteMovieById(@PathVariable String id) {
        movieService.deleteMovieById(id);
        return ResponseEntity.ok(Map.of("message", "Movie with ID " + id + " deleted successfully."));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMoviesByTitle(@RequestParam("title") String title) {
        List<Movie> movies = movieService.findByTitleContains(title);
        return ResponseEntity.ok(movies);
    }
}

