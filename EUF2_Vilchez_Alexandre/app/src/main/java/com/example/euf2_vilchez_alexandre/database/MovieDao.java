package com.example.euf2_vilchez_alexandre.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMovie(Movie movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertGenre(Genre genre);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovieGenreCrossRef(MovieGenreCrossRef crossRef);

    @Update
    void updateMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Transaction
    @Query("SELECT * FROM Movie")
    List<MovieWithGenres> getAllMoviesWithGenres();

    @Transaction
    @Query("SELECT * FROM Movie WHERE id IN (SELECT movieId FROM MovieGenreCrossRef WHERE genreId = :genreId)")
    List<MovieWithGenres> getMoviesByGenre(int genreId);
    
    @Query("SELECT * FROM Genre")
    List<Genre> getAllGenres();
}
