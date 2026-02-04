package com.example.pt71_vilchez_alexandre.sampledata;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TascaDAO {

    @Insert
    long insertTasca(Tasca tasca);

    @Update
    void updateTasca(Tasca tasca);

    @Delete
    void deleteTasca(Tasca tasca);

    @Query("DELETE FROM tasca WHERE id = (SELECT MAX(id) FROM tasca)")
    void deleteLastTasca();

    @Transaction
    @Query("SELECT * FROM tasca")
    List<TascaAmbTag> getAllTasquesAmbTags();

    @Transaction
    @Query("SELECT tasca.* FROM tasca " +
            "INNER JOIN tasca_tag ON tasca.id = tasca_tag.tascaId " +
            "WHERE tasca_tag.tagId = :tagId")
    List<TascaAmbTag> getTasquesByTagAmbTags(int tagId);
}
