package com.example.shareameal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shareameal.data.ShareAMealRoomDatabase;
import com.example.shareameal.listener.ClickListener;
import com.example.shareameal.domain.Meal;
import com.example.shareameal.presentation.MealListActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MealListAdapter extends RecyclerView.Adapter<MealListAdapter.ViewHolder> {
    private List<Meal> mealList;
    private Context context;
    private LayoutInflater inflater;
    private static ClickListener clickListener;

    // Log tag
    private static final String TAG = MealListAdapter.class.getName();

    // Initialize Adapter class with class attributes.
    public MealListAdapter(Context context, List<Meal> mealList){
        // Create a LayoutInflater from the given context
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.mealList = mealList;
    }

    // Overwritten method from abstract class RecyclerView.Adapter
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate parent layout so a view can be sent to the inner ViewHolder class
        View view = this.inflater.inflate(R.layout.meal_listitem, parent, false);
        return new ViewHolder(view);
    }

    // Overwritten method from abstract class RecyclerView.Adapter
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind meal data with the given ViewHolder and position
        Meal meal = this.mealList.get(position);
        holder.nameTextView.setText(meal.getName());
        holder.detailTextView.setText(String.format("%s\n%s", String.format("€ %s", meal.getPrice()), meal.getDateTime()));

        // Fit image into imageview component using the Picasso library
        Picasso.with(context)
                .load(meal.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .fit()
                .into(holder.mealImageView);
    }

    // Overwritten method from abstract class RecyclerView.Adapter
    @Override
    public int getItemCount() {
        return this.mealList.size();
    }

    // Set the OnClickListener for the adapter to the given ClickListener
    public void setOnItemClickListener(ClickListener clickListener) {
        MealListAdapter.clickListener = clickListener;
    }

    // Inner ViewHolder class containing layout components
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mealImageView;
        private TextView nameTextView;
        private TextView detailTextView;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            this.nameTextView = itemView.findViewById(R.id.meal_list_card_name);
            this.mealImageView = itemView.findViewById(R.id.meal_list_card_image);
            this.detailTextView = itemView.findViewById(R.id.meal_list_card_details);

            // Set the itemView onclick listener to this class (overwritten onClick method)
            itemView.setOnClickListener(this);
        }

        // Override the onClick method to a method from the ClickListener interface
        @Override
        public void onClick(View v) {
            // Pass the mealID and view to the onItemClick method
            clickListener.onItemClick(mealList.get(getAdapterPosition()).getId(), v);
        }
    }
}
