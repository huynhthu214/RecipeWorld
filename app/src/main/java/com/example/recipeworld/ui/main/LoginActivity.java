package com.example.recipeworld.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeworld.R;
import com.example.recipeworld.data.db.SessionManager;

public class LoginActivity extends AppCompatActivity {

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(this);

        ImageButton backButton = findViewById(R.id.backButton);
        Button buttonLogin = findViewById(R.id.buttonLogin);

        backButton.setOnClickListener(v -> finish());

        buttonLogin.setOnClickListener(v -> {
            session.setLogin(true);  // demo login

            Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

            // Quay lại MainActivity (fragment Home mặc định)
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}
