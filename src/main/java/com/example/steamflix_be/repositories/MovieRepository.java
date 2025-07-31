package com.example.steamflix_be.repositories;

import com.example.steamflix_be.models.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {
}
