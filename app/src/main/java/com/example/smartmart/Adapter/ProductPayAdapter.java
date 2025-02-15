package com.example.smartmart.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartmart.R;
import com.example.smartmart.models.ChiTietDonHang;

import java.text.DecimalFormat;
import java.util.List;

public class ProductPayAdapter extends RecyclerView.Adapter<ProductPayAdapter.ProductViewHolder> {
    private List<ChiTietDonHang> productList;

    public ProductPayAdapter(List<ChiTietDonHang> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thanhtoan, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ChiTietDonHang product = productList.get(position);
        holder.tvProductName.setText(product.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("#,##0,000");
        holder.tvProductPrice.setText(decimalFormat.format(product.getGia())+" đ");
        holder.tvProductDesc.setText("Số lượng: " + product.getSoLuong());
        // Load image using Glide
        if (product.getImageUrl() != null && !product.getImageUrl().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(product.getImageUrl())
                    .placeholder(R.drawable.placeholder_image) // Hình ảnh mặc định khi đang tải
                    .error(R.drawable.error_image) // Hình ảnh hiển thị khi có lỗi
                    .into(holder.imageView);
        } else {
            // Đặt một hình ảnh mặc định nếu imageUrl là null hoặc rỗng
            holder.imageView.setImageResource(R.drawable.placeholder_image);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvProductPrice, tvProductDesc;
        ImageView imageView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
            tvProductDesc = itemView.findViewById(R.id.tv_product_desc);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}