package com.example.recipeworld.data.model;

import com.google.gson.annotations.SerializedName;

public class Ingredient {

    @SerializedName("idIngredient")
    private String idIngredient;

    @SerializedName("strIngredient")
    private String name;

    @SerializedName("strDescription")
    private String description;

    @SerializedName("strType")
    private String type;

    // Getter, Setter
    public String getIdIngredient() {
        return idIngredient;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }
}
