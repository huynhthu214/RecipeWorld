package com.example.recipeworld.view.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.recipeworld.data.db.FavoriteMeal;
import com.example.recipeworld.data.repository.MealRepository;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {
    private MealRepository repo;
    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        repo = new MealRepository(application.getApplicationContext());
    }

    public LiveData<List<FavoriteMeal>> getFavorites() {
        return repo.getAllFavorites();
    }

    public void deleteFavorite(FavoriteMeal f) {
        repo.deleteFavorite(f);
    }
}
