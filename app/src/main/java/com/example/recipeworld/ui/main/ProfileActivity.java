package com.example.recipeworld.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeworld.R;
import com.example.recipeworld.data.db.SessionManager;

public class ProfileActivity extends AppCompatActivity {

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        session = new SessionManager(this);

        ImageButton backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(v -> finish());

        Button logoutBtn = findViewById(R.id.buttonLogout);
        logoutBtn.setOnClickListener(v -> {
            session.resetLogin();

            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.optionFavorites).setOnClickListener(v -> {
            if (session.isLoggedIn()) {
                // Chuyển về MainActivity và mở FavoriteFragment
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.putExtra("open_favorites", true);
                startActivity(intent);
            } else {
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
