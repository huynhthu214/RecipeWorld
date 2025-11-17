package com.example.recipeworld.data.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApiService {
    @GET("filter.php")
    Call<MealResponse> filterMealsByIngredient(@Query("i") String ingredient);
    @GET("lookup.php")
    Call<MealResponse> getMealDetails(@Query("i") String mealId);
}
