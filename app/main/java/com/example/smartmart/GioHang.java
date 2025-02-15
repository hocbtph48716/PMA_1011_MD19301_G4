package com.example.smartmart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartmart.Adapter.ChiTietDonHangAdapter;
import com.example.smartmart.DAO.ChiTietDonHangDAO;
import com.example.smartmart.DAO.DonHangDAO;
import com.example.smartmart.models.ChiTietDonHang;
import com.example.smartmart.models.DonHang;
import com.example.smartmart.models.User;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GioHang extends AppCompatActivity {
    private ChiTietDonHangDAO chiTietDonHangDAO;
    private DonHangDAO donHangDAO;
    private List<ChiTietDonHang> chiTietDonHangList;
    private ChiTietDonHangAdapter adapter;
    private TextView totalPriceTextView;
    private Button buyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        buyButton = findViewById(R.id.buyButton);
        chiTietDonHangDAO = new ChiTietDonHangDAO(this);
        donHangDAO = new DonHangDAO(this);

        // Retrieve the user from the intent
        User user = (User) getIntent().getSerializableExtra("user");

        RecyclerView recyclerView = findViewById(R.id.cartRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Filter ChiTietDonHang by maUser
        chiTietDonHangList = chiTietDonHangDAO.getChiTietDonHangByUserId(user.getMaUser());
        adapter = new ChiTietDonHangAdapter(chiTietDonHangList, this, totalPriceTextView);
        recyclerView.setAdapter(adapter);


        findViewById(R.id.backmain).setOnClickListener((v) -> {
            Intent intent = new Intent();
            intent.putExtra("user", user);
            intent.setClass(GioHang.this, MainActivity.class);
            startActivity(intent);
        });

        buyButton.setOnClickListener(v -> {
            List<ChiTietDonHang> checkedProducts = adapter.getCheckedProducts();
            if (checkedProducts.isEmpty()) {
                // Handle case when no products are checked
                return;
            }

            DonHang donHang = new DonHang();
            donHang.setMaChiTietDonHang(checkedProducts.get(0).getMaChiTietDonHang()); // Example, set appropriate value
            donHang.setMaUser(user.getMaUser());
            donHang.setMaSanPham(checkedProducts.get(0).getMaSanPham()); // Example, set appropriate value

            // Convert long to String
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String currentDate = formatter.format(new Date());
            donHang.setNgayDatHang(currentDate);
            donHang.setTrangThai("Pending");
            donHang.setNgayThayDoiTrangThai(null);

            donHang.setTongGia(calculateTotalPrice(checkedProducts));

//            chiTietDonHangDAO.clearChiTietSanPham();
//            chiTietDonHangList.clear();
            adapter.notifyDataSetChanged();
            totalPriceTextView.setText("Tổng giá: 0 đ");

            // Navigate to ThanhToan activity
            Intent intent = new Intent(GioHang.this, ThanhToan.class);
            intent.putExtra("user", user);
            intent.putParcelableArrayListExtra("checkedProducts",new ArrayList<>(checkedProducts));
            startActivity(intent);
        });
    }

    private double calculateTotalPrice(List<ChiTietDonHang> products) {
        double totalPrice = 0.0;
        for (ChiTietDonHang product : products) {
            totalPrice += product.getGia() * product.getSoLuong();
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,##0,000");
        totalPriceTextView.setText("Tổng giá: " + decimalFormat.format(totalPrice) + " đ");
        return totalPrice;
    }
}