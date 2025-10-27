package com.example.recipeworld.data.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.recipeworld.data.db.AppDatabase;
import com.example.recipeworld.data.db.FavoriteMeal;
import com.example.recipeworld.data.model.Meal;
import com.example.recipeworld.data.network.ApiResponse;
import com.example.recipeworld.data.network.ApiService;
import com.example.recipeworld.data.network.RetrofitClient;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealRepository {
    private final ApiService api;
    private final AppDatabase db;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public MealRepository(Context context) {
        api = RetrofitClient.getApiService();
        db = AppDatabase.getInstance(context);
    }

    public LiveData<List<Meal>> getMealsByIngredient(String ingredient) {
        MutableLiveData<List<Meal>> result = new MutableLiveData<>();
        api.getMealsByIngredient(ingredient).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) result.setValue(response.body().getMeals());
                else result.setValue(null);
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                result.setValue(null);
            }
        });
        return result;
    }

    public LiveData<List<Meal>> getMealDetail(String idMeal) {
        MutableLiveData<List<Meal>> result = new MutableLiveData<>();
        api.getMealDetail(idMeal).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) result.setValue(response.body().getMeals());
                else result.setValue(null);
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                result.setValue(null);
            }
        });
        return result;
    }

    // Room operations
    public LiveData<List<FavoriteMeal>> getAllFavorites() {
        return db.mealDao().getAllFavorites();
    }

    public void insertFavorite(FavoriteMeal meal) {
        executor.execute(() -> db.mealDao().insert(meal));
    }

    public void deleteFavorite(FavoriteMeal meal) {
        executor.execute(() -> db.mealDao().delete(meal));
    }

    public FavoriteMeal getFavoriteById(String id) {
        return db.mealDao().getById(id);
    }
}
