package com.example.smartmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartmart.Adapter.ProductManagementAdapter;
import com.example.smartmart.DAO.SanPhamDAO;

import com.example.smartmart.models.SanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class ProductManagementActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductManagementAdapter adapter;
    private List<SanPham> productList;
    private SanPhamDAO sanPhamDAO;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_management);

        // Ánh xạ FloatingActionButton
        fabAdd = findViewById(R.id.fabAdd);

        // Sự kiện click cho nút FloatingActionButton
        fabAdd.setOnClickListener(v -> {
            // Chuyển tới AddProductActivity
            Intent intent = new Intent(ProductManagementActivity.this, AddProductActivity.class);
            startActivity(intent);
        });

        // Cấu hình Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quản lý sản phẩm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable the back button

        // Cấu hình RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2); // 2 cột
        recyclerView.setLayoutManager(layoutManager);

        // Cấu hình Adapter
        sanPhamDAO = new SanPhamDAO(this);
        productList = new ArrayList<>();
        adapter = new ProductManagementAdapter(this, productList);
        recyclerView.setAdapter(adapter);

        // Load dữ liệu sản phẩm
        loadProducts();

        // Sự kiện tìm kiếm
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchProducts(newText);
                return true;
            }
        });
    }

    // Hàm tải danh sách sản phẩm
    private void loadProducts() {
        productList.clear();
        productList.addAll(sanPhamDAO.getAllProducts());
        adapter.notifyDataSetChanged();
    }

    // Hàm tìm kiếm sản phẩm
    private void searchProducts(String keyword) {
        List<SanPham> searchResults = sanPhamDAO.searchProducts(keyword);
        adapter.updateList(searchResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Handle the back button click event
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}