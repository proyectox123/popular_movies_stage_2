package com.example.android.popularmoviesstate1.data.translators;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.popularmoviesstate1.data.local.database.converters.DateConverter;
import com.example.android.popularmoviesstate1.data.local.database.tables.MovieEntity;
import com.example.android.popularmoviesstate1.data.remote.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieTranslator {

    public static List<MovieEntity> translateMovieList(@Nullable List<Movie> movieList){
        if (movieList == null || movieList.size() == 0) {
            return null;
        }

        List<MovieEntity> movieEntityList = new ArrayList<>();
        for(Movie movie : movieList){
            movieEntityList.add(translateMovie(movie));
        }

        return movieEntityList;
    }

    private static MovieEntity translateMovie(@NonNull Movie movie){
        return new MovieEntity(
                movie.getId(),
                movie.getTitle(),
                DateConverter.toDate(movie.getReleaseDate()),
                movie.getPosterPath(),
                movie.getVoteAverage(),
                movie.getPlotSynopsis()
        );
    }

}