package com.example.recipeworld.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface FavoriteMealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(FavoriteMeal meal);

    @Delete
    void deleteFavorite(FavoriteMeal meal);

    @Query("SELECT * FROM favorite_meals WHERE userId = :userId")
    LiveData<List<FavoriteMeal>> getFavoritesForUser(int userId);

    @Query("SELECT * FROM favorite_meals WHERE userId = :userId AND idMeal = :mealId LIMIT 1")
    FavoriteMeal getFavoriteByMealId(int userId, String mealId);
    @Query("SELECT * FROM favorite_meals WHERE userId = :userId")
    LiveData<List<FavoriteMeal>> getAllFavoritesByUser(int userId);

    @Query("SELECT * FROM favorite_meals WHERE userId = :userId AND idMeal = :mealId LIMIT 1")
    LiveData<FavoriteMeal> getFavoriteById(int userId, String mealId);

    @Query("SELECT * FROM favorite_meals WHERE userId = :userId AND idMeal = :mealId LIMIT 1")
    LiveData<FavoriteMeal> observeFavorite(int userId, String mealId);

    @Query("SELECT * FROM favorite_meals WHERE userId = :userId AND idMeal = :mealId LIMIT 1")
    FavoriteMeal checkFavorite(int userId, String mealId);
}


