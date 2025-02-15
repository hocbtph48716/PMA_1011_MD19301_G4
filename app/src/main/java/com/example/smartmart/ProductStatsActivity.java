package com.example.smartmart;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.smartmart.models.ProductStat;
import com.example.smartmart.DBHelper.DatabaseHelper;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProductStatsActivity extends AppCompatActivity {
    private TableLayout tableLayout;
    private List<ProductStat> productStats;
    private DatabaseHelper dbHelper;
    private TextView tvStartDate, tvEndDate;
    private Button btnApplyDateRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_stats);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thống kê sản phẩm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tableLayout = findViewById(R.id.tableLayoutStats);
        tvStartDate = findViewById(R.id.tvStartDate);
        tvEndDate = findViewById(R.id.tvEndDate);
        btnApplyDateRange = findViewById(R.id.btnApplyDateRange);

        productStats = new ArrayList<>();
        dbHelper = new DatabaseHelper(this);

        tvStartDate.setOnClickListener(v -> showDatePickerDialog(tvStartDate));
        tvEndDate.setOnClickListener(v -> showDatePickerDialog(tvEndDate));
        btnApplyDateRange.setOnClickListener(v -> loadTopProducts());

        loadTopProducts();
    }

    private void showDatePickerDialog(TextView textView) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> {
                    String selectedDate = year1 + "-" + String.format("%02d", month1 + 1) + "-" + String.format("%02d", dayOfMonth);
                    textView.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void loadTopProducts() {
        String startDate = tvStartDate.getText().toString().trim();
        String endDate = tvEndDate.getText().toString().trim();

        if (startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn cả ngày bắt đầu và ngày kết thúc.", Toast.LENGTH_SHORT).show();
            return;
        }

        productStats.clear();
        tableLayout.removeViews(1, tableLayout.getChildCount() - 1);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT maSanPham, tenSanPham, gia, sold, date " +
                "FROM SanPham " +
                "WHERE date BETWEEN ? AND ? " +
                "ORDER BY sold DESC " +
                "LIMIT 5";

        try (Cursor cursor = db.rawQuery(query, new String[]{startDate, endDate})) {
            if (cursor.moveToFirst()) {
                int rank = 1;
                do {
                    ProductStat stat = new ProductStat();
                    stat.setId(cursor.getInt(cursor.getColumnIndexOrThrow("maSanPham")));
                    stat.setName(cursor.getString(cursor.getColumnIndexOrThrow("tenSanPham")));
                    stat.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow("gia")));
                    stat.setSaleDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));
                    stat.setSalesCount(cursor.getInt(cursor.getColumnIndexOrThrow("sold")));
                    stat.setRank(rank++);
                    productStats.add(stat);

                    TableRow row = new TableRow(this);
                    row.addView(createTextView(String.valueOf(stat.getRank())));
                    row.addView(createTextView(stat.getName()));
                    row.addView(createTextView(String.valueOf(stat.getPrice())));
                    row.addView(createTextView(stat.getSaleDate()));
                    row.addView(createTextView(String.valueOf(stat.getSalesCount())));
                    tableLayout.addView(row);
                } while (cursor.moveToNext());
            } else {
                TextView noDataView = new TextView(this);
                noDataView.setText("Không có dữ liệu cho khoảng thời gian này.");
                noDataView.setPadding(16, 16, 16, 16);
                tableLayout.addView(noDataView);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi truy vấn cơ sở dữ liệu.", Toast.LENGTH_SHORT).show();
        }
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setPadding(8, 8, 8, 8);
        return textView;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
