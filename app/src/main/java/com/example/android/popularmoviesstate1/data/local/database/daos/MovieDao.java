package com.example.android.popularmoviesstate1.data.local.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.popularmoviesstate1.data.local.database.tables.MovieEntity;

import java.util.List;

@Dao
public interface MovieDao {

    //region Create

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(MovieEntity movieEntity);

    //endregion

    //region Read

    @Query("SELECT * FROM movie")
    List<MovieEntity> loadAllMovies();

    @Query("SELECT * FROM movie where movie_id = :movieId")
    MovieEntity loadMovieById(int movieId);

    //endregion

    //region Delete

    @Delete
    void deleteMovie(MovieEntity movieEntity);

    //endregion
}
