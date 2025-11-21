package com.example.recipeworld.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.recipeworld.R;
import com.example.recipeworld.data.db.AppDatabase;
import com.example.recipeworld.data.db.SessionManager;
import com.example.recipeworld.data.model.User;
import com.google.android.material.textfield.TextInputLayout;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etMail;
    private Button btnSaveProfile;
    private SessionManager session;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        session = new SessionManager(this);
        if (!session.isLoggedIn()) {
            finish();
            return;
        }

        CardView profileImageCard = findViewById(R.id.profileImageCard);
        TextView userNameTv = findViewById(R.id.userName);
        etMail = findViewById(R.id.etMail);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        ImageButton backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(v -> finish());

        new Thread(() -> {
            currentUser = AppDatabase.getInstance(EditProfileActivity.this)
                    .userDao().getCurrentUser();
            if (currentUser != null) {
                runOnUiThread(() -> {
                    userNameTv.setText(currentUser.email);
                    etMail.setText(currentUser.email);

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

        btnSaveProfile.setOnClickListener(v -> {
            String newMail = etMail.getText().toString().trim();
            if (newMail.isEmpty()) return;

            new Thread(() -> {
                User user = AppDatabase.getInstance(EditProfileActivity.this)
                        .userDao().getCurrentUser();
                if (user != null) {
                    user.email = newMail;
                    AppDatabase.getInstance(EditProfileActivity.this)
                            .userDao().updateUser(user);

                    runOnUiThread(EditProfileActivity.this::finish); // quay lại ProfileActivity
                }
            }).start();
        });
        TextInputLayout passwordLayout = findViewById(R.id.passwordLayout);
        passwordLayout.setEndIconOnClickListener(v -> {
            startActivity(new Intent(EditProfileActivity.this, ChangePasswordActivity.class));
        });

    }
}
