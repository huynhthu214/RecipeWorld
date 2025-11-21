package com.example.recipeworld.ui.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.recipeworld.R;
import com.example.recipeworld.data.db.AppDatabase;
import com.example.recipeworld.data.db.SessionManager;
import com.example.recipeworld.data.model.User;
import com.example.recipeworld.ui.category.MealFilterFragment;
import com.example.recipeworld.ui.favorites.FavoriteFragment;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnHome, btnFavorite, btnGrid, btnProfile;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionManager(this);

        btnHome = findViewById(R.id.btn_home);
        btnFavorite = findViewById(R.id.btn_favorite);
        btnGrid = findViewById(R.id.btn_categories);
        btnProfile = findViewById(R.id.btn_profile);

        boolean openFavorites = getIntent().getBooleanExtra("open_favorites", false);

        if (savedInstanceState == null) {
            if (openFavorites && session.isLoggedIn()) {
                loadFragment(new FavoriteFragment(), false);
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
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                } else {
                    finish();
                }
            }
        });

        updateProfileButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateProfileButton();
    }

    private void loadFragment(Fragment fragment, boolean clearBackStack) {
        if (clearBackStack) {
            getSupportFragmentManager().popBackStackImmediate(null, 0);
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_activity_container, fragment, fragment.getClass().getName());
        ft.commit();
    }

    private void updateProfileButton() {
        if (session.isLoggedIn()) {
            int userId = session.getLoggedInUserId();

            new Thread(() -> {
                AppDatabase db = AppDatabase.getInstance(this);
                User user = db.userDao().getUserById(userId); // lấy đúng user hiện tại

                if (user != null && user.email != null && !user.email.isEmpty()) {
                    String firstLetter = user.email.substring(0, 1).toUpperCase();

                    runOnUiThread(() -> btnProfile.setImageDrawable(createCircularTextDrawable(firstLetter, 32)));
                } else {
                    runOnUiThread(() -> btnProfile.setImageResource(R.drawable.ic_account));
                }
            }).start();
        } else {
            btnProfile.setImageResource(R.drawable.ic_account);
        }
    }

    private BitmapDrawable createCircularTextDrawable(String text, int textSizeSp) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
        tv.setGravity(Gravity.CENTER);

        tv.setBackgroundColor(Color.TRANSPARENT);

        tv.measure(TextView.MeasureSpec.UNSPECIFIED, TextView.MeasureSpec.UNSPECIFIED);
        tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());

        Bitmap bitmap = Bitmap.createBitmap(tv.getMeasuredWidth(), tv.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        tv.draw(canvas);

        return new BitmapDrawable(getResources(), bitmap);
    }
}
