package com.example.recipeworld.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.recipeworld.R;
import com.example.recipeworld.data.model.Meal;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private Context context;
    private List<Meal> mealList;
    private OnMealClickListener listener;

    public interface OnMealClickListener {
        void onMealClick(Meal meal);
    }

    public MealAdapter(Context context, List<Meal> mealList, OnMealClickListener listener) {
        this.context = context;
        this.mealList = mealList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = mealList.get(position);
        holder.tvMealName.setText(meal.getStrMeal());
        Glide.with(context).load(meal.getStrMealThumb()).into(holder.imgMeal);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onMealClick(meal);
        });
    }

    @Override
    public int getItemCount() {
        return mealList != null ? mealList.size() : 0;
    }

    public void setMealList(List<Meal> newList) {
        this.mealList = newList;
        notifyDataSetChanged();
    }

    static class MealViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMeal;
        TextView tvMealName;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMeal = itemView.findViewById(R.id.imgMeal);
            tvMealName = itemView.findViewById(R.id.tvMealName);
        }
    }
}
