package com.example.recipeworld.data.api;

import com.example.recipeworld.data.model.Meal;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MealResponse {

    @SerializedName("meals")
    private List<Meal> meals;

    public MealResponse() {
    }

    public MealResponse(List<Meal> meals) {
        this.meals = meals;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
