/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package da.model;

/**
 *
 * @author BNC
 */
public class diemDG {
    private int stt;
    private String ngay;
    private String maGiaoVien;
    private String maHocSinh;
    private String maMon;
    private boolean diemTX1;
    private boolean diemTX2;
    private boolean diemTX3;
    private boolean diemDK1;
    private boolean diemDK2;
    private boolean diemHK;
    private boolean diemTBMDanhGia;
    private boolean hocKi;

    public diemDG() {
    }

    public diemDG(int stt, String ngay, String maGiaoVien, String maHocSinh, String maMon,boolean diemTX1, boolean diemTX2,boolean diemTX3,boolean diemDK1,boolean diemDK2,boolean diemHK, boolean diemTBMDanhGia, boolean hocKi) {
        super();
        this.stt = stt;
        this.ngay = ngay;
        this.maGiaoVien = maGiaoVien;
        this.maHocSinh = maHocSinh;
        this.maMon = maMon;
        this.diemTX1 = diemTX1;
        this.diemTX2 = diemTX2;
        this.diemTX3 = diemTX3;
        this.diemDK1 = diemDK1;
        this.diemDK2 = diemDK2;
        this.diemHK = diemHK;
        this.diemTBMDanhGia = diemTBMDanhGia;
        this.hocKi = hocKi;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getMaGiaoVien() {
        return maGiaoVien;
    }

    public void setMaGiaoVien(String maGiaoVien) {
        this.maGiaoVien = maGiaoVien;
    }

    public String getMaHocSinh() {
        return maHocSinh;
    }

    public void setMaHocSinh(String maHocSinh) {
        this.maHocSinh = maHocSinh;
    }

    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public boolean isDiemTX1() {
        return diemTX1;
    }

    public void setDiemTX1(boolean diemTX1) {
        this.diemTX1 = diemTX1;
    }

    public boolean isDiemTX2() {
        return diemTX2;
    }

    public void setDiemTX2(boolean diemTX2) {
        this.diemTX2 = diemTX2;
    }

    public boolean isDiemTX3() {
        return diemTX3;
    }

    public void setDiemTX3(boolean diemTX3) {
        this.diemTX3 = diemTX3;
    }

    public boolean isDiemDK1() {
        return diemDK1;
    }

    public void setDiemDK1(boolean diemDK1) {
        this.diemDK1 = diemDK1;
    }

    public boolean isDiemDK2() {
        return diemDK2;
    }

    public void setDiemDK2(boolean diemDK2) {
        this.diemDK2 = diemDK2;
    }

    public boolean isDiemHK() {
        return diemHK;
    }

    public void setDiemHK(boolean diemHK) {
        this.diemHK = diemHK;
    }

    public boolean isDiemTBMDanhGia() {
        return diemTBMDanhGia;
    }

    public void setDiemTBMDanhGia(boolean diemTBMDanhGia) {
        this.diemTBMDanhGia = diemTBMDanhGia;
    }

    public boolean isHocKi() {
        return hocKi;
    }

    public void setHocKi(boolean hocKi) {
        this.hocKi = hocKi;
    }
    
    
}
