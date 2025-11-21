package com.example.recipeworld.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.recipeworld.data.db.FavoriteMeal;
import com.example.recipeworld.data.db.FavoriteMealDao;
import com.example.recipeworld.data.db.MealDatabase;
import com.example.recipeworld.data.db.SessionManager;
import com.example.recipeworld.data.model.Meal;
import com.example.recipeworld.data.repository.MealRepository;

import androidx.lifecycle.MediatorLiveData;
import java.util.List;

public class DetailViewModel extends AndroidViewModel {

    private final MealRepository repository;
    private final FavoriteMealDao favoriteDao;
    private final MediatorLiveData<Meal> mealLiveData = new MediatorLiveData<>();

    public DetailViewModel(@NonNull Application application) {
        super(application);
        repository = new MealRepository(application.getApplicationContext());
        favoriteDao = MealDatabase.getInstance(application).mealDao();
    }

    /** LiveData món ăn chi tiết */
    public LiveData<Meal> getMeal() {
        return mealLiveData;
    }

    /** Load chi tiết món ăn từ API hoặc DB */
    public void loadDetail(String idMeal) {
        LiveData<List<Meal>> source = repository.getMealDetail(idMeal);
        mealLiveData.addSource(source, list -> {
            if (list != null && !list.isEmpty()) {
                mealLiveData.setValue(list.get(0));
            } else {
                mealLiveData.setValue(null);
            }
            mealLiveData.removeSource(source);
        });
    }

    /** Quan sát trạng thái yêu thích của món ăn */
    public LiveData<FavoriteMeal> observeFavorite(String mealId) {
        int userId = new SessionManager(getApplication()).getLoggedInUserId();
        return favoriteDao.observeFavorite(userId, mealId);
    }

    public FavoriteMeal checkFavorite(int userId, String mealId) {
        return favoriteDao.checkFavorite(userId, mealId);
    }

    /** Kiểm tra xem món ăn đã yêu thích hay chưa */
    public LiveData<FavoriteMeal> getFavoriteById(String mealId) {
        int userId = new SessionManager(getApplication()).getLoggedInUserId();
        return favoriteDao.getFavoriteById(userId, mealId);
    }

    /** Toggle món ăn yêu thích */
    public void toggleFavorite(Meal m) {
        int userId = new SessionManager(getApplication()).getLoggedInUserId();
        if (userId == -1 || m == null || m.getIdMeal() == null) return;

        new Thread(() -> {
            FavoriteMeal exist = favoriteDao.checkFavorite(userId, m.getIdMeal());
            if (exist != null) {
                // Xóa món khỏi favorite
                favoriteDao.deleteFavorite(exist);
            } else {
                // Thêm món vào favorite
                FavoriteMeal fav = new FavoriteMeal(
                        m.getIdMeal(),
                        m.getStrMeal(),
                        m.getThumbnail(),
                        m.getStrYoutube(),
                        m.getInstructions(),
                        userId
                );
                favoriteDao.insertFavorite(fav);
            }
        }).start();
    }

    /** Xóa món yêu thích trực tiếp */
    public void deleteFavorite(FavoriteMeal fav) {
        if (fav == null) return;
        new Thread(() -> favoriteDao.deleteFavorite(fav)).start();
    }
}
