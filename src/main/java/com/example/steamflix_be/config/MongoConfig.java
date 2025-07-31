package com.example.steamflix_be.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

@Configuration
public class MongoConfig {
    @Autowired
    public void configureMongoConverter(MappingMongoConverter converter) {
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
    }
}
