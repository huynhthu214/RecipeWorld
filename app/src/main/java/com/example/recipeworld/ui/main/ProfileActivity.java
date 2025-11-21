package com.example.recipeworld.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.recipeworld.R;
import com.example.recipeworld.data.db.AppDatabase;
import com.example.recipeworld.data.db.SessionManager;
import com.example.recipeworld.data.model.User;

public class ProfileActivity extends AppCompatActivity {

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        session = new SessionManager(this);

        if (!session.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        CardView profileImageCard = findViewById(R.id.profileImageCard);
        TextView userEmailTv = findViewById(R.id.userEmail);

        ImageButton backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(v -> finish());

        findViewById(R.id.optionEditProfile).setOnClickListener(v ->
                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class))
        );

        findViewById(R.id.optionNotifications).setOnClickListener(v ->
                startActivity(new Intent(ProfileActivity.this, ChangePasswordActivity.class))
        );

        findViewById(R.id.optionFavorites).setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            intent.putExtra("open_favorites", true);
            startActivity(intent);
            finish();
        });

        Button logoutBtn = findViewById(R.id.buttonLogout);
        logoutBtn.setOnClickListener(v -> {
            session.resetLogin();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        });

        // Load user trong background thread
        new Thread(() -> {
            int userId = session.getLoggedInUserId();
            User currentUser = AppDatabase.getInstance(this).userDao().getUserById(userId);
            if (currentUser != null) {
                runOnUiThread(() -> {
                    userEmailTv.setText(currentUser.email);
                    TextView avatarTv = new TextView(this);
                    avatarTv.setText(currentUser.email.substring(0, 1).toUpperCase());
                    avatarTv.setTextColor(Color.WHITE);
                    avatarTv.setTextSize(36f);
                    avatarTv.setGravity(Gravity.CENTER);
                    avatarTv.setBackgroundResource(R.drawable.circle_bg_purple);

                    profileImageCard.removeAllViews();
                    profileImageCard.addView(avatarTv);
                });
            }
        }).start();
    }
}
