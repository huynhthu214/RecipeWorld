package com.example.recipeworld.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeworld.R;
import com.example.recipeworld.data.model.Meal;
import com.example.recipeworld.ui.adapter.MealAdapter;
import com.example.recipeworld.view.viewmodel.MainViewModel;

import java.util.List;

public class MainFragment extends Fragment {
    private MainViewModel vm;
    private MealAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle s) {
        super.onViewCreated(view, s);
        RecyclerView rv = view.findViewById(R.id.rvMeals);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MealAdapter(requireContext(), meal -> {
            // navigate to detail, pass id via safe args or bundle
            Bundle b = new Bundle();
            b.putString("meal_id", meal.getIdMeal());
            b.putString("meal_name", meal.getStrMeal());
            b.putString("meal_thumb", meal.getStrMealThumb());
            b.putString("meal_youtube", meal.getStrYoutube());
            b.putString("meal_instructions", meal.getStrInstructions());
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_detailFragment, b);
        });
        rv.setAdapter(adapter);

        vm = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        androidx.appcompat.widget.SearchView sv = view.findViewById(R.id.searchView);
        sv.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                loadByIngredient(query);
                return true;
            }
            @Override public boolean onQueryTextChange(String newText) { return false; }
        });

        // load default (e.g., "chicken")
        loadByIngredient("chicken");
    }

    private void loadByIngredient(String ingredient) {
        vm.getMealsForIngredient(ingredient).observe(getViewLifecycleOwner(), this::onMealsLoaded);
    }

    private void onMealsLoaded(List<Meal> meals) {
        if (meals == null || meals.isEmpty()) {
            Toast.makeText(getContext(), "No meals found (possibly offline and no cache).", Toast.LENGTH_SHORT).show();
        }
        adapter.setData(meals);
    }
}
