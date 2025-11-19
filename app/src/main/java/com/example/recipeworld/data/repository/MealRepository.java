package com.example.recipeworld.data.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.recipeworld.data.db.FavoriteMeal;
import com.example.recipeworld.data.db.FavoriteMealDao;
import com.example.recipeworld.data.db.MealDatabase;
import com.example.recipeworld.data.model.Meal;
import com.example.recipeworld.data.api.MealResponse;
import com.example.recipeworld.data.api.MealApiService;
import com.example.recipeworld.data.api.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealRepository {

    private final MealApiService api;
    private final FavoriteMealDao favoriteDao;  // ← chỉ dao yêu thích
    private final Executor executor = Executors.newSingleThreadExecutor();

    public MealRepository(Context context) {
        api = RetrofitClient.getMealApiService();
        favoriteDao = MealDatabase.getInstance(context).mealDao();
    }

    // ===================== API =====================

    public LiveData<List<Meal>> getMealsByIngredient(String ingredient) {
        MutableLiveData<List<Meal>> result = new MutableLiveData<>();

        api.filterMealsByIngredient(ingredient).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null)
                    result.setValue(response.body().getMeals());
                else
                    result.setValue(null);
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                result.setValue(null);
            }
        });

        return result;
    }

    public LiveData<List<Meal>> getMealDetail(String idMeal) {
        MutableLiveData<List<Meal>> result = new MutableLiveData<>();

        api.getMealDetails(idMeal).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null)
                    result.setValue(response.body().getMeals());
                else
                    result.setValue(null);
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                result.setValue(null);
            }
        });

        return result;
    }

    // ===================== ROOM =====================

    public LiveData<List<FavoriteMeal>> getAllFavorites() {
        return favoriteDao.getAllFavorites();
    }

    public void insertFavorite(FavoriteMeal meal) {
        executor.execute(() -> favoriteDao.insertFavorite(meal));
    }

    public void deleteFavorite(FavoriteMeal meal) {
        executor.execute(() -> favoriteDao.deleteFavorite(meal));
    }

    public LiveData<FavoriteMeal> getFavoriteById(String id) {
        return favoriteDao.getFavoriteById(id);
    }

    public LiveData<List<Meal>> getMealsByCategory(String category) {
        MutableLiveData<List<Meal>> mealsLiveData = new MutableLiveData<>();

        api.getMealsByCategory(category).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mealsLiveData.setValue(response.body().getMeals());
                } else {
                    mealsLiveData.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                mealsLiveData.setValue(new ArrayList<>());
            }
        });

        return mealsLiveData;
    }

}
