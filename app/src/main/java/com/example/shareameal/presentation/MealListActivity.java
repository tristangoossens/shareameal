package com.example.shareameal.presentation;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.shareameal.MealListAdapter;
import com.example.shareameal.MealViewModal;
import com.example.shareameal.R;
import com.example.shareameal.data.ShareAMealApiRepository;
import com.example.shareameal.listener.ClickListener;
import com.example.shareameal.domain.Meal;
import com.example.shareameal.listener.MealListResponseListener;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MealListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MealListResponseListener {
    // Layout component attributes
    private RecyclerView rv;
    private RadioGroup filterRadioGroup;

    // Meal list class attribute
    private List<Meal> mealList;

    // Drawer menu class attributes
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;

    // SharedPreferences class attributes
    private SharedPreferences filterPreferences;
    private SharedPreferences.Editor filterPreferencesEditor;

    // Database

    // Log tag
    private static final String TAG = MealListActivity.class.getName();

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

        // Check if the device is connected to the internet
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if(cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()){
            // Retrieve meal list from API and set this class to be its listener
            ShareAMealApiRepository api = new ShareAMealApiRepository();
            api.getMeals(this);

            Log.i(TAG, "onCreate: Internet connection found, retrieving data from API");
        }else {
            // Retrieve meal list from the Room database (no internet)
            MealViewModal mealViewModal = new MealViewModal(this.getApplication());
            mealViewModal.getAllMeals().observe(this, new Observer<List<Meal>>() {
                @Override
                public void onChanged(List<Meal> meals) {
                    mealList = meals;
                    showMealListCountToast(mealList.size());
                    setMealListAdapter(meals, filterRadioGroup.getCheckedRadioButtonId());
                }
            });
            Log.w(TAG, "onCreate: No internet connection found, retrieving data from API");
        }

        // Retrieve shared preferences and load them into the frontend component
        filterPreferences = getPreferences(MODE_PRIVATE);
        filterPreferencesEditor = filterPreferences.edit();
        int selectedFilter = filterPreferences.getInt("selectedFilter", 1);
        loadFilterSharedPreference(selectedFilter);

        // Retrieve RadioGroup component and set a check listener
        filterRadioGroup = (RadioGroup) findViewById(R.id.list_filter_group);
        filterRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            // Check which filter was clicked and set the adapter with the new filtered list
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.list_filter_all:
                        filterPreferencesEditor.putInt("selectedFilter", 1);
                        break;
                    case R.id.list_filter_vegan:
                        filterPreferencesEditor.putInt("selectedFilter", 2);
                        break;
                    case R.id.list_filter_vega:
                        filterPreferencesEditor.putInt("selectedFilter", 3);
                        break;
                }

                setMealListAdapter(mealList, radioGroup.getCheckedRadioButtonId());

                // Save the altered shared preference key by committing it to the editor
                filterPreferencesEditor.commit();

                Log.i(TAG, String.format("onCheckedChanged: Filter preference changed (id: %s)", radioGroup.getCheckedRadioButtonId()));
            }
        });
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }


    // Load the saved preference filter by checking the radiobutton by the SharedPreference
    public void loadFilterSharedPreference(int filterSP){
        RadioButton allRadioButton = (RadioButton) findViewById(R.id.list_filter_all);
        RadioButton veganRadioButton = (RadioButton) findViewById(R.id.list_filter_vegan);
        RadioButton vegaRadioButton = (RadioButton) findViewById(R.id.list_filter_vega);

        Log.i(TAG, "loadFilterSharedPreference: Loading saved selected filter");

        switch (filterSP){
            case 1:
                allRadioButton.setChecked(true);
                break;
            case 2:
                veganRadioButton.setChecked(true);
                break;
            case 3:
                vegaRadioButton.setChecked(true);
                break;
        }
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

    @Override
    public void onMealListResponse(List<Meal> meals) {
        // Assign the response body to the meal list attribute
        mealList = meals;
        showMealListCountToast(mealList.size());
        setMealListAdapter(mealList, filterRadioGroup.getCheckedRadioButtonId());

        // Update room database (connected to internet)
        MealViewModal mealViewModal = new MealViewModal(this.getApplication());
        for (Meal meal: mealList) {
            mealViewModal.insertMeal(meal);

        }
    }

    private void showMealListCountToast(int mealCount){
        Toast.makeText(getApplicationContext(),String.format("%s: %d", getResources().getString(R.string.meal_list_count_label), mealCount),Toast.LENGTH_SHORT).show();
        Log.i(TAG, "showMealListCountToast: displayed meal count toast");
    }

    private void setMealListAdapter(List<Meal> meals, int selectedFilterID){
        List<Meal> filteredMealList = null;

        switch (selectedFilterID){
            case R.id.list_filter_all:
                filteredMealList = meals;
                break;
            case R.id.list_filter_vegan:
                filteredMealList = filterMeals(true);
                break;
            case R.id.list_filter_vega:
                filteredMealList = filterMeals(false);
                break;
        }

        // Create an instance of the MealListAdapter class to bind api data to frontend components
        MealListAdapter mla = new MealListAdapter(MealListActivity.this, filteredMealList);

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

        Log.i(TAG, "setMealListAdapter: Updated RecyclerView adapter");
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

        Log.i(TAG, "initDrawerMenu: Initialized drawer menu");
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
                Log.d(TAG, "onNavigationItemSelected: Selected meal list menu item");
                break;
        }

        // Close drawer after clicking menu item
        drawer.close();
        return true;
    }
}