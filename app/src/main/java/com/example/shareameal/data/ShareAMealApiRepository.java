package com.example.shareameal.data;

import android.util.Log;

import com.example.shareameal.domain.Meal;
import com.example.shareameal.domain.MealListResponse;
import com.example.shareameal.domain.MealResponse;
import com.example.shareameal.listener.MealListResponseListener;
import com.example.shareameal.listener.MealResponseListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareAMealApiRepository {
    private ShareAMealApiDAO apiDAO;

    // Log tag
    private static final String TAG = ShareAMealApiRepository.class.getName();

    public ShareAMealApiRepository(){
        Log.i(TAG, "ShareAMealApiRepository: Creating API connection with the DAO interface");
        apiDAO = ShareAMealApi.getRetrofit().create(ShareAMealApiDAO.class);
    }

    // Get meal list from the api
    public void getMeals(MealListResponseListener listener){
        // Create a call object for the route
        Call<MealListResponse> call = apiDAO.getMeals();

        // Send the request asynchronously (in background)
        call.enqueue(new Callback<MealListResponse>() {
            @Override
            public void onResponse(Call<MealListResponse> call, Response<MealListResponse> response) {
                // Check if the request returns with a 200 response code
                if(response.isSuccessful()){
                    // Execute the listener method to access response data
                    listener.onMealListResponse(response.body().getMealList());
                    Log.i(TAG, "getMeals: Successfully retrieved list of meals from the API");
                }else{
                    Log.e(TAG, "getMeals: API responded successfully but not with a 200 code");
                }
            }

            // The API returns with a server error
            @Override
            public void onFailure(Call<MealListResponse> call, Throwable t) {
                Log.e(TAG, "getMeals: Error occurred while retrieving meals from the API");
            }
        });
    }

    // Get a single meal from the api by its id
    public void getMeal(int id, MealResponseListener listener){
        // Create a call object for the route
        Call<MealResponse> call = apiDAO.getMealById(id);

        // Send the request asynchronously (in background)
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                // Check if the request returns with a 200 response code
                if(response.isSuccessful()){
                    // Execute the listener method to access response data
                    listener.onMealResponse(response.body().getMealResponse());
                    Log.i(TAG, "getMeals: Successfully retrieved meal by ID from the API");
                }else{
                    Log.e(TAG, "getMeal: API responded successfully but not with a 200 code");
                }
            }

            // The API returns with a server error
            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                Log.e(TAG, "getMeal: Error occurred while retrieving meals from the API");
            }
        });
    }
}
