package com.example.shareameal.data;

import com.example.shareameal.domain.MealListResponse;
import com.example.shareameal.domain.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

// Interface containing all routes with parsing methods
public interface ShareAMealApiInterface {
    // Get all meals route
    @GET("meal")
    Call<MealListResponse> getMeals();

    // Get a single meal by id
    @GET("meal/{id}")
    Call<MealResponse> getMealById(@Path("id") int id);
}
