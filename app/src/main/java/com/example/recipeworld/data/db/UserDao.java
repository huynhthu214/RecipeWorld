package com.example.recipeworld.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.recipeworld.data.model.User;

@Dao
public interface UserDao {

    @Insert
    long insertUser(User user);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    @Query("SELECT * FROM users LIMIT 1")
    User getCurrentUser();

    @Update
    void updateUser(User user);

    @Query("DELETE FROM users")
    void clearUsers();
    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    User getUserById(int id);
}
