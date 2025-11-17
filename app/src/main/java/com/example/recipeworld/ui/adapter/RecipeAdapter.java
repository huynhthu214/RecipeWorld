package com.example.recipeworld.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipeworld.R;
import com.example.recipeworld.data.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final Context context;
    private final OnItemClickListener listener;
    private List<Meal> mealList = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(Meal meal);
    }

    public RecipeAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe_card, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Meal meal = mealList.get(position);

        holder.tvTitle.setText(meal.getStrMeal());

        Glide.with(context)
                .load(meal.getThumbnail())
                .placeholder(R.drawable.ic_recipe_placeholder)
                .into(holder.imgRecipe);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(meal));
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public void submitList(List<Meal> meals) {
        this.mealList = meals != null ? meals : new ArrayList<>();
        notifyDataSetChanged();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {

        ImageView imgRecipe;
        ImageButton btnFavorite;
        TextView tvTitle;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRecipe = itemView.findViewById(R.id.img_recipe);
            btnFavorite = itemView.findViewById(R.id.btn_favorite);
            tvTitle = itemView.findViewById(R.id.tv_recipe_title);
        }
    }
}
