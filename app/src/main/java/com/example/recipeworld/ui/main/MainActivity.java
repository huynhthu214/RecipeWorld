package com.example.recipeworld.ui.main;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.recipeworld.R;
import com.example.recipeworld.ui.category.MealFilterFragment;
import com.example.recipeworld.ui.favorites.FavoriteFragment;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnHome, btnFavorite, btnAdd, btnGrid, btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind bottom nav buttons
        btnHome = findViewById(R.id.btn_home);
        btnFavorite = findViewById(R.id.btn_favorite);
        btnGrid = findViewById(R.id.btn_categories);
        btnProfile = findViewById(R.id.btn_profile);

        // Load mặc định HomeFragment
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment(), false);
        }

        // Button listeners
        btnHome.setOnClickListener(v -> loadFragment(new HomeFragment(), false));
        btnFavorite.setOnClickListener(v -> loadFragment(new FavoriteFragment(), false));
        btnGrid.setOnClickListener(v -> loadFragment(new MealFilterFragment(), false));// placeholder
//        btnProfile.setOnClickListener(v -> loadFragment(new ProfileFragment(), false));

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    // Nếu có fragment trong back stack → pop
                    getSupportFragmentManager().popBackStack();
                } else {
                    // Nếu không còn fragment nào → thoát app
                    finish();
                }
            }
        });

    }

    /**
     * Load Fragment vào container
     * @param fragment Fragment cần load
     * @param clearBackStack True nếu muốn xóa toàn bộ back stack (ví dụ reset app)
     */
    private void loadFragment(Fragment fragment, boolean clearBackStack) {
        if (clearBackStack) {
            getSupportFragmentManager().popBackStackImmediate(null, 0);
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        // Luôn replace fragment, không check tag
        ft.replace(R.id.main_activity_container, fragment, fragment.getClass().getName());
        ft.commit();
    }

    /**
     * Override onBackPressed để xử lý back stack chuẩn
     */
}
