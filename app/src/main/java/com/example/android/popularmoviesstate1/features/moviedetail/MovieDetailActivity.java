package com.example.android.popularmoviesstate1.features.moviedetail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviesstate1.R;
import com.example.android.popularmoviesstate1.data.local.database.tables.MovieEntity;
import com.example.android.popularmoviesstate1.data.remote.models.Trailer;
import com.example.android.popularmoviesstate1.features.trailer.TrailerListAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailNavigator {

    //region Constants

    private final static String TAG = MovieDetailActivity.class.getSimpleName();

    //endregion

    //region Fields

    private ImageView movieDetailPosterImageView;
    private RecyclerView trailerListView;
    private TextView movieDetailTitleTextView;
    private TextView movieDetailReleaseDateTextView;
    private TextView movieDetailVoteAverageTextView;
    private TextView movieDetailDescriptionTextView;

    private TrailerListAdapter trailerListAdapter;

    private MovieDetailViewModel movieDetailViewModel;

    //endregion

    //region Override Methods & Callbacks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movieDetailPosterImageView = findViewById(R.id.movieDetailPosterImageView);
        movieDetailTitleTextView = findViewById(R.id.movieDetailTitleTextView);
        movieDetailReleaseDateTextView = findViewById(R.id.movieDetailReleaseDateTextView);
        movieDetailVoteAverageTextView = findViewById(R.id.movieDetailVoteAverageTextView);
        movieDetailDescriptionTextView = findViewById(R.id.movieDetailDescriptionTextView);
        trailerListView = findViewById(R.id.trailerListView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, HORIZONTAL, false);
        trailerListView.setHasFixedSize(true);
        trailerListView.setLayoutManager(linearLayoutManager);
        //trailerListView.setAdapter(trailerListAdapter);

        initData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void closeOnError() {
        finish();
        Toast.makeText(this, R.string.error_movie_detail, Toast.LENGTH_SHORT).show();
    }

    //endregion

    //region Private Methods

    private void initData(){
        initViewModel();

        movieDetailViewModel.validateMovieDetailExtraData(getIntent());
    }

    private void initViewModel(){
        movieDetailViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        movieDetailViewModel.setNavigator(this);

        movieDetailViewModel.getMovieData().observe(this, new Observer<MovieEntity>() {
            @Override
            public void onChanged(@Nullable MovieEntity movie) {
                validateMovieDate(movie);
            }
        });
        movieDetailViewModel.getTrailerListData().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(@Nullable List<Trailer> trailers) {
                updateTrailerList(trailers);
            }
        });
    }

    private void validateMovieDate(MovieEntity movie){
        Log.d(TAG, "validateMovieDate");
        if(movie == null){
            return;
        }

        loadMovieDetailPoster(movie.getPosterPath());
        loadMovieDetailTitle(movie.getTitle());
        loadMovieDetailReleaseDate(movie.getReleaseDateLabel());
        loadMovieDetailVoteAverage(movie.getVoteAverageLabel(this));
        loadMovieDetailDescription(movie.getPlotSynopsis());
    }

    private void loadMovieDetailPoster(String posterPath) {
        Picasso.get()
                .load(posterPath)
                .placeholder(R.drawable.ic_movie_black_48dp)
                .error(R.drawable.ic_movie_black_48dp)
                .into(movieDetailPosterImageView);
    }

    private void loadMovieDetailTitle(String title) {
        movieDetailTitleTextView.setText(title);
    }

    private void loadMovieDetailReleaseDate(String releaseDate) {
        movieDetailReleaseDateTextView.setText(releaseDate);
    }

    private void loadMovieDetailVoteAverage(String voteAverage) {
        movieDetailVoteAverageTextView.setText(voteAverage);
    }

    private void loadMovieDetailDescription(String plotSynopsis) {
        movieDetailDescriptionTextView.setText(plotSynopsis);
    }

    private void updateTrailerList(@Nullable List<Trailer> trailerList) {
        Log.d(TAG, "updateTrailerList");
        trailerListView.setVisibility(View.VISIBLE);
        //trailerListAdapter.setTrailerList(trailerList);
    }

    //endregion
}
