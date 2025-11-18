package com.example.recipeworld.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView; // Sử dụng đúng lớp SearchView
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeworld.R;
import com.example.recipeworld.ui.adapter.RecipeAdapter;
import com.example.recipeworld.ui.detail.MealDetailFragment; // SỬA LỖI: Dùng Fragment UI
import com.example.recipeworld.viewmodel.MealViewModel;

public class HomeFragment extends Fragment {

    private MealViewModel viewModel;
    private RecipeAdapter adapter;
    private RecyclerView recyclerView;
    private SearchView searchView; // Biến cho SearchView

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // SỬA LỖI 1: Tham chiếu đúng ID RecyclerView (rvMeals)
        recyclerView = view.findViewById(R.id.rvMeals);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Tạo adapter với listener mở MealDetailFragment (Fragment UI)
        adapter = new RecipeAdapter(getContext(), meal -> {
            if (meal.getIdMeal() != null) {
                // SỬA LỖI 2: Dùng MealDetailFragment thay vì DetailFragment (ViewModel)
                MealDetailFragment fragment = MealDetailFragment.newInstance(meal.getIdMeal());
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.main_activity_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        recyclerView.setAdapter(adapter);

        // Sử dụng MealViewModel để load danh sách món ăn
        viewModel = new ViewModelProvider(this).get(MealViewModel.class);
        viewModel.getMeals().observe(getViewLifecycleOwner(), meals -> adapter.submitList(meals));

        // Load mặc định món "chicken"
        viewModel.loadMealsByIngredient("chicken");

        // SỬA LỖI 3: Khởi tạo và thiết lập listener cho SearchView
        searchView = view.findViewById(R.id.searchView); // Tham chiếu đúng ID

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null && !query.isEmpty()) {
                    viewModel.loadMealsByIngredient(query);
                }
                searchView.clearFocus(); // Đóng bàn phím sau khi tìm kiếm
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Bạn có thể thêm logic gợi ý tìm kiếm tại đây nếu cần
                return false;
            }
        });
    }
}