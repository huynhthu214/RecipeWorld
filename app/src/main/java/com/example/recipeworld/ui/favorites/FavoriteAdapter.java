package com.example.recipeworld.ui.favorites;

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
import com.example.recipeworld.data.db.FavoriteMeal;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private List<FavoriteMeal> favoriteMeals = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(FavoriteMeal meal);
        void onFavoriteClick(FavoriteMeal meal);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setFavoriteMeals(List<FavoriteMeal> meals) {
        this.favoriteMeals = meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe_card, parent, false); // sử dụng item_recipe_card
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        FavoriteMeal meal = favoriteMeals.get(position);
        holder.tvTitle.setText(meal.getName());
        holder.tvAuthor.setText("Recipe"); // hoặc meal.getInstructions() nếu muốn hiển thị

        Glide.with(holder.itemView.getContext())
                .load(meal.getThumbnail())
                .placeholder(R.drawable.ic_recipe_placeholder)
                .into(holder.imgRecipe);

        // Nút yêu thích
        holder.btnFavorite.setImageResource(R.drawable.ic_favorite_filled);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(meal);
        });

        holder.btnFavorite.setOnClickListener(v -> {
            if (listener != null) listener.onFavoriteClick(meal);
        });
    }

    @Override
    public int getItemCount() {
        return favoriteMeals.size();
    }

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView imgRecipe;
        ImageButton btnFavorite;
        TextView tvTitle, tvAuthor;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRecipe = itemView.findViewById(R.id.img_recipe);
            btnFavorite = itemView.findViewById(R.id.btn_favorite);
            tvTitle = itemView.findViewById(R.id.tv_recipe_title);
            tvAuthor = itemView.findViewById(R.id.tv_recipe_author);
        }
    }
}
