package com.example.steamflix_be.controllers;

import com.example.steamflix_be.annotations.SecureRoute;
import com.example.steamflix_be.models.Movie;
import com.example.steamflix_be.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping
    @SecureRoute
    public ResponseEntity<?> createMovie(@RequestBody Movie movie) {
        try {
            Movie saved = movieService.addNewMovie(movie);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
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
            return ResponseEntity
                    .internalServerError()
                    .body("Failed to retrieve movies: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable String id) {
        try {
            Movie movie = movieService.getMovieById(id);
            if (movie == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie with ID " + id + " not found.");
            }
            return ResponseEntity.ok(movie);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body("Error retrieving movie: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @SecureRoute
    public ResponseEntity<?> updateMovieById(@PathVariable String id, @RequestBody Movie updatedMovie) {
        try {
            Movie movie = movieService.updateMovieById(id, updatedMovie);
            return ResponseEntity.ok(movie);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());

        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body("Failed to update movie: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @SecureRoute
    public ResponseEntity<?> deleteMovieById(@PathVariable String id) {
        try {
            movieService.deleteMovieById(id);
            return ResponseEntity.ok("Movie with ID " + id + " deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());

        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body("Failed to delete movie: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchMoviesByTitle(@RequestParam("title") String title) {
        try {
            List<Movie> movies = movieService.findByTitleContains(title);
            return ResponseEntity.ok(movies);
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body("Failed to search movies: " + e.getMessage());
        }
    }
}

