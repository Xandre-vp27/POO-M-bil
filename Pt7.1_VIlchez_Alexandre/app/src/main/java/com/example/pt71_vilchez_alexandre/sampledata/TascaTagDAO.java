package com.example.pt71_vilchez_alexandre.sampledata;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TascaTagDAO {

    @Insert
    void insertTascaTag(TascaTag tascaTag);

    @Delete
    void deleteTascaTag(TascaTag tascaTag);

    @Query("SELECT * FROM tasca_tag")
    List<TascaTag> getAllTascaTags();
}