package com.example.android.popularmoviesstate1.data.local.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.popularmoviesstate1.data.local.database.tables.MovieEntity;

import java.util.List;

@Dao
public interface MovieDao {

    //region Create

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllMovies(List<MovieEntity> movieList);

    //endregion

    //region Read

    @Query("SELECT * FROM movie")
    LiveData<List<MovieEntity>> loadAllMovies();

    //endregion
}
