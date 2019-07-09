package com.example.android.popularmoviesstate1.features.moviedetail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviesstate1.R;
import com.example.android.popularmoviesstate1.adapters.review.ReviewListAdapter;
import com.example.android.popularmoviesstate1.adapters.trailer.TrailerListAdapter;
import com.example.android.popularmoviesstate1.data.local.database.tables.MovieEntity;
import com.example.android.popularmoviesstate1.data.remote.models.Review;
import com.example.android.popularmoviesstate1.data.remote.models.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailNavigator,
        TrailerListAdapter.OnTrailerListAdapterListener,
        ReviewListAdapter.OnReviewListAdapterListener{

    //region Constants

    private final static String TAG = MovieDetailActivity.class.getSimpleName();

    //endregion

    //region Fields

    private boolean isFavoriteMovie = false;

    private MenuItem favoriteMenuItem;

    private ImageView movieDetailPosterImageView;
    private TextView movieDetailTitleTextView;
    private TextView movieDetailReleaseDateTextView;
    private TextView movieDetailVoteAverageTextView;
    private TextView movieDetailDescriptionTextView;
    private View trailerSectionContainer;
    private View reviewSectionContainer;

    private TrailerListAdapter trailerListAdapter;
    private ReviewListAdapter reviewListAdapter;

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
        trailerSectionContainer = findViewById(R.id.trailerSectionContainer);
        reviewSectionContainer = findViewById(R.id.reviewSectionContainer);

        trailerListAdapter = new TrailerListAdapter(this);
        reviewListAdapter = new ReviewListAdapter(this);

        RecyclerView trailerListView = findViewById(R.id.trailerListView);
        trailerListView.setHasFixedSize(true);
        trailerListView.setLayoutManager(new LinearLayoutManager(this, HORIZONTAL, false));
        trailerListView.setAdapter(trailerListAdapter);

        RecyclerView reviewListView = findViewById(R.id.reviewListView);
        reviewListView.setHasFixedSize(true);
        reviewListView.setLayoutManager(new LinearLayoutManager(this));
        reviewListView.setAdapter(reviewListAdapter);

        initData(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_detail, menu);

        this.favoriteMenuItem = menu.findItem(R.id.action_favorite);
        updateFavoriteMenuItem();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        if(item.getItemId() == R.id.action_favorite){
            movieDetailViewModel.updateFavoriteMovieStatus();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        movieDetailViewModel.saveInstanceState(outState);
    }

    @Override
    public void validateMovieDetailExtraData() {
        movieDetailViewModel.validateMovieDetailExtraData(getIntent());
    }

    @Override
    public void closeOnError() {
        finish();
        Toast.makeText(this, R.string.error_movie_detail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickedTrailerItem(Trailer trailer) {
        String url = trailer.getVideoPath();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    public void onClickedReviewItem(Review review) {
        String url = review.getUrl();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    //endregion

    //region Private Methods

    private void initData(Bundle savedInstanceState){
        initViewModel();

        movieDetailViewModel.validateInstanceState(savedInstanceState);
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
        movieDetailViewModel.getReviewListData().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(@Nullable List<Review> reviewList) {
                updateReviewList(reviewList);
            }
        });
        movieDetailViewModel.getIsFavorite().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isFavorite) {
                Log.d(TAG, "getIsFavorite onChanged isFavorite " + isFavorite);
                isFavoriteMovie = isFavorite != null ? isFavorite : false;
                updateFavoriteMenuItem();
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

    private void updateFavoriteMenuItem(){
        if(favoriteMenuItem == null){
            Log.d(TAG, "updateFavoriteMenuItem null");
            return;
        }

        Log.d(TAG, "updateFavoriteMenuItem");
        if(isFavoriteMovie){
            favoriteMenuItem.setIcon(R.drawable.ic_favorite_full);
        }else{
            favoriteMenuItem.setIcon(R.drawable.ic_favorite_border);
        }
    }

    private void updateTrailerList(@Nullable List<Trailer> trailerList) {
        Log.d(TAG, "updateTrailerList");
        if(trailerList != null && trailerList.size() > 0){
            trailerSectionContainer.setVisibility(View.VISIBLE);
            trailerListAdapter.setList(trailerList);
        }else{
            trailerSectionContainer.setVisibility(View.GONE);
            trailerListAdapter.clearList();
        }
    }

    private void updateReviewList(@Nullable List<Review> reviewList){
        Log.d(TAG, "updateReviewList");
        if(reviewList != null && reviewList.size() > 0){
            reviewSectionContainer.setVisibility(View.VISIBLE);
            reviewListAdapter.setList(reviewList);
        }else{
            reviewSectionContainer.setVisibility(View.GONE);
            reviewListAdapter.clearList();
        }
    }

    //endregion
}
