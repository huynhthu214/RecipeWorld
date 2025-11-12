package com.example.recipeworld.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.recipeworld.data.api.MealApiService;
import com.example.recipeworld.data.api.RetrofitClient;
import com.example.recipeworld.data.model.Meal;
import com.example.recipeworld.data.api.MealResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealRepository {

    private final MealApiService apiService;

    public MealRepository() {
        apiService = RetrofitClient.getMealApiService();
    }

    // Lấy danh sách món theo nguyên liệu
    public LiveData<List<Meal>> getMealsByIngredient(String ingredient) {
        MutableLiveData<List<Meal>> mealsLiveData = new MutableLiveData<>();

        apiService.filterMealsByIngredient(ingredient).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mealsLiveData.setValue(response.body().getMeals());
                } else {
                    mealsLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                mealsLiveData.setValue(null);
            }
        });

        return mealsLiveData;
    }

    // Lấy chi tiết món theo idMeal
    public LiveData<Meal> getMealDetail(String mealId) {
        MutableLiveData<Meal> mealLiveData = new MutableLiveData<>();

        apiService.getMealDetails(mealId).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().getMeals().isEmpty()) {
                    mealLiveData.setValue(response.body().getMeals().get(0));
                } else {
                    mealLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                mealLiveData.setValue(null);
            }
        });

        return mealLiveData;
    }
}
