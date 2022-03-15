package com.example.shareameal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shareameal.data.ClickListener;
import com.example.shareameal.domain.Meal;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MealListAdapter extends RecyclerView.Adapter<MealListAdapter.ViewHolder> {
    private List<Meal> mealList;
    private Context context;
    private LayoutInflater inflater;
    private static ClickListener clickListener;

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
        holder.priceTextView.setText(meal.getPrice());

        // Fit image into imageview component using the Picasso library
        Picasso.with(context)
                .load(meal.getImageUrl())
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
        private TextView priceTextView;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            this.nameTextView = itemView.findViewById(R.id.meal_list_card_name);
            this.mealImageView = itemView.findViewById(R.id.meal_list_card_image);
            this.priceTextView = itemView.findViewById(R.id.meal_list_card_price);

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
