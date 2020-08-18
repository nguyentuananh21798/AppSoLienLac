/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import da.dao.DiemDanhDAO;
import da.dao.DiemDAO;
import da.dao.HocSinhDAO;
import da.dao.LopDAO;
import da.dao.MonDAO;
import da.helper.CsvFile;
import da.helper.DialogHelper;
import da.model.Diem;
import da.model.HocSinh;
import da.model.Lop;
import da.model.Mon;
import da.model.diemDG;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Rahmans
 */
public class qlydiem extends javax.swing.JFrame {

    static boolean maximized = true;
    int xMouse;
    int yMouse;
    String head[] = {"Mã học sinh", "Họ và tên", "Ngày sinh", "Điểm miệng 1", "Điểm miệng 2", "Điểm miệng 3", "Điểm 15p 1", "Điểm 15p 2", "Điểm 15p 3", "Điểm 1 tiết 1", "Điểm 1 tiết 2", "Điểm HK", "Điểm TB"};
    String head1[] = {"Mã học sinh", "Họ và tên", "Ngày sinh", "Điểm KT thường xuyên 1", "Điểm KT thường xuyên 2", "Điểm KT thường xuyên 3", "Điểm KT định kì 1", "Điểm KT định kì 2", "Điểm học kì", "Điểm TBM"};
    String head2[] = {"Mã học sinh", "Họ và tên", "Ngày sinh", "Điểm học kì 1", "Điểm học kì 2", "Tổng Kết", "Học Lực", "Danh Hiệu", "Vắng có phép", "Vắng Không Phép", "Hạnh Kiểm", "Ghi Chú"};
    DefaultTableModel model = new DefaultTableModel(head, 0);
    DefaultTableModel model1 = new DefaultTableModel(head1, 0);
    DefaultTableModel model2 = new DefaultTableModel(head2, 0);
    DiemDAO dDao = new DiemDAO();
    MonDAO mDAO = new MonDAO();
    LopDAO lDAO = new LopDAO();
    HocSinhDAO hDAO = new HocSinhDAO();
    DiemDanhDAO dgDAO = new DiemDanhDAO();
      CsvFile fv = new CsvFile();

    public qlydiem() {
        initComponents();
        full();
        this.loadToCboLop();
        this.loadToCboMon();
        this.loadToCboMonDG();
        this.tuChoiNhap();
        String tenLop = cbo_LopTK.getSelectedItem().toString();
        this.loadTableResult(tenLop);
    }

    public void loadToCboMon() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbo_Mon1.getModel();
        model.removeAllElements();
        try {
            List<Mon> list = mDAO.select();
            for (Mon dv : list) {
                model.addElement(dv.getTenMon());
                cbo_Mon1.setModel(model);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi 1");
        }
    }

    public void loadToCboMonDG() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbo_MonDG.getModel();
        model.removeAllElements();
        try {
            List<Mon> list = mDAO.selectByDG();
            for (Mon dv : list) {
                model.addElement(dv.getTenMon());
                cbo_MonDG.setModel(model);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi 2");
        }
    }

    void loadToCboLop() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbo_Lop1.getModel();
        DefaultComboBoxModel model1 = (DefaultComboBoxModel) cbo_LopDG.getModel();
        DefaultComboBoxModel model2 = (DefaultComboBoxModel) cbo_LopTK.getModel();
        model.removeAllElements();
        model1.removeAllElements();
        model2.removeAllElements();
        try {
            List<Lop> list = lDAO.select();
            for (Lop dv : list) {
                model.addElement(dv.getTenLop());
                model1.addElement(dv.getTenLop());
                model2.addElement(dv.getTenLop());
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi 3");
        }
    }

    public void timKiem1(String tenHs, String maMon, boolean ki) {
        model.setRowCount(0);
        try {
            if (tenHs.isEmpty()) {
                DialogHelper.alert(this, "Không có dữ liệu cần tìm");
                model.setRowCount(0);
            } else {
                  HocSinh TT = hDAO.selectByKeyword(tenHs);
                Diem diem = dDao.findByHSId(TT.getMaHocSinh(), maMon, ki);
                Vector row = new Vector();
                row.add(TT.getMaHocSinh());
                row.add(TT.getHoTen());
                row.add(TT.getNgaySinh());
                row.add(diem.getDiemMieng1());
                row.add(diem.getDiemMieng2());
                row.add(diem.getDiemMieng3());
                row.add(diem.getDiem15p1());
                row.add(diem.getDiem15p2());
                row.add(diem.getDiem15p3());
                row.add(diem.getDiem1Tiet1());
                row.add(diem.getDiem1Tiet2());
                row.add(diem.getDiemThi());
                row.add(diem.getTBM());
                model.addRow(row);
                DialogHelper.alert(this, "thành công");
            }

        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi 4");
            e.printStackTrace();
        }
    }

    public void loadToTable(String maLop, String maMon, boolean ki) {
        model.setRowCount(0);
        try {
            ResultSet rs = dDao.findByEve(maLop, maMon, ki);
            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString("mahocsinh"));
                row.add(rs.getString("hoten"));
                row.add(rs.getString("ngaysinh"));
                row.add(rs.getInt("diemMieng1"));
                row.add(rs.getInt("diemMieng2"));
                row.add(rs.getInt("diemMieng3"));
                row.add(rs.getInt("diem15phut1"));
                row.add(rs.getInt("diem15phut2"));
                row.add(rs.getInt("diem15phut3"));
                row.add(rs.getFloat("diem1Tiet1"));
                row.add(rs.getFloat("diem1Tiet2"));
                row.add(rs.getFloat("diemthi"));
                row.add(rs.getFloat("diemTBM"));
                model.addRow(row);
            }
            tblGridView.setModel(model);
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi load data nhập điểm");
            e.printStackTrace();
        }
    }

    public void loadTableResult(String tenLop) {
        model2.setRowCount(0);
        try {
            ResultSet rs = dDao.kqHk1(tenLop);
            ResultSet rs2 = dDao.kqHk2(tenLop);
            while (rs.next() && rs2.next()) {
                Vector row = new Vector();
                row.add(rs.getString("mahocsinh"));
                row.add(rs.getString("hoten"));
                row.add(rs.getString("ngaysinh"));
                row.add(rs.getFloat("TBhocKi1"));
                row.add(rs2.getFloat("TBhocKi2"));
                Float rate = (rs.getFloat("TBhocKi1") + rs2.getFloat("TBhocKi2")) / 2;
                row.add((double) Math.round(rate * 10) / 10);
                if (rate >= 8) {
                    row.add("Giỏi");
                    row.add("Học Sinh Giỏi");
                } else if (rate >= 7 && rate < 8) {
                    row.add("Khá");
                    row.add("Học Sinh Khá");
                } else if (rate >= 5 && rate < 7) {
                    row.add("Trung Bình");
                    row.add("Không Có");
                } else if (rate < 5) {
                    row.add("Yếu");
                    row.add("Không Có");
                }
                int sobuoinghicp = dgDAO.selectNghiCoPhep(rs.getString("mahocsinh"));
                row.add(sobuoinghicp);
                int sobuoinghikp = dgDAO.selectNghiKoCoPhep(rs.getString("mahocsinh"));
                row.add(sobuoinghikp);
                int tongNghi = sobuoinghicp + sobuoinghikp;
                if (tongNghi >= 30) {
                    row.add("Yếu");
                } else if (tongNghi >= 10 && tongNghi < 30) {
                    row.add("Trung Bình");
                } else if (tongNghi >= 5 && tongNghi < 10) {
                    row.add("Khá");
                } else if (tongNghi < 5) {
                    row.add("Tốt");
                }

                if (tongNghi > 20 || rate < 5 && rate >= 3) {
                    row.add("rèn luyện lại");
                } else if (tongNghi > 20 || rate < 3) {
                    row.add("Lưu Ban");
                } else {
                    row.add("");
                }
                model2.addRow(row);
            }
            tblGridView_Kq.setModel(model2);
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi load data tổng kết");
            e.printStackTrace();
        }
    }

    public void loadToTableDG(String maLop, String maMon, boolean ki) {
        model1.setRowCount(0);
        try {
            ResultSet rs = dDao.findDG(maLop, maMon, ki);
            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString("mahocsinh"));
                row.add(rs.getString("hoten"));
                row.add(rs.getString("ngaysinh"));
                row.add(rs.getBoolean("diemTX1"));
                row.add(rs.getBoolean("diemTX2"));
                row.add(rs.getBoolean("diemTX3"));
                row.add(rs.getBoolean("diemDK1"));
                row.add(rs.getBoolean("diemDK2"));
                row.add(rs.getBoolean("diemHK"));
                row.add(rs.getBoolean("diemTBMdanhgia"));
                model1.addRow(row);
            }
            tblGridView_DG.setModel(model1);
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi load data DG");
            e.printStackTrace();
        }
    }

    public void LoadClass(String maLop) {
        model.setRowCount(0);
        try {
            ResultSet rs = dDao.findByClass(maLop);
            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString("mahocsinh"));
                row.add(rs.getString("hoten"));
                row.add(rs.getString("ngaysinh"));
                row.add(0);
                row.add(0);
                row.add(0);
                row.add(0);
                row.add(0);
                row.add(0);
                row.add(0);
                row.add(0);
                row.add(0);
                row.add(0);
                model.addRow(row);
            }
            tblGridView.setModel(model);
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi load lớp nhập điểm");
            e.printStackTrace();
        }
    }
    
    
    public int countClass(String maLop){  
        try {
            int i =0;
            ResultSet rs = dDao.findByClass(maLop);
            while (rs.next()) {
                i++;
            }
            return i;
        }catch (Exception e) {
            DialogHelper.alert(this, "Lỗi load lớp nhập điểm");
            e.printStackTrace();
        }
        return 0;
    }
    
    public void LoadNewDataDiem(String tenLop,String maMon, boolean ki){
        try {
            ResultSet rs = dDao.LoadNewData(tenLop,maMon, ki);
            while (rs.next()) {
                Diem themdiem = new Diem();
                themdiem.setNgay(LocalDate.now().toString());
                themdiem.setMaGiaoVien("gv01");
                themdiem.setMaHocSinh(rs.getString("mahocsinh"));
                System.out.println(rs.getString("mahocsinh"));
                themdiem.setMaMon(mDAO.selectByName(cbo_Mon1.getSelectedItem().toString()).getMaMon());
                themdiem.setDiemMieng1(0);
                themdiem.setDiemMieng2(0);
                themdiem.setDiemMieng3(0);
                themdiem.setDiem15p1(0);
                themdiem.setDiem15p2(0);
                themdiem.setDiem15p3(0);
                themdiem.setDiem1Tiet1(0);
                themdiem.setDiem1Tiet2(0);
                themdiem.setDiemThi(0);
                themdiem.setTBM(0);
                String hk = cbo_hocKi.getSelectedItem().toString();
                
                if (hk.equals("Học Kỳ 1")) {
                    themdiem.setHocKi(false);
                } else {
                    themdiem.setHocKi(true);
                }
                dDao.insert(themdiem);
            }
      
        }catch (Exception e) {
            DialogHelper.alert(this, "Lỗi load data moi lớp nhập điểm");
            e.printStackTrace();
        }
    }
    
    public void LoadNewDataDG(String tenLop, String maMon, boolean ki){
        try {
            ResultSet rs = dDao.LoadNewData(tenLop,maMon,ki);
            while (rs.next()) {
                diemDG themdiem = new diemDG();
                themdiem.setNgay(LocalDate.now().toString());
                themdiem.setMaGiaoVien("gv01");
                themdiem.setMaHocSinh(rs.getString("mahocsinh"));
                System.out.println(rs.getString("mahocsinh"));
                themdiem.setMaMon(mDAO.selectByName(cbo_MonDG.getSelectedItem().toString()).getMaMon());
                themdiem.setDiemTX1(false);
                themdiem.setDiemTX2(false);
                themdiem.setDiemTX3(false);
                themdiem.setDiemDK1(false);
                themdiem.setDiemDK2(false);
                themdiem.setDiemHK(false);
                themdiem.setDiemTBMDanhGia(false);
                String hk = cbo_hocKi.getSelectedItem().toString();
                if (hk.equals("Học Kỳ 1")) {
                    themdiem.setHocKi(false);
                } else {
                    themdiem.setHocKi(true);
                }
                dDao.insertDG(themdiem);
            }
      
        }catch (Exception e) {
            DialogHelper.alert(this, "Lỗi load data moi lớp nhập điểm DG");
            e.printStackTrace();
        }
    }

    public void LoadClassDG(String maLop) {
        model1.setRowCount(0);
        try {
            ResultSet rs = dDao.findByClass(maLop);
            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString("mahocsinh"));
                row.add(rs.getString("hoten"));
                row.add(rs.getString("ngaysinh"));
                row.add("false");
                row.add("false");
                row.add("false");
                row.add("false");
                row.add("false");
                row.add("false");
                row.add("false");
                model1.addRow(row);
            }
            tblGridView_DG.setModel(model1);
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi load lớp DG");
            e.printStackTrace();
        }
    }

    public void themL1() {
        try {
            for (int i = 0; i < tblGridView.getRowCount(); i++) {
                Diem themdiem = new Diem();
                themdiem.setNgay(LocalDate.now().toString());
                themdiem.setMaGiaoVien("gv01");
                themdiem.setMaHocSinh((tblGridView.getValueAt(i, 0).toString()));
                themdiem.setMaMon(mDAO.selectByName(cbo_Mon1.getSelectedItem().toString()).getMaMon());
                themdiem.setDiemMieng1(Integer.parseInt(tblGridView.getValueAt(i, 3).toString()));
                themdiem.setDiemMieng2(Integer.parseInt(tblGridView.getValueAt(i, 4).toString()));
                themdiem.setDiemMieng3(Integer.parseInt(tblGridView.getValueAt(i, 5).toString()));
                themdiem.setDiem15p1(Integer.parseInt(tblGridView.getValueAt(i, 6).toString()));
                themdiem.setDiem15p2(Integer.parseInt(tblGridView.getValueAt(i, 7).toString()));
                themdiem.setDiem15p3(Integer.parseInt(tblGridView.getValueAt(i, 8).toString()));
                themdiem.setDiem1Tiet1(Float.parseFloat(tblGridView.getValueAt(i, 9).toString()));
                themdiem.setDiem1Tiet2(Float.parseFloat(tblGridView.getValueAt(i, 10).toString()));
                themdiem.setDiemThi(Float.parseFloat(tblGridView.getValueAt(i, 11).toString()));
                themdiem.setTBM(Float.parseFloat(tblGridView.getValueAt(i, 12).toString()));
                String hk = cbo_hocKi.getSelectedItem().toString();
                
                if (hk.equals("Học Kỳ 1")) {
                    themdiem.setHocKi(false);
                } else {
                    themdiem.setHocKi(true);
                }
                dDao.insert(themdiem);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi 5");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void themDGL1() {
        try {
            for (int i = 0; i < tblGridView_DG.getRowCount(); i++) {
                diemDG themdiem = new diemDG();
                themdiem.setNgay(LocalDate.now().toString());
                themdiem.setMaGiaoVien("gv01");
                themdiem.setMaHocSinh(tblGridView_DG.getValueAt(i, 0).toString());
                themdiem.setMaMon(mDAO.selectByName(cbo_MonDG.getSelectedItem().toString()).getMaMon());
                themdiem.setDiemTX1(tblGridView_DG.getValueAt(i, 3).toString() == "false" ? false : true);
                themdiem.setDiemTX2(tblGridView_DG.getValueAt(i, 4).toString() == "false" ? false : true);
                themdiem.setDiemTX3(tblGridView_DG.getValueAt(i, 5).toString() == "false" ? false : true);
                themdiem.setDiemDK1(tblGridView_DG.getValueAt(i, 6).toString() == "false" ? false : true);
                themdiem.setDiemDK2(tblGridView_DG.getValueAt(i, 7).toString() == "false" ? false : true);
                themdiem.setDiemHK(tblGridView_DG.getValueAt(i, 8).toString() == "false" ? false : true);
                themdiem.setDiemTBMDanhGia(tblGridView_DG.getValueAt(i, 9).toString() == "false" ? false : true);
                String hk = cbo_hocKi.getSelectedItem().toString();
                if (hk.equals("Học Kỳ 1")) {
                    themdiem.setHocKi(false);
                } else {
                    themdiem.setHocKi(true);
                }
                dDao.insertDG(themdiem);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi 6");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void tinhDiemTBM() {
        Double m1 = Double.valueOf(txt_diemMieng1.getText());
        Double m2 = Double.valueOf(txt_diemMieng2.getText());
        Double m3 = Double.valueOf(txt_diemMieng3.getText());
        Double diem15pl1 = Double.parseDouble(txt_15p1.getText());
        Double diem15pl2 = Double.parseDouble(txt_15p2.getText());
        Double diem15pl3 = Double.parseDouble(txt_15p3.getText());
        Double diem45pl1 = Double.parseDouble(txt_45p1.getText());
        Double diem45pl2 = Double.parseDouble(txt_45p2.getText());
        Double diemHK = Double.parseDouble(txt_hk.getText());
        Double rate = (double) Math.round((m1 + m2 + m3 + diem15pl1 + diem15pl2 + diem15pl3 + diem45pl1 * 2 + diem45pl2 * 2 + diemHK * 3) / 13);
        Double TBM = (rate * 100) / 100;
        lbl_diemTBM.setText(TBM.toString());
    }

    public void tinhDiemDG() {
        int diemTX1 = cbo_diemTX1.getSelectedItem().toString().equals("Chưa Đạt") ? 0 : 1;
        int diemTX2 = cbo_diemTX2.getSelectedItem().toString().equals("Chưa Đạt") ? 0 : 1;
        int diemTX3 = cbo_diemTX3.getSelectedItem().toString().equals("Chưa Đạt") ? 0 : 1;
        int diemDK1 = cbo_diemDK1.getSelectedItem().toString().equals("Chưa Đạt") ? 0 : 1;
        int diemDK2 = cbo_diemDK2.getSelectedItem().toString().equals("Chưa Đạt") ? 0 : 1;
        int diemHK = cbo_diemHK.getSelectedItem().toString().equals("Chưa Đạt") ? 0 : 1;
        int TBM = diemHK * 3 + diemDK1 * 2 + diemDK2 * 2 + diemTX1 + diemTX2 + diemTX3;
        lbl_diemTBMDG.setText(TBM > 5 ? "Đạt" : "Chưa Đạt");
    }

    public void choPhepNhap() {
        txt_diemMieng1.setEditable(true);
        txt_diemMieng2.setEditable(true);
        txt_diemMieng3.setEditable(true);
        txt_15p1.setEditable(true);
        txt_15p2.setEditable(true);
        txt_15p3.setEditable(true);
        txt_45p1.setEditable(true);
        txt_45p2.setEditable(true);
        txt_hk.setEditable(true);
    }

    public void tuChoiNhap() {
        txt_diemMieng1.setEditable(false);
        txt_diemMieng2.setEditable(false);
        txt_diemMieng3.setEditable(false);
        txt_15p1.setEditable(false);
        txt_15p2.setEditable(false);
        txt_15p3.setEditable(false);
        txt_45p1.setEditable(false);
        txt_45p2.setEditable(false);
        txt_hk.setEditable(false);
    }

    public void Update() {
        try {
            Diem model = new Diem();
            model.setMaMon(mDAO.selectByName(cbo_Mon1.getSelectedItem().toString()).getMaMon());
            model.setDiemMieng1(Integer.parseInt(txt_diemMieng1.getText()));
            model.setDiemMieng2(Integer.parseInt(txt_diemMieng2.getText()));
            model.setDiemMieng3(Integer.parseInt(txt_diemMieng3.getText()));
            model.setDiem15p1(Integer.parseInt(txt_15p1.getText()));
            model.setDiem15p2(Integer.parseInt(txt_15p2.getText()));
            model.setDiem15p3(Integer.parseInt(txt_15p3.getText()));
            model.setDiem1Tiet1(Float.parseFloat(txt_45p1.getText()));
            model.setDiem1Tiet2(Float.parseFloat(txt_45p2.getText()));
            model.setDiemThi(Float.parseFloat(txt_hk.getText()));
            model.setTBM(Float.parseFloat(lbl_diemTBM.getText()));
            model.setMaHocSinh(lbl_mhs.getText());
            model.setHocKi(cbo_hocKi.getSelectedItem().toString().equals("Học Kỳ 1") ? false : true);
            dDao.update(model);
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi 7");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void UpdateDG() {
        try {
            diemDG model = new diemDG();
            model.setMaMon(mDAO.selectByName(cbo_MonDG.getSelectedItem().toString()).getMaMon());
            model.setDiemTX1(cbo_diemTX1.getSelectedItem().toString().equals("Chưa Đạt") ? false : true);
            model.setDiemTX2(cbo_diemTX2.getSelectedItem().toString().equals("Chưa Đạt") ? false : true);
            model.setDiemTX3(cbo_diemTX3.getSelectedItem().toString().equals("Chưa Đạt") ? false : true);
            model.setDiemDK1(cbo_diemDK1.getSelectedItem().toString().equals("Chưa Đạt") ? false : true);
            model.setDiemDK2(cbo_diemDK2.getSelectedItem().toString().equals("Chưa Đạt") ? false : true);
            model.setDiemHK(cbo_diemHK.getSelectedItem().toString().equals("Chưa Đạt") ? false : true);
            model.setDiemTBMDanhGia(lbl_diemTBMDG.getText().equals("Chưa Đạt") ? false : true);
            model.setMaHocSinh(lbl_mhsDG.getText());
            model.setHocKi(cbo_hocKi.getSelectedItem().toString().equals("Học Kỳ 1") ? false : true);
            dDao.updateDG(model);
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi 8");
            e.printStackTrace();
        }
    }
    
    public void UpdateTableDiem(){
        try{
            
        }catch(Exception e){
            DialogHelper.alert(this, "Lỗi update table diem");
            e.printStackTrace();
        }
    }

    public void tblDanhGiaClick() {
        int row = tblGridView_DG.getSelectedRow();
        lbl_mhsDG.setText(tblGridView_DG.getValueAt(row, 0).toString());
        lbl_hvtDG.setText(tblGridView_DG.getValueAt(row, 1).toString());
        lbl_nsDG.setText(tblGridView_DG.getValueAt(row, 2).toString());
        cbo_diemTX1.setSelectedItem(tblGridView_DG.getValueAt(row, 3).toString() == "false" ? "Chưa Đạt" : "Đạt");
        cbo_diemTX2.setSelectedItem(tblGridView_DG.getValueAt(row, 4).toString().toString() == "false" ? "Chưa Đạt" : "Đạt");
        cbo_diemTX3.setSelectedItem(tblGridView_DG.getValueAt(row, 5).toString().toString() == "false" ? "Chưa Đạt" : "Đạt");
        cbo_diemDK1.setSelectedItem(tblGridView_DG.getValueAt(row, 6).toString().toString() == "false" ? "Chưa Đạt" : "Đạt");
        cbo_diemDK2.setSelectedItem(tblGridView_DG.getValueAt(row, 7).toString().toString() == "false" ? "Chưa Đạt" : "Đạt");
        cbo_diemHK.setSelectedItem(tblGridView_DG.getValueAt(row, 8).toString().toString() == "false" ? "Chưa Đạt" : "Đạt");
        lbl_diemTBMDG.setText(tblGridView_DG.getValueAt(row, 9).toString().toString() == "false" ? "Chưa Đạt" : "Đạt");
    }

    public void TongKetClick() {
        int row = tblGridView_Kq.getSelectedRow();
        lbl_mhsKq.setText(tblGridView_Kq.getValueAt(row, 0).toString());
        lbl_hvtKq.setText(tblGridView_Kq.getValueAt(row, 1).toString());
        lbl_nsKq.setText(tblGridView_Kq.getValueAt(row, 2).toString());
        lbl_KQTB1.setText(tblGridView_Kq.getValueAt(row, 3).toString());
        lbl_KQTB2.setText(tblGridView_Kq.getValueAt(row, 4).toString());
        lbl_HanhKiem1.setText(tblGridView_Kq.getValueAt(row, 10).toString());
        lbl_HanhKiem2.setText(tblGridView_Kq.getValueAt(row, 10).toString());
        lbl_HanhKiemFull.setText(tblGridView_Kq.getValueAt(row, 10).toString());
        Float diemHk1 = Float.parseFloat(tblGridView_Kq.getValueAt(row, 3).toString());
        Float diemHk2 = Float.parseFloat(tblGridView_Kq.getValueAt(row, 4).toString());
        Float diemCaNam = Float.parseFloat(tblGridView_Kq.getValueAt(row, 5).toString());

        if (diemHk1 >= 8) {
            lbl_HocLuc1.setText("Giỏi");
        } else if (diemHk1 >= 7 && diemHk1 < 8) {
            lbl_HocLuc1.setText("Khá");
        } else if (diemHk1 >= 5 && diemHk1 < 7) {
            lbl_HocLuc1.setText("Trung Bình");
        } else if (diemHk1 < 5) {
            lbl_HocLuc1.setText("Yếu");
        }
        
        if (diemHk2 >= 8) {
            lbl_HocLuc2.setText("Giỏi");
        } else if (diemHk2 >= 7 && diemHk2 < 8) {
            lbl_HocLuc2.setText("Khá");
        } else if (diemHk2 >= 5 && diemHk2 < 7) {
            lbl_HocLuc2.setText("Trung Bình");
        } else if (diemHk2 < 5) {
            lbl_HocLuc2.setText("Yếu");
        }
        
        if (diemCaNam >= 8) {
            lbl_HocLuc3.setText("Giỏi");
        } else if (diemCaNam >= 7 && diemCaNam < 8) {
            lbl_HocLuc3.setText("Khá");
        } else if (diemCaNam >= 5 && diemCaNam < 7) {
            lbl_HocLuc3.setText("Trung Bình");
        } else if (diemCaNam < 5) {
            lbl_HocLuc3.setText("Yếu");
        }
        
        if(lbl_HocLuc1.getText().equals("Giỏi") || lbl_HanhKiem1.equals("Tốt")){
            lbl_DanhHieu1.setText("Có");
        }else{
            lbl_DanhHieu1.setText("Không có");
        }
        
        if(lbl_HocLuc2.getText().equals("Giỏi") || lbl_HanhKiem2.equals("Tốt")){
            lbl_DanhHieu2.setText("Có");
        }else{
            lbl_DanhHieu2.setText("Không có");
        }
        
        if(lbl_HocLuc3.getText().equals("Giỏi") || lbl_HanhKiemFull.equals("Tốt")){
            lbl_DanhHieu3.setText("Có");
        }else{
            lbl_DanhHieu3.setText("Không có");
        }
        
        lbl_VangCP.setText(tblGridView_Kq.getValueAt(row, 8).toString());
        lbl_VangKP.setText(tblGridView_Kq.getValueAt(row, 9).toString());
        
    }

    void full() {
        if (maximized) {
            //handle fullscreen - taskbar
            qlydiem.this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            qlydiem.this.setMaximizedBounds(env.getMaximumWindowBounds());
            maximized = false;
        } else {
            setExtendedState(JFrame.NORMAL);
            maximized = true;
        }
    }

    void check() {
        int row = tblGridView.getSelectedRow();
        lbl_mhs.setText(tblGridView.getValueAt(row, 0).toString());
        lbl_hvt.setText(tblGridView.getValueAt(row, 1).toString());
        lbl_ns.setText(tblGridView.getValueAt(row, 2).toString());
        txt_diemMieng1.setText(tblGridView.getValueAt(row, 3).toString());
        txt_diemMieng2.setText(tblGridView.getValueAt(row, 4).toString());
        txt_diemMieng3.setText(tblGridView.getValueAt(row, 5).toString());
        txt_15p1.setText(tblGridView.getValueAt(row, 6).toString());
        txt_15p2.setText(tblGridView.getValueAt(row, 7).toString());
        txt_15p3.setText(tblGridView.getValueAt(row, 8).toString());
        txt_45p1.setText(tblGridView.getValueAt(row, 9).toString());
        txt_45p2.setText(tblGridView.getValueAt(row, 10).toString());
        txt_hk.setText(tblGridView.getValueAt(row, 11).toString());
        lbl_diemTBM.setText(tblGridView.getValueAt(row, 12).toString());
    }
    
    public boolean CheckDataDiem() {
        String checkNumber = "[0-9]{1,2}";
        String checkFloatNumber = "[0-9]{1,2}.[0-9]{1,2}";
        if (txt_diemMieng1.getText().isEmpty()) {
            DialogHelper.alert(this, "Vui lòng nhập TT !");
            txt_diemMieng1.requestFocus();
            return false;
        } else if (txt_diemMieng2.getText().isEmpty()) {
            DialogHelper.alert(this, "Vui lòng nhập TT !");
            txt_diemMieng2.requestFocus();
            return false;
        } else if (txt_diemMieng3.getText().isEmpty()) {
            DialogHelper.alert(this, "Vui lòng nhập TT !");
            txt_diemMieng3.requestFocus();
            return false;
        } else if (txt_15p1.getText().isEmpty()) {
            DialogHelper.alert(this, "Vui lòng nhập TT !");
            txt_15p1.requestFocus();
            return false;
        }else if (txt_15p2.getText().isEmpty()) {
            DialogHelper.alert(this, "Vui lòng nhập TT !");
            txt_15p2.requestFocus();
            return false;
        }else if (txt_15p3.getText().isEmpty()) {
            DialogHelper.alert(this, "Vui lòng nhập TT !");
            txt_15p3.requestFocus();
            return false;
        }else if (txt_45p1.getText().isEmpty()) {
            DialogHelper.alert(this, "Vui lòng nhập TT !");
            txt_15p1.requestFocus();
            return false;
        }else if (txt_45p2.getText().isEmpty()) {
            DialogHelper.alert(this, "Vui lòng nhập TT !");
            txt_15p1.requestFocus();
            return false;
        }else if (txt_hk.getText().isEmpty()) {
            DialogHelper.alert(this, "Vui lòng nhập TT !");
            txt_15p1.requestFocus();
            return false;
        }else if (Pattern.matches(checkNumber, txt_diemMieng1.getText())==false) {
            DialogHelper.alert(this, "Sai định dạng so !");
            txt_diemMieng1.requestFocus();
            return false;
        }else if (Pattern.matches(checkNumber, txt_diemMieng2.getText())==false) {
            DialogHelper.alert(this, "Sai định dạng so !");
            txt_diemMieng2.requestFocus();
            return false;
        }else if (Pattern.matches(checkNumber, txt_diemMieng3.getText())==false) {
            DialogHelper.alert(this, "Sai định dạng so !");
            txt_diemMieng3.requestFocus();
            return false;
        }else if (Pattern.matches(checkNumber, txt_15p1.getText())==false) {
            DialogHelper.alert(this, "Sai định dạng so !");
            txt_15p1.requestFocus();
            return false;
        }else if (Pattern.matches(checkNumber, txt_15p2.getText())==false) {
            DialogHelper.alert(this, "Sai định dạng so !");
            txt_15p2.requestFocus();
            return false;
        }else if (Pattern.matches(checkNumber, txt_15p3.getText())==false) {
            DialogHelper.alert(this, "Sai định dạng so !");
            txt_15p3.requestFocus();
            return false;
        }else if (Pattern.matches(checkFloatNumber, txt_45p1.getText())==false) {
            DialogHelper.alert(this, "Sai định dạng so !");
            txt_45p1.requestFocus();
            return false;
        }else if (Pattern.matches(checkFloatNumber, txt_45p2.getText())==false) {
            DialogHelper.alert(this, "Sai định dạng so !");
            txt_45p2.requestFocus();
            return false;
        }else if (Pattern.matches(checkFloatNumber, txt_hk.getText())==false) {
            DialogHelper.alert(this, "Sai định dạng so !");
            txt_hk.requestFocus();
            return false;
        }if( 0 > Integer.parseInt(txt_diemMieng1.getText()) || Integer.parseInt(txt_diemMieng1.getText()) > 10){
            DialogHelper.alert(this, "Sai khoang diem !");
            txt_diemMieng1.requestFocus();
            return false;
        }else if(0 > Integer.parseInt(txt_diemMieng2.getText()) || Integer.parseInt(txt_diemMieng2.getText()) >10){
            DialogHelper.alert(this, "Sai khoang diem !");
            txt_diemMieng2.requestFocus();
            return false;
        }else if(0 > Integer.parseInt(txt_diemMieng3.getText()) || Integer.parseInt(txt_diemMieng3.getText()) >10){
            DialogHelper.alert(this, "Sai khoang diem !");
            txt_diemMieng3.requestFocus();
            return false;
        }
        else if(0 > Integer.parseInt(txt_15p1.getText()) || Integer.parseInt(txt_15p1.getText()) >10){
            DialogHelper.alert(this, "Sai khoang diem !");
            txt_15p1.requestFocus();
            return false;
        }
        else if(0 > Integer.parseInt(txt_15p2.getText()) || Integer.parseInt(txt_15p2.getText()) >10){
            DialogHelper.alert(this, "Sai khoang diem !");
            txt_15p2.requestFocus();
            return false;
        }
        else if(0 > Integer.parseInt(txt_15p3.getText()) || Integer.parseInt(txt_15p3.getText()) >10){
            DialogHelper.alert(this, "Sai khoang diem !");
            txt_15p3.requestFocus();
            return false;
        }
        else if(0 > Float.parseFloat(txt_45p1.getText()) || Float.parseFloat(txt_45p1.getText()) >10){
            DialogHelper.alert(this, "Sai khoang diem !");
            txt_45p1.requestFocus();
            return false;
        }
        else if(0 > Float.parseFloat(txt_45p2.getText()) || Float.parseFloat(txt_45p2.getText()) >10){
            DialogHelper.alert(this, "Sai khoang diem !");
            txt_45p2.requestFocus();
            return false;
        }
        else if(0 > Float.parseFloat(txt_hk.getText()) || Float.parseFloat(txt_hk.getText()) >10){
            DialogHelper.alert(this, "Sai khoang diem !");
            txt_hk.requestFocus();
            return false;
        }
        return true;
    }
    
     void filter() {
        DefaultTableModel model = (DefaultTableModel) tblGridView.getModel();
        String ft = txt_timKiem1.getText();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        tblGridView.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(ft));
    }
     
       void filter2() {
        DefaultTableModel model = (DefaultTableModel) tblGridView_DG.getModel();
        String ft = txt_timKiem2.getText();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        tblGridView_DG.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(ft));
    }
       
       void filter3() {
        DefaultTableModel model = (DefaultTableModel) tblGridView_Kq.getModel();
        String ft = txtTimKiem3.getText();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        tblGridView_Kq.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(ft));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        pnlHeader = new javax.swing.JPanel();
        btnExit = new javax.swing.JButton();
        btnMaximize = new javax.swing.JButton();
        btnMinimize = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbl_hvt = new javax.swing.JLabel();
        lbl_ns = new javax.swing.JLabel();
        lbl_mhs = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txt_diemMieng1 = new javax.swing.JTextField();
        txt_diemMieng2 = new javax.swing.JTextField();
        txt_diemMieng3 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_15p1 = new javax.swing.JTextField();
        txt_15p2 = new javax.swing.JTextField();
        txt_15p3 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txt_45p1 = new javax.swing.JTextField();
        txt_45p2 = new javax.swing.JTextField();
        cbo_Mon1 = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        cbo_Lop1 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGridView = new javax.swing.JTable();
        jPanel23 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txt_hk = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        lbl_diemTBM = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btn_capNhat = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        txt_timKiem1 = new javax.swing.JTextField();
        btn_reSetDiem = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        lbl_diemTBMDG = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        cbo_diemTX1 = new javax.swing.JComboBox<>();
        cbo_diemTX2 = new javax.swing.JComboBox<>();
        cbo_diemTX3 = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGridView_DG = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        cbo_diemDK1 = new javax.swing.JComboBox<>();
        cbo_diemDK2 = new javax.swing.JComboBox<>();
        jPanel12 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbl_mhsDG = new javax.swing.JLabel();
        lbl_hvtDG = new javax.swing.JLabel();
        lbl_nsDG = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        cbo_diemHK = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        btn_capNhatDG = new javax.swing.JButton();
        txt_timKiem2 = new javax.swing.JTextField();
        cbo_LopDG = new javax.swing.JComboBox<>();
        cbo_MonDG = new javax.swing.JComboBox<>();
        btn_reSetDG = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lbl_mhsKq = new javax.swing.JLabel();
        lbl_hvtKq = new javax.swing.JLabel();
        lbl_nsKq = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        lbl_HocLuc1 = new javax.swing.JLabel();
        lbl_HocLuc2 = new javax.swing.JLabel();
        lbl_HocLuc3 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lbl_KQTB1 = new javax.swing.JLabel();
        lbl_KQTB2 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        lbl_HanhKiemFull = new javax.swing.JLabel();
        lbl_HanhKiem1 = new javax.swing.JLabel();
        lbl_HanhKiem2 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        lbl_VangCP = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        lbl_DanhHieu1 = new javax.swing.JLabel();
        lbl_DanhHieu2 = new javax.swing.JLabel();
        lbl_DanhHieu3 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        lbl_VangKP = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        cbo_LopTK = new javax.swing.JComboBox<>();
        txtTimKiem3 = new javax.swing.JTextField();
        jButton12 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblGridView_Kq = new javax.swing.JTable();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jComboBox9 = new javax.swing.JComboBox<>();
        jLabel52 = new javax.swing.JLabel();
        cbo_hocKi = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cube UI");
        setUndecorated(true);
        setSize(new java.awt.Dimension(1000, 600));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlHeader.setBackground(new java.awt.Color(255, 255, 255));
        pnlHeader.setForeground(new java.awt.Color(255, 255, 255));
        pnlHeader.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlHeaderMouseDragged(evt);
            }
        });
        pnlHeader.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlHeaderMousePressed(evt);
            }
        });

        btnExit.setBackground(new java.awt.Color(255, 255, 255));
        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Exit.png"))); // NOI18N
        btnExit.setContentAreaFilled(false);
        btnExit.setFocusable(false);
        btnExit.setOpaque(true);
        btnExit.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Exit (2).png"))); // NOI18N
        btnExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExitMouseExited(evt);
            }
        });
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnMaximize.setBackground(new java.awt.Color(255, 255, 255));
        btnMaximize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Maximize.png"))); // NOI18N
        btnMaximize.setContentAreaFilled(false);
        btnMaximize.setFocusable(false);
        btnMaximize.setOpaque(true);
        btnMaximize.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Maximize (2).png"))); // NOI18N
        btnMaximize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMaximizeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMaximizeMouseExited(evt);
            }
        });
        btnMaximize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaximizeActionPerformed(evt);
            }
        });

        btnMinimize.setBackground(new java.awt.Color(255, 255, 255));
        btnMinimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Minimize.png"))); // NOI18N
        btnMinimize.setContentAreaFilled(false);
        btnMinimize.setFocusable(false);
        btnMinimize.setOpaque(true);
        btnMinimize.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Minimize (2).png"))); // NOI18N
        btnMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMinimizeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMinimizeMouseExited(evt);
            }
        });
        btnMinimize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMinimizeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHeaderLayout.createSequentialGroup()
                .addContainerGap(1723, Short.MAX_VALUE)
                .addComponent(btnMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMaximize, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnExit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMaximize, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMinimize, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 5, Short.MAX_VALUE))
        );

        getContentPane().add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 1898, 30));

        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N

        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin học sinh", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel1.setText("Mã học sinh :");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel2.setText("Họ và tên :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel3.setText("Ngày sinh :");

        lbl_hvt.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_hvt.setText("Nguyễn Xuân Bách");

        lbl_ns.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_ns.setText("04/06/2000");

        lbl_mhs.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_mhs.setText("02932823");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_mhs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(32, 32, 32)
                        .addComponent(lbl_ns, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(36, 36, 36)
                        .addComponent(lbl_hvt, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)))
                .addGap(12, 12, 12))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lbl_mhs))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lbl_hvt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lbl_ns))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Điểm hệ số 1", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel8.setText("Kiểm tra miệng :");

        txt_diemMieng1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_diemMieng1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_diemMieng1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_diemMieng1MouseClicked(evt);
            }
        });
        txt_diemMieng1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_diemMieng1ActionPerformed(evt);
            }
        });
        txt_diemMieng1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_diemMieng1KeyPressed(evt);
            }
        });

        txt_diemMieng2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_diemMieng2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_diemMieng2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_diemMieng2ActionPerformed(evt);
            }
        });
        txt_diemMieng2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_diemMieng2KeyPressed(evt);
            }
        });

        txt_diemMieng3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_diemMieng3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_diemMieng3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_diemMieng3ActionPerformed(evt);
            }
        });
        txt_diemMieng3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_diemMieng3KeyPressed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel10.setText("Kiểm tra 15 phút :");

        txt_15p1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_15p1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_15p1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_15p1ActionPerformed(evt);
            }
        });
        txt_15p1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_15p1KeyPressed(evt);
            }
        });

        txt_15p2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_15p2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_15p2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_15p2ActionPerformed(evt);
            }
        });
        txt_15p2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_15p2KeyPressed(evt);
            }
        });

        txt_15p3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_15p3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_15p3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_15p3ActionPerformed(evt);
            }
        });
        txt_15p3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_15p3KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_diemMieng1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_diemMieng2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_diemMieng3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(28, 28, 28)
                        .addComponent(txt_15p1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_15p2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_15p3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_diemMieng2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_diemMieng3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_diemMieng1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_15p2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_15p3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_15p1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Điểm hệ số 2", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel11.setText("Kiểm tra 45 phút :");

        txt_45p1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_45p1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_45p1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_45p1ActionPerformed(evt);
            }
        });
        txt_45p1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_45p1KeyPressed(evt);
            }
        });

        txt_45p2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_45p2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_45p2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_45p2ActionPerformed(evt);
            }
        });
        txt_45p2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_45p2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_45p2KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(28, 28, 28)
                .addComponent(txt_45p1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txt_45p2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_45p2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_45p1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cbo_Mon1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_Mon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_Mon1ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setText("Tìm kiếm:");

        cbo_Lop1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_Lop1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_Lop1ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("Lớp :");

        tblGridView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"098992323", "Nguyễn Xuân Bách", "04/06/2020", "9", "9", "9", "9", "9", "9", "9", "9", "9", "0"},
                {"2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "0"},
                {"3", "3", "3", "3", "3", "3", "3", "3", "3", "3", "3", "3", "0"},
                {"5", "4", "4", "4", "4", "4", "4", "4", "4", "4", "0", "4", "4"},
                {"5", "5", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
                {"5", "5", null, null, null, null, null, null, null, null, null, null, null},
                {"5", "5", null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã học sinh", "Họ tên", "Ngày sinh", "Điểm miệng 1", "Điểm miệng 2", "Điểm miệng 3", "Điểm 15p 1", "Điểm 15p 2", "Điểm 15p 3", "Điểm 1 tiết 1", "Điểm 1 tiết 2", "Điểm HK", "Điểm TB"
            }
        ));
        tblGridView.setRowHeight(25);
        tblGridView.setSelectionBackground(new java.awt.Color(52, 152, 219));
        tblGridView.getTableHeader().setReorderingAllowed(false);
        tblGridView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGridViewMouseClicked(evt);
            }
        });
        tblGridView.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblGridViewKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblGridViewKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblGridView);

        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Điểm hệ số 3", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel7.setText("Kiểm tra học kì  :");

        txt_hk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_hk.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_hk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hkActionPerformed(evt);
            }
        });
        txt_hk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_hkKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(28, 28, 28)
                .addComponent(txt_hk, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_hk, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Điểm trung bình môn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        lbl_diemTBM.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        lbl_diemTBM.setForeground(new java.awt.Color(255, 0, 0));
        lbl_diemTBM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(74, Short.MAX_VALUE)
                .addComponent(lbl_diemTBM, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(lbl_diemTBM, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton2.setText("Nhập Excel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Xuất Excel");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btn_capNhat.setText("Cập Nhập");
        btn_capNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_capNhatActionPerformed(evt);
            }
        });

        jButton9.setText("Tải Form");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        txt_timKiem1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_timKiem1KeyReleased(evt);
            }
        });

        btn_reSetDiem.setText("Reset");
        btn_reSetDiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reSetDiemActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel22.setText("Môn :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbo_Lop1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel22)
                                .addGap(18, 18, 18)
                                .addComponent(cbo_Mon1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(61, 61, 61)
                                .addComponent(jLabel13)
                                .addGap(47, 47, 47)
                                .addComponent(txt_timKiem1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_capNhat)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_reSetDiem)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1827, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbo_Lop1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbo_Mon1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_capNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_reSetDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_timKiem1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Môn học nhập điểm", jPanel1);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setText("Tìm kiếm:");

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Điểm trung bình môn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        lbl_diemTBMDG.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        lbl_diemTBMDG.setForeground(new java.awt.Color(255, 0, 0));
        lbl_diemTBMDG.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(74, Short.MAX_VALUE)
                .addComponent(lbl_diemTBMDG, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(lbl_diemTBMDG, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Điểm kiểm tra thường xuyên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        cbo_diemTX1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đạt", "Chưa Đạt" }));
        cbo_diemTX1.setBorder(null);

        cbo_diemTX2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đạt", "Chưa Đạt" }));
        cbo_diemTX2.setBorder(null);

        cbo_diemTX3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đạt", "Chưa Đạt" }));
        cbo_diemTX3.setBorder(null);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbo_diemTX1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbo_diemTX2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbo_diemTX3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbo_diemTX1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_diemTX2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_diemTX3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblGridView_DG.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"098992323", "Nguyễn Xuân Bách", "04/06/2020", "9", "9", "9", "9", "9", "9", "9"},
                {"2", "2", "2", "2", "2", "2", "2", "2", "2", "2"},
                {"3", "3", "3", "3", "3", "3", "3", "3", "3", "3"},
                {"5", "4", "4", "4", "4", "4", "4", "4", "4", "4"},
                {"5", "5", "0", "0", "0", "0", "0", "0", "0", "0"},
                {"5", "5", null, null, null, null, null, null, null, null},
                {"5", "5", null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã học sinh", "Họ tên", "Ngày sinh", "Điểm KT thường xuyên 1", "Điểm KT thường xuyên 2", "Điểm KT thường xuyên 3", "Điểm KT định kì 1", "Điểm KT định kì 2", "Điểm KT học kì", "Điểm trung bình"
            }
        ));
        tblGridView_DG.setRowHeight(25);
        tblGridView_DG.setSelectionBackground(new java.awt.Color(52, 152, 219));
        tblGridView_DG.getTableHeader().setReorderingAllowed(false);
        tblGridView_DG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGridView_DGMouseClicked(evt);
            }
        });
        tblGridView_DG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblGridView_DGKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblGridView_DGKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblGridView_DG);

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Điểm kiểm tra định kì", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        cbo_diemDK1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đạt", "Chưa Đạt" }));
        cbo_diemDK1.setBorder(null);

        cbo_diemDK2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đạt", "Chưa Đạt" }));
        cbo_diemDK2.setBorder(null);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbo_diemDK1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(cbo_diemDK2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbo_diemDK1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_diemDK2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin học sinh", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel4.setText("Mã học sinh :");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel5.setText("Họ và tên :");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel6.setText("Ngày sinh :");

        lbl_mhsDG.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_mhsDG.setText("02932823");

        lbl_hvtDG.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_hvtDG.setText("Nguyễn Xuân Bách");

        lbl_nsDG.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_nsDG.setText("04/06/2000");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_mhsDG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(32, 32, 32)
                        .addComponent(lbl_nsDG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(36, 36, 36)
                        .addComponent(lbl_hvtDG, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lbl_mhsDG))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lbl_hvtDG))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lbl_nsDG))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Điểm kiểm tra học kỳ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        cbo_diemHK.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đạt", "Chưa Đạt" }));
        cbo_diemHK.setBorder(null);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(cbo_diemHK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(93, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbo_diemHK, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel18.setText("Môn :");

        jButton6.setText("Nhập Excel");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Xuất Excel");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton10.setText("Tải Form");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        btn_capNhatDG.setText("Cập Nhập");
        btn_capNhatDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_capNhatDGActionPerformed(evt);
            }
        });

        txt_timKiem2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_timKiem2KeyReleased(evt);
            }
        });

        cbo_LopDG.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_LopDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_LopDGActionPerformed(evt);
            }
        });

        cbo_MonDG.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_MonDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_MonDGActionPerformed(evt);
            }
        });

        btn_reSetDG.setText("Reset");
        btn_reSetDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reSetDGActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel24.setText("Lớp :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1780, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 17, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(18, 18, 18)
                                .addComponent(cbo_MonDG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(103, 103, 103)
                                .addComponent(cbo_LopDG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(77, 77, 77)
                                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel14)
                                .addGap(33, 33, 33)
                                .addComponent(txt_timKiem2, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(91, 91, 91)
                                .addComponent(btn_capNhatDG, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(67, 67, 67)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_reSetDG, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(676, 676, 676)
                    .addComponent(jLabel24)
                    .addContainerGap(1149, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btn_capNhatDG, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_timKiem2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_reSetDG, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbo_LopDG, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(cbo_MonDG)))
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(333, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(136, 136, 136)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(651, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Môn học đánh giá", jPanel2);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin học sinh", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel9.setText("Mã học sinh :");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel15.setText("Họ và tên :");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel16.setText("Ngày sinh :");

        lbl_mhsKq.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_mhsKq.setText("02932823");

        lbl_hvtKq.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_hvtKq.setText("Nguyễn Xuân Bách");

        lbl_nsKq.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_nsKq.setText("04/06/2000");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_mhsKq, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(32, 32, 32)
                        .addComponent(lbl_nsKq, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(36, 36, 36)
                        .addComponent(lbl_hvtKq, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)))
                .addGap(12, 12, 12))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lbl_mhsKq))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(lbl_hvtKq))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(lbl_nsKq))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Học lực", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel17.setText("Học kỳ 1 :");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel19.setText("Học kỳ 2 :");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel23.setText("Cả năm :");

        lbl_HocLuc1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_HocLuc1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_HocLuc1.setText("Giỏi");

        lbl_HocLuc2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_HocLuc2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_HocLuc2.setText("Khá");

        lbl_HocLuc3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_HocLuc3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_HocLuc3.setText("Trung Bình");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_HocLuc1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGap(14, 14, 14)
                        .addComponent(lbl_HocLuc3, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_HocLuc2, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_HocLuc1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_HocLuc2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_HocLuc3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Điểm trung bình", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel20.setText("Học kỳ 1 :");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel21.setText("Học kỳ 2 :");

        lbl_KQTB1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_KQTB1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_KQTB1.setText("8");

        lbl_KQTB2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_KQTB2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_KQTB2.setText("8");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_KQTB1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_KQTB2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(lbl_KQTB1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_KQTB2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hạnh kiểm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel25.setText("Học kỳ 1 :");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel26.setText("Học kỳ 2 :");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel27.setText("Cả năm :");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_HanhKiem1, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_HanhKiem2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(4, 4, 4))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_HanhKiemFull, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6)))
                .addGap(19, 19, 19))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_HanhKiem1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_HanhKiem2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_HanhKiemFull, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Vắng có phép", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        jLabel33.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel33.setText("Cả năm :");

        lbl_VangCP.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_VangCP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_VangCP.setText("5");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33)
                .addGap(14, 14, 14)
                .addComponent(lbl_VangCP, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_VangCP, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh hiệu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        jLabel37.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel37.setText("Học kỳ 1 :");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel38.setText("Học kỳ 2 :");

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel39.setText("Cả năm :");

        lbl_DanhHieu1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_DanhHieu1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_DanhHieu1.setText("Giỏi");

        lbl_DanhHieu2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_DanhHieu2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_DanhHieu2.setText("Tiến Tiến");

        lbl_DanhHieu3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_DanhHieu3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_DanhHieu3.setText("Tiên Tiến");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_DanhHieu1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addGap(14, 14, 14)
                        .addComponent(lbl_DanhHieu3, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_DanhHieu2, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_DanhHieu1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_DanhHieu2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_DanhHieu3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Vắng không phép", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        jLabel45.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel45.setText("Cả năm :");

        lbl_VangKP.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_VangKP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_VangKP.setText("5");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel45)
                .addGap(14, 14, 14)
                .addComponent(lbl_VangKP, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_VangKP, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46))
        );

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel49.setText("Lớp :");

        cbo_LopTK.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_LopTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_LopTKActionPerformed(evt);
            }
        });

        txtTimKiem3.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtTimKiem3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiem3KeyReleased(evt);
            }
        });

        jButton12.setText("Cập Nhập");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        tblGridView_Kq.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"098992323", "Nguyễn Xuân Bách", "04/06/2020", "9", "9", "9", "9", "9", "9", "9", null, null},
                {"2", "2", "2", "2", "2", "2", "2", "2", "2", "2", null, null},
                {"3", "3", "3", "3", "3", "3", "3", "3", "3", "3", null, null},
                {"5", "4", "4", "4", "4", "4", "4", "4", "4", "4", null, null},
                {"5", "5", "0", "0", "0", "0", "0", "0", "0", "0", null, null},
                {"5", "5", null, null, null, null, null, null, null, null, null, null},
                {"5", "5", null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã học sinh", "Họ tên", "Ngày sinh", "Điểm học kỳ 1", "Điểm học kỳ 2", "Điểm cả năm", "Hạnh Kiểm", "Học Lực", "Danh Hiệu", "Vắng có phép", "Vắng không phép", "Ghi chú"
            }
        ));
        tblGridView_Kq.setRowHeight(25);
        tblGridView_Kq.setSelectionBackground(new java.awt.Color(52, 152, 219));
        tblGridView_Kq.getTableHeader().setReorderingAllowed(false);
        tblGridView_Kq.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGridView_KqMouseClicked(evt);
            }
        });
        tblGridView_Kq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblGridView_KqKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblGridView_KqKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tblGridView_Kq);

        jButton13.setText("Tải Form");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setText("Nhập Excel");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton15.setText("Xuất Excel");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel50.setText("Tìm kiếm:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1816, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(339, 339, 339)
                                .addComponent(jLabel49)
                                .addGap(31, 31, 31)
                                .addComponent(cbo_LopTK, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel50)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTimKiem3, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(63, 63, 63)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(21, 21, 21))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbo_LopTK)
                        .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtTimKiem3)
                    .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(113, 113, 113))
        );

        jTabbedPane1.addTab("Kết quả cuối năm", jPanel3);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, 860));

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel51.setText("Năm Học :");
        getContentPane().add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(1490, 50, -1, 24));

        jComboBox9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2018 - 2019", "2019 - 2020", "2020 - 2021", "2021 - 2022", "2022 - 2023" }));
        jComboBox9.setBorder(null);
        getContentPane().add(jComboBox9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1570, 50, 113, 27));

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel52.setText("Học Kỳ :");
        getContentPane().add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(1690, 50, 60, 27));

        cbo_hocKi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbo_hocKi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Học Kỳ 1", "Học Kỳ 2" }));
        cbo_hocKi.setBorder(null);
        cbo_hocKi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_hocKiActionPerformed(evt);
            }
        });
        getContentPane().add(cbo_hocKi, new org.netbeans.lib.awtextra.AbsoluteConstraints(1760, 50, 113, 27));

        setSize(new java.awt.Dimension(1898, 810));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseEntered
        btnExit.setBackground(new Color(232, 17, 35));
    }//GEN-LAST:event_btnExitMouseEntered

    private void btnExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseExited
        btnExit.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnExitMouseExited

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void pnlHeaderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlHeaderMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_pnlHeaderMousePressed

    private void pnlHeaderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlHeaderMouseDragged
        if (maximized) {
            int x = evt.getXOnScreen();
            int y = evt.getYOnScreen();
            this.setLocation(x - xMouse, y - yMouse);
        }
    }//GEN-LAST:event_pnlHeaderMouseDragged

    private void btnMaximizeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMaximizeMouseEntered
        btnMaximize.setBackground(new Color(229, 229, 229));
    }//GEN-LAST:event_btnMaximizeMouseEntered

    private void btnMaximizeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMaximizeMouseExited
        btnMaximize.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnMaximizeMouseExited

    private void btnMaximizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaximizeActionPerformed
        full();
    }//GEN-LAST:event_btnMaximizeActionPerformed

    private void btnMinimizeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizeMouseEntered
        btnMinimize.setBackground(new Color(229, 229, 229));
    }//GEN-LAST:event_btnMinimizeMouseEntered

    private void btnMinimizeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizeMouseExited
        btnMinimize.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnMinimizeMouseExited

    private void btnMinimizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMinimizeActionPerformed
        this.setState(Frame.ICONIFIED);
    }//GEN-LAST:event_btnMinimizeActionPerformed

    private void btn_capNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_capNhatActionPerformed
        String maLop = cbo_Lop1.getSelectedItem().toString();
        boolean ki = cbo_hocKi.getSelectedItem().toString().equals("Học Kỳ 1") ? false : true;
        
        if (tblGridView.getRowCount() == 0) {
            DialogHelper.alert(this, "Không có dữ liệu điểm, vui lòng tạo mới");
            if (DialogHelper.confirm(this, "bạn có muốn tạo mới danh sách") == true) {
                this.LoadClass(maLop);
                this.themL1();
                DialogHelper.alert(this, "Thêm dữ liệu thành công");
            } else {
                DialogHelper.alert(this, "vui lòng kiểm tra lại");
            }
        }else if(this.CheckDataDiem()== true){
            this.tinhDiemTBM();
            this.Update();
            this.loadToTable(cbo_Lop1.getSelectedItem().toString(), cbo_Mon1.getSelectedItem().toString(), ki);
            tuChoiNhap();
            DialogHelper.alert(this, "Sửa dữ liệu thành công");
        }else{
            DialogHelper.alert(this, "vui lòng kiểm tra du lieu dau vao");
        }
    }//GEN-LAST:event_btn_capNhatActionPerformed

    private void txt_hkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_hkKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hkKeyPressed

    private void txt_hkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hkActionPerformed

    private void tblGridViewKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblGridViewKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DOWN || evt.getKeyCode() == KeyEvent.VK_UP) {
            check();

        }
    }//GEN-LAST:event_tblGridViewKeyReleased

    private void tblGridViewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblGridViewKeyPressed

    }//GEN-LAST:event_tblGridViewKeyPressed

    private void tblGridViewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGridViewMouseClicked
        check();
        this.choPhepNhap();
    }//GEN-LAST:event_tblGridViewMouseClicked

    private void cbo_Lop1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_Lop1ActionPerformed
        String tenLop = (String) cbo_Lop1.getSelectedItem();
        String tenMon = (String) cbo_Mon1.getSelectedItem();
        boolean ki = cbo_hocKi.getSelectedItem().toString().equals("Học Kỳ 1") ? false : true;
        this.loadToTable(tenLop, tenMon, ki);
    }//GEN-LAST:event_cbo_Lop1ActionPerformed

    private void cbo_Mon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_Mon1ActionPerformed
        String tenLop = (String) cbo_Lop1.getSelectedItem();
        String tenMon = (String) cbo_Mon1.getSelectedItem();
        boolean ki = cbo_hocKi.getSelectedItem().toString().equals("Học Kỳ 1") ? false : true;
        this.loadToTable(tenLop, tenMon, ki);
    }//GEN-LAST:event_cbo_Mon1ActionPerformed

    private void txt_45p2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_45p2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_45p2KeyReleased

    private void txt_45p2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_45p2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_hk.requestFocus();
            tinhDiemTBM();
        }
    }//GEN-LAST:event_txt_45p2KeyPressed

    private void txt_45p2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_45p2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_45p2ActionPerformed

    private void txt_45p1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_45p1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_45p2.requestFocus();
            tinhDiemTBM();
        }
    }//GEN-LAST:event_txt_45p1KeyPressed

    private void txt_45p1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_45p1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_45p1ActionPerformed

    private void txt_15p3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_15p3KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_45p1.requestFocus();
            tinhDiemTBM();
        }
    }//GEN-LAST:event_txt_15p3KeyPressed

    private void txt_15p3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_15p3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_15p3ActionPerformed

    private void txt_15p2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_15p2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_15p3.requestFocus();
            tinhDiemTBM();
        }
    }//GEN-LAST:event_txt_15p2KeyPressed

    private void txt_15p2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_15p2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_15p2ActionPerformed

    private void txt_15p1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_15p1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_15p2.requestFocus();
            tinhDiemTBM();
        }
    }//GEN-LAST:event_txt_15p1KeyPressed

    private void txt_15p1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_15p1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_15p1ActionPerformed

    private void txt_diemMieng3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_diemMieng3KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_15p1.requestFocus();
            tinhDiemTBM();
        }
    }//GEN-LAST:event_txt_diemMieng3KeyPressed

    private void txt_diemMieng3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_diemMieng3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_diemMieng3ActionPerformed

    private void txt_diemMieng2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_diemMieng2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_diemMieng3.requestFocus();
            tinhDiemTBM();
        }
    }//GEN-LAST:event_txt_diemMieng2KeyPressed

    private void txt_diemMieng2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_diemMieng2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_diemMieng2ActionPerformed

    private void txt_diemMieng1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_diemMieng1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_diemMieng2.requestFocus();
            tinhDiemTBM();
        }
    }//GEN-LAST:event_txt_diemMieng1KeyPressed

    private void txt_diemMieng1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_diemMieng1ActionPerformed

    }//GEN-LAST:event_txt_diemMieng1ActionPerformed

    private void txt_diemMieng1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_diemMieng1MouseClicked

    }//GEN-LAST:event_txt_diemMieng1MouseClicked

    private void tblGridView_KqKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblGridView_KqKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblGridView_KqKeyReleased

    private void tblGridView_KqKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblGridView_KqKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblGridView_KqKeyPressed

    private void tblGridView_KqMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGridView_KqMouseClicked
        this.TongKetClick();
    }//GEN-LAST:event_tblGridView_KqMouseClicked

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        String tenLop = cbo_LopTK.getSelectedItem().toString();
        this.loadTableResult(tenLop);
        if (tblGridView_Kq.getRowCount() == 0) {
            DialogHelper.alert(this, "Không có dữ liệu tổng hợp, vui lòng xem lại");
        } else {
            DialogHelper.alert(this, "Thanh Cong");
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void cbo_LopTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_LopTKActionPerformed
//        String tenLop = cbo_LopTK.getSelectedItem().toString();
//        this.loadTableResult(tenLop);
    }//GEN-LAST:event_cbo_LopTKActionPerformed

    private void btn_capNhatDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_capNhatDGActionPerformed
        String maLop = (String)cbo_LopDG.getSelectedItem();
        boolean ki = cbo_hocKi.getSelectedItem().toString().equals("Học Kỳ 1") ? false : true;
        if (tblGridView_DG.getRowCount() == 0) {
            DialogHelper.alert(this, "Không có dữ liệu điểm, vui lòng tạo mới");
            if (DialogHelper.confirm(this, "bạn có muốn tạo mới danh sách") == true) {
                this.LoadClassDG(maLop);
                this.themDGL1();
                DialogHelper.alert(this, "Thêm dữ liệu thành công");
            } else {
                DialogHelper.alert(this, "vui lòng kiểm tra lại");
            }
        } else {

            this.tinhDiemDG();
            this.UpdateDG();
            this.loadToTableDG(cbo_LopDG.getSelectedItem().toString(), cbo_MonDG.getSelectedItem().toString(), ki);
            DialogHelper.alert(this, "thanh cong");
        }
    }//GEN-LAST:event_btn_capNhatDGActionPerformed

    private void tblGridView_DGKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblGridView_DGKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblGridView_DGKeyReleased

    private void tblGridView_DGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblGridView_DGKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblGridView_DGKeyPressed

    private void tblGridView_DGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGridView_DGMouseClicked
        this.tblDanhGiaClick();
    }//GEN-LAST:event_tblGridView_DGMouseClicked

    private void cbo_hocKiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_hocKiActionPerformed
        String maLop = cbo_Lop1.getSelectedItem().toString();
        String maLopDG = cbo_LopDG.getSelectedItem().toString();
        String maMon = cbo_Mon1.getSelectedItem().toString();
        String maMonDG = cbo_MonDG.getSelectedItem().toString();
        boolean ki = cbo_hocKi.getSelectedItem().toString().equals("Học Kỳ 1") ? false : true;
        this.loadToTable(maLop, maMon, ki);
        this.loadToTableDG(maLopDG, maMonDG, ki);
    }//GEN-LAST:event_cbo_hocKiActionPerformed

    private void cbo_MonDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_MonDGActionPerformed
        String maLopDG = (String)cbo_LopDG.getSelectedItem();       
        String maMonDG = (String)cbo_MonDG.getSelectedItem();
        boolean ki = cbo_hocKi.getSelectedItem().toString().equals("Học Kỳ 1") ? false : true;
        this.loadToTableDG(maLopDG, maMonDG, ki);
    }//GEN-LAST:event_cbo_MonDGActionPerformed

    private void cbo_LopDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_LopDGActionPerformed
        String maLopDG = (String)cbo_LopDG.getSelectedItem();       
        String maMonDG = (String)cbo_MonDG.getSelectedItem();
        boolean ki = cbo_hocKi.getSelectedItem().toString().equals("Học Kỳ 1") ? false : true;
        this.loadToTableDG(maLopDG, maMonDG, ki);
    }//GEN-LAST:event_cbo_LopDGActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        jFileChooser1.setSelectedFile(new File("C:\\Form môn học đánh giá.csv"));
        jFileChooser1.showSaveDialog(null);
        File file = jFileChooser1.getSelectedFile();
        if (file.exists()) {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "Bạn muốn ghi đè lên File đã có", "Hệ thống quản lý đào tạo", dialogButton);
            if (dialogResult == 0) {
                System.out.println("starting write user.csv file: " + file.getPath());
                fv.writeFormCsv(file.getPath(), cbo_Lop1.getSelectedItem().toString());
            } else {
                System.out.println("No Option");
            }
        } else {
            System.out.println("starting write form file: " + file.getPath());
            fv.writeFormCsv(file.getPath(), cbo_Lop1.getSelectedItem().toString());
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        boolean ki = cbo_hocKi.getSelectedItem().toString().equals("Học Kỳ 1") ? false : true;
        FileNameExtensionFilter filter = new FileNameExtensionFilter("File csv", "csv");
        jFileChooser1.setFileFilter(filter);
        jFileChooser1.showSaveDialog(null);
        File file = jFileChooser1.getSelectedFile();
        System.out.println("starting read file: " + file.getPath());
        fv.readCsv(cbo_MonDG.getSelectedItem().toString(), cbo_hocKi.getSelectedItem().toString(), file.getPath());
        DialogHelper.alert(this, "nhập thành công");
        this.loadToTableDG(cbo_LopDG.getSelectedItem().toString(), cbo_MonDG.getSelectedItem().toString(), ki);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
            jFileChooser1.setSelectedFile(new File("C:\\Môn học đánh giá lớp " + cbo_LopDG.getSelectedItem().toString() + " Môn " + cbo_MonDG.getSelectedItem().toString() + ".csv"));

        jFileChooser1.showSaveDialog(null);
        File file = jFileChooser1.getSelectedFile();
        if (file.exists()) {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "Bạn muốn ghi đè lên File đã có", "Hệ thống quản lý đào tạo", dialogButton);
            if (dialogResult == 0) {
                System.out.println("starting write user.csv file: " + file.getPath());
                fv.writeCsv(cbo_LopDG.getSelectedItem().toString(), cbo_MonDG.getSelectedItem().toString(), file.getPath(), cbo_hocKi.getSelectedItem().toString());
            } else {
                System.out.println("No Option");
            }
        } else {
            System.out.println("starting write file: " + file.getPath());
            fv.writeCsv(cbo_LopDG.getSelectedItem().toString(), cbo_MonDG.getSelectedItem().toString(), file.getPath(), cbo_hocKi.getSelectedItem().toString());
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
          boolean ki = cbo_hocKi.getSelectedItem().toString().equals("Học Kỳ 1") ? false : true;
        FileNameExtensionFilter filter = new FileNameExtensionFilter("File csv", "csv");
        jFileChooser1.setFileFilter(filter);
        jFileChooser1.showSaveDialog(null);
        File file = jFileChooser1.getSelectedFile();
        System.out.println("starting read file: " + file.getPath());
        fv.read2Csv( cbo_Mon1.getSelectedItem().toString(), cbo_hocKi.getSelectedItem().toString(), file.getPath());
        DialogHelper.alert(this,"nhập excel thành công");
        this.loadToTable(cbo_Lop1.getSelectedItem().toString(), cbo_Mon1.getSelectedItem().toString(),ki);
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
            jFileChooser1.setSelectedFile(new File("C:\\Bảng điểm Lớp " + cbo_Lop1.getSelectedItem().toString() + " Môn " + cbo_Mon1.getSelectedItem().toString() + ".csv"));
        jFileChooser1.showSaveDialog(null);
        File file = jFileChooser1.getSelectedFile();
        if (file.exists()) {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "Bạn muốn ghi đè lên File đã có", "Hệ thống quản lý đào tạo", dialogButton);
            if (dialogResult == 0) {
                System.out.println("starting write user.csv file: " + file.getPath());
                fv.write2Csv(cbo_Lop1.getSelectedItem().toString(),(String) cbo_Mon1.getSelectedItem().toString(), file.getPath(), cbo_hocKi.getSelectedItem().toString());
            } else {
                System.out.println("No Option");
            }
        } else {
            System.out.println("starting write file: " + file.getPath());
           fv.write2Csv((String) cbo_Lop1.getSelectedItem(),(String) cbo_Mon1.getSelectedItem(), file.getPath(),(String) cbo_hocKi.getSelectedItem());
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
             jFileChooser1.setSelectedFile(new File("C:\\Form Bảng điểm.csv"));
        jFileChooser1.showSaveDialog(null);
        File file = jFileChooser1.getSelectedFile();
        if (file.exists()) {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "Bạn muốn ghi đè lên File đã có", "Hệ thống quản lý đào tạo", dialogButton);
            if (dialogResult == 0) {
                System.out.println("starting write user.csv file: " + file.getPath());
                fv.writeForm2Csv(file.getPath(), cbo_Lop1.getSelectedItem().toString());
            } else {
                System.out.println("No Option");
            }
        } else {
            System.out.println("starting write form file: " + file.getPath());
            fv.writeForm2Csv(file.getPath(), cbo_Lop1.getSelectedItem().toString());
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
          jFileChooser1.setSelectedFile(new File("C:\\Form kết quả cuối năm.csv"));
        jFileChooser1.showSaveDialog(null);
        File file = jFileChooser1.getSelectedFile();
        if (file.exists()) {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "Bạn muốn ghi đè lên File đã có", "Hệ thống quản lý đào tạo", dialogButton);
            if (dialogResult == 0) {
                System.out.println("starting write user.csv file: " + file.getPath());
                CsvFile.writeForm1Csv(file.getPath());
            } else {
                System.out.println("No Option");
            }
        } else {
            System.out.println("starting write form file: " + file.getPath());
            CsvFile.writeForm1Csv(file.getPath());
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
       
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        jFileChooser1.setSelectedFile(new File("C:\\Kết quả cuối năm lớp " + cbo_LopTK.getSelectedItem().toString() + ".csv"));

        jFileChooser1.showSaveDialog(null);
        File file = jFileChooser1.getSelectedFile();
        if (file.exists()) {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "Bạn muốn ghi đè lên File đã có", "Hệ thống quản lý đào tạo", dialogButton);
            if (dialogResult == 0) {
                System.out.println("starting write user.csv file: " + file.getPath());
                fv.write1Csv(cbo_LopTK.getSelectedItem().toString(), file.getPath());
            } else {
                System.out.println("No Option");
            }
        } else {
            System.out.println("starting write file: " + file.getPath());
            fv.write1Csv(cbo_LopTK.getSelectedItem().toString(), file.getPath());
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void btn_reSetDiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reSetDiemActionPerformed
        String maLop = cbo_Lop1.getSelectedItem().toString();
        boolean ki = cbo_hocKi.getSelectedItem().toString().equals("Học Kỳ 1") ? false : true;
        if(tblGridView.getRowCount() < this.countClass(maLop)){
            DialogHelper.alert(this, "Danh sách lớp có dữ liệu mới, vui lòng update");
            if (DialogHelper.confirm(this, "bạn có muốn tạo lại danh sách") == true) {
                this.LoadNewDataDiem(lDAO.findByName(maLop).getMaLop(), mDAO.selectByName(cbo_Mon1.getSelectedItem().toString()).getMaMon(), ki);
                this.loadToTable(cbo_Lop1.getSelectedItem().toString(), cbo_Mon1.getSelectedItem().toString(), ki);
                DialogHelper.alert(this, "Update dữ liệu thành công");
            } else {
                DialogHelper.alert(this, "vui lòng kiểm tra lại");
            }
        }
    }//GEN-LAST:event_btn_reSetDiemActionPerformed

    private void btn_reSetDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reSetDGActionPerformed
        String maLop = (String)cbo_LopDG.getSelectedItem();
        boolean ki = cbo_hocKi.getSelectedItem().toString().equals("Học Kỳ 1") ? false : true;
        if(tblGridView_DG.getRowCount() < this.countClass(maLop)){
            DialogHelper.alert(this, "Danh sách lớp có dữ liệu mới, vui lòng update");
            if (DialogHelper.confirm(this, "bạn có muốn tạo lại danh sách") == true) {
                this.LoadNewDataDG(lDAO.findByName(maLop).getMaLop(), mDAO.selectByName(cbo_MonDG.getSelectedItem().toString()).getMaMon(), ki);
                this.loadToTableDG(cbo_LopDG.getSelectedItem().toString(), cbo_MonDG.getSelectedItem().toString(), ki);
                DialogHelper.alert(this, "Update dữ liệu thành công");
            } else {
                DialogHelper.alert(this, "vui lòng kiểm tra lại");
            }
        }
    }//GEN-LAST:event_btn_reSetDGActionPerformed

    private void txt_timKiem1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_timKiem1KeyReleased
        this.filter();
    }//GEN-LAST:event_txt_timKiem1KeyReleased

    private void txt_timKiem2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_timKiem2KeyReleased
        this.filter2();
    }//GEN-LAST:event_txt_timKiem2KeyReleased

    private void txtTimKiem3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiem3KeyReleased
        this.filter3();
    }//GEN-LAST:event_txtTimKiem3KeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(qlydiem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(qlydiem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(qlydiem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(qlydiem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new qlydiem().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnMaximize;
    private javax.swing.JButton btnMinimize;
    private javax.swing.JButton btn_capNhat;
    private javax.swing.JButton btn_capNhatDG;
    private javax.swing.JButton btn_reSetDG;
    private javax.swing.JButton btn_reSetDiem;
    private javax.swing.JComboBox<String> cbo_Lop1;
    private javax.swing.JComboBox<String> cbo_LopDG;
    private javax.swing.JComboBox<String> cbo_LopTK;
    private javax.swing.JComboBox<String> cbo_Mon1;
    private javax.swing.JComboBox<String> cbo_MonDG;
    private javax.swing.JComboBox<String> cbo_diemDK1;
    private javax.swing.JComboBox<String> cbo_diemDK2;
    private javax.swing.JComboBox<String> cbo_diemHK;
    private javax.swing.JComboBox<String> cbo_diemTX1;
    private javax.swing.JComboBox<String> cbo_diemTX2;
    private javax.swing.JComboBox<String> cbo_diemTX3;
    private javax.swing.JComboBox<String> cbo_hocKi;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox9;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbl_DanhHieu1;
    private javax.swing.JLabel lbl_DanhHieu2;
    private javax.swing.JLabel lbl_DanhHieu3;
    private javax.swing.JLabel lbl_HanhKiem1;
    private javax.swing.JLabel lbl_HanhKiem2;
    private javax.swing.JLabel lbl_HanhKiemFull;
    private javax.swing.JLabel lbl_HocLuc1;
    private javax.swing.JLabel lbl_HocLuc2;
    private javax.swing.JLabel lbl_HocLuc3;
    private javax.swing.JLabel lbl_KQTB1;
    private javax.swing.JLabel lbl_KQTB2;
    private javax.swing.JLabel lbl_VangCP;
    private javax.swing.JLabel lbl_VangKP;
    private javax.swing.JLabel lbl_diemTBM;
    private javax.swing.JLabel lbl_diemTBMDG;
    private javax.swing.JLabel lbl_hvt;
    private javax.swing.JLabel lbl_hvtDG;
    private javax.swing.JLabel lbl_hvtKq;
    private javax.swing.JLabel lbl_mhs;
    private javax.swing.JLabel lbl_mhsDG;
    private javax.swing.JLabel lbl_mhsKq;
    private javax.swing.JLabel lbl_ns;
    private javax.swing.JLabel lbl_nsDG;
    private javax.swing.JLabel lbl_nsKq;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JTable tblGridView;
    private javax.swing.JTable tblGridView_DG;
    private javax.swing.JTable tblGridView_Kq;
    private javax.swing.JTextField txtTimKiem3;
    private javax.swing.JTextField txt_15p1;
    private javax.swing.JTextField txt_15p2;
    private javax.swing.JTextField txt_15p3;
    private javax.swing.JTextField txt_45p1;
    private javax.swing.JTextField txt_45p2;
    private javax.swing.JTextField txt_diemMieng1;
    private javax.swing.JTextField txt_diemMieng2;
    private javax.swing.JTextField txt_diemMieng3;
    private javax.swing.JTextField txt_hk;
    private javax.swing.JTextField txt_timKiem1;
    private javax.swing.JTextField txt_timKiem2;
    // End of variables declaration//GEN-END:variables

}
