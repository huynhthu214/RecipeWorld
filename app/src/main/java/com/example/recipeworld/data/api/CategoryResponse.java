package com.example.recipeworld.data.api;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CategoryResponse {
    private List<CategoryItem> categories;
    public List<CategoryItem> getCategories() { return categories; }

    public static class CategoryItem {
        private String idCategory;
        private String strCategory;
        private String strCategoryThumb;
        private String strCategoryDescription;

        public String getStrCategory() { return strCategory; }
        public String getStrCategoryThumb() { return strCategoryThumb; }
        public String getStrCategoryDescription() { return strCategoryDescription; }
    }
}

