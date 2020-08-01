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
public class Diem {

    private int stt;
    private String ngay;
    private String maGiaoVien;
    private String maHocSinh;
    private String maMon;
    private int diemMieng1;
    private int diemMieng2;
    private int diemMieng3;
    private int diem15p1;
    private int diem15p2;
    private int diem15p3;
    private float diem1Tiet1;
    private float diem1Tiet2;
    private float diemThi;
    private float TBM;
    private boolean hocKi;

    public Diem() {
    }

    public Diem(int stt, String ngay, String maGiaoVien, String maHocSinh, String maMon, int diemMieng1,
            int diemMieng2, int diemMieng3, int diem15p1, int diem15p2, int diem15p3, float diem1Tiet1,
            float diem1Tiet2, float diemThi, float tBM, boolean hocKi) {
        super();
        this.stt = stt;
        this.ngay = ngay;
        this.maGiaoVien = maGiaoVien;
        this.maHocSinh = maHocSinh;
        this.maMon = maMon;
        this.diemMieng1 = diemMieng1;
        this.diemMieng2 = diemMieng2;
        this.diemMieng3 = diemMieng3;
        this.diem15p1 = diem15p1;
        this.diem15p2 = diem15p2;
        this.diem15p3 = diem15p3;
        this.diem1Tiet1 = diem1Tiet1;
        this.diem1Tiet2 = diem1Tiet2;
        this.diemThi = diemThi;
        this.TBM = tBM;
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

    public int getDiemMieng1() {
        return diemMieng1;
    }

    public void setDiemMieng1(int diemMieng1) {
        this.diemMieng1 = diemMieng1;
    }

    public int getDiemMieng2() {
        return diemMieng2;
    }

    public void setDiemMieng2(int diemMieng2) {
        this.diemMieng2 = diemMieng2;
    }

    public int getDiemMieng3() {
        return diemMieng3;
    }

    public void setDiemMieng3(int diemMieng3) {
        this.diemMieng3 = diemMieng3;
    }

    public int getDiem15p1() {
        return diem15p1;
    }

    public void setDiem15p1(int diem15p1) {
        this.diem15p1 = diem15p1;
    }

    public int getDiem15p2() {
        return diem15p2;
    }

    public void setDiem15p2(int diem15p2) {
        this.diem15p2 = diem15p2;
    }

    public int getDiem15p3() {
        return diem15p3;
    }

    public void setDiem15p3(int diem15p3) {
        this.diem15p3 = diem15p3;
    }

    public float getDiem1Tiet1() {
        return diem1Tiet1;
    }

    public void setDiem1Tiet1(float diem1Tiet1) {
        this.diem1Tiet1 = diem1Tiet1;
    }

    public float getDiem1Tiet2() {
        return diem1Tiet2;
    }

    public void setDiem1Tiet2(float diem1Tiet2) {
        this.diem1Tiet2 = diem1Tiet2;
    }

    public float getDiemThi() {
        return diemThi;
    }

    public void setDiemThi(float diemThi) {
        this.diemThi = diemThi;
    }

    public float getTBM() {
        return TBM;
    }

    public void setTBM(float TBM) {
        this.TBM = TBM;
    }

    public boolean isHocKi() {
        return hocKi;
    }

    public void setHocKi(boolean hocKi) {
        this.hocKi = hocKi;
    }
    
    
}
