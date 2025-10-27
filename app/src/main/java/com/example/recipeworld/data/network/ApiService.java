package com.example.recipeworld.data.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("filter.php")
    Call<ApiResponse> getMealsByIngredient(@Query("i") String ingredient);

    @GET("lookup.php")
    Call<ApiResponse> getMealDetail(@Query("i") String idMeal);
}
