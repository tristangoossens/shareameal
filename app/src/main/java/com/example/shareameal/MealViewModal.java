package com.example.shareameal;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.shareameal.data.ShareAMealRoomRepository;
import com.example.shareameal.domain.Meal;

import java.util.List;

public class MealViewModal extends AndroidViewModel {
    private ShareAMealRoomRepository repository;
    private LiveData<List<Meal>> allMeals;

    public MealViewModal(@NonNull Application application) {
        super(application);
        repository = new ShareAMealRoomRepository(application);
        allMeals = repository.getAllMeals();
    }

    public void insertMeal(Meal model) {
        repository.insertMeal(model);
    }

    public LiveData<List<Meal>> getAllMeals() {
        return repository.getAllMeals();
    }

    public LiveData<Meal> getMealByID(int id){ return repository.getMealByID(id); }
}
