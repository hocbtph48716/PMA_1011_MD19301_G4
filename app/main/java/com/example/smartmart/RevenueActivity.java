package com.example.smartmart;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smartmart.DBHelper.DatabaseHelper;

import java.util.Calendar;

public class RevenueActivity extends AppCompatActivity {

    private EditText fromDateEditText, toDateEditText;
    private TextView revenueTextView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue);

        // Thiết lập Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Doanh thu");
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // Ánh xạ các thành phần UI
        fromDateEditText = findViewById(R.id.fromDateEditText);
        toDateEditText = findViewById(R.id.toDateEditText);
        revenueTextView = findViewById(R.id.revenueTextView);

        Button fromDateButton = findViewById(R.id.fromDateButton);
        Button toDateButton = findViewById(R.id.toDateButton);

        dbHelper = new DatabaseHelper(this);

        // Sự kiện chọn ngày
        fromDateButton.setOnClickListener(v -> showDatePickerDialog(fromDateEditText));
        toDateButton.setOnClickListener(v -> showDatePickerDialog(toDateEditText));

        // Tự động tính doanh thu khi ngày được chọn
        fromDateEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) calculateRevenue();
        });
        toDateEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) calculateRevenue();
        });
    }


    //Hiển thị DatePickerDialog để chọn ngày.
    //editText EditText sẽ hiển thị ngày được chọn.

    private void showDatePickerDialog(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> {
                    String selectedDate = year1 + "-" + String.format("%02d", month1 + 1) + "-" + String.format("%02d", dayOfMonth);
                    editText.setText(selectedDate);
                    calculateRevenue();
                }, year, month, day);
        datePickerDialog.show();
    }


     //Tính toán doanh thu trong khoảng thời gian được chọn.

    private void calculateRevenue() {
        String fromDate = fromDateEditText.getText().toString().trim();
        String toDate = toDateEditText.getText().toString().trim();

        if (fromDate.isEmpty() || toDate.isEmpty()) {
            revenueTextView.setText("0 vnd");
            return;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT SUM(tongGia) as totalRevenue FROM DonHang WHERE ngayDatHang BETWEEN ? AND ?";
        Cursor cursor = db.rawQuery(query, new String[]{fromDate, toDate});

        try {
            if (cursor.moveToFirst()) {
                double totalRevenue = cursor.getDouble(cursor.getColumnIndexOrThrow("totalRevenue"));
                if (Double.isNaN(totalRevenue)) {
                    totalRevenue = 0;
                }
                revenueTextView.setText(String.format("%.0f vnd", totalRevenue));
            } else {
                revenueTextView.setText("0 vnd");
            }
        } catch (Exception e) {
            e.printStackTrace();
            revenueTextView.setText("0 vnd");
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
