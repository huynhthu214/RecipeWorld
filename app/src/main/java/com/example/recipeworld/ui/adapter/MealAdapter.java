package com.example.recipeworld.ui.adapter;

import android.content.Context;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.recipeworld.R;
import com.example.recipeworld.data.model.Meal;
import java.util.ArrayList;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.VH> {
    private final Context ctx;
    private List<Meal> meals = new ArrayList<>();
    private OnItemClickListener listener;

    public MealAdapter(Context ctx) { this.ctx = ctx; }

    public interface OnItemClickListener { void onItemClick(Meal meal); }
    public void setListener(OnItemClickListener l) { this.listener = l; }

    public void setMeals(List<Meal> list) { this.meals = list; notifyDataSetChanged(); }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_meal, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Meal m = meals.get(position);
        holder.tv.setText(m.getStrMeal());
        Glide.with(ctx).load(m.getStrMealThumb()).centerCrop().into(holder.iv);
        holder.itemView.setOnClickListener(v -> { if (listener != null) listener.onItemClick(m); });
    }

    @Override public int getItemCount() { return meals.size(); }

    static class VH extends RecyclerView.ViewHolder {
        ImageView iv; TextView tv;
        VH(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.imgThumb);
            tv = itemView.findViewById(R.id.tvName);
        }
    }
}
