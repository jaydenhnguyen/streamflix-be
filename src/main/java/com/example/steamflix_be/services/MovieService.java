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

        if (updatedMovie.getTitle() != null && !updatedMovie.getTitle().isBlank())
            existingMovie.setTitle(updatedMovie.getTitle().trim());

        if (updatedMovie.getType() != null && !updatedMovie.getType().isBlank())
            existingMovie.setType(updatedMovie.getType().trim());

        if (updatedMovie.getYear() > 1900)
            existingMovie.setYear(updatedMovie.getYear());

        if (updatedMovie.getGenre() != null && !updatedMovie.getGenre().isBlank())
            existingMovie.setGenre(updatedMovie.getGenre().trim());

        if (updatedMovie.getSynopsis() != null && !updatedMovie.getSynopsis().isBlank())
            existingMovie.setSynopsis(updatedMovie.getSynopsis().trim());

        if (updatedMovie.getPoster() != null && !updatedMovie.getPoster().isBlank())
            existingMovie.setPoster(updatedMovie.getPoster().trim());

        if (updatedMovie.getLargePoster() != null && !updatedMovie.getLargePoster().isBlank())
            existingMovie.setLargePoster(updatedMovie.getLargePoster().trim());

        if (updatedMovie.getRentPrice() > 0)
            existingMovie.setRentPrice(updatedMovie.getRentPrice());

        if (updatedMovie.getPurchasePrice() > 0)
            existingMovie.setPurchasePrice(updatedMovie.getPurchasePrice());

        existingMovie.setFeatured(updatedMovie.isFeatured());
        existingMovie.setMostDemanded(updatedMovie.isMostDemanded());

        if (updatedMovie.getRating() >= 0)
            existingMovie.setRating(updatedMovie.getRating());

        existingMovie.setUpdatedAt(new Date());

        return movieRepo.save(existingMovie);
    }


}
