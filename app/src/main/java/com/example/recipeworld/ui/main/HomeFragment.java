package com.example.recipeworld.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeworld.R;
import com.example.recipeworld.data.model.Meal;
import com.example.recipeworld.ui.adapter.MealAdapter;
import com.example.recipeworld.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {
    private HomeViewModel viewModel;
    private MealAdapter adapter;
    private RecyclerView recyclerView;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Ánh xạ view
        recyclerView = view.findViewById(R.id.rvMeals);
        searchView = view.findViewById(R.id.searchView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // ViewModel
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Adapter + click listener
        adapter = new MealAdapter(getContext(), null, meal -> {
            NavController navController = Navigation.findNavController(view);
            HomeFragmentDirections.ActionHomeToDetail action =
                    HomeFragmentDirections.actionHomeToDetail(meal.getIdMeal());
            navController.navigate(action);
        });
        recyclerView.setAdapter(adapter);

        // Quan sát LiveData
        viewModel.getMeals().observe(getViewLifecycleOwner(), meals -> {
            if (meals != null) {
                adapter.setMealList(meals);
            }
        });

        // Gọi API mặc định khi load
        viewModel.searchMeals("chicken");

        // SearchView: người dùng nhập nguyên liệu
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null && !query.isEmpty()) {
                    viewModel.searchMeals(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;
    }
}
