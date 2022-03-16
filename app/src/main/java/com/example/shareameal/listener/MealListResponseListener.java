package com.example.shareameal.listener;

import com.example.shareameal.domain.Meal;

import java.util.List;

public interface MealListResponseListener {
    // Meal list response listener
    void onMealListResponse(List<Meal> meals);
}
