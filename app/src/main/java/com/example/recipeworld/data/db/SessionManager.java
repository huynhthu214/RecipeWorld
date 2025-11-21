package com.example.recipeworld.data.db;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREFS_NAME = "session_prefs";
    private static final String KEY_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_ID = "user_id"; // mới

    private final SharedPreferences prefs;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setLoggedIn(boolean loggedIn, int userId) {
        prefs.edit()
                .putBoolean(KEY_LOGGED_IN, loggedIn)
                .putInt(KEY_USER_ID, userId) // lưu userId
                .apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_LOGGED_IN, false);
    }

    public int getLoggedInUserId() {
        return prefs.getInt(KEY_USER_ID, -1); // trả về -1 nếu chưa đăng nhập
    }

    public void resetLogin() {
        prefs.edit()
                .putBoolean(KEY_LOGGED_IN, false)
                .putInt(KEY_USER_ID, -1)
                .apply();
    }
}

