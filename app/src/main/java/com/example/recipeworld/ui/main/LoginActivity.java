package com.example.recipeworld.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeworld.R;
import com.example.recipeworld.data.db.AppDatabase;
import com.example.recipeworld.data.db.SessionManager;
import com.example.recipeworld.data.model.User;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(this);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        ImageButton backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> finish());

        buttonLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ email và mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> loginUser(email, password)).start();
        });
    }

    private void loginUser(String email, String password) {
        AppDatabase db = AppDatabase.getInstance(this);
        User user = db.userDao().getUserByEmail(email);

        if (user == null) {
            // User chưa có → tạo mới
            user = new User(email, password);
            long id = db.userDao().insertUser(user);
            user.id = (int) id;
        } else {
            // User tồn tại → kiểm tra mật khẩu
            if (!user.password.equals(password)) {
                runOnUiThread(() -> Toast.makeText(this, "Sai mật khẩu", Toast.LENGTH_SHORT).show());
                return;
            }
        }

        // Lưu trạng thái đăng nhập
        session.setLoggedIn(true, user.id);

        runOnUiThread(() -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}
