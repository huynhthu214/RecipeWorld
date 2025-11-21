package com.example.recipeworld.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.recipeworld.data.db.FavoriteMeal;
import com.example.recipeworld.data.db.FavoriteMealDao;
import com.example.recipeworld.data.db.MealDatabase;
import com.example.recipeworld.data.db.SessionManager;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {

    private final FavoriteMealDao favoriteDao;
    private final int userId;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        favoriteDao = MealDatabase.getInstance(application).mealDao();
        userId = new SessionManager(application).getLoggedInUserId();
    }

    public LiveData<List<FavoriteMeal>> getAllFavorites() {
        return favoriteDao.getAllFavoritesByUser(userId);
    }

    public void deleteFavorite(FavoriteMeal meal) {
        if (meal == null) return;
        new Thread(() -> favoriteDao.deleteFavorite(meal)).start();
    }
}
