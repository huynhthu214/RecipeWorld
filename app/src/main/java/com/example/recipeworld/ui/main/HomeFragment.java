package com.example.recipeworld.ui.main;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
import com.example.recipeworld.data.api.MealApiService;
import com.example.recipeworld.data.api.RetrofitClient;
import com.example.recipeworld.data.db.SessionManager;
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
    private RecyclerView recyclerView;
    private SearchView searchView;
    private RecyclerView rvCategories;
    private CategoryAdapter categoryAdapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rvMeals);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new RecipeAdapter(getContext(), new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Meal meal) {
                if (meal.getIdMeal() != null) {
                    MealDetailFragment fragment = MealDetailFragment.newInstance(meal.getIdMeal());
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.main_activity_container, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
        rvCategories = view.findViewById(R.id.rvCategories);
        rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 3)); // 3 cột

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

        RetrofitClient.getMealApiService().getCategories().enqueue(new retrofit2.Callback<CategoryResponse>() {
            @Override
            public void onResponse(retrofit2.Call<CategoryResponse> call, retrofit2.Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CategoryResponse.CategoryItem> allCategories = response.body().getCategories();
                    categoryAdapter.setCategories(allCategories, true); // addMoreItem = true
                }
            }

            @Override
            public void onFailure(retrofit2.Call<CategoryResponse> call, Throwable t) {
            }
        });


        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(MealViewModel.class);

        viewModel.getMeals().observe(getViewLifecycleOwner(), meals -> {
            if (meals != null) adapter.submitList(meals);
        });

        // Load mặc định món ăn "chicken"
        viewModel.loadMealsByIngredient("chicken");

        searchView = view.findViewById(R.id.searchView);

        // Xử lý realtime + submit
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

