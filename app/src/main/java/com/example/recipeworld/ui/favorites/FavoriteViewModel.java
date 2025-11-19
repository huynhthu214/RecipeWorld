package com.example.recipeworld.ui.favorites;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.recipeworld.data.db.FavoriteMeal;
import com.example.recipeworld.data.db.MealDao;
import com.example.recipeworld.data.db.MealDatabase;
import com.example.recipeworld.data.repository.MealRepository;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {

    private final MealDao mealDao;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        mealDao = (MealDao) MealDatabase.getInstance(application).mealDao();
    }

    public void insertFavorite(FavoriteMeal meal) {
        AsyncTask.execute(() -> mealDao.insertFavorite(meal));
    }

    public void deleteFavorite(FavoriteMeal meal) {
        AsyncTask.execute(() -> mealDao.deleteFavorite(meal));
    }

    public LiveData<Boolean> isFavorite(String idMeal) {
        return mealDao.isFavorite(idMeal);
    }

    public LiveData<List<FavoriteMeal>> getAllFavorites() {
        return mealDao.getAllFavorites();
    }
}
