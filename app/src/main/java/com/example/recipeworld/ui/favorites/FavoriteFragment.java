package com.example.recipeworld.ui.favorites;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipeworld.R;
import com.example.recipeworld.data.db.FavoriteMeal;
import com.example.recipeworld.data.db.MealDatabase;
import com.example.recipeworld.ui.detail.*;

import java.util.List;

public class FavoriteFragment extends Fragment {

    private RecyclerView rvFavorites;
    private FavoriteAdapter adapter;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        rvFavorites = view.findViewById(R.id.rvFavorites);
        rvFavorites.setLayoutManager(new GridLayoutManager(getContext(), 2));

        adapter = new FavoriteAdapter();
        rvFavorites.setAdapter(adapter);

        adapter.setOnItemClickListener(new FavoriteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FavoriteMeal meal) {
                DetailFragment fragment = DetailFragment.newInstance(meal.getIdMeal());
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.main_activity_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void onFavoriteClick(FavoriteMeal meal) {
                AsyncTask.execute(() -> {
                    MealDatabase.getInstance(getContext()).favoriteMealDao().delete(meal);
                });
            }
        });

        loadFavorites();

        return view;
    }

    private void loadFavorites() {
        MealDatabase.getInstance(getContext())
                .favoriteMealDao()
                .getAllFavorites()
                .observe(getViewLifecycleOwner(), favoriteMeals -> adapter.setFavoriteMeals(favoriteMeals));
    }
}
