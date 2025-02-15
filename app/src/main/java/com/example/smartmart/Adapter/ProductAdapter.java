package com.example.smartmart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartmart.ChiTietSanPham;
import com.example.smartmart.R;
import com.example.smartmart.models.SanPham;
import com.example.smartmart.models.User;

import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<SanPham> productList;
    private Context context;
    private User user;

    public ProductAdapter(List<SanPham> productList, Context context, User user) {
        this.productList = productList;
        this.context = context;
        this.user = user;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        SanPham product = productList.get(position);
        holder.productName.setText(product.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        holder.productPrice.setText(decimalFormat.format(product.getGia()) + " đ");
        Glide.with(holder.itemView.getContext())
                .load(product.getImage_url())
                .into(holder.productImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChiTietSanPham.class);
            intent.putExtra("product_id", product.getMaSanPham());
            intent.putExtra("user", user);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
        }
    }
}