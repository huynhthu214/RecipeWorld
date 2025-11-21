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

    @Query("SELECT * FROM favorite_meals WHERE idMeal = :id LIMIT 1")
    LiveData<FavoriteMeal> getFavoriteById(String id);

    @Query("SELECT * FROM favorite_meals WHERE idMeal = :id LIMIT 1")
    LiveData<FavoriteMeal> getFavoriteByIdLive(String id);

    @Query("SELECT * FROM favorite_meals ORDER BY uid DESC")
    LiveData<List<FavoriteMeal>> getAllFavorites();

    @Query("SELECT * FROM favorite_meals WHERE idMeal = :id LIMIT 1")
    FavoriteMeal getFavoriteSync(String id);

}

