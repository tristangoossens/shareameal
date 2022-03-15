package com.example.shareameal.domain;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealListResponse {

    @SerializedName("result")
    @Expose
    private List<Meal> mealList = null;

    public MealListResponse(List<Meal> mealList) {
        this.mealList = mealList;
    }

    public List<Meal> getMealList() {
        return mealList;
    }

    public void setMealList(List<Meal> result) {
        this.mealList = result;
    }

}