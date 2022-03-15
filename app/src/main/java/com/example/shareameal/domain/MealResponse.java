package com.example.shareameal.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealResponse {
    @SerializedName("result")
    @Expose
    private Meal mealResponse = null;

    public MealResponse(Meal mealResponse) {
        this.mealResponse = mealResponse;
    }

    public Meal getMealResponse() {
        return mealResponse;
    }

    public void setMeal(Meal mealResponse) {
        this.mealResponse = mealResponse;
    }
}
