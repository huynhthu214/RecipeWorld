package com.example.recipeworld.ui.favorites;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.recipeworld.data.db.FavoriteMeal;
import com.example.recipeworld.data.db.FavoriteMealDao;
import com.example.recipeworld.data.db.MealDatabase;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {

    private final FavoriteMealDao mealDao;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        mealDao = MealDatabase.getInstance(application).mealDao();
    }

    public void insertFavorite(FavoriteMeal meal) {
        new Thread(() -> mealDao.insertFavorite(meal)).start();
    }

    public void deleteFavorite(FavoriteMeal meal) {
        new Thread(() -> mealDao.deleteFavorite(meal)).start();
    }

    public LiveData<List<FavoriteMeal>> getAllFavorites() {
        return mealDao.getAllFavorites();
    }

    public LiveData<FavoriteMeal> getFavoriteById(String idMeal) {
        return mealDao.getFavoriteById(idMeal);
    }
}
