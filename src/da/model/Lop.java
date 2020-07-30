/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package da.model;

/**
 *
 * @author Administrator
 */
public class Lop {
    private String maLop;
    private String tenLop;
    private String maGiaoVien;
    private String namHoc;

    public Lop() {
    }

    public Lop(String maLop, String tenLop, String maGiaoVien, String namHoc) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.maGiaoVien = maGiaoVien;
        this.namHoc = namHoc;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public String getMaGiaoVien() {
        return maGiaoVien;
    }

    public void setMaGiaoVien(String maGiaoVien) {
        this.maGiaoVien = maGiaoVien;
    }

    public String getNamHoc() {
        return namHoc;
    }

    public void setNamHoc(String namHoc) {
        this.namHoc = namHoc;
    }
    
    
    
}
