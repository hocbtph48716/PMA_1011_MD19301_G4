package com.example.smartmart.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.smartmart.models.Order;
import com.example.smartmart.models.OrderDetail;
import com.example.smartmart.models.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smartmart1.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PRODUCTS = "SanPham";
    public static final String COLUMN_ID = "maSanPham";
    public static final String COLUMN_NAME = "tenSanPham";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PRICE = "gia";
    public static final String COLUMN_CATEGORY = "danhMuc";
    public static final String COLUMN_QUANTITY = "soLuong";

    public static final String COLUMN_SOLD = "sold";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_IMAGE_URL = "image_url";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_PRICE + " REAL, " +
                    COLUMN_CATEGORY + " TEXT, " +
                    COLUMN_QUANTITY + " INTEGER, " +
                    COLUMN_SOLD + " INTEGER, " +
                    COLUMN_DATE + " TEXT, " +
                    COLUMN_IMAGE_URL + " TEXT" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL("CREATE TABLE User (\n" +
                "    maUser INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    passWord TEXT NOT NULL,\n" +
                "    nickName TEXT,\n" +
                "    email TEXT,\n" +
                "    soDienThoai TEXT,\n" +
                "    diaChi TEXT,\n" +
                "    vaiTro TEXT\n" +
                ");");
        db.execSQL("INSERT INTO User (maUser,passWord,nickName,email,soDienThoai,diaChi,vaiTro)" +

                "VALUES (1,'thong212002','Kim Thong','thongnk21@gmail.com','0362014553','Phương Bản, Phụng Châu, Chương Mỹ, Hà Nội','ADMIN')," +
                "(2,'hocbt123','Thái Học','hocbuj2001@gmail.com','0969097521','Đông Lâm,Tiền Hải, Thái Bình','KhachHang')");


        db.execSQL("CREATE TABLE YeuThich (\n" +
                "    maYeuThich INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    maKhachHang INTEGER,\n" +
                "    maSanPham INTEGER,\n" +
                "    tenSanPham TEXT\n" +
                ");");
        db.execSQL("CREATE TABLE LichSuBanHang (\n" +
                "    maLichSu INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "    maSanPham INTEGER,\n" +
                "    soLuong INTEGER,\n" +
                "    ngayBanHang TEXT,\n" +
                "    FOREIGN KEY (maSanPham) REFERENCES SanPham(maSanPham)\n" +
                ");");
        db.execSQL("CREATE TABLE DanhGia (\n" +
                "    maDanhGia INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    maKhachHang INTEGER,\n" +
                "    maSanPham INTEGER,\n" +
                "    danhGia INTEGER,\n" +
                "    binhLuan TEXT,\n" +
                "    ngayDanhGia TEXT\n" +
                ");");
        db.execSQL("CREATE TABLE ChiTietDonHang (\n" +
                "    maChiTietDonHang INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    image_url Text,\n"+
                "    maSanPham INTEGER,\n" +
                "    tenSanPham Text,\n"+
                "    maUser INTEGER,\n" +
                "    soLuong INTEGER,\n" +
                "    isChecked boolean,\n" +
                "    gia REAL\n" +
                ");");
        db.execSQL("CREATE TABLE DonHang (\n" +
                "maDonHang INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maChiTietDonHang INTEGER, " +
                "maUser INTEGER, " +
                "ngayDatHang TEXT, " +
                "trangThai INTEGER, " +
                "diaChiDatHang TEXT,"+
                "ngayThayDoiTrangThai TEXT, " +
                "tongGia REAL, " +
                "phuongThucThanhToan TEXT," +
                "    FOREIGN KEY (maChiTietDonHang) REFERENCES ChiTietDonHang(maChiTietDonHang),\n" +
                "    FOREIGN KEY (maUser) REFERENCES User(maUser)\n" +
                ");");
        String createSanPhamTrongDonHangTable = "CREATE TABLE SanPhamTrongDonHang (" +
                "maSanPhamTrongDonHang INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maDonHang INTEGER, " +
                "maSanPham INTEGER, " +
                "tenSanPham TEXT,"+
                "soLuong INTEGER, " +
                "gia Real,"+
                "image_url TEXT,"+
                "FOREIGN KEY (maDonHang) REFERENCES DonHang(maDonHang), " +
                "FOREIGN KEY (maSanPham) REFERENCES SanPham(maSanPham)" +
                ")";
        db.execSQL(createSanPhamTrongDonHangTable);
        db.execSQL("CREATE TABLE DanhMucSanPham (\n" +
                "    maDanhMuc INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    tenDanhMuc TEXT NOT NULL\n" +
                ");");
        db.execSQL("INSERT INTO DanhMucSanPham(maDanhMuc,tenDanhMuc)" +
                "VALUES(0,'IPhone')," +
                "(1,'SamSung')," +
                "(2,'XiaoMi')," +
                "(3,'Sony')," +
                "(4,'Oppo')," +
                "(5,'HuaWei')");
        db.execSQL("INSERT INTO SanPham (maSanPham,tenSanPham,description,gia,danhMuc,soLuong,sold,date,image_url) " +

                "VALUES (1,'IPhone 16 ProMax 256GB','iPhone 16 series mang đến nhiều nâng cấp quan trọng so với iPhone 15 series, từ hiệu năng, camera, đến các tính năng tiên tiến khác. Được trang bị chip A18 mạnh mẽ hơn, iPhone 16 mang lại hiệu suất vượt trội so với iPhone 15 với chip A16, giúp cải thiện khả năng xử lý đồ họa và tiết kiệm năng lượng tốt hơn\u200B.\n" +
                "\n" +
                "iPhone 16 mang đến sự đột phá với camera \"Fusion\" 48 MP, giúp tạo ra những bức ảnh rõ nét, đặc biệt khi thiếu sáng. Tính năng quay video không gian và chụp ảnh macro biến những khoảnh khắc thành ảnh và video 3D sống động. Nổi bật không kém là nút Camera Control, hỗ trợ thao tác nhanh chóng và điều khiển cảm ứng, đồng thời tương thích với nhiều ứng dụng bên thứ ba.\n Chip Apple A18 Pro 6 nhân\n" +
                "\n" +
                "RAM: 8 GB\n" +
                "\n" +
                "Dung lượng: 256 GB\n" +
                "\n" +
                "Camera sau: Chính 48 MP & Phụ 48 MP, 12 MP\n" +
                "\n" +
                "Camera trước: 12 MP\n" +
                "\n" +
                "Pin 33 giờ, Sạc 20 W',34490000,'IPhone',50,10,'2024-11-19','https://cdn.tgdd.vn/Products/Images/42/329149/iphone-16-pro-max-black-thumb-600x600.jpg')");

        db.execSQL("INSERT INTO SanPham (maSanPham,tenSanPham,description,gia,danhMuc,soLuong,sold,date,image_url) " +
                "VALUES (2,'IPhone 15 ProMax 256GB','Diện mạo đẳng cấp và cực kỳ sang trọng\n" +
                "iPhone 15 Pro Max tiếp tục sẽ là một chiếc điện thoại có màn hình và mặt lưng phẳng đặc trưng đến từ nhà Apple, mang lại vẻ đẹp thanh lịch và sang trọng.\n" +
                "\n" +
                "Chất liệu chủ đạo của iPhone 15 Pro Max vẫn là khung kim loại và mặt lưng kính cường lực, tạo nên sự bền bỉ và chắc chắn. Tuy nhiên, với công nghệ tiên tiến, khung này đã được nâng cấp thành chất liệu titanium thay vì thép không gỉ hay nhôm ở những thế hệ trước." +

                "\n" +
                "Chip Apple A17 Pro 6 nhân\n" +
                "\n" +
                "RAM: 8 GB\n" +
                "\n" +
                "Dung lượng: 512 GB\n" +
                "\n" +
                "Camera sau: Chính 48 MP & Phụ 12 MP, 12 MP\n" +
                "\n" +
                "Camera trước: 12 MP\n" +
                "\n" +
                "Pin 4422 mAh, Sạc 20 W',25990000,'IPhone',50,10,'2024-06-12','https://cdn.tgdd.vn/Products/Images/42/305659/iphone-15-pro-max-black-thumbnew-600x600.jpg')");
        db.execSQL("INSERT INTO SanPham (maSanPham,tenSanPham,description,gia,danhMuc,soLuong,sold,date,image_url) " +

                "VALUES (3,'IPhone 14','iPhone 14 128GB được xem là mẫu sxmartphone bùng nổ của nhà táo trong năm 2022, ấn tượng với ngoại hình trẻ trung, màn hình chất lượng đi kèm với những cải tiến về hệ điều hành và thuật toán xử lý hình ảnh, giúp máy trở thành cái tên thu hút được đông đảo người dùng quan tâm tại thời điểm ra mắt.\n Chip Apple A15 Bionic\n" +
                "\n" +
                "RAM: 6 GB\n" +
                "\n" +
                "Dung lượng: 128 GB\n" +
                "\n" +
                "Camera sau: 2 camera 12 MP\n" +
                "\n" +
                "Camera trước: 12 MP\n" +
                "\n" +
                "Pin 3279 mAh, Sạc 20 W',17590000,'IPhone',50,10,'2024-11-19','https://cdn.tgdd.vn/Products/Images/42/240259/iPhone-14-thumb-tim-1-600x600.jpg')");



    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS DonHang");
        onCreate(db);
    }

    // Lấy danh sách tất cả đơn hàng
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM DonHang ORDER BY ngayDatHang DESC";
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    int maDonHang = cursor.getInt(cursor.getColumnIndexOrThrow("maDonHang"));
                    int maUser = cursor.getInt(cursor.getColumnIndexOrThrow("maUser"));
                    String ngayDatHang = cursor.getString(cursor.getColumnIndexOrThrow("ngayDatHang"));
                    double tongGia = cursor.getDouble(cursor.getColumnIndexOrThrow("tongGia"));
                    String trangThai = cursor.getString(cursor.getColumnIndexOrThrow("trangThai"));
                    String diaChiDatHang = cursor.getString(cursor.getColumnIndexOrThrow("diaChiDatHang"));
                    String phuongThucThanhToan = cursor.getString(cursor.getColumnIndexOrThrow("phuongThucThanhToan"));
                    orders.add(new Order(maDonHang, maUser,ngayDatHang, tongGia, trangThai,diaChiDatHang,phuongThucThanhToan));
                } while (cursor.moveToNext());
            }

            // Ghi log kiểm tra số lượng đơn hàng
            System.out.println("Số lượng đơn hàng: " + orders.size());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
        }
        return orders;
    }
    public User getUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("User", null, "maUser = ?", new String[]{String.valueOf(userId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            User user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow("maUser")),
                    cursor.getString(cursor.getColumnIndexOrThrow("passWord")),
                    cursor.getString(cursor.getColumnIndexOrThrow("nickName")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("soDienThoai")),
                    cursor.getString(cursor.getColumnIndexOrThrow("diaChi")),
                    cursor.getString(cursor.getColumnIndexOrThrow("vaiTro"))
            );
            cursor.close();
            return user;
        }
        return null;
    }

    public void updateOrderStatus(int maDonHang, int newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("trangThai", newStatus);
        db.update("DonHang", values, "maDonHang = ?", new String[]{String.valueOf(maDonHang)});
        db.close();
    }
    public List<Order> getOrdersByUser(int maUser, int... statuses) {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        StringBuilder query = new StringBuilder("SELECT * FROM DonHang WHERE maUser = ? AND trangThai IN (");
        for (int i = 0; i < statuses.length; i++) {
            query.append(statuses[i]);
            if (i < statuses.length - 1) {
                query.append(",");
            }
        }
        query.append(")");
        Cursor cursor = db.rawQuery(query.toString(), new String[]{String.valueOf(maUser)});
        if (cursor.moveToFirst()) {
            do {
                Order order = new Order(
                        cursor.getInt(cursor.getColumnIndexOrThrow("maDonHang")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("maUser")),
                        cursor.getString(cursor.getColumnIndexOrThrow("ngayDatHang")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("tongGia")),
                        cursor.getString(cursor.getColumnIndexOrThrow("trangThai")),
                        cursor.getString(cursor.getColumnIndexOrThrow("diaChiDatHang")),
                        cursor.getString(cursor.getColumnIndexOrThrow("phuongThucThanhToan"))
                );
                orders.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return orders;
    }







    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE email = ?", new String[]{email});
        if (cursor != null && cursor.moveToFirst()) {
            User user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow("maUser")),
                    cursor.getString(cursor.getColumnIndexOrThrow("passWord")),
                    cursor.getString(cursor.getColumnIndexOrThrow("nickName")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("soDienThoai")),
                    cursor.getString(cursor.getColumnIndexOrThrow("diaChi")),
                    cursor.getString(cursor.getColumnIndexOrThrow("vaiTro"))
            );
            cursor.close();
            return user;
        }
        return null;
    }

    public Order getOrderById(int orderId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("DonHang", null, "MaDonHang = ?", new String[]{String.valueOf(orderId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Order order = new Order(
            cursor.getInt(cursor.getColumnIndexOrThrow("maDonHang")),
            cursor.getInt(cursor.getColumnIndexOrThrow("maUser")),
            cursor.getString(cursor.getColumnIndexOrThrow("ngayDatHang")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("tongGia")),
            cursor.getString(cursor.getColumnIndexOrThrow("trangThai")),
            cursor.getString(cursor.getColumnIndexOrThrow("diaChiDatHang")),
            cursor.getString(cursor.getColumnIndexOrThrow("phuongThucThanhToan")
            ));
            cursor.close();
            return order;
        }
        return null;
    }

    // Method to get product details by order ID
    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("SanPhamTrongDonHang", null, "MaDonHang = ?", new String[]{String.valueOf(orderId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                OrderDetail orderDetail = new OrderDetail(
                        cursor.getString(cursor.getColumnIndexOrThrow("tenSanPham")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("soLuong")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("gia")),
                        cursor.getString(cursor.getColumnIndexOrThrow("image_url"))
                );
                orderDetails.add(orderDetail);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return orderDetails;
    }
}