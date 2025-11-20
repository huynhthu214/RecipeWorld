package com.example.recipeworld.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.recipeworld.data.model.Meal;
import com.example.recipeworld.data.repository.MealRepository;

import java.util.List;

public class MealViewModel extends AndroidViewModel {

    private final MealRepository repository;
    private final MediatorLiveData<List<Meal>> mealsLiveData;
    public MealViewModel(@NonNull Application application) {
        super(application);
        repository = new MealRepository(application.getApplicationContext());
        mealsLiveData = new MediatorLiveData<>();
    }

    public LiveData<List<Meal>> getMeals() {
        return mealsLiveData;
    }
    public void loadMealsByIngredient(String ingredient) {
        LiveData<List<Meal>> source = repository.getMealsByIngredient(ingredient);

        mealsLiveData.addSource(source, list -> {
            mealsLiveData.setValue(list);
            mealsLiveData.removeSource(source);
        });
    }

    public void loadMealsByCategory(String category) {
        LiveData<List<Meal>> source = repository.getMealsByCategory(category);
        mealsLiveData.addSource(source, list -> {
            mealsLiveData.setValue(list);
            mealsLiveData.removeSource(source);
        });
    }

    // Tìm món ăn theo tên
    public void searchMeals(String mealName) {
        repository.getMealsByIngredient(mealName).observeForever(meals -> {
            mealsLiveData.setValue(meals);
        });
    }
}