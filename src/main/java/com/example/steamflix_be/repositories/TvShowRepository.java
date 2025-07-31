package com.example.steamflix_be.repositories;

import com.example.steamflix_be.models.TvShow;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TvShowRepository extends MongoRepository<TvShow, String> {
}
