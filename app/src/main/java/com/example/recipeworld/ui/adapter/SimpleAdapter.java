package com.example.recipeworld.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(String item);
    }

    private List<String> items = new ArrayList<>();
    private OnItemClickListener listener;

    public void setItems(List<String> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView tv = new TextView(parent.getContext());
        tv.setPadding(32, 32, 32, 32);
        tv.setTextSize(16);
        return new ViewHolder(tv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = items.get(position);
        holder.textView.setText(item);
        holder.textView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView)itemView;
        }
    }
}
