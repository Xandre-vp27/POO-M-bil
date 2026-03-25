package com.example.euf2_vilchez_alexandre.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Movie {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public int duration;

    public Movie(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }
}
