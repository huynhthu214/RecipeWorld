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
        btnGrid = findViewById(R.id.btn_categories); // ID trong XML là btn_categories
        btnProfile = findViewById(R.id.btn_profile);

        // Mặc định load HomeFragment
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment(), false); // false = không cần thêm vào back stack
        }

        // Xử lý click từng button
        // Thêm logic xóa back stack khi chuyển tab chính để tránh lỗi
        btnHome.setOnClickListener(v -> loadFragment(new HomeFragment(), true));
        btnFavorite.setOnClickListener(v -> loadFragment(new FavoriteFragment(), true));
        btnGrid.setOnClickListener(v -> loadFragment(new HomeFragment(), true)); // Đang dùng HomeFragment làm placeholder
//        btnProfile.setOnClickListener(v -> loadFragment(new ProfileFragment(), true));
    }

    /**
     * Tải Fragment vào container chính.
     * @param fragment Fragment cần hiển thị.
     * @param clearBackStack True nếu đây là tab chính và cần xóa back stack (để tránh lỗi back)
     */
    private void loadFragment(Fragment fragment, boolean clearBackStack) {
        // Xóa back stack khi chuyển đổi giữa các tab chính (Home, Favorite)
        if (clearBackStack) {
            getSupportFragmentManager().popBackStackImmediate(null, 0);
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        // Kiểm tra xem Fragment đó có đang được hiển thị không
        if (getSupportFragmentManager().findFragmentByTag(fragment.getClass().getName()) == null) {
            // Thay thế fragment
            ft.replace(R.id.main_activity_container, fragment, fragment.getClass().getName());
            ft.commit();
        }
    }
}