package com.example.android.popularmoviesstate1.features.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmoviesstate1.R;
import com.example.android.popularmoviesstate1.adapters.movie.MovieListAdapter;
import com.example.android.popularmoviesstate1.data.local.database.tables.MovieEntity;
import com.example.android.popularmoviesstate1.features.moviedetail.MovieDetailActivity;

import java.util.List;

import static com.example.android.popularmoviesstate1.utils.Constants.EXTRA_MOVIE;

public class MainActivity extends AppCompatActivity implements MainNavigator,
        MovieListAdapter.OnMovieListAdapterListener{

    //region Constants

    private final static String TAG = MainActivity.class.getSimpleName();
    private final static int MOVIE_GRID_SPAN_VERTICAL_COUNT = 2;

    //endregion

    //region Fields

    private Menu menu;

    private ProgressBar movieListProgressBar;
    private RecyclerView movieListView;
    private SwipeRefreshLayout movieListSwipeRefresh;
    private TextView noDataLabel;

    private MovieListAdapter movieListAdapter;

    private MainViewModel mainViewModel;

    //endregion

    //region Override Methods & Callbacks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieListSwipeRefresh = findViewById(R.id.movieListSwipeRefresh);
        movieListProgressBar = findViewById(R.id.movieListProgressBar);
        movieListView = findViewById(R.id.movieListView);
        noDataLabel = findViewById(R.id.noDataLabel);

        movieListAdapter = new MovieListAdapter(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, MOVIE_GRID_SPAN_VERTICAL_COUNT);
        movieListView.setHasFixedSize(true);
        movieListView.setLayoutManager(gridLayoutManager);
        movieListView.setAdapter(movieListAdapter);

        movieListSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainViewModel.initMovieList();
            }
        });

        initData(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mainViewModel.saveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie, menu);

        this.menu = menu;

        mainViewModel.validateMenuItemSelected();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_movie_most_popular:
                mainViewModel.selectMovieMostPopularOption();
                return true;
            case R.id.menu_movie_highest_rated:
                mainViewModel.selectMovieHighestRatedOption();
                return true;
            case R.id.menu_movie_favorites:
                mainViewModel.selectMovieFavoriteOption();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClickedMovieItem(MovieEntity movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        startActivity(intent);
    }

    @Override
    public void hideProgressBar() {
        movieListProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMessage() {
        hideProgressBar();

        movieListSwipeRefresh.setRefreshing(false);
        movieListView.setVisibility(View.GONE);
        noDataLabel.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgressBar() {
        movieListProgressBar.setVisibility(View.VISIBLE);
        movieListView.setVisibility(View.GONE);
        noDataLabel.setVisibility(View.GONE);
    }

    @Override
    public void updateMenuItemChecked(int menuItemId) {
        if(menu == null){
            return;
        }

        menu.findItem(menuItemId).setChecked(true);
    }

    //endregion

    //region Private Methods

    private void initData(Bundle savedInstanceState){
        initViewModel();

        mainViewModel.validateInstanceState(savedInstanceState);
    }

    private void initViewModel(){
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.setNavigator(this);

        mainViewModel.getMovieList().observe(this, new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(@Nullable List<MovieEntity> movieList) {
                updateMovieList(movieList);
            }
        });
        mainViewModel.getFavoriteMovieListData().observe(this, new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(@Nullable List<MovieEntity> movieEntityList) {
                mainViewModel.updateFavoriteMovieList(movieEntityList);
            }
        });
    }

    private void updateMovieList(@Nullable List<MovieEntity> movieList) {
        Log.d(TAG, "updateMovieList");
        if (movieList != null && movieList.size() > 0) {
            hideProgressBar();

            movieListSwipeRefresh.setRefreshing(false);
            movieListView.setVisibility(View.VISIBLE);
            movieListAdapter.setList(movieList);
        } else {
            showErrorMessage();
        }
    }

    //endregion
}
