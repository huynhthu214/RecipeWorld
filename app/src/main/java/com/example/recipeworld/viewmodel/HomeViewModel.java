package com.example.recipeworld.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.recipeworld.data.model.Meal;
import com.example.recipeworld.data.repository.MealRepository;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private final MealRepository repository = new MealRepository();
    private MutableLiveData<List<Meal>> meals = new MutableLiveData<>();

    public void searchMeals(String ingredient){
        repository.getMealsByIngredient(ingredient).observeForever(result ->{
            meals.setValue(result);
        });
    }
    public LiveData<List<Meal>> getMeals() {
        return meals;
    }
}
