package com.example.steamflix_be.services;

import com.example.steamflix_be.models.Movie;
import com.example.steamflix_be.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepo;

    public Movie addNewMovie(Movie movie) {
        movie.setCreatedAt(new Date());
        movie.setUpdatedAt(new Date());
        return movieRepo.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepo.findAll();
    }

    public List<Movie> findByTitleContains(String title) {
        return movieRepo.findByTitleContainingIgnoreCase(title);
    }

    public List<Movie> findFeaturedMovies() {
        return movieRepo.findByFeaturedTrue();
    }

    public Movie getMovieById(String id) {
        try {
            return movieRepo.findById(id).orElse(null);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid movie ID format: " + e.getMessage());
        }
    }
}
