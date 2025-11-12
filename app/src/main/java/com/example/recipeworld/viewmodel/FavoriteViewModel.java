package com.example.recipeworld.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class FavoriteViewModel extends AndroidViewModel {
    public FavoriteViewModel(@NonNull Application application) {
        super(application);
    }
//    private MealRepository repo;
//    public FavoriteViewModel(@NonNull Application application) {
//        super(application);
//        repo = new MealRepository(application.getApplicationContext());
//    }
//
//    public LiveData<List<FavoriteMeal>> getFavorites() {
//        return repo.getAllFavorites();
//    }
//
//    public void deleteFavorite(FavoriteMeal f) {
//        repo.deleteFavorite(f);
//    }
}
