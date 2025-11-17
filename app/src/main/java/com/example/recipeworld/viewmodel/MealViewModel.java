package com.example.recipeworld.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipeworld.data.model.Meal;
import com.example.recipeworld.data.repository.MealRepository;

import java.util.List;

public class MealViewModel extends ViewModel {

    private final MealRepository repository;
    private final MutableLiveData<List<Meal>> mealsLiveData;

    public MealViewModel() {
        repository = new MealRepository();
        mealsLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Meal>> getMeals() {
        return mealsLiveData;
    }

    // Load món ăn theo nguyên liệu
    public void loadMealsByIngredient(String ingredient) {
        repository.getMealsByIngredient(ingredient).observeForever(meals -> {
            mealsLiveData.setValue(meals);
        });
    }

    // Tìm món ăn theo tên
    public void searchMeals(String mealName) {
        repository.searchMeals(mealName).observeForever(meals -> {
            mealsLiveData.setValue(meals);
        });
    }
}
