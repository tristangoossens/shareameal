package com.example.shareameal.data;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.shareameal.domain.Meal;
import com.example.shareameal.presentation.MealListActivity;

import java.util.List;

public class ShareAMealRoomRepository {
    private ShareAMealRoomDAO dao;
    private LiveData<List<Meal>> allMeals;

    // Log tag
    private static final String TAG = ShareAMealRoomRepository.class.getName();

    public ShareAMealRoomRepository(Application application) {
        ShareAMealRoomDatabase database = ShareAMealRoomDatabase.getDatabase(application);
        dao = database.shareAMealRoomDAO();
        allMeals = dao.getAllMeals();
    }

    // Execute the async task to insert a meal into the database
    public void insertMeal(Meal model) {
        Log.i(TAG, "insertMeal: Inserting meal into room database");
        new InsertMealAsyncTask(dao).execute(model);
    }

    // Return the list of meals returned from the database
    public LiveData<List<Meal>> getAllMeals() {
        return allMeals;
    }

    // Return a single meal retrieved from the database by its id
    public LiveData<Meal> getMealByID(int id) { return dao.getMealByID(id); }

    // Inner class that defines a async task to insert a meal into the database
    private static class InsertMealAsyncTask extends AsyncTask<Meal, Void, Void> {
        private ShareAMealRoomDAO dao;

        private InsertMealAsyncTask(ShareAMealRoomDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Meal... model) {
            dao.insertMeal(model[0]);
            return null;
        }
    }
}
