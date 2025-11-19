package com.example.recipeworld.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeworld.R;
import com.example.recipeworld.data.api.CategoryResponse;
import com.example.recipeworld.data.api.RetrofitClient;
import com.example.recipeworld.ui.category.MealsByCategoryFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealFilterFragment extends Fragment {

    private RecyclerView rvCategory;
    private CategoryAdapter categoryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_filter, container, false);

        rvCategory = view.findViewById(R.id.rvMealsByCategory);
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        categoryAdapter = new CategoryAdapter();
        rvCategory.setAdapter(categoryAdapter);

        categoryAdapter.setOnItemClickListener(category -> {
            // Khi nhấn vào category, mở fragment MealsByCategoryFragment
            MealsByCategoryFragment fragment = MealsByCategoryFragment.newInstance(category.getStrCategory());
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.main_activity_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        loadCategories();

        return view;
    }

    private void loadCategories() {
        RetrofitClient.getMealApiService().getCategories()
                .enqueue(new Callback<CategoryResponse>() {
                    @Override
                    public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<CategoryResponse.CategoryItem> categories = response.body().getCategories();
                            categoryAdapter.setCategories(categories);
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoryResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Lỗi tải danh mục: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
