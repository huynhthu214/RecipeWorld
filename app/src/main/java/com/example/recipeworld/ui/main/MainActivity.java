package com.example.recipeworld.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.recipeworld.R;
import com.example.recipeworld.data.db.SessionManager;
import com.example.recipeworld.ui.category.MealFilterFragment;
import com.example.recipeworld.ui.favorites.FavoriteFragment;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnHome, btnFavorite, btnAdd, btnGrid, btnProfile;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionManager(this);
        //session.resetLogin();

        btnHome = findViewById(R.id.btn_home);
        btnFavorite = findViewById(R.id.btn_favorite);
        btnGrid = findViewById(R.id.btn_categories);
        btnProfile = findViewById(R.id.btn_profile);
        boolean openFavorites = getIntent().getBooleanExtra("open_favorites", false);

        if (savedInstanceState == null) {
            if (openFavorites && session.isLoggedIn()) {
                loadFragment(new com.example.recipeworld.ui.favorites.FavoriteFragment(), false);
            } else {
                loadFragment(new HomeFragment(), false);
            }
        }

        btnHome.setOnClickListener(v -> loadFragment(new HomeFragment(), false));
        btnFavorite.setOnClickListener(v -> {
            if (session.isLoggedIn()) {
                loadFragment(new FavoriteFragment(), false);
            } else {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        btnGrid.setOnClickListener(v -> loadFragment(new MealFilterFragment(), false));

        btnProfile.setOnClickListener(v -> {
            if (session.isLoggedIn()) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            } else {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

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

        ft.replace(R.id.main_activity_container, fragment, fragment.getClass().getName());
        ft.commit();
    }

}
