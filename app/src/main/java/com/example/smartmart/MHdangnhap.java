// MHdangnhap.java
package com.example.smartmart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartmart.DAO.UserDAO;
import com.example.smartmart.models.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MHdangnhap extends AppCompatActivity {

    String TAG = "zzzzzzzz";

    // FireBase
    FirebaseAuth auth;
    Button btnDangNhap, btnDangKy;
    TextView btnQuenMatKhau;
    TextInputEditText edtTaiKhoan, edtMatKhau;
    CheckBox chkGhiNho;
    // SharedPreferences
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mhdangnhap);
        userDAO = new UserDAO(this);

        FirebaseApp.initializeApp(this);
        // Mapping
        auth = FirebaseAuth.getInstance();
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangKy = findViewById(R.id.btnDangKy);
        btnQuenMatKhau = findViewById(R.id.btnQuenMatKhau);
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        chkGhiNho = findViewById(R.id.chkGhiNho);
        pref = getSharedPreferences("user_info", MODE_PRIVATE);
        editor = pref.edit();

        // Load saved email, password
        loadSavedCredentials();

        // Handle login button click
        btnDangNhap.setOnClickListener(v -> {
            String email = edtTaiKhoan.getText().toString();
            String password = edtMatKhau.getText().toString();
            if (!email.equals("") && !password.equals("")) {
                loginUser(email, password);
            } else {
                Toast.makeText(MHdangnhap.this, "Không để trống email hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
            }
        });

        // Navigate to registration screen
        btnDangKy.setOnClickListener(v -> {
            Intent intent = new Intent(this, MHdangky.class);
            startActivity(intent);
        });

        btnQuenMatKhau.setOnClickListener(v -> {
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = auth.getCurrentUser();

                        if (chkGhiNho.isChecked()) {
                            editor.putString("email", email);
                            editor.putString("password", password);
                            editor.putBoolean("remember", true);
                        } else {
                            editor.remove("email");
                            editor.remove("password");
                            editor.putBoolean("remember", false);
                        }
                        editor.apply();

                        // Retrieve user information from the database
                        User dbUser = userDAO.getUserByEmail(email);
                        if (dbUser != null) {
                            Intent intent = new Intent(this, MainActivity.class);
                            intent.putExtra("user", dbUser);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MHdangnhap.this, "User not found in database.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(MHdangnhap.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadSavedCredentials() {
        String savedEmail = pref.getString("email", "");
        String savedPassword = pref.getString("password", "");
        boolean isRemembered = pref.getBoolean("remember", false);

        if (isRemembered) {
            edtTaiKhoan.setText(savedEmail);
            edtMatKhau.setText(savedPassword);
            chkGhiNho.setChecked(isRemembered);
        }
    }
}