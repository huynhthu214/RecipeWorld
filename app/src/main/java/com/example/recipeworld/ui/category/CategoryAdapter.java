package com.example.recipeworld.ui.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipeworld.R;
import com.example.recipeworld.data.api.CategoryResponse;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<CategoryResponse.CategoryItem> categories = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(CategoryResponse.CategoryItem category);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setCategories(List<CategoryResponse.CategoryItem> categories) {
        this.categories = categories != null ? categories : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryResponse.CategoryItem category = categories.get(position);
        holder.tvName.setText(category.getStrCategory());

        Glide.with(holder.itemView.getContext())
                .load(category.getStrCategoryThumb())
                .placeholder(R.drawable.ic_recipe_placeholder)
                .into(holder.imgThumb);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(category);
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imgThumb;
        TextView tvName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThumb = itemView.findViewById(R.id.img_category_thumb);
            tvName = itemView.findViewById(R.id.tv_category_name);
        }
    }
}
