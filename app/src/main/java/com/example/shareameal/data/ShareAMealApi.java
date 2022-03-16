package com.example.shareameal.data;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShareAMealApi {
    // API base URL and retrofit object attributes
    private static String baseURL = "https://shareameal-api.herokuapp.com/api/";
    private static retrofit2.Retrofit retrofit;

    // Log tag
    private static final String TAG = ShareAMealApi.class.getName();

    // Static method to get retrofit object
    public static retrofit2.Retrofit getRetrofit(){
        // Create an instance of the retrofit object if it does not exist
        if(retrofit == null){
            Log.i(TAG, "getRetrofit: Creating retrofit api connection");
            retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }
}
