package com.example.recipeworld.data.network;

import com.example.recipeworld.data.model.Meal;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiResponse {
    @SerializedName("meals")
    private List<Meal> meals;
    public List<Meal> getMeals() { return meals; }
}
