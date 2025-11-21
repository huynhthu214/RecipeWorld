package com.example.recipeworld.ui.favorites;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipeworld.R;
import com.example.recipeworld.data.db.FavoriteMeal;
import com.example.recipeworld.ui.detail.MealDetailFragment;
import com.example.recipeworld.viewmodel.FavoriteViewModel;

public class FavoriteFragment extends Fragment {

    private RecyclerView rvFavorites;
    private FavoriteAdapter adapter;
    private FavoriteViewModel viewModel;

    public FavoriteFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        rvFavorites = view.findViewById(R.id.rvFavorites);
        rvFavorites.setLayoutManager(new GridLayoutManager(getContext(), 2));

        adapter = new FavoriteAdapter();
        rvFavorites.setAdapter(adapter);

        adapter.setOnItemClickListener(new FavoriteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FavoriteMeal meal) {
                MealDetailFragment fragment = MealDetailFragment.newInstance(meal.getIdMeal());
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.main_activity_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void onUnfavoriteClick(FavoriteMeal meal) {
                viewModel.deleteFavorite(meal);
                Toast.makeText(getContext(), "Đã xóa khỏi yêu thích", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);

        viewModel.getAllFavorites().observe(getViewLifecycleOwner(), favoriteMeals -> {
            adapter.setFavoriteMeals(favoriteMeals);
            adapter.notifyDataSetChanged(); // bắt buộc để RecyclerView refresh
            if (favoriteMeals.isEmpty()) {
                Toast.makeText(getContext(), "Danh sách yêu thích trống", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
