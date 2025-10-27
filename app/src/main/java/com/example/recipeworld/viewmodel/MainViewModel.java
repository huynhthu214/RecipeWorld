package com.example.recipeworld.view.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.recipeworld.data.model.Meal;
import com.example.recipeworld.data.repository.MealRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private final MealRepository repo;
    private MutableLiveData<List<Meal>> meals = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        repo = new MealRepository(application.getApplicationContext());
    }

    public LiveData<List<Meal>> getMeals() {
        return meals;
    }

    public void searchByIngredient(String ingredient) {
        repo.getMealsByIngredient(ingredient).observeForever(list -> meals.setValue(list));
    }
}
