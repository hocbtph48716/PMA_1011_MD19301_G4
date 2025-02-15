package com.example.smartmart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartmart.DBHelper.DatabaseHelper;
import com.example.smartmart.OrderDetailActivity;
import com.example.smartmart.R;
import com.example.smartmart.models.Order;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OrderWaitAdapter extends RecyclerView.Adapter<OrderWaitAdapter.OrderViewHolder> {
    private List<Order> orders;
    private Context context;
    private NumberFormat currencyFormat;

    public OrderWaitAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
        this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_donhang, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);

        // Set thông tin đơn hàng
        holder.tvOrderNumber.setText("Đơn hàng #" + order.getMaDonHang());
        holder.tvOrderDate.setText("Ngày đặt: " + order.getNgayDatHang());
        holder.tvOrderAmount.setText(currencyFormat.format(order.getTongGia()));
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, OrderDetailActivity.class);
            intent.putExtra("orderId", order.getMaDonHang());
            intent.putExtra("user", order.getMaUser());
            context.startActivity(intent);
        });
        if (order.getTrangThai().equals("3")) {
            holder.btnReceiveOrder.setVisibility(View.GONE);
        } else {
            holder.btnReceiveOrder.setVisibility(View.VISIBLE);
        }
        holder.btnReceiveOrder.setOnClickListener(v -> {
            // Update the order status to 3
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            dbHelper.updateOrderStatus(order.getMaDonHang(), 3);

            // Hide the button
            holder.btnReceiveOrder.setVisibility(View.GONE);

            // Update the order status in the list and notify the adapter
            order.setTrangThai("3");
            notifyItemChanged(position);
        });
        // Hiển thị trạng thái đơn hàng
        int status = Integer.parseInt(order.getTrangThai());
        holder.tvOrderStatus.setText(getStatusText(status));
        holder.tvOrderStatus.setTextColor(getStatusColor(status));
    }

    private String getStatusText(int status) {
        switch (status) {
            case 1:
                return "Chờ giao hàng";
            case 2:
                return "Đang giao";
            case 3:
                return "Đã nhận hàng";
            default:
                return "Không xác định";
        }
    }

    private int getStatusColor(int status) {
        switch (status) {
            case 1:
                return ContextCompat.getColor(context, R.color.red); // Màu đỏ cho trạng thái "Chờ xác nhận"
            case 2:
                return ContextCompat.getColor(context, R.color.blue); // Màu xanh cho trạng thái "Đang giao"
            case 3:
                return ContextCompat.getColor(context, R.color.green); // Màu xanh cho trạng thái "Đã nhận hàng"
            default:
                return ContextCompat.getColor(context, R.color.gray); // Mặc định là màu xám
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderNumber, tvOrderDate, tvOrderAmount, tvOrderStatus;
        Button btnReceiveOrder;
        OrderViewHolder(View itemView) {
            super(itemView);

            // Ánh xạ các thành phần giao diện
            tvOrderNumber = itemView.findViewById(R.id.tvOrderTitle);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvOrderAmount = itemView.findViewById(R.id.tvOrderTotal);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            btnReceiveOrder = itemView.findViewById(R.id.btnReceiveOrder);
        }
    }
}