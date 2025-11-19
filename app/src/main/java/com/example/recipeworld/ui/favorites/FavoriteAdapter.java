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
        void onUnfavoriteClick(FavoriteMeal meal);
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
                .inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        FavoriteMeal meal = favoriteMeals.get(position);

        holder.tvName.setText(meal.getName());
        Glide.with(holder.itemView.getContext())
                .load(meal.getThumbnail())
                .placeholder(R.drawable.ic_recipe_placeholder)
                .into(holder.imgFavoriteItem);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(meal);
        });

        holder.btnUnfavorite.setOnClickListener(v -> {
            if (listener != null) listener.onUnfavoriteClick(meal);
        });
    }

    @Override
    public int getItemCount() {
        return favoriteMeals.size();
    }

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFavoriteItem;
        ImageButton btnUnfavorite;
        TextView tvName;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFavoriteItem = itemView.findViewById(R.id.img_favorite_item);
            btnUnfavorite = itemView.findViewById(R.id.btn_unfavorite);
            tvName = itemView.findViewById(R.id.tv_item_name);
        }
    }
}
