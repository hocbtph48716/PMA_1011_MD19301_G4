package com.example.smartmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartmart.Fragment.fgcholayhang;
import com.example.smartmart.Fragment.fgdalayhang;
import com.example.smartmart.models.User;

public class LichSuMuaHang extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lich_su_mua_hang);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Lịch Sử Mua Hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button btnFragment1 = findViewById(R.id.btndalayhang);
        Button btnFragment2 = findViewById(R.id.btnchogiaohang);
        User user = (User) getIntent().getSerializableExtra("user");
        int maUser = user.getMaUser();

        // Hiển thị fgdalayhang làm Fragment mặc định khi mở Activity
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, new fgdalayhang().newInstance(maUser));
            fragmentTransaction.commit();
        }

        // Xử lý sự kiện nhấn nút để chuyển Fragment
        btnFragment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, new fgdalayhang().newInstance(maUser));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btnFragment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, fgcholayhang.newInstance(maUser));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        User user = (User) getIntent().getSerializableExtra("user");
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
        return true;
    }
}