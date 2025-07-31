package com.example.steamflix_be.services;

import com.example.steamflix_be.models.Movie;
import com.example.steamflix_be.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

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

    public Movie updateMovieById(String id, Movie updatedMovie) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Movie ID must not be empty");
        }
        if (updatedMovie == null) {
            throw new IllegalArgumentException("Updated movie data is required");
        }

        Movie existingMovie = movieRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Movie with ID " + id + " not found"));

        // Update allowed fields (example: rentPrice, purchasePrice, synopsis, featured, etc.)
        if (updatedMovie.getTitle() != null) existingMovie.setTitle(updatedMovie.getTitle());
        if (updatedMovie.getType() != null) existingMovie.setType(updatedMovie.getType());
        if (updatedMovie.getYear() != 0) existingMovie.setYear(updatedMovie.getYear());
        if (updatedMovie.getGenre() != null) existingMovie.setGenre(updatedMovie.getGenre());
        if (updatedMovie.getSynopsis() != null) existingMovie.setSynopsis(updatedMovie.getSynopsis());
        if (updatedMovie.getPoster() != null) existingMovie.setPoster(updatedMovie.getPoster());
        if (updatedMovie.getLargePoster() != null) existingMovie.setLargePoster(updatedMovie.getLargePoster());
        if (updatedMovie.getRentPrice() > 0) existingMovie.setRentPrice(updatedMovie.getRentPrice());
        if (updatedMovie.getPurchasePrice() > 0) existingMovie.setPurchasePrice(updatedMovie.getPurchasePrice());
        existingMovie.setFeatured(updatedMovie.isFeatured());
        existingMovie.setMostDemanded(updatedMovie.isMostDemanded());
        if (updatedMovie.getRating() > 0) existingMovie.setRating(updatedMovie.getRating());

        existingMovie.setUpdatedAt(new Date()); // update timestamp

        return movieRepo.save(existingMovie);
    }

}
