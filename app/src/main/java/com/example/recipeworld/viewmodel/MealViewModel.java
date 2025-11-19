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

// Sửa lỗi: Kế thừa từ AndroidViewModel để truy cập Application/Context
public class MealViewModel extends AndroidViewModel {

    private final MealRepository repository;
    private final MediatorLiveData<List<Meal>> mealsLiveData;


    // Sửa lỗi: Constructor phải nhận Application
    public MealViewModel(@NonNull Application application) {
        super(application);
        // Truyền Context (là application.getApplicationContext()) vào MealRepository
        repository = new MealRepository(application.getApplicationContext());
        mealsLiveData = new MediatorLiveData<>();
    }

    public LiveData<List<Meal>> getMeals() {
        return mealsLiveData;
    }

    // Load món ăn theo nguyên liệu
//    public void loadMealsByIngredient(String ingredient) {
//        // Lưu ý: .observeForever() có thể gây rò rỉ bộ nhớ.
//        // Trong ViewModel, tốt hơn là nên dùng MediatorLiveData để quản lý LiveData từ Repository.
//        repository.getMealsByIngredient(ingredient).observeForever(meals -> {
//            mealsLiveData.setValue(meals);
//        });
//    }

    public void loadMealsByIngredient(String ingredient) {
        LiveData<List<Meal>> source = repository.getMealsByIngredient(ingredient);

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