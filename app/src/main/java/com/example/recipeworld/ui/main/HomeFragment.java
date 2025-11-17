package com.example.recipeworld.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.recipeworld.R;
import com.example.recipeworld.ui.adapter.RecipeAdapter;
import com.example.recipeworld.ui.detail.DetailFragment;
import com.example.recipeworld.viewmodel.MealViewModel;

public class HomeFragment extends Fragment {

    private MealViewModel viewModel;
    private RecipeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.rv_trending_recipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Tạo adapter với listener mở DetailFragment theo idMeal
        adapter = new RecipeAdapter(getContext(), meal -> {
            DetailFragment fragment = DetailFragment.newInstance(meal.getIdMeal());
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.main_activity_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        recyclerView.setAdapter(adapter);

        // Sử dụng MealViewModel để load danh sách món ăn
        viewModel = new ViewModelProvider(this).get(MealViewModel.class);
        viewModel.getMeals().observe(getViewLifecycleOwner(), meals -> adapter.submitList(meals));

        // Load mặc định món "chicken"
        viewModel.loadMealsByIngredient("chicken");

        // Search theo nguyên liệu
        EditText searchEdit = view.findViewById(R.id.edit_search);
        searchEdit.setOnEditorActionListener((v, actionId, event) -> {
            String query = searchEdit.getText().toString().trim();
            if (!query.isEmpty()) {
                viewModel.loadMealsByIngredient(query);
            }
            return true;
        });
    }
}
