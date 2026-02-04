package com.example.pt71_vilchez_alexandre.sampledata;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TagDAO {

    @Insert
    void insertTag(Tag tag);

    @Query("SELECT * FROM tag")
    List<Tag> getAllTag();

    @Update
    void updateTag(Tag tag);

    @Delete
    void deleteTag(Tag tag);
}
