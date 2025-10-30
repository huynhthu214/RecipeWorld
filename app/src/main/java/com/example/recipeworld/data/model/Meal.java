package com.example.recipeworld.data.model;

public class Meal {
    private String idMeal;
    private String strMeal;
    private String strMealThumb;
    private String strYoutube;
    private String strInstructions;
    private String strIngredient1;
    private String strIngredient2;
    private String strIngredient3;
    private String strIngredient4;
    private String strIngredient5;
    private String strIngredient6;
    private String strIngredient7;
    private String strIngredient8;
    private String strIngredient9;
    private String strIngredient10;
    private String strIngredient11;
    private String strIngredient12;
    private String strIngredient13;
    private String strIngredient14;
    private String strIngredient15;
    private String strIngredient16;
    private String strIngredient17;
    private String strIngredient18;
    private String strIngredient19;
    private String strIngredient20;

    // Getter & Setter
    public String getIdMeal() { return idMeal; }
    public void setIdMeal(String idMeal) { this.idMeal = idMeal; }

    public String getStrMeal() { return strMeal; }
    public void setStrMeal(String strMeal) { this.strMeal = strMeal; }

    public String getStrMealThumb() { return strMealThumb; }
    public void setStrMealThumb(String strMealThumb) { this.strMealThumb = strMealThumb; }

    public String getStrYoutube() { return strYoutube; }
    public void setStrYoutube(String strYoutube) { this.strYoutube = strYoutube; }

    public String getStrInstructions() { return strInstructions; }
    public void setStrInstructions(String strInstructions) { this.strInstructions = strInstructions; }

    // Có thể thêm constructor nhanh gọn:
    public Meal(String idMeal, String strMeal, String strMealThumb) {
        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
    }
}
