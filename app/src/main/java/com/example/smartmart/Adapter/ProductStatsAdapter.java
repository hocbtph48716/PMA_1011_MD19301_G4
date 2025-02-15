package com.example.smartmart.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartmart.R;
import com.example.smartmart.models.ProductStat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductStatsAdapter extends RecyclerView.Adapter<ProductStatsAdapter.ViewHolder> {
    private List<ProductStat> productStats;
    private NumberFormat currencyFormat;

    public ProductStatsAdapter(List<ProductStat> productStats) {
        this.productStats = productStats;
        this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_stat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductStat stat = productStats.get(position);

        holder.tvProductName.setText(stat.getName());
        holder.tvSales.setText("Số lượt bán: " + stat.getSalesCount());
        holder.tvPrice.setText(currencyFormat.format(stat.getPrice()));
        holder.topRank.setText("Top " + stat.getRank());
    }

    @Override
    public int getItemCount() {
        return productStats.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName;
        TextView tvSales;
        TextView tvPrice;
        TextView topRank;

        ViewHolder(View view) {
            super(view);
            tvProductName = view.findViewById(R.id.tvProductName);
            tvSales = view.findViewById(R.id.tvSales);
            tvPrice = view.findViewById(R.id.tvPrice);
            topRank = view.findViewById(R.id.topRank);
        }
    }
}

