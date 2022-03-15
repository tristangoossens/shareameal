package com.example.shareameal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.shareameal.data.APIConnection;
import com.example.shareameal.data.ShareAMealApiInterface;
import com.example.shareameal.domain.Cook;
import com.example.shareameal.domain.Meal;
import com.example.shareameal.domain.MealResponse;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealdetail);

        // Get the sending intent with extra info
        Intent intent = getIntent();

        // Send given id to method so an API response can be parsed
        getMealById(intent.getIntExtra(MealListActivity.MEAL_ID, 1));
    }

    // Retrieve
    private void setData(Meal meal){
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
                .fit()
                .into(mealImageView);

        // Set meal text data into frontend components
        mealNameTextView.setText(meal.getName());
        mealPriceTextView.setText(meal.getPrice());
        mealDescriptionTextView.setText(meal.getDescription());
        mealDateTextView.setText(String.format("Geserveerd op: %s", meal.getDateTime()));

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

        // Set meal text data
        cookNameTextView.setText(String.format("%s %s", cook.getFirstName(), cook.getLastName()));
        cookEmailTextView.setText(cook.getEmailAddress());
        cookAddressTextView.setText(String.format("%s - %s", cook.getStreet(), cook.getCity()));
    }

    // Get a meal from the API with a given ID
    private void getMealById(int id){
        // Create an instance of the API interface with the generated methods using the retrofit object
        ShareAMealApiInterface shareAMealApi = APIConnection.getRetrofit().create(ShareAMealApiInterface.class);

        // Create a call object for the route
        Call<MealResponse> call = shareAMealApi.getMealById(id);

        // Send the request asynchronously (in background)
        call.enqueue(new Callback<MealResponse>() {
            // If the API sends a response that is not a server error
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                // Check if the request returns with a 200 response code
                if(response.isSuccessful()){
                    // Call the setData method to fill frontend components using the parsed response body
                    setData(response.body().getMealResponse());
                }else{
                    Log.d("CALL_ERROR", response.message());
                }
            }

            // The API returns with a server error
            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                Log.d("CALL_ERROR", t.getMessage());
            }
        });
    }
}