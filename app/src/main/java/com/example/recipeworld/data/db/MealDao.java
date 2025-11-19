package com.example.recipeworld.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(FavoriteMeal meal);

    @Delete
    void deleteFavorite(FavoriteMeal meal);

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_meals WHERE idMeal = :id)")
    LiveData<Boolean> isFavorite(String id);

    @Query("SELECT * FROM favorite_meals")
    LiveData<List<FavoriteMeal>> getAllFavorites();
}
