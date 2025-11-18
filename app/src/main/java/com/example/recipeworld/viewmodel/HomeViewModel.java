package com.example.recipeworld.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.recipeworld.data.model.Meal;
import com.example.recipeworld.data.repository.MealRepository;

import java.util.ArrayList;
import java.util.List;

// Sửa lỗi: Kế thừa từ AndroidViewModel để truy cập Context
public class HomeViewModel extends AndroidViewModel {

    private final MealRepository repository;
    private final MutableLiveData<List<Meal>> mealsLiveData = new MutableLiveData<>();

    // Sửa lỗi: Constructor phải nhận Application
    public HomeViewModel(@NonNull Application application) {
        super(application);
        // Khởi tạo Repository bằng Application Context
        repository = new MealRepository(application.getApplicationContext());

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
            // Trường hợp này không nên xảy ra sau khi fix constructor
            mealsLiveData.setValue(filterMockData(ingredient));
        }
    }

    private List<Meal> filterMockData(String ingredient) {
        List<Meal> filtered = new ArrayList<>();
        for (Meal meal : getAllMockData()) {
            if (meal.getName().toLowerCase().contains(ingredient.toLowerCase()) || // Dùng getName()
                    (meal.getInstructions() != null && // Dùng getInstructions()
                            meal.getInstructions().toLowerCase().contains(ingredient.toLowerCase()))) {
                filtered.add(meal);
            }
        }
        return filtered;
    }

    private List<Meal> getAllMockData() {
        List<Meal> mockList = new ArrayList<>();

        // SỬA LỖI: Thay thế constructor 3 đối số bằng constructor mặc định và sử dụng setters.
        // Giả định rằng Meal.java có các setter tương ứng: setIdMeal, setStrMeal, setStrMealThumb.

        Meal meal1 = new Meal();
        meal1.setIdMeal("1");
        meal1.setStrMeal("Chicken Curry");
        meal1.setStrMealThumb("https://www.themealdb.com/images/media/meals/1529444830.jpg");
        meal1.setStrInstructions("Delicious chicken curry with spices");
        meal1.setStrYoutube("https://www.youtube.com/watch?v=IO0issT0Rmc");

        Meal meal2 = new Meal();
        meal2.setIdMeal("2");
        meal2.setStrMeal("Beef Steak");
        meal2.setStrMealThumb("https://www.themealdb.com/images/media/meals/sytuqu1511553755.jpg");
        meal2.setStrInstructions("Juicy beef steak with garlic butter");
        meal2.setStrYoutube("https://www.youtube.com/watch?v=3AAdKl1UYZs");

        Meal meal3 = new Meal();
        meal3.setIdMeal("3");
        meal3.setStrMeal("Vegetable Salad");
        meal3.setStrMealThumb("https://www.themealdb.com/images/media/meals/wvpsxx1468256321.jpg");
        meal3.setStrInstructions("Fresh vegetable salad with lemon dressing");
        meal3.setStrYoutube("https://www.youtube.com/watch?v=92cXC_2Xj2c");

        mockList.add(meal1);
        mockList.add(meal2);
        mockList.add(meal3);

        return mockList;
    }
}