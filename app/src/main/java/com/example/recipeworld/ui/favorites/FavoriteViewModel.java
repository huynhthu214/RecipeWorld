package com.example.recipeworld.ui.favorites;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.recipeworld.data.db.FavoriteMeal;
import com.example.recipeworld.data.repository.MealRepository;

import java.util.List;

/**
 * ViewModel giúp tách logic truy xuất dữ liệu khỏi giao diện.
 * Quan sát dữ liệu từ Room thông qua LiveData.
 */
public class FavoriteViewModel extends AndroidViewModel {

    private final MealRepository repo;

    public FavoriteViewModel(@NonNull Application app) {
        super(app);
        repo = new MealRepository(app);
    }

    public LiveData<List<FavoriteMeal>> getFavorites() {
        return repo.getAllFavorites();
    }

    public void deleteFavorite(FavoriteMeal meal) {
        repo.deleteFavorite(meal);
    }
}

