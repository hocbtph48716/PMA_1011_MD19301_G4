package com.example.smartmart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartmart.models.User;

public class TaiKhoan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_khoan);

        User user = (User) getIntent().getSerializableExtra("user");

        if (user != null) {
            TextView txtUserName = findViewById(R.id.name);
            TextView txtUserEmail = findViewById(R.id.email);

            txtUserName.setText(user.getNickName());
            txtUserEmail.setText(user.getEmail());
        }
        findViewById(R.id.backmain).setOnClickListener((v)->{
            Intent intent = new Intent();
            intent.putExtra("user",user);
            intent.setClass(TaiKhoan.this, MainActivity.class);
            startActivity(intent);
        });
    }
}