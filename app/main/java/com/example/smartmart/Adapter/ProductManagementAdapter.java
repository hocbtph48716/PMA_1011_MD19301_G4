package com.example.smartmart.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartmart.R;
import com.example.smartmart.DAO.SanPhamDAO;
import com.example.smartmart.EditProductActivity;  // Import Activity chỉnh sửa
import com.example.smartmart.models.SanPham;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;

public class ProductManagementAdapter extends RecyclerView.Adapter<ProductManagementAdapter.ProductViewHolder> {

    private Context context;
    private List<SanPham> productList;
    private SanPhamDAO sanPhamDAO;

    public ProductManagementAdapter(Context context, List<SanPham> productList) {
        this.context = context;
        this.productList = productList;
        this.sanPhamDAO = new SanPhamDAO(context);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_management, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        SanPham product = productList.get(position);

        holder.productName.setText(product.getTenSanPham());
        holder.productPrice.setText(formatPrice(product.getGia()));
        holder.productQuantity.setText("Số lượng: " + product.getSoLuong());

        Glide.with(context)
                .load(product.getImage_url())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(holder.productImage);

        // Chỉnh sửa sản phẩm
        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditProductActivity.class);
            // Chuyển dữ liệu sản phẩm sang EditProductActivity
            intent.putExtra("productId", String.valueOf(product.getMaSanPham()));
            intent.putExtra("productName", product.getTenSanPham());
            intent.putExtra("productPrice", product.getGia());
            intent.putExtra("productDescription", product.getMoTa());
            intent.putExtra("productImage", product.getImage_url());
            intent.putExtra("productQuantity", product.getSoLuong());
            context.startActivity(intent);
        });

        // Xóa sản phẩm
        holder.btnDelete.setOnClickListener(v -> {
            // Hiển thị hộp thoại xác nhận trước khi xóa
            showDeleteConfirmationDialog(product, position);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    private String formatPrice(double price) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(price);
    }

    // Hiển thị hộp thoại xác nhận xóa sản phẩm
    private void showDeleteConfirmationDialog(SanPham product, int position) {
        new AlertDialog.Builder(context)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa sản phẩm này không?")
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteProduct(product, position);
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void deleteProduct(SanPham product, int position) {
        int result = sanPhamDAO.deleteProduct(String.valueOf(product.getMaSanPham()));
        if (result > 0) {
            productList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, productList.size());
            Toast.makeText(context, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Không thể xóa sản phẩm", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateList(List<SanPham> newList) {
        productList.clear();
        productList.addAll(newList);
        notifyDataSetChanged();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, productQuantity;
        ImageButton btnEdit, btnDelete;

        ProductViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
