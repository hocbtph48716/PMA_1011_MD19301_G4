package com.example.smartmart.models;

public class SanPham {
    private int maSanPham;
    private String tenSanPham;
    private String moTa;
    private double gia;
    private String maDanhMuc;
    private int soLuong;
    private int soLuongDaBan;
    private String ngayThayDoiTrangThai;
    private String image_url;

    // Constructor
    public SanPham(int maSanPham, String tenSanPham, String moTa, double gia,
                   String maDanhMuc, int soLuong, int soLuongDaBan,
                   String ngayThayDoiTrangThai, String image_url) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.moTa = moTa;
        this.gia = gia;
        this.maDanhMuc = maDanhMuc;
        this.soLuong = soLuong;
        this.soLuongDaBan = soLuongDaBan;
        this.ngayThayDoiTrangThai = ngayThayDoiTrangThai;
        this.image_url = image_url;
    }

    // Empty constructor for Firebase
    public SanPham() {
    }

    // Getters and Setters
    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(String maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getSoLuongDaBan() {
        return soLuongDaBan;
    }

    public void setSoLuongDaBan(int soLuongDaBan) {
        this.soLuongDaBan = soLuongDaBan;
    }

    public String getNgayThayDoiTrangThai() {
        return ngayThayDoiTrangThai;
    }

    public void setNgayThayDoiTrangThai(String ngayThayDoiTrangThai) {
        this.ngayThayDoiTrangThai = ngayThayDoiTrangThai;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}