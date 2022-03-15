package com.example.shareameal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.shareameal.data.APIConnection;
import com.example.shareameal.data.ClickListener;
import com.example.shareameal.data.ShareAMealApiInterface;
import com.example.shareameal.domain.Meal;
import com.example.shareameal.domain.MealListResponse;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView rv;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private List<Meal> mealList;

    // Intent extra info tag for sending a meal id to the detail page
    public static final String MEAL_ID = "com.example.shareameal.extra.MEAL_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meallist);

        // Initialize the drawer menu
        initDrawerMenu();

        // Get the RecyclerView frontend component and define the layout manager
        rv = findViewById(R.id.meal_list);
        rv.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve meal list from API and set its data
        getMealList();

        // Retrieve RadioGroup component and set a check listener
        RadioGroup filterRadioGroup = (RadioGroup) findViewById(R.id.list_filter_group);
        filterRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            // Check which filter was clicked and set the adapter with the new filtered list
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                List<Meal> filteredMealList = null;

                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.list_filter_all:
                        filteredMealList = mealList;
                        break;
                    case R.id.list_filter_vegan:
                        filteredMealList = filterMeals(true);
                        break;
                    case R.id.list_filter_vega:
                        filteredMealList = filterMeals(false);
                        break;
                }

                MealListAdapter mla = new MealListAdapter(MealListActivity.this, filteredMealList);
                rv.setAdapter(mla);
            }
        });
    }

    // Method to filter the list of meals on a few criteria.
    public List<Meal> filterMeals(boolean isFilterVeganSelected) {
        List<Meal> filteredMeals = new ArrayList<>();

        for(Meal meal: mealList){
            if(isFilterVeganSelected){
                if(meal.getIsVegan()) {
                    filteredMeals.add(meal);
                }
            }else{
                if(meal.getIsVega()){
                    filteredMeals.add(meal);
                }
            }
        }

        return filteredMeals;
    }


    // Load API data into RecyclerView by setting recyclerview Adapter using http call response data
    public void getMealList() {
        // Create an instance of the API interface with the generated methods using the retrofit object
        ShareAMealApiInterface shareAMealApi = APIConnection.getRetrofit().create(ShareAMealApiInterface.class);

        // Create a call object for the route
        Call<MealListResponse> call = shareAMealApi.getMeals();

        // Send the request asynchronously (in background)
        call.enqueue(new Callback<MealListResponse>() {
            @Override
            public void onResponse(Call<MealListResponse> call, Response<MealListResponse> response) {
                // Check if the request returns with a 200 response code
                if(response.isSuccessful()){
                    // Assign the response body to the meal list attribute
                    mealList = response.body().getMealList();

                    // Create an instance of the MealListAdapter class to bind api data to frontend components
                    MealListAdapter mla = new MealListAdapter(MealListActivity.this, mealList);

                    // Implement the ClickListener interface to define a on click method for each meal card
                    mla.setOnItemClickListener(new ClickListener() {
                        // Override the onItemClick method for the ClickListener interface
                        @Override
                        public void onItemClick(int id, View v) {
                            // Create a intent object for the meal detail page and setting the extra data (meal id)
                            Intent intent = new Intent(MealListActivity.this, MealDetailActivity.class);
                            intent.putExtra(MEAL_ID, id);

                            // Start the meal detail activity
                            startActivity(intent);
                        }
                    });

                    // Set the adapter for the Recyclerview component so the data can be displayed on screen
                    rv.setAdapter(mla);
                }else{
                    Log.d("CALL_ERROR", response.message());
                }
            }

            // The API returns with a server error
            @Override
            public void onFailure(Call<MealListResponse> call, Throwable t) {
                Log.d("CALL_ERROR", t.getMessage());
            }
        });
    }

    // Init drawer menu with a toggle
    public void initDrawerMenu() {
        // Retrieve the DrawerLayout component from the frontend and create a toggle button
        drawer = (DrawerLayout) findViewById(R.id.meal_list_activity);
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);

        // Add the drawer toggle and sync its state
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Get the NavigationView component and set the navigation item selected listener to this class
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // Check whether the drawer menu has been toggled
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }

        return false;
    }

    // Method defining a on-click listener for the drawer menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Check witch item in the drawer has been clicked
        switch(item.getItemId()){
            case R.id.mu_meallist:
                Log.d("NAV_MENU", "MEAL_LIST MENU ITEM CLICKED");
                break;
        }

        // Close drawer after clicking menu item
        drawer.close();
        return true;
    }
}