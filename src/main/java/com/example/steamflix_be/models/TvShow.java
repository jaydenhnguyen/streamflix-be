package com.example.steamflix_be.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "tvshows")
public class TvShow {
    @Id
    private String id;

    private String title;
    private String type;
    private int year;
    private String genre;
    private String synopsis;
    private String poster;
    private String largePoster;
    private double rentPrice;
    private double purchasePrice;
    private boolean featured;
    private boolean mostDemanded;
    private double rating;
    private int seasons;
    private int episodes;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;
}
