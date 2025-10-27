package com.example.recipeworld.data.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_meals")
public class FavoriteMeal {
    @PrimaryKey(autoGenerate = true)
    private int uid;
    private String idMeal;
    private String name;
    private String thumbnail;
    private String youtube;
    private String instructions;

    public FavoriteMeal(String idMeal, String name, String thumbnail, String youtube, String instructions) {
        this.idMeal = idMeal;
        this.name = name;
        this.thumbnail = thumbnail;
        this.youtube = youtube;
        this.instructions = instructions;
    }

    public int getUid() { return uid; }
    public void setUid(int uid) { this.uid = uid; }

    public String getIdMeal() { return idMeal; }
    public String getName() { return name; }
    public String getThumbnail() { return thumbnail; }
    public String getYoutube() { return youtube; }
    public String getInstructions() { return instructions; }
}
