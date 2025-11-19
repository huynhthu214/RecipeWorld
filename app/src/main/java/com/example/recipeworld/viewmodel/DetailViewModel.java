package com.example.recipeworld.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.recipeworld.data.model.Meal;
import com.example.recipeworld.data.repository.MealRepository;
import com.example.recipeworld.data.db.FavoriteMeal;
import androidx.lifecycle.MediatorLiveData;
import java.util.List;

public class DetailViewModel extends AndroidViewModel {
    private final MealRepository repo;
    private MediatorLiveData<Meal> mealLiveData = new MediatorLiveData<>();

    public DetailViewModel(@NonNull Application application) {
        super(application);
        repo = new MealRepository(application.getApplicationContext());
    }

    public LiveData<Meal> getMeal() { return mealLiveData; }

    public void loadDetail(String idMeal) {
        LiveData<List<Meal>> source = repo.getMealDetail(idMeal);

        mealLiveData.addSource(source, list -> {
            if (list != null && !list.isEmpty())
                mealLiveData.setValue(list.get(0));
            else
                mealLiveData.setValue(null);

            mealLiveData.removeSource(source);
        });
    }

    public void saveFavorite(Meal m) {
        FavoriteMeal f = new FavoriteMeal(m.getIdMeal(), m.getStrMeal(), m.getThumbnail(), m.getYoutubeLink(), m.getInstructions());
        repo.insertFavorite(f);
    }

    public void deleteFavorite(FavoriteMeal fav) {
        repo.deleteFavorite(fav);
    }

    public LiveData<java.util.List<com.example.recipeworld.data.db.FavoriteMeal>> getAllFavorites() {
        return repo.getAllFavorites();
    }

    public void addFavorite(Meal m) {
        saveFavorite(m);
    }
}