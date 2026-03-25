package com.example.euf2_vilchez_alexandre.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Genre {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;

    public Genre(String name) {
        this.name = name;
    }
}
