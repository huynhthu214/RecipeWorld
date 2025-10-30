package com.example.recipeworld.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.recipeworld.data.model.Meal;
import com.example.recipeworld.data.network.ApiResponse;
import com.example.recipeworld.data.network.ApiService;
import com.example.recipeworld.data.network.RetrofitClient;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealRepository {
    private final ApiService apiService;

    public MealRepository() {
        apiService = RetrofitClient.getApiService();
    }

    // 1️⃣ Lấy danh sách món ăn theo nguyên liệu
    public LiveData<List<Meal>> getMealsByIngredient(String ingredient) {
        MutableLiveData<List<Meal>> data = new MutableLiveData<>();

        apiService.getMealsByIngredient(ingredient).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body().getMeals());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    // 2️⃣ Lấy chi tiết món ăn theo ID
    public LiveData<Meal> getMealDetail(String idMeal) {
        MutableLiveData<Meal> data = new MutableLiveData<>();

        apiService.getMealDetail(idMeal).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getMeals() != null) {
                    data.setValue(response.body().getMeals().get(0));
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
