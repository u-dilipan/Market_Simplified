package com.example.market_simplified.api;

import com.example.market_simplified.models.UserData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("repositories")
    Call<List<UserData>> getMovies();
}
