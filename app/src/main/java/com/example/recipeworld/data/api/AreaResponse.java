package com.example.recipeworld.data.api;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AreaResponse {

    @SerializedName("meals")
    private List<Area> meals;

    public List<Area> getMeals() {
        return meals;
    }

    public static class Area {
        @SerializedName("strArea")
        private String strArea;

        public String getStrArea() {
            return strArea;
        }
    }
}
