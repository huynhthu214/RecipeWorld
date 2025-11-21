package com.example.recipeworld.data.model;

import com.google.gson.annotations.SerializedName;

public class Meal {

    @SerializedName("idMeal")
    private String idMeal;

    @SerializedName("strMeal")
    private String name;

    @SerializedName("strDrinkAlternate")
    private String drinkAlternate;

    @SerializedName("strCategory")
    private String category;

    @SerializedName("strArea")
    private String area;

    @SerializedName("strInstructions")
    private String instructions;

    @SerializedName("strMealThumb")
    private String thumbnail;

    @SerializedName("strTags")
    private String tags;

    @SerializedName("strYoutube")
    private String youtubeLink;

    // Các nguyên liệu (API có 20 cặp ingredient + measure)
    @SerializedName("strIngredient1")
    private String ingredient1;
    @SerializedName("strMeasure1")
    private String measure1;

    @SerializedName("strIngredient2")
    private String ingredient2;
    @SerializedName("strMeasure2")
    private String measure2;

    @SerializedName("strSource")
    private String source;

    // Constructor, Getter, Setter
    public Meal() {
    }

    public String getIdMeal() {
        return idMeal;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getArea() {
        return area;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getTags() {
        return tags;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public String getIngredient1() {
        return ingredient1;
    }

    public String getMeasure1() {
        return measure1;
    }

    public String getSource() {
        return source;
    }

    public void setIdMeal(String id) {
    }

    public void setStrMeal(String name) { this.name = name; }

    public void setStrMealThumb(String thumb) { this.thumbnail = thumb; }
    public void setStrYoutube(String youtube) { this.youtubeLink = youtube; }
    public void setStrInstructions(String instructions) { this.instructions = instructions;}

    public String getStrMeal() {
        return name;
    }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
    public String getStrYoutube() {
        return youtubeLink;
    }
}
