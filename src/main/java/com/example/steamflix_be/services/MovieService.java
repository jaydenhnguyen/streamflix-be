package com.example.steamflix_be.services;

import com.example.steamflix_be.models.Movie;
import com.example.steamflix_be.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepo;

    public Movie addNewMovie(Movie movie) {
        movie.setCreatedAt(new Date());
        movie.setUpdatedAt(new Date());
        return movieRepo.save(movie);
    }
}
