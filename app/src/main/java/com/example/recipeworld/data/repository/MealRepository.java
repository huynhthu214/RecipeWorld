package com.example.recipeworld.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.recipeworld.data.api.MealApiService;
import com.example.recipeworld.data.api.MealResponse;
import com.example.recipeworld.data.api.RetrofitClient;
import com.example.recipeworld.data.db.FavoriteMeal;
import com.example.recipeworld.data.db.FavoriteMealDao;
import com.example.recipeworld.data.db.MealDatabase;
import com.example.recipeworld.data.db.SessionManager;
import com.example.recipeworld.data.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealRepository {

    private final MealApiService api;
    private final FavoriteMealDao favoriteDao;
    private final SessionManager session;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public MealRepository(Context context) {
        api = RetrofitClient.getMealApiService();
        favoriteDao = MealDatabase.getInstance(context).mealDao();
        session = new SessionManager(context);
    }

    // ================= ROOM =================

    // Lấy tất cả favorite của user đang đăng nhập
    public LiveData<List<FavoriteMeal>> getAllFavorites() {
        int userId = session.getLoggedInUserId();
        return favoriteDao.getFavoritesForUser(userId);
    }

    // Lấy favorite theo mealId và userId
    public LiveData<FavoriteMeal> getFavoriteById(String id) {
        int userId = session.getLoggedInUserId();
        return favoriteDao.getFavoriteById(userId, id);
    }

    public void insertFavorite(FavoriteMeal meal) {
        meal.setUserId(session.getLoggedInUserId()); // đảm bảo lưu userId
        executor.execute(() -> favoriteDao.insertFavorite(meal));
    }

    public void deleteFavorite(FavoriteMeal meal) {
        executor.execute(() -> favoriteDao.deleteFavorite(meal));
    }

    // ================= API =================

    public LiveData<List<Meal>> getMealsByIngredient(String ingredient) {
        MutableLiveData<List<Meal>> result = new MutableLiveData<>();

        api.filterMealsByIngredient(ingredient).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null)
                    result.setValue(response.body().getMeals());
                else
                    result.setValue(new ArrayList<>());
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                result.setValue(new ArrayList<>());
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
                    result.setValue(new ArrayList<>());
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                result.setValue(new ArrayList<>());
            }
        });

        return result;
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
