package com.example.shareameal.data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.shareameal.domain.Meal;

import java.util.List;

@Database(entities = {Meal.class}, version = 1)
public abstract class ShareAMealRoomDatabase extends RoomDatabase {
    // Instance of the database
    private static ShareAMealRoomDatabase dbInstance;

    // Abstract method to
    public abstract ShareAMealRoomDAO shareAMealRoomDAO();

    // Log tag
    private static final String TAG = ShareAMealRoomDatabase.class.getName();

    // Get an instance of the database
    public static ShareAMealRoomDatabase getDatabase(Context context){
        // Create an instance of the database if it does not exist
        if(dbInstance == null){
            Log.i(TAG, "getDatabase: Creating an instance of the shareameal room database");
            dbInstance = Room.databaseBuilder(context, ShareAMealRoomDatabase.class, "shareameal")
                    .addCallback(roomCallback)
                    .build();
        }

        return dbInstance;
    }

    // Callback that is ran while creating our database. It will populate the database using the async task
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(dbInstance).execute();
        }
    };


    // Asynchronous task to populate the database with data
    public static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        PopulateDbAsyncTask(ShareAMealRoomDatabase instance) {
            ShareAMealRoomDAO dao = instance.shareAMealRoomDAO();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
