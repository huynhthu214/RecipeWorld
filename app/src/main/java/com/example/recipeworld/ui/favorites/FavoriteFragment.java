package com.example.recipeworld.ui.favorites;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.recipeworld.R;
import com.example.recipeworld.data.db.FavoriteMeal;
import com.example.recipeworld.data.db.MealDatabase;
import com.example.recipeworld.ui.detail.MealDetailFragment;

public class FavoriteFragment extends Fragment {
    private FavoriteViewModel favoriteViewModel;
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
        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        adapter = new FavoriteAdapter();
        rvFavorites.setAdapter(adapter);

        adapter.setOnItemClickListener(new FavoriteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FavoriteMeal meal) {
                if (meal.getIdMeal() != null) {
                    MealDetailFragment fragment = MealDetailFragment.newInstance(meal.getIdMeal());
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.main_activity_container, fragment)
                            .addToBackStack(null)
                            .commit();
                } else {
                    Toast.makeText(getContext(), "ID món ăn yêu thích bị lỗi.", Toast.LENGTH_SHORT).show();
                }
            }

//            @Override
//            public void onFavoriteClick(FavoriteMeal meal) {
//                AsyncTask.execute(() -> {
//                    if (getContext() != null) {
//                        MealDatabase.getInstance(getContext()).mealDao().deleteFavorite(meal);
//                    }
//                });
//            }
            @Override
            public void onFavoriteClick(FavoriteMeal meal) {
                favoriteViewModel.deleteFavorite(meal);
            }
        });

        loadFavorites();

        return view;
    }

    private void loadFavorites() {
        if (getContext() != null) {
            MealDatabase.getInstance(getContext())
                    .mealDao()
                    .getAllFavorites()
                    .observe(getViewLifecycleOwner(), favoriteMeals -> {
                        if (favoriteMeals != null) {
                            adapter.setFavoriteMeals(favoriteMeals);
                        }
                    });
        }
    }
}