package com.example.euf2_vilchez_alexandre.database;

import androidx.room.Entity;

@Entity(primaryKeys = {"movieId", "genreId"})
public class MovieGenreCrossRef {
    public int movieId;
    public int genreId;

    public MovieGenreCrossRef(int movieId, int genreId) {
        this.movieId = movieId;
        this.genreId = genreId;
    }
}
