package com.example.recipeworld.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.recipeworld.R;
import com.example.recipeworld.data.db.AppDatabase;
import com.example.recipeworld.data.db.SessionManager;
import com.example.recipeworld.data.model.User;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText etOldPassword, etNewPassword;
    private Button btnChangePassword;
    private SessionManager session;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        session = new SessionManager(this);
        if (!session.isLoggedIn()) {
            finish();
            return;
        }

        CardView profileImageCard = findViewById(R.id.profileImageCard);
        TextView userNameTv = findViewById(R.id.userName);
        etOldPassword = findViewById(R.id.etOldPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        ImageButton backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(v -> finish());
        // Load user trong background thread
        new Thread(() -> {
            int userId = session.getLoggedInUserId();
            User currentUser = AppDatabase.getInstance(this).userDao().getUserById(userId);
            if (currentUser != null) {
                runOnUiThread(() -> {
                    userNameTv.setText(currentUser.email);
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

        btnChangePassword.setOnClickListener(v -> {
            String oldPass = etOldPassword.getText().toString();
            String newPass = etNewPassword.getText().toString();
            if (oldPass.isEmpty() || newPass.isEmpty()) return;

            new Thread(() -> {
                User user = AppDatabase.getInstance(ChangePasswordActivity.this)
                        .userDao().getCurrentUser();
                if (user != null) {
                    if (!oldPass.equals(user.password)) {
                        runOnUiThread(() ->
                                Toast.makeText(ChangePasswordActivity.this,
                                        "Old password incorrect",
                                        Toast.LENGTH_SHORT).show()
                        );
                        return;
                    }

                    user.password = newPass;
                    AppDatabase.getInstance(ChangePasswordActivity.this)
                            .userDao().updateUser(user);

                    runOnUiThread(() -> {
                        Toast.makeText(ChangePasswordActivity.this,
                                "Password changed",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }
            }).start();
        });
    }
}
