package com.example.steamflix_be.services;

import com.example.steamflix_be.exceptions.AppException;
import com.example.steamflix_be.models.Movie;
import com.example.steamflix_be.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepo;

    public Movie addNewMovie(Movie movie) {
        try {
            movie.setCreatedAt(new Date());
            movie.setUpdatedAt(new Date());
            return movieRepo.save(movie);
        } catch (Exception e) {
            throw new AppException(
                    "MOVIE_CREATION_FAILED",
                    "Failed to create movie: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public List<Movie> getAllMovies() {
        try {
            return movieRepo.findAll();
        } catch (Exception e) {
            throw new AppException(
                    "MOVIE_FETCH_FAILED",
                    "Failed to retrieve all movies: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public List<Movie> findFeaturedMovies() {
        try {
            return movieRepo.findByFeaturedTrue();
        } catch (Exception e) {
            throw new AppException(
                    "MOVIE_FETCH_FAILED",
                    "Failed to retrieve featured movies: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public Movie getMovieById(String id) {
        try {
            return movieRepo.findById(id)
                    .orElseThrow(() -> new AppException(
                            "NOT_FOUND",
                            "Movie with ID " + id + " not found.",
                            HttpStatus.NOT_FOUND
                    ));
        } catch (IllegalArgumentException e) {
            throw new AppException(
                    "INVALID_ID_FORMAT",
                    "Invalid movie ID format: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    public Movie updateMovieById(String id, Movie updatedMovie) {
        if (id == null || id.isBlank()) {
            throw new AppException("INVALID_ID", "Movie ID must not be empty", HttpStatus.BAD_REQUEST);
        }
        if (updatedMovie == null) {
            throw new AppException("INVALID_DATA", "Updated movie data is required", HttpStatus.BAD_REQUEST);
        }

        Movie existingMovie = movieRepo.findById(id)
                .orElseThrow(() -> new AppException(
                        "NOT_FOUND",
                        "Movie with ID " + id + " not found",
                        HttpStatus.NOT_FOUND));

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

    public void deleteMovieById(String id) {
        if (id == null || id.isBlank()) {
            throw new AppException("INVALID_ID", "Movie ID must not be empty", HttpStatus.BAD_REQUEST);
        }

        boolean exists = movieRepo.existsById(id);
        if (!exists) {
            throw new AppException("NOT_FOUND", "Movie with ID " + id + " not found", HttpStatus.NOT_FOUND);
        }

        movieRepo.deleteById(id);
    }

    public List<Movie> findByTitleContains(String title) {
        try {
            return movieRepo.findByTitleContainingIgnoreCase(title);
        } catch (Exception e) {
            throw new AppException(
                    "MOVIE_SEARCH_FAILED",
                    "Failed to search movies by title: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
