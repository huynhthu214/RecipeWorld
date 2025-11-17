package com.example.recipeworld.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.recipeworld.data.db.FavoriteMeal;
import com.example.recipeworld.data.repository.MealRepository;
import com.example.recipeworld.data.model.Meal;

import java.util.List;

public class DetailViewModel extends AndroidViewModel {

    private final MealRepository repository;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        repository = new MealRepository(application.getApplicationContext());
    }

    public void addFavorite(Meal meal) {
        FavoriteMeal fav = new FavoriteMeal(
                meal.getIdMeal(),
                meal.getStrMeal(),
                meal.getThumbnail(),
                meal.getYoutubeLink(),
                meal.getInstructions()
        );
        repository.insertFavorite(fav);
    }

    public void deleteFavorite(FavoriteMeal favorite) {
        repository.deleteFavorite(favorite);
    }

    public LiveData<List<Meal>> getMealDetail(String id) {
        return repository.getMealDetail(id);
    }
}
