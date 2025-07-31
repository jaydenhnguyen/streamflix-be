package com.example.steamflix_be.repositories;

import com.example.steamflix_be.models.TvShow;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TvShowRepository extends MongoRepository<TvShow, String> {
    List<TvShow> findByTitleContainingIgnoreCase(String title);

    List<TvShow> findByFeaturedTrue();
}
