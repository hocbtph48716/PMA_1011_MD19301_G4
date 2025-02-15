package com.example.smartmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.smartmart.DAO.SanPhamDAO;
import com.example.smartmart.models.SanPham;

public class EditProductActivity extends AppCompatActivity {

    private EditText edtProductName, edtProductPrice, edtProductDescription, edtImageUrl, edtQuantity;
    private ImageView imgPreview;
    private Button btnUpdateProduct;
    private SanPhamDAO sanPhamDAO;

    private String productId, productName, productDescription, productImage;
    private double productPrice;
    private int productQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        // Ánh xạ các thành phần giao diện
        edtProductName = findViewById(R.id.edt_product_name);
        edtProductPrice = findViewById(R.id.edt_product_price);
        edtProductDescription = findViewById(R.id.edt_product_description);
        edtImageUrl = findViewById(R.id.edt_image_url);
        edtQuantity = findViewById(R.id.edt_quantity);
        imgPreview = findViewById(R.id.img_preview);
        btnUpdateProduct = findViewById(R.id.btn_update_product);

        // Khởi tạo đối tượng DAO
        sanPhamDAO = new SanPhamDAO(this);

        // Nhận thông tin từ Intent
        Intent intent = getIntent();
        productId = intent.getStringExtra("productId");
        productName = intent.getStringExtra("productName");
        productPrice = intent.getDoubleExtra("productPrice", 0);
        productDescription = intent.getStringExtra("productDescription");
        productImage = intent.getStringExtra("productImage");
        productQuantity = intent.getIntExtra("productQuantity", 0);

        // Hiển thị thông tin vào các trường
        edtProductName.setText(productName);
        edtProductPrice.setText(String.valueOf(productPrice));
        edtProductDescription.setText(productDescription);
        edtImageUrl.setText(productImage);
        edtQuantity.setText(String.valueOf(productQuantity));

        // Hiển thị ảnh sản phẩm
        Glide.with(this).load(productImage).into(imgPreview);

        // Cập nhật sản phẩm khi nhấn nút
        btnUpdateProduct.setOnClickListener(v -> updateProduct());

        // Xử lý nút Quay lại
        ImageView btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish()); // Đóng Activity hiện tại và quay lại màn hình trước

        // Xử lý sự kiện tăng giảm số lượng
        Button btnDecreaseQuantity = findViewById(R.id.btn_decrease_quantity);
        Button btnIncreaseQuantity = findViewById(R.id.btn_increase_quantity);

        btnDecreaseQuantity.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(edtQuantity.getText().toString());
            if (currentQuantity > 1) {  // Đảm bảo số lượng không nhỏ hơn 1
                edtQuantity.setText(String.valueOf(currentQuantity - 1));
            }
        });

        btnIncreaseQuantity.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(edtQuantity.getText().toString());
            edtQuantity.setText(String.valueOf(currentQuantity + 1));
        });

        // Xử lý sự kiện xem trước ảnh
        Button btnPreviewImage = findViewById(R.id.btn_preview_image);
        btnPreviewImage.setOnClickListener(v -> {
            String imageUrl = edtImageUrl.getText().toString().trim();
            if (!imageUrl.isEmpty()) {
                Glide.with(this)
                        .load(imageUrl)
                        .into(imgPreview);
            } else {
                Toast.makeText(this, "Vui lòng nhập URL hình ảnh", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateProduct() {
        // Lấy thông tin từ các trường nhập liệu
        String updatedName = edtProductName.getText().toString().trim();
        String updatedDescription = edtProductDescription.getText().toString().trim();
        String updatedImageUrl = edtImageUrl.getText().toString().trim();
        int updatedQuantity = Integer.parseInt(edtQuantity.getText().toString().trim());  // Đảm bảo nhập đúng giá trị số
        double updatedPrice = Double.parseDouble(edtProductPrice.getText().toString().trim());

        // Tạo đối tượng SanPham với thông tin mới
        SanPham updatedProduct = new SanPham(
                Integer.parseInt(productId),
                updatedName,
                updatedDescription,
                updatedPrice,
                "",
                updatedQuantity,
                0, "",
                updatedImageUrl
        );

        // Cập nhật sản phẩm trong cơ sở dữ liệu
        int result = sanPhamDAO.updateProduct(updatedProduct);
        if (result > 0) {
            Toast.makeText(this, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
            finish(); // Quay lại Activity trước
        } else {
            Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}
