package com.example.smartmart;

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

public class AddProductActivity extends AppCompatActivity {

    private EditText edtName, edtPrice, edtDescription, edtImageUrl, edtQuantity;
    private ImageView imgPreview, btnBack;
    private Button btnPreviewImage, btnAddProduct, btnIncreaseQuantity, btnDecreaseQuantity;
    private SanPhamDAO sanPhamDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Ánh xạ view
        edtName = findViewById(R.id.edt_product_name);
        edtPrice = findViewById(R.id.edt_product_price);
        edtDescription = findViewById(R.id.edt_product_description);
        edtImageUrl = findViewById(R.id.edt_image_url);
        edtQuantity = findViewById(R.id.edt_quantity);
        imgPreview = findViewById(R.id.img_preview);
        btnPreviewImage = findViewById(R.id.btn_preview_image);
        btnAddProduct = findViewById(R.id.btn_add_product);
        btnIncreaseQuantity = findViewById(R.id.btn_increase_quantity);
        btnDecreaseQuantity = findViewById(R.id.btn_decrease_quantity);
        btnBack = findViewById(R.id.btn_back);

        sanPhamDAO = new SanPhamDAO(this);

        // Xử lý nút quay lại
        btnBack.setOnClickListener(v -> finish());

        // Hiển thị ảnh từ URL
        btnPreviewImage.setOnClickListener(v -> {
            String imageUrl = edtImageUrl.getText().toString();
            if (!imageUrl.isEmpty()) {
                Glide.with(this)
                        .load(imageUrl)
                        .placeholder(R.drawable.placeholder_image)
                        .into(imgPreview);
            } else {
                Toast.makeText(this, "Vui lòng nhập URL hình ảnh!", Toast.LENGTH_SHORT).show();
            }
        });

        // Tăng/giảm số lượng sản phẩm
        btnIncreaseQuantity.setOnClickListener(v -> {
            int quantity = Integer.parseInt(edtQuantity.getText().toString());
            edtQuantity.setText(String.valueOf(quantity + 1));
        });

        btnDecreaseQuantity.setOnClickListener(v -> {
            int quantity = Integer.parseInt(edtQuantity.getText().toString());
            if (quantity > 1) {
                edtQuantity.setText(String.valueOf(quantity - 1));
            }
        });

        // Xử lý thêm sản phẩm
        btnAddProduct.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            String priceStr = edtPrice.getText().toString();
            String description = edtDescription.getText().toString();
            String imageUrl = edtImageUrl.getText().toString();
            String quantityStr = edtQuantity.getText().toString();

            if (name.isEmpty() || priceStr.isEmpty() || imageUrl.isEmpty() || quantityStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            double price = Double.parseDouble(priceStr);
            int quantity = Integer.parseInt(quantityStr);

            SanPham product = new SanPham(0, name, description, price, "", quantity, 0, "", imageUrl);
            long result = sanPhamDAO.addProduct(product);

            if (result > 0) {
                Toast.makeText(this, "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Thêm sản phẩm thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
