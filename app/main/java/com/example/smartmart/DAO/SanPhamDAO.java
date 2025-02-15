package com.example.smartmart.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.smartmart.DBHelper.DatabaseHelper;
import com.example.smartmart.models.SanPham;

import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public SanPhamDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }
    public SanPham getProductById(int id) {
        SanPham product = null;
        Cursor cursor = database.query(DatabaseHelper.TABLE_PRODUCTS, null, DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            product = new SanPham(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPTION)),
                    cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRICE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CATEGORY)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_QUANTITY)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SOLD)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMAGE_URL))
            );
            cursor.close();
        }
        return product;
    }
    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + "DanhMucSanPham", null);
        if (cursor.moveToFirst()) {
            do {
                categories.add(cursor.getString(cursor.getColumnIndexOrThrow("tenDanhMuc")));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return categories;
    }
    public List<SanPham> getProductsByCategory(String category) {
        List<SanPham> products = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_PRODUCTS + " WHERE " + DatabaseHelper.COLUMN_CATEGORY + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{category});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                SanPham product = new SanPham();
                product.setMaSanPham(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)));
                product.setTenSanPham(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)));
                product.setMoTa(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION)));
                product.setGia(cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRICE)));
                product.setMaDanhMuc(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY)));
                product.setSoLuong(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUANTITY)));
                product.setSoLuongDaBan(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_SOLD)));
                product.setNgayThayDoiTrangThai(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE)));
                product.setImage_url(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE_URL)));
                products.add(product);
            }
            cursor.close();
        }
        return products;
    }
    public void addSampleProducts() {
        for (int i = 1; i <= 8; i++) {
            SanPham product = new SanPham(
                    0,
                    "Sản phẩm " + i,
                    "Mô tả sản phẩm " + i,
                    100000 + i * 10000,
                    "Danh mục " + i,
                    10 + i,
                    5 + i,
                    "2023-10-0" + i,
                    "https://example.com/image" + i + ".jpg"
            );
            addProduct(product);
        }
    }
    public long addProduct(SanPham product) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, product.getTenSanPham());
        values.put(DatabaseHelper.COLUMN_DESCRIPTION, product.getMoTa());
        values.put(DatabaseHelper.COLUMN_PRICE, product.getGia());
        values.put(DatabaseHelper.COLUMN_CATEGORY, product.getMaDanhMuc());
        values.put(DatabaseHelper.COLUMN_QUANTITY, product.getSoLuong());
        values.put(DatabaseHelper.COLUMN_SOLD, product.getSoLuongDaBan());
        values.put(DatabaseHelper.COLUMN_DATE, product.getNgayThayDoiTrangThai());
        values.put(DatabaseHelper.COLUMN_IMAGE_URL, product.getImage_url());
        return database.insert(DatabaseHelper.TABLE_PRODUCTS, null, values);
    }

    public List<SanPham> getAllProducts() {
        List<SanPham> products = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_PRODUCTS, null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                SanPham product = new SanPham();
                product.setMaSanPham(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)));
                product.setTenSanPham(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)));
                product.setMoTa(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION)));
                product.setGia(cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRICE)));
                product.setMaDanhMuc(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY)));
                product.setSoLuong(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUANTITY)));
                product.setSoLuongDaBan(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_SOLD)));
                product.setNgayThayDoiTrangThai(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE)));
                product.setImage_url(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE_URL)));
                products.add(product);
            }
            cursor.close();
        }
        return products;
    }

    public int updateProduct(SanPham product) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, product.getTenSanPham());
        values.put(DatabaseHelper.COLUMN_DESCRIPTION, product.getMoTa());
        values.put(DatabaseHelper.COLUMN_PRICE, product.getGia());
        values.put(DatabaseHelper.COLUMN_CATEGORY, product.getMaDanhMuc());
        values.put(DatabaseHelper.COLUMN_QUANTITY, product.getSoLuong());
        values.put(DatabaseHelper.COLUMN_SOLD, product.getSoLuongDaBan());
        values.put(DatabaseHelper.COLUMN_DATE, product.getNgayThayDoiTrangThai());
        values.put(DatabaseHelper.COLUMN_IMAGE_URL, product.getImage_url());

        return database.update(DatabaseHelper.TABLE_PRODUCTS, values, DatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(product.getMaSanPham())});
    }

    public int deleteProduct(String productId) {
        return database.delete(DatabaseHelper.TABLE_PRODUCTS, DatabaseHelper.COLUMN_ID + " = ?", new String[]{productId});
    }
    public List<SanPham> searchProducts(String keyword) {
        List<SanPham> products = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_PRODUCTS + " WHERE " +
                DatabaseHelper.COLUMN_NAME + " LIKE ? OR " +
                DatabaseHelper.COLUMN_DESCRIPTION + " LIKE ?";
        Cursor cursor = database.rawQuery(query, new String[]{"%" + keyword + "%", "%" + keyword + "%"});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                SanPham product = new SanPham();
                product.setMaSanPham(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)));
                product.setTenSanPham(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)));
                product.setMoTa(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION)));
                product.setGia(cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRICE)));
                product.setMaDanhMuc(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY)));
                product.setSoLuong(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUANTITY)));
                product.setSoLuongDaBan(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_SOLD)));
                product.setNgayThayDoiTrangThai(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE)));
                product.setImage_url(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE_URL)));

                products.add(product);
            }
            cursor.close();
        }

        return products;
    }
}