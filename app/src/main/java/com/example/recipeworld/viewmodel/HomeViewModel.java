package com.example.recipeworld.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipeworld.data.model.Meal;
import com.example.recipeworld.data.repository.MealRepository;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MealRepository repository;
    private final MutableLiveData<List<Meal>> mealsLiveData = new MutableLiveData<>();

    public HomeViewModel() {
        repository = new MealRepository();
        // Có thể load mặc định mock data
        mealsLiveData.setValue(getAllMockData());
    }

    // LiveData để fragment quan sát
    public LiveData<List<Meal>> getMealsLiveData() {
        return mealsLiveData;
    }

    // Tìm kiếm món ăn theo nguyên liệu (API + fallback mock)
    public void searchMeals(String ingredient) {
        if (repository != null) {
            repository.getMealsByIngredient(ingredient).observeForever(meals -> {
                if (meals != null && !meals.isEmpty()) {
                    mealsLiveData.setValue(meals);
                } else {
                    mealsLiveData.setValue(filterMockData(ingredient));
                }
            });
        } else {
            mealsLiveData.setValue(filterMockData(ingredient));
        }
    }

    private List<Meal> filterMockData(String ingredient) {
        List<Meal> filtered = new ArrayList<>();
        for (Meal meal : getAllMockData()) {
            if (meal.getStrMeal().toLowerCase().contains(ingredient.toLowerCase()) ||
                    (meal.getStrInstructions() != null &&
                            meal.getStrInstructions().toLowerCase().contains(ingredient.toLowerCase()))) {
                filtered.add(meal);
            }
        }
        return filtered;
    }

    private List<Meal> getAllMockData() {
        List<Meal> mockList = new ArrayList<>();

        Meal meal1 = new Meal("1", "Chicken Curry",
                "https://www.themealdb.com/images/media/meals/1529444830.jpg");
        meal1.setStrInstructions("Delicious chicken curry with spices");
        meal1.setStrYoutube("https://www.youtube.com/watch?v=IO0issT0Rmc");

        Meal meal2 = new Meal("2", "Beef Steak",
                "https://www.themealdb.com/images/media/meals/sytuqu1511553755.jpg");
        meal2.setStrInstructions("Juicy beef steak with garlic butter");
        meal2.setStrYoutube("https://www.youtube.com/watch?v=3AAdKl1UYZs");

        Meal meal3 = new Meal("3", "Vegetable Salad",
                "https://www.themealdb.com/images/media/meals/wvpsxx1468256321.jpg");
        meal3.setStrInstructions("Fresh vegetable salad with lemon dressing");
        meal3.setStrYoutube("https://www.youtube.com/watch?v=92cXC_2Xj2c");

        mockList.add(meal1);
        mockList.add(meal2);
        mockList.add(meal3);

        return mockList;
    }
}
