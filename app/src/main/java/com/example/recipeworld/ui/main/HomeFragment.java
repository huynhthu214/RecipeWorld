package com.example.recipeworld.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeworld.R;
import com.example.recipeworld.ui.adapter.MealAdapter;
import com.example.recipeworld.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;
    private MealAdapter adapter;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private Button btnFavorite;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Ánh xạ view
        recyclerView = view.findViewById(R.id.rvMeals);
        searchView = view.findViewById(R.id.searchView);
        btnFavorite = view.findViewById(R.id.btnFavorite);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // ViewModel
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Adapter với sự kiện click item → sang DetailFragment
        adapter = new MealAdapter(getContext(), null, meal -> {
            NavController navController = NavHostFragment.findNavController(HomeFragment.this);

            String name = meal.getStrMeal() != null ? meal.getStrMeal() : "";
            String thumb = meal.getStrMealThumb() != null ? meal.getStrMealThumb() : "";
            String instructions = meal.getStrInstructions() != null ? meal.getStrInstructions() : "";
            String youtube = meal.getStrYoutube() != null ? meal.getStrYoutube() : "";

            HomeFragmentDirections.ActionHomeToDetail action =
                    HomeFragmentDirections.actionHomeToDetail(name, thumb, instructions, youtube);

            navController.navigate(action);
        });
        recyclerView.setAdapter(adapter);

        // Quan sát LiveData từ ViewModel
        viewModel.getMealsLiveData().observe(getViewLifecycleOwner(), meals -> {
            if (meals != null) adapter.setMealList(meals);
        });

        // Mặc định hiển thị vài món trước
        viewModel.searchMeals("noodles");


        // Tìm kiếm
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null && !query.trim().isEmpty()) {
                    viewModel.searchMeals(query.trim());
                    searchView.clearFocus();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // Nút Favorite → sang FavoriteFragment
        btnFavorite.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(HomeFragment.this);
            navController.navigate(R.id.favoriteFragment);
        });

        return view;
    }
}
