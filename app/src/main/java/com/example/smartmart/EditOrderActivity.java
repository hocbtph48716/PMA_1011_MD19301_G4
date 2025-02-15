package com.example.smartmart;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.smartmart.DAO.DonHangDAO;
import com.example.smartmart.models.DonHang;

public class EditOrderActivity extends AppCompatActivity {
    private EditText etOrderDate, etOrderStatus, etTotalPrice, etPaymentMethod;
    private TextView tvOrderId;
    private Button btnSaveOrder;
    private ImageButton btnBack;
    private DonHangDAO donHangDAO;
    private int orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);

        etOrderDate = findViewById(R.id.etOrderDate);
        etOrderStatus = findViewById(R.id.etOrderStatus);
        etTotalPrice = findViewById(R.id.etTotalPrice);
        etPaymentMethod = findViewById(R.id.etPaymentMethod);
        tvOrderId = findViewById(R.id.tvOrderId);
        btnSaveOrder = findViewById(R.id.btnSaveOrder);
        btnBack = findViewById(R.id.btnBack);
        donHangDAO = new DonHangDAO(this);

        orderId = getIntent().getIntExtra("ORDER_ID", -1);
        loadOrderDetails();

        btnSaveOrder.setOnClickListener(v -> saveOrder());
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadOrderDetails() {
        DonHang order = donHangDAO.getDonHang(orderId);
        if (order != null) {
            tvOrderId.setText(String.valueOf(order.getMaDonHang()));
            etOrderDate.setText(order.getNgayDatHang());
            etOrderStatus.setText(order.getTrangThai());
            etTotalPrice.setText(String.valueOf(order.getTongGia()));
            etPaymentMethod.setText(order.getPhuongThucThanhToan());
        }
    }

    private void saveOrder() {
        String orderDate = etOrderDate.getText().toString();
        String status = etOrderStatus.getText().toString();
        double totalPrice = Double.parseDouble(etTotalPrice.getText().toString());
        String paymentMethod = etPaymentMethod.getText().toString();

        DonHang updatedOrder = new DonHang();
        updatedOrder.setMaDonHang(orderId);
        updatedOrder.setNgayDatHang(orderDate);
        updatedOrder.setTrangThai(status);
        updatedOrder.setTongGia(totalPrice);
        updatedOrder.setPhuongThucThanhToan(paymentMethod);

        int rowsAffected = donHangDAO.updateDonHang(updatedOrder);
        if (rowsAffected > 0) {
            Toast.makeText(this, "Đã cập nhật đơn hàng thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Cập nhật đơn hàng thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}

