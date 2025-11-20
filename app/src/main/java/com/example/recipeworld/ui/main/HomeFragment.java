package com.example.recipeworld.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeworld.R;
import com.example.recipeworld.data.api.CategoryResponse;
import com.example.recipeworld.data.api.RetrofitClient;
import com.example.recipeworld.data.model.Meal;
import com.example.recipeworld.ui.adapter.RecipeAdapter;
import com.example.recipeworld.ui.category.AllCategoriesFragment;
import com.example.recipeworld.ui.category.CategoryAdapter;
import com.example.recipeworld.ui.category.MealsByCategoryFragment;
import com.example.recipeworld.ui.detail.MealDetailFragment;
import com.example.recipeworld.viewmodel.MealViewModel;

import java.util.List;

public class HomeFragment extends Fragment {

    private MealViewModel viewModel;
    private RecipeAdapter adapter;
    private RecyclerView rvMeals;
    private RecyclerView rvCategories;
    private SearchView searchView;
    private CategoryAdapter categoryAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMeals = view.findViewById(R.id.rvMeals);
        rvMeals.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMeals.setNestedScrollingEnabled(false); // Disable nested scroll

        adapter = new RecipeAdapter(getContext(), meal -> {
            if (meal.getIdMeal() != null) {
                MealDetailFragment fragment = MealDetailFragment.newInstance(meal.getIdMeal());
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.main_activity_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        rvMeals.setAdapter(adapter);

        rvCategories = view.findViewById(R.id.rvCategories);
        rvCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvCategories.setNestedScrollingEnabled(false); // Disable nested scroll

        categoryAdapter = new CategoryAdapter();
        rvCategories.setAdapter(categoryAdapter);

        categoryAdapter.setOnItemClickListener(category -> {
            if ("...".equals(category.getStrCategory())) {
                AllCategoriesFragment fragment = new AllCategoriesFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.main_activity_container, fragment)
                        .addToBackStack(null)
                        .commit();
            } else {
                MealsByCategoryFragment fragment = MealsByCategoryFragment.newInstance(category.getStrCategory());
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.main_activity_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Load categories từ API
        RetrofitClient.getMealApiService().getCategories().enqueue(new retrofit2.Callback<CategoryResponse>() {
            @Override
            public void onResponse(retrofit2.Call<CategoryResponse> call, retrofit2.Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CategoryResponse.CategoryItem> allCategories = response.body().getCategories();
                    categoryAdapter.setCategories(allCategories, true);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<CategoryResponse> call, Throwable t) {}
        });

        viewModel = new ViewModelProvider(this).get(MealViewModel.class);

        viewModel.getMeals().observe(getViewLifecycleOwner(), meals -> {
            if (meals != null) adapter.submitList(meals);
        });

        // Load mặc định món "chicken"
        viewModel.loadMealsByIngredient("chicken");

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null && !query.isEmpty()) {
                    viewModel.loadMealsByIngredient(query);
                }
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && !newText.isEmpty()) {
                    viewModel.loadMealsByIngredient(newText);
                }
                return true;
            }
        });
    }
}
