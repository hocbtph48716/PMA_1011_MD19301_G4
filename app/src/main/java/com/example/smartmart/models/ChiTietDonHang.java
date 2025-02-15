package com.example.smartmart.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ChiTietDonHang implements Parcelable {
    private int maChiTietDonHang;
    private String tenSanPham;
    private String imageUrl;
    private int maSanPham;
    private int maUser;
    private int soLuong;
    private double gia;
    private boolean isChecked;

    public ChiTietDonHang() {
    }

    public ChiTietDonHang(int maChiTietDonHang, String tenSanPham, String imageUrl, int maSanPham, int maUser, int soLuong, double gia, boolean isChecked) {
        this.maChiTietDonHang = maChiTietDonHang;
        this.tenSanPham = tenSanPham;
        this.imageUrl = imageUrl;
        this.maSanPham = maSanPham;
        this.maUser = maUser;
        this.soLuong = soLuong;
        this.gia = gia;
        this.isChecked = isChecked;
    }

    public int getMaChiTietDonHang() {
        return maChiTietDonHang;
    }

    public void setMaChiTietDonHang(int maChiTietDonHang) {
        this.maChiTietDonHang = maChiTietDonHang;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public int getMaUser() {
        return maUser;
    }

    public void setMaUser(int maUser) {
        this.maUser = maUser;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    protected ChiTietDonHang(Parcel in) {
        maChiTietDonHang = in.readInt();
        maSanPham = in.readInt();
        tenSanPham = in.readString();
        imageUrl = in.readString();
        maUser = in.readInt();
        soLuong = in.readInt();
        gia = in.readDouble();
        isChecked = in.readByte() != 0;
    }

    public static final Creator<ChiTietDonHang> CREATOR = new Creator<ChiTietDonHang>() {
        @Override
        public ChiTietDonHang createFromParcel(Parcel in) {
            return new ChiTietDonHang(in);
        }

        @Override
        public ChiTietDonHang[] newArray(int size) {
            return new ChiTietDonHang[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(maChiTietDonHang);
        dest.writeInt(maSanPham);
        dest.writeString(tenSanPham);
        dest.writeString(imageUrl);
        dest.writeInt(maUser);
        dest.writeInt(soLuong);
        dest.writeDouble(gia);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }
}