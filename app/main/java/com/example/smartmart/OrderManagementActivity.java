package com.example.smartmart;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartmart.Adapter.OrderAdapter;
import com.example.smartmart.models.Order;
import com.example.smartmart.DBHelper.DatabaseHelper;
import com.example.smartmart.models.User;

import java.util.ArrayList;
import java.util.List;

public class OrderManagementActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    private List<Order> orderList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_management);

        // Cấu hình Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quản lý đơn hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        User user = (User) getIntent().getSerializableExtra("user");


        // Cài đặt RecyclerView
        recyclerView = findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderList = new ArrayList<>();
        dbHelper = new DatabaseHelper(this);


        // Load danh sách đơn hàng
        loadOrders();

        // Kiểm tra danh sách có dữ liệu hay không
        if (orderList.isEmpty()) {
            // Hiển thị thông báo nếu không có đơn hàng
            findViewById(R.id.emptyState).setVisibility(View.VISIBLE);
        } else {
            // Thiết lập adapter
            adapter = new OrderAdapter(this, orderList);
            recyclerView.setAdapter(adapter);
        }
    }

    private void loadOrders() {
        try {
            orderList = dbHelper.getAllOrders(); // Lấy danh sách đơn hàng từ DBHelper

            // Ghi log kiểm tra danh sách
            System.out.println("Danh sách đơn hàng tải về: " + orderList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}


