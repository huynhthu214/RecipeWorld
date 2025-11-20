package com.example.recipeworld.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeworld.R;
import com.example.recipeworld.ui.adapter.RecipeAdapter;
import com.example.recipeworld.ui.detail.MealDetailFragment;
import com.example.recipeworld.viewmodel.MealViewModel;

public class MealsByCategoryFragment extends Fragment {

    private static final String ARG_CATEGORY = "category_name";
    private String categoryName;
    private MealViewModel viewModel;
    private RecipeAdapter adapter;
    private RecyclerView recyclerView;

    public static MealsByCategoryFragment newInstance(String categoryName) {
        MealsByCategoryFragment fragment = new MealsByCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, categoryName);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            categoryName = getArguments().getString(ARG_CATEGORY);
        }

        recyclerView = view.findViewById(R.id.rvAllCategories);

        // GridLayoutManager: 2 cột, scroll dọc
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new RecipeAdapter(getContext(), meal -> {
            MealDetailFragment fragment = MealDetailFragment.newInstance(meal.getIdMeal());
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.main_activity_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(MealViewModel.class);

        viewModel.getMeals().observe(getViewLifecycleOwner(), meals -> adapter.submitList(meals));

        // Load món ăn theo category
        viewModel.loadMealsByCategory(categoryName);
    }
}
