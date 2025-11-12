package com.example.recipeworld.data.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApiService {

    // Lấy danh sách món ăn theo nguyên liệu
    // Ví dụ: filter.php?i=chicken
    @GET("filter.php")
    Call<MealResponse> filterMealsByIngredient(@Query("i") String ingredient);

    // Lấy chi tiết món ăn theo id
    // Ví dụ: lookup.php?i=52772
    @GET("lookup.php")
    Call<MealResponse> getMealDetails(@Query("i") String mealId);
}
