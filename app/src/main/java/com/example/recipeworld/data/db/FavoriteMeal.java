package com.example.recipeworld.data.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(
        tableName = "favorite_meals",
        primaryKeys = {"idMeal", "userId"}
)
public class FavoriteMeal {

    @NonNull
    private String idMeal;        // Mã món ăn, không được null
    private String name;          // Tên món
    private String thumbnail;     // Link hình ảnh
    private String youtube;       // Link video
    private String instructions;  // Hướng dẫn nấu
    private int userId;           // Id user, part of primary key

    private boolean isFavorite = true; // Luôn true nếu trong bảng favorite

    // Constructor đầy đủ
    public FavoriteMeal(@NonNull String idMeal, String name, String thumbnail,
                        String youtube, String instructions, int userId) {
        this.idMeal = idMeal;
        this.name = name;
        this.thumbnail = thumbnail;
        this.youtube = youtube;
        this.instructions = instructions;
        this.userId = userId;
    }

    // ===== Getter & Setter =====
    @NonNull
    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(@NonNull String idMeal) {
        this.idMeal = idMeal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    // ===== Phương thức tiện ích cho Adapter/Fragment =====
    public String getStrMeal() {
        return name;
    }

    public String getStrMealThumb() {
        return thumbnail;
    }

    public String getYoutubeLink() {
        return youtube;
    }

    public String getInstructionsText() {
        return instructions;
    }
}
