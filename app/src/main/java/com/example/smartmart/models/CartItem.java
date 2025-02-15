package com.example.smartmart.models;

public class CartItem {
    private int maChiTietDonHang;
    private int maSanPham;
    private int maUser;
    private int soLuong;
    private double gia;

    public CartItem() {
    }

    public CartItem(int maChiTietDonHang, int maSanPham, int maUser, int soLuong, double gia) {
        this.maChiTietDonHang = maChiTietDonHang;
        this.maSanPham = maSanPham;
        this.maUser = maUser;
        this.soLuong = soLuong;
        this.gia = gia;
    }

    public int getMaChiTietDonHang() {
        return maChiTietDonHang;
    }

    public void setMaChiTietDonHang(int maChiTietDonHang) {
        this.maChiTietDonHang = maChiTietDonHang;
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
}