package com.example.android.popularmoviesstate1.data.local.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import com.example.android.popularmoviesstate1.data.local.database.converters.DateConverter;
import com.example.android.popularmoviesstate1.data.local.database.daos.MovieDao;
import com.example.android.popularmoviesstate1.data.local.database.tables.MovieEntity;

@Database(entities = {MovieEntity.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    //region Constants

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "popular_movie_state";

    //endregion

    //region Fields

    private static AppDatabase sInstance;

    //endregion

    //region Public Methods

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .allowMainThreadQueries()//TODO Delete
                        .build();
            }
        }

        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    //endregion

    //region Abstract Methods

    public abstract MovieDao movieDao();

    //endregion

}
