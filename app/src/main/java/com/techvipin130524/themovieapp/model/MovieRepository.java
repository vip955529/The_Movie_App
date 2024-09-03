package com.techvipin130524.themovieapp.model;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.techvipin130524.themovieapp.R;
import com.techvipin130524.themovieapp.serviceapi.MovieApiService;
import com.techvipin130524.themovieapp.serviceapi.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    // used to abstract data source details and provides a clean API for the ViewModel to
    // fetch and manage the data.

    private ArrayList<Movie> movies = new ArrayList<>();
    private MutableLiveData<List<Movie>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public MovieRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Movie>> getMutableLiveData() {
        MovieApiService movieApiService = RetrofitInstance.getService();

        Call<Result> call = movieApiService
                .getPopularMovies(application.getApplicationContext().getString(R.string.api_key));

        //perform network request in te background thread and handle the response in the main(UI) thread
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                // Success
                Result result = response.body();

                if (result != null && result.getResults() != null) {
                    movies = (ArrayList<Movie>) result.getResults();
                    mutableLiveData.setValue(movies);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable throwable) {

            }
        });

        return mutableLiveData;
    }
}
