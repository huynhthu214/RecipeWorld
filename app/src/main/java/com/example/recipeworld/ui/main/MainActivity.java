package com.example.recipeworld.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.recipeworld.R;
import com.example.recipeworld.ui.favorites.FavoriteFragment;
import com.example.recipeworld.ui.main.HomeFragment;
//import com.example.recipeworld.ui.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnHome, btnFavorite, btnAdd, btnGrid, btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Gán các nút bottom nav
        btnHome = findViewById(R.id.btn_home);
        btnFavorite = findViewById(R.id.btn_favorite);
        btnGrid = findViewById(R.id.btn_categories);
        btnProfile = findViewById(R.id.btn_profile);

        // Mặc định load HomeFragment
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }

        // Xử lý click từng button
        btnHome.setOnClickListener(v -> loadFragment(new HomeFragment()));
        btnFavorite.setOnClickListener(v -> loadFragment(new FavoriteFragment()));
        btnGrid.setOnClickListener(v -> loadFragment(new HomeFragment())); // nếu có Fragment Grid riêng
//        btnProfile.setOnClickListener(v -> loadFragment(new ProfileFragment()));
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_activity_container, fragment); // id container trong activity_main.xml
        ft.commit();
    }
}
