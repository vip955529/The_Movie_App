package com.techvipin130524.themovieapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.techvipin130524.themovieapp.model.Movie;
import com.techvipin130524.themovieapp.model.MovieRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    // ViewModel : suitable for non android specific logic
    // AndroidViewModel : used when ViewModel class needs to access Android- Specific components

    private MovieRepository repository;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.repository = new MovieRepository(application);
    }

    // Live Data
    public LiveData<List<Movie>> getAllMovies(){
        return repository.getMutableLiveData();

    }
}
