package com.example.recipeworld.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeworld.R;
import com.example.recipeworld.data.api.CategoryResponse;
import com.example.recipeworld.data.api.RetrofitClient;

import java.util.List;

public class AllCategoriesFragment extends Fragment {

    private RecyclerView recyclerView;
    private CategoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rvAllCategories);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new CategoryAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(category -> {
            MealsByCategoryFragment fragment = MealsByCategoryFragment.newInstance(category.getStrCategory());
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.main_activity_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        // Load tất cả category
        RetrofitClient.getMealApiService().getCategories().enqueue(new retrofit2.Callback<CategoryResponse>() {
            @Override
            public void onResponse(retrofit2.Call<CategoryResponse> call, retrofit2.Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CategoryResponse.CategoryItem> allCategories = response.body().getCategories();
                    adapter.setCategories(allCategories, false); // show tất cả
                }
            }

            @Override
            public void onFailure(retrofit2.Call<CategoryResponse> call, Throwable t) {
            }
        });
    }
}
