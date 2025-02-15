// UserDAO.java
package com.example.smartmart.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.smartmart.DBHelper.DatabaseHelper;
import com.example.smartmart.models.User;

public class UserDAO {
    private DatabaseHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Method to add a new user
    public long addUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", user.getEmail());
        values.put("nickName", user.getNickName());
        values.put("passWord", user.getPassWord());
        values.put("soDienThoai", user.getSoDienThoai());
        values.put("diaChi", user.getDiaChi());
        values.put("vaiTro", user.getVaiTro());

        long result = db.insert("User", null, values);
        db.close();
        return result;
    }

    // Method to get a user by email
    public User getUserByEmail(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("User", new String[]{"maUser", "email", "nickName", "passWord", "soDienThoai", "diaChi", "vaiTro"},
                "email=?", new String[]{email}, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            User user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow("maUser")),
                    cursor.getString(cursor.getColumnIndexOrThrow("passWord")),
                    cursor.getString(cursor.getColumnIndexOrThrow("nickName")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("soDienThoai")),
                    cursor.getString(cursor.getColumnIndexOrThrow("diaChi")),
                    cursor.getString(cursor.getColumnIndexOrThrow("vaiTro"))
            );
            cursor.close();
            return user;
        }
        return null;
    }

    // Method to update a user
    public int updateUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", user.getEmail());
        values.put("nickName", user.getNickName());
        values.put("passWord", user.getPassWord());
        values.put("soDienThoai", user.getSoDienThoai());
        values.put("diaChi", user.getDiaChi());
        values.put("vaiTro", user.getVaiTro());

        int result = db.update("User", values, "maUser=?", new String[]{String.valueOf(user.getMaUser())});
        db.close();
        return result;
    }

    // Method to delete a user
    public int deleteUser(int userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete("User", "maUser=?", new String[]{String.valueOf(userId)});
        db.close();
        return result;
    }
}