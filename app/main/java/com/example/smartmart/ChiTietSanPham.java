// ChiTietSanPham.java
package com.example.smartmart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.smartmart.DAO.ChiTietDonHangDAO;
import com.example.smartmart.DAO.SanPhamDAO;
import com.example.smartmart.models.ChiTietDonHang;
import com.example.smartmart.models.SanPham;
import com.example.smartmart.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;

public class ChiTietSanPham extends AppCompatActivity {
    private SanPhamDAO sanPhamDAO;
    private ChiTietDonHangDAO cartDAO;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        sanPhamDAO = new SanPhamDAO(this);
        cartDAO = new ChiTietDonHangDAO(this);

        // Retrieve the user from the intent
        user = (User) getIntent().getSerializableExtra("user");
        if (user != null) {
            Log.d("ChiTietSanPham", "User received: " + user.getNickName());
        } else {
            Log.d("ChiTietSanPham", "No user received");
        }

        findViewById(R.id.backmain).setOnClickListener((v) -> {
            Intent intent = new Intent();
            intent.putExtra("user", user);
            intent.setClass(ChiTietSanPham.this, MainActivity.class);
            startActivity(intent);
        });

        int productId = getIntent().getIntExtra("product_id", -1);
        if (productId != -1) {
            SanPham product = sanPhamDAO.getProductById(productId);
            if (product != null) {
                TextView productName = findViewById(R.id.product_name);
                TextView productPrice = findViewById(R.id.product_price);
                TextView productDescription = findViewById(R.id.product_description);
                ImageView productImage = findViewById(R.id.product_image);
                NumberPicker numberPicker = findViewById(R.id.numberPicker);
                Button btnAddToCart = findViewById(R.id.btnAddToCart);

                productName.setText(product.getTenSanPham());
                DecimalFormat decimalFormat = new DecimalFormat("#,##0,000");
                productPrice.setText(decimalFormat.format(product.getGia()) + " đ");
                productDescription.setText(product.getMoTa());
                Glide.with(this).load(product.getImage_url()).into(productImage);

                // Set min and max values for NumberPicker
                numberPicker.setMinValue(1);
                numberPicker.setMaxValue(10);

                btnAddToCart.setOnClickListener(v -> {
                    int quantity = numberPicker.getValue();
                    ChiTietDonHang chiTietDonHang = new ChiTietDonHang();
                    chiTietDonHang.setMaSanPham(product.getMaSanPham());
                    chiTietDonHang.setMaUser(user.getMaUser());
                    chiTietDonHang.setSoLuong(quantity);
                    chiTietDonHang.setGia(product.getGia());
                    chiTietDonHang.setImageUrl(product.getImage_url());
                    chiTietDonHang.setTenSanPham(product.getTenSanPham());
                    chiTietDonHang.setChecked(false);
                    cartDAO.insertChiTietDonHang(chiTietDonHang);
                    Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                });

                FloatingActionButton fabGotoCart = findViewById(R.id.fab_goto_cart);
                fabGotoCart.setOnClickListener(v -> {
                    Intent intent = new Intent(ChiTietSanPham.this, GioHang.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                });
            }
        }
    }
}