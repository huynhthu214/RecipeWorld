package com.example.recipeworld.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MealDao {
    @Insert
    void insert(FavoriteMeal meal);

    @Delete
    void delete(FavoriteMeal meal);

    @Query("SELECT * FROM favorite_meals ORDER BY uid DESC")
    LiveData<List<FavoriteMeal>> getAllFavorites();

    @Query("SELECT * FROM favorite_meals WHERE idMeal = :id LIMIT 1")
    FavoriteMeal getById(String id);
}
