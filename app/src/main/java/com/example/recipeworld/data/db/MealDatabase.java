package com.example.recipeworld.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteMeal.class}, version = 1, exportSchema = false)
public abstract class MealDatabase extends RoomDatabase {

    private static final String DB_NAME = "meal_database";
    private static MealDatabase instance;

    public abstract FavoriteMealDao mealDao();

    public static synchronized MealDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            MealDatabase.class,
                            DB_NAME
                    )
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
