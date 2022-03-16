package com.example.shareameal.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shareameal.MealViewModal;
import com.example.shareameal.R;
import com.example.shareameal.data.ShareAMealApi;
import com.example.shareameal.data.ShareAMealApiDAO;
import com.example.shareameal.data.ShareAMealApiRepository;
import com.example.shareameal.domain.Cook;
import com.example.shareameal.domain.Meal;
import com.example.shareameal.domain.MealResponse;
import com.example.shareameal.listener.MealResponseListener;
import com.example.shareameal.presentation.MealListActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealDetailActivity extends AppCompatActivity implements MealResponseListener {
    // Log tag
    private static final String TAG = MealDetailActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealdetail);

        // Get the sending intent with extra info
        Intent intent = getIntent();
        int mealId = intent.getIntExtra(MealListActivity.MEAL_ID, 1);


        // Check if the device is connected to the internet
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
            // Retrieve meal from API and set this class to be its listener
            ShareAMealApiRepository api = new ShareAMealApiRepository();
            api.getMeal(mealId, this);

            Log.i(TAG, "onCreate: Internet connection found, retrieving data from API");
        } else {
            // Retrieve meal from the Room database (no internet)
            MealViewModal mealViewModal = new MealViewModal(this.getApplication());
            mealViewModal.getMealByID(mealId).observe(this, new Observer<Meal>() {
                @Override
                public void onChanged(Meal meal) {
                    setData(meal);
                }
            });

            Log.w(TAG, "onCreate: No internet connection found, retrieving data from API");
        }
    }

    @Override
    public void onMealResponse(Meal meal) {
        setData(meal);
    }

    private void setData(Meal meal){
        Log.i(TAG, "Loading data into layout components");

        // Retrieve frontend components for meal information
        ImageView mealImageView = (ImageView) findViewById(R.id.meal_image);
        TextView mealNameTextView = (TextView) findViewById(R.id.meal_name);
        TextView mealPriceTextView = (TextView) findViewById(R.id.meal_price);
        TextView mealDescriptionTextView = (TextView) findViewById(R.id.meal_description);
        TextView mealDateTextView = (TextView) findViewById(R.id.meal_date);
        TextView mealVeganTextView = (TextView) findViewById(R.id.meal_vegan);
        TextView mealVegetarianTextView = (TextView) findViewById(R.id.meal_vegetarian);

        // Retrieve frontend components for cook information
        TextView cookNameTextView = (TextView) findViewById(R.id.cook_name);
        TextView cookEmailTextView = (TextView) findViewById(R.id.cook_email);
        TextView cookAddressTextView = (TextView) findViewById(R.id.cook_address);

        // Load meal image into imageview using Picasso
        Picasso.with(this)
                .load(meal.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .fit()
                .into(mealImageView);

        // Set meal text data into frontend components
        mealNameTextView.setText(meal.getName());
        mealPriceTextView.setText(String.format("€ %s", meal.getPrice()));
        mealDescriptionTextView.setText(meal.getDescription());
        mealDateTextView.setText(String.format("%s: %s", getResources().getString(R.string.meal_date_prefix), meal.getDateTime()));

        // Check whether the meal is vegan and set the icon to a checkmark if true
        if(meal.getIsVegan()){
            mealVeganTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check, 0, 0, 0);
        }

        // Check whether the meal is vegetarian and set the icon to a checkmark if true
        if(meal.getIsVega()){
            mealVegetarianTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check, 0, 0, 0);
        }

        // Extract the cook from the meal object
        Cook cook = meal.getCook();

        if(cook != null){
            // Set cook text data
            cookNameTextView.setText(String.format("%s %s", cook.getFirstName(), cook.getLastName()));
            cookEmailTextView.setText(cook.getEmailAddress());
            cookAddressTextView.setText(String.format("%s - %s", cook.getStreet(), cook.getCity()));
        }else{
            Log.w(TAG, "setData: No cook found in meal object, maintaining default layout");
        }

    }
}