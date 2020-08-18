/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package da.model;

import da.helper.DateHelper;
import java.util.Date;

/**
 *
 * @author Administrator
 */
public class HocSinh {
    private String maHocSinh;
    private String hoTen;
    private boolean gioiTinh;
    private Date ngaySinh;
    private String diaChi;
    private String dienThoai;
    private String email;
    private String cmnd;
    private String lop;
    private String danToc;
    private String tongiao;
    private Date ngayVaoDoan;
    private String trangThai;
    private String htBO;
    private String htMe;
    private String sdtBo;
    private String sdtMe;
    private String dvctBo;
    private String dvctMe;
    private  boolean xoa;
    private byte[] picture;

    public HocSinh() {
    }

    public HocSinh(String maHocSinh, String hoTen, boolean gioiTinh, Date ngaySinh, String diaChi, String dienThoai, String email, String cmnd, String lop, String danToc, String tongiao, Date ngayVaoDoan, String trangThai, String htBO, String htMe, String sdtBo, String sdtMe, String dvctBo, String dvctMe, boolean xoa, byte[] image) {
        this.maHocSinh = maHocSinh;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.dienThoai = dienThoai;
        this.email = email;
        this.cmnd = cmnd;
        this.lop = lop;
        this.danToc = danToc;
        this.tongiao = tongiao;
        this.ngayVaoDoan = ngayVaoDoan;
        this.trangThai = trangThai;
        this.htBO = htBO;
        this.htMe = htMe;
        this.sdtBo = sdtBo;
        this.sdtMe = sdtMe;
        this.dvctBo = dvctBo;
        this.dvctMe = dvctMe;
        this.xoa = xoa;
        this.picture = picture;
    }

    public String getMaHocSinh() {
        return maHocSinh;
    }

    public void setMaHocSinh(String maHocSinh) {
        this.maHocSinh = maHocSinh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public boolean getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getDanToc() {
        return danToc;
    }

    public void setDanToc(String danToc) {
        this.danToc = danToc;
    }

    public String getTongiao() {
        return tongiao;
    }

    public void setTongiao(String tongiao) {
        this.tongiao = tongiao;
    }

    public Date getNgayVaoDoan() {
        return ngayVaoDoan;
    }

    public void setNgayVaoDoan(Date ngayVaoDoan) {
        this.ngayVaoDoan = ngayVaoDoan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getHtBO() {
        return htBO;
    }

    public void setHtBO(String htBO) {
        this.htBO = htBO;
    }

    public String getHtMe() {
        return htMe;
    }

    public void setHtMe(String htMe) {
        this.htMe = htMe;
    }

    public String getSdtBo() {
        return sdtBo;
    }

    public void setSdtBo(String sdtBo) {
        this.sdtBo = sdtBo;
    }

    public String getSdtMe() {
        return sdtMe;
    }

    public void setSdtMe(String sdtMe) {
        this.sdtMe = sdtMe;
    }

    public String getDvctBo() {
        return dvctBo;
    }

    public void setDvctBo(String dvctBo) {
        this.dvctBo = dvctBo;
    }

    public String getDvctMe() {
        return dvctMe;
    }

    public void setDvctMe(String dvctMe) {
        this.dvctMe = dvctMe;
    }

    public boolean isXoa() {
        return xoa;
    }

    public void setXoa(boolean xoa) {
        this.xoa = xoa;
    }

    public byte[] getImage() {
        return picture;
    }

    public void setImage(byte[] picture) {
        this.picture = picture;
    }

  

  

    
    
    
    

   
    

  
   
   
    
   
    
    
    
    
}
