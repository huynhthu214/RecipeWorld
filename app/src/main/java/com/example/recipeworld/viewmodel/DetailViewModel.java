package com.example.recipeworld.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.recipeworld.data.model.Meal;
import com.example.recipeworld.data.repository.MealRepository;

public class DetailViewModel extends ViewModel {
    private final MealRepository repository = new MealRepository();

    public LiveData<Meal> getMealDetail(String idMeal) {
        return repository.getMealDetail(idMeal);
    }
}
