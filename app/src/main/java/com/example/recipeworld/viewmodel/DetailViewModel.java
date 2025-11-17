package com.example.recipeworld.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.recipeworld.data.model.Meal;
import com.example.recipeworld.data.repository.MealRepository;
import com.example.recipeworld.data.db.FavoriteMeal;

import java.util.List;

public class DetailViewModel extends AndroidViewModel {
    private final MealRepository repo;
    private MutableLiveData<Meal> meal = new MutableLiveData<>();

    public DetailViewModel(@NonNull Application application) {
        super(application);
        repo = new MealRepository(application.getApplicationContext());
    }

    public LiveData<Meal> getMeal() { return meal; }

    public void loadDetail(String idMeal) {
        repo.getMealDetail(idMeal).observeForever(list -> {
            if (list != null && !list.isEmpty()) meal.setValue(list.get(0));
            else meal.setValue(null);
        });
    }

    public void saveFavorite(Meal m) {
        FavoriteMeal f = new FavoriteMeal(m.getIdMeal(), m.getStrMeal(), m.getStrMealThumb(), m.getStrYoutube(), m.getStrInstructions());
        repo.insertFavorite(f);
    }

    public void deleteFavorite(FavoriteMeal fav) {
        repo.deleteFavorite(fav);
    }

    public LiveData<java.util.List<com.example.recipeworld.data.db.FavoriteMeal>> getAllFavorites() {
        return repo.getAllFavorites();
    }

    public void addFavorite(Meal m) {
    }
}
