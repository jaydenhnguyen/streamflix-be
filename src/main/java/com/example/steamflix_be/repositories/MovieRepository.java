package com.example.steamflix_be.repositories;

import com.example.steamflix_be.models.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {
    List<Movie> findByTitleContainingIgnoreCase(String title);
}
