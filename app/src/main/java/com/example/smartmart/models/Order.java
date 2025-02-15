package com.example.smartmart.models;

import java.io.Serializable;

public class Order implements Serializable {
    private int maDonHang;
    private int maUser;
    private String ngayDatHang;
    private double tongGia;
    private String trangThai;
    private String diaChiDatHang;
    private String phuongThucThanhToan;

    public int getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(int maDonHang) {
        this.maDonHang = maDonHang;
    }

    public int getMaUser() {
        return maUser;
    }

    public void setMaUser(int maUser) {
        this.maUser = maUser;
    }

    public String getNgayDatHang() {
        return ngayDatHang;
    }

    public void setNgayDatHang(String ngayDatHang) {
        this.ngayDatHang = ngayDatHang;
    }

    public double getTongGia() {
        return tongGia;
    }

    public void setTongGia(double tongGia) {
        this.tongGia = tongGia;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getDiaChiDatHang() {
        return diaChiDatHang;
    }

    public void setDiaChiDatHang(String diaChiDatHang) {
        this.diaChiDatHang = diaChiDatHang;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public Order(int maDonHang, int maUser, String ngayDatHang, double tongGia, String trangThai, String diaChiDatHang, String phuongThucThanhToan) {
        this.maDonHang = maDonHang;
        this.maUser = maUser;
        this.ngayDatHang = ngayDatHang;
        this.tongGia = tongGia;
        this.trangThai = trangThai;
        this.diaChiDatHang = diaChiDatHang;
        this.phuongThucThanhToan = phuongThucThanhToan;
    }
}