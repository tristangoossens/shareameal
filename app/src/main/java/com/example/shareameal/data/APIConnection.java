package com.example.shareameal.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIConnection {
    // API base URL and retrofit object attributes
    private static String baseURL = "https://shareameal-api.herokuapp.com/api/";
    private static retrofit2.Retrofit retrofit;

    // Static method to get retrofit object
    public static retrofit2.Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }
}
