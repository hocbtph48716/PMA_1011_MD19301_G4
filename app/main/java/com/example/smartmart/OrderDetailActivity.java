package com.example.smartmart;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartmart.Adapter.OrderDetailAdapter;
import com.example.smartmart.DBHelper.DatabaseHelper;
import com.example.smartmart.models.Order;
import com.example.smartmart.models.OrderDetail;
import com.example.smartmart.models.User;

import java.text.DecimalFormat;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {
    private TextView tvRecipientInfo, paymentMethod, tvPaymentDetails, tvTotalAmount;
    private RecyclerView recyclerViewProducts;
    private Button btnChangeTrangThai;
    private OrderDetailAdapter adapter;
    private DatabaseHelper dbHelper;
    private Order order;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        tvRecipientInfo = findViewById(R.id.tv_recipient_info);
        paymentMethod = findViewById(R.id.payment_method);
        tvPaymentDetails = findViewById(R.id.tv_payment_details);
        tvTotalAmount = findViewById(R.id.tv_total_amount);
        recyclerViewProducts = findViewById(R.id.recycler_view_products);
        btnChangeTrangThai = findViewById(R.id.btn_change_trangthai);
        ImageView backMain = findViewById(R.id.backmain);
        dbHelper = new DatabaseHelper(this);

        // Get the order ID passed from the previous activity
        int orderId = getIntent().getIntExtra("orderId", -1);

        // Load order details
        loadOrder(orderId);

        // Load product details
        loadOrderDetails(orderId);

        // Set button click listener to change order status
        btnChangeTrangThai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update the order status to 2
                dbHelper.updateOrderStatus(order.getMaDonHang(), 2);

                // Hide the button
                btnChangeTrangThai.setVisibility(View.GONE);
            }
        });

        // Set back button click listener
        backMain.setOnClickListener(v -> finish());


    }

    private void loadOrder(int orderId) {
        order = dbHelper.getOrderById(orderId);
        if (order != null) {
            tvRecipientInfo.setText(order.getDiaChiDatHang());
            paymentMethod.setText(order.getPhuongThucThanhToan());
            int maUser = getIntent().getIntExtra("user",-1);
            user = dbHelper.getUserById(maUser);
            tvPaymentDetails.setText("Name: " + user.getNickName() + "\n" +
                    "Email: " + user.getEmail() + "\n" +
                    "Phone: " + user.getSoDienThoai() + "\n");
            DecimalFormat decimalFormat = new DecimalFormat("#,##0,000");
            tvTotalAmount.setText(
                    "Tổng đơn hàng: " + decimalFormat.format(order.getTongGia()) + " đ");
            if (!order.getTrangThai().equals("1")) {
                btnChangeTrangThai.setVisibility(View.GONE);
            }
        }
    }

    private void loadOrderDetails(int orderId) {
        List<OrderDetail> orderDetails = dbHelper.getOrderDetailsByOrderId(orderId);
        adapter = new OrderDetailAdapter(this, orderDetails);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewProducts.setAdapter(adapter);
    }
}