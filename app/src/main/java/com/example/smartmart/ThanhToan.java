package com.example.smartmart;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartmart.Adapter.ProductPayAdapter;
import com.example.smartmart.DBHelper.DatabaseHelper;
import com.example.smartmart.models.ChiTietDonHang;
import com.example.smartmart.models.User;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ThanhToan extends AppCompatActivity {
    private TextView tvRecipientInfo;

    private TextView tvPaymentDetails;
    private TextView tvTotalAmount;
    private RecyclerView recyclerViewProducts;
    private ProductPayAdapter productPayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        tvRecipientInfo = findViewById(R.id.tv_recipient_info);

        tvPaymentDetails = findViewById(R.id.tv_payment_details);
        tvTotalAmount = findViewById(R.id.tv_total_amount);
        recyclerViewProducts = findViewById(R.id.recycler_view_products);
        Button btnPlaceOrder = findViewById(R.id.btn_place_order);
        // Retrieve user and checked products from the intent
        User user = (User) getIntent().getSerializableExtra("user");
        List<ChiTietDonHang> checkedProducts = getIntent().getParcelableArrayListExtra("checkedProducts");

        // Display user information
        if (user != null) {
            String userInfo =
                    "Address: " + user.getDiaChi();
            tvRecipientInfo.setText(userInfo);
        }

        // Display payment details
        if (checkedProducts != null && !checkedProducts.isEmpty()) {
            productPayAdapter = new ProductPayAdapter(checkedProducts);
            recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewProducts.setAdapter(productPayAdapter);

            double totalAmount = 0.0;
            for (ChiTietDonHang product : checkedProducts) {
                totalAmount += product.getGia() * product.getSoLuong();
            }
            tvPaymentDetails.setText("Name: " + user.getNickName() + "\n" +
                    "Email: " + user.getEmail() + "\n" +
                    "Phone: " + user.getSoDienThoai() + "\n");
            DecimalFormat decimalFormat = new DecimalFormat("#,##0,000");
            tvTotalAmount.setText(
                    "Tổng đơn hàng: " + decimalFormat.format(totalAmount) + " đ");
        }

        // Set shipping method and fee (example values)
        Spinner spinnerPaymentMethod = findViewById(R.id.spinner_payment_method);
        ArrayAdapter<CharSequence> paymentMethodAdapter = ArrayAdapter.createFromResource(this,
                R.array.payment_methods, android.R.layout.simple_spinner_item);
        paymentMethodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPaymentMethod.setAdapter(paymentMethodAdapter);
        findViewById(R.id.backmain).setOnClickListener((v) -> {
            Intent intent = new Intent();
            intent.putExtra("user", user);
            intent.setClass(ThanhToan.this, GioHang.class);
            startActivity(intent);
        });
        btnPlaceOrder.setOnClickListener(v -> {
            // Show success message
            Toast.makeText(this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();

            // Insert order details into DonHang table

            String selectedPaymentMethod = spinnerPaymentMethod.getSelectedItem().toString();
            long donHangId = insertOrderDetails(user, checkedProducts, selectedPaymentMethod);

            // Insert products into SanPhamTrongDonHang table
            insertProductsIntoSanPhamTrongDonHang(donHangId, checkedProducts);
            clearChiTietDonHangTable();
            // Insert order details into DonHang table

            Intent intent = new Intent();
            intent.putExtra("user", user);
            intent.setClass(ThanhToan.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
    private void clearChiTietDonHangTable() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("ChiTietDonHang", null, null);
        db.close();
    }
    private long insertOrderDetails(User user, List<ChiTietDonHang> checkedProducts, String paymentMethod) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maUser", user.getMaUser());
        values.put("diaChiDatHang",user.getDiaChi());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDate = formatter.format(new Date());
        values.put("ngayDatHang", currentDate);
        values.put("trangThai",1);
        values.put("ngayThayDoiTrangThai", (String) null);
        double totalAmount = 0.0;
        for (ChiTietDonHang product : checkedProducts) {
            totalAmount += product.getGia() * product.getSoLuong();
        }
        values.put("tongGia", totalAmount);
        values.put("phuongThucThanhToan", paymentMethod);
        long donHangId = db.insert("DonHang", null, values);
        db.close();
        return donHangId;
    }

    private void insertProductsIntoSanPhamTrongDonHang(long donHangId, List<ChiTietDonHang> checkedProducts) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (ChiTietDonHang product : checkedProducts) {
            ContentValues values = new ContentValues();
            values.put("maDonHang", donHangId);
            values.put("maSanPham", product.getMaSanPham());
            values.put("tenSanPham",product.getTenSanPham());
            values.put("soLuong", product.getSoLuong());
            values.put("image_url",product.getImageUrl());
            db.insert("SanPhamTrongDonHang", null, values);
        }
        db.close();
    }
    }
