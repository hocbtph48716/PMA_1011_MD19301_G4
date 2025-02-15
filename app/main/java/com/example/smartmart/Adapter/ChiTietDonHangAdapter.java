package com.example.smartmart.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartmart.DAO.ChiTietDonHangDAO;
import com.example.smartmart.R;
import com.example.smartmart.models.ChiTietDonHang;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ChiTietDonHangAdapter extends RecyclerView.Adapter<ChiTietDonHangAdapter.ViewHolder> {
    private List<ChiTietDonHang> chiTietDonHangList;
    private Context context;
    private TextView totalPriceTextView;

    public ChiTietDonHangAdapter(List<ChiTietDonHang> chiTietDonHangList, Context context, TextView totalPriceTextView) {
        this.chiTietDonHangList = chiTietDonHangList;
        this.context = context;
        this.totalPriceTextView = totalPriceTextView;
        updateTotalPrice();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chi_tiet_don_hang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChiTietDonHang chiTietDonHang = chiTietDonHangList.get(holder.getAdapterPosition());
        holder.productName.setText(chiTietDonHang.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("#,##0,000");
        holder.productPrice.setText(decimalFormat.format(chiTietDonHang.getGia()) + " đ");
        holder.quantityEditText.setText(String.valueOf(chiTietDonHang.getSoLuong()));
        holder.checkBox.setChecked(chiTietDonHang.isChecked());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> chiTietDonHang.setChecked(isChecked));
        Glide.with(context).load(chiTietDonHang.getImageUrl()).into(holder.productImage);

        holder.minusButton.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            int quantity = Integer.parseInt(holder.quantityEditText.getText().toString());
            if (quantity > 1) {
                quantity--;
                holder.quantityEditText.setText(String.valueOf(quantity));
                chiTietDonHangList.get(currentPosition).setSoLuong(quantity);
                updateChiTietSanPham(chiTietDonHangList.get(currentPosition));
                holder.itemView.post(() -> notifyItemChanged(currentPosition));
                updateTotalPrice();
            }
        });

        holder.plusButton.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            int quantity = Integer.parseInt(holder.quantityEditText.getText().toString());
            quantity++;
            holder.quantityEditText.setText(String.valueOf(quantity));
            chiTietDonHangList.get(currentPosition).setSoLuong(quantity);
            updateChiTietSanPham(chiTietDonHangList.get(currentPosition));
            holder.itemView.post(() -> notifyItemChanged(currentPosition));
            updateTotalPrice();
        });

        holder.quantityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No action needed
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int currentPosition = holder.getAdapterPosition();
                    int quantity = Integer.parseInt(s.toString());
                    chiTietDonHangList.get(currentPosition).setSoLuong(quantity);
                    updateChiTietSanPham(chiTietDonHangList.get(currentPosition));
                    holder.itemView.post(() -> notifyItemChanged(currentPosition));
                    updateTotalPrice();
                } catch (NumberFormatException e) {
                    // Handle the exception if the input is not a valid number
                }
            }
        });

        holder.deleteButton.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            ChiTietDonHangDAO chiTietDonHangDAO = new ChiTietDonHangDAO(context);
            chiTietDonHangDAO.deleteChiTietDonHang(chiTietDonHangList.get(currentPosition).getMaChiTietDonHang());
            chiTietDonHangList.remove(currentPosition);
            holder.itemView.post(() -> {
                notifyItemRemoved(currentPosition);
                notifyItemRangeChanged(currentPosition, chiTietDonHangList.size());
                updateTotalPrice();
            });
        });
    }

    private void updateChiTietSanPham(ChiTietDonHang chiTietDonHang) {
        ChiTietDonHangDAO chiTietDonHangDAO = new ChiTietDonHangDAO(context);
        chiTietDonHangDAO.updateChiTietDonHang(chiTietDonHang);
    }

    private void updateTotalPrice() {
        double totalPrice = 0;
        for (ChiTietDonHang chiTietDonHang : chiTietDonHangList) {
            totalPrice += chiTietDonHang.getGia() * chiTietDonHang.getSoLuong();
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,##0,000");
        totalPriceTextView.setText("Tổng đơn hàng: "+decimalFormat.format(totalPrice) + " đ");
    }

    @Override
    public int getItemCount() {
        return chiTietDonHangList.size();
    }
    public List<ChiTietDonHang> getCheckedProducts() {
        List<ChiTietDonHang> checkedProducts = new ArrayList<>();
        for (ChiTietDonHang product : chiTietDonHangList) {
            if (product.isChecked()) {
                checkedProducts.add(product);
            }
        }
        return checkedProducts;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;
        public ImageView productImage;
        public TextView productName;
        public TextView productPrice;
        public EditText quantityEditText;
        public Button minusButton;
        public Button plusButton;
        public ImageButton deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            quantityEditText = itemView.findViewById(R.id.quantityEditText);
            minusButton = itemView.findViewById(R.id.minusButton);
            plusButton = itemView.findViewById(R.id.plusButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}