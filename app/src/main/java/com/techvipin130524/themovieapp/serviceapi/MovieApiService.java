package com.techvipin130524.themovieapp.serviceapi;


import com.techvipin130524.themovieapp.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApiService {

    // The service Interface defines the data structure and behaviour of the API requests.
    // Acts as a bridge between your app and the API.

    @GET("movie/popular")
    Call<Result> getPopularMovies(@Query("api_key") String apiKey);

}
