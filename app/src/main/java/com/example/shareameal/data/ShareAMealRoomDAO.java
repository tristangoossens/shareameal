package com.example.shareameal.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.shareameal.domain.Meal;

import java.util.List;

@Dao
public interface ShareAMealRoomDAO {
    // Retrieve meals from the database
    @Query("SELECT * FROM Meal")
    LiveData<List<Meal>> getAllMeals();

    // Retrieve a single meal by its id from the database
    @Query("SELECT * FROM Meal WHERE id = :id")
    LiveData<Meal> getMealByID(int id);

    // Insert a meal into the database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(Meal meal);
}
