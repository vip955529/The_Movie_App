package com.techvipin130524.themovieapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.techvipin130524.themovieapp.databinding.ActivityMainBinding;

import com.techvipin130524.themovieapp.model.Movie;
import com.techvipin130524.themovieapp.view.MovieAdapter;
import com.techvipin130524.themovieapp.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ArrayList<Movie> movies;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.swipe_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);

        viewModel = new ViewModelProvider(this)
                .get(MainActivityViewModel.class);

        getPopularMovie();
        swipeRefreshLayout = binding.swipeLayout;
        swipeRefreshLayout.setColorSchemeResources(R.color.black);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPopularMovie();
            }
        });

    }

    private void DisplayMoviesInRecyclerView() {
        recyclerView = binding.recyclerview;
        movieAdapter = new MovieAdapter(this,movies);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        // notify an adapter associated with a RecyclerView
        // that the underlying dataset has changed
        movieAdapter.notifyDataSetChanged();

    }

    private void getPopularMovie(){
        viewModel.getAllMovies().observe(this
                , new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(List<Movie> moviesFromLiveData) {
                        movies = (ArrayList<Movie>) moviesFromLiveData;
                        DisplayMoviesInRecyclerView();
                    }
                });
    }
}