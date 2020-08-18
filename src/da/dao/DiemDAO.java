/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package da.dao;

import da.helper.DialogHelper;
import da.helper.JdbcHelper;
import da.model.Diem;
import da.model.diemDG;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BNC
 */
public class DiemDAO {
    JdbcHelper Jdbc = new JdbcHelper();
    public void insert(Diem model) {
        String sql = "insert into diem(ngay,magiaovien,mahocsinh,mamon,diemMieng1,diemMieng2,diemMieng3,diem15phut1,diem15phut2,diem15phut3,diem1Tiet1,diem1Tiet2,diemthi,diemTBM,hocki) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Jdbc.executeUpdate(sql, model.getNgay(), model.getMaGiaoVien(), model.getMaHocSinh(), model.getMaMon(), model.getDiemMieng1(), model.getDiemMieng2()
        , model.getDiemMieng3(), model.getDiem15p1(), model.getDiem15p2(), model.getDiem15p3(), model.getDiem1Tiet1(), model.getDiem1Tiet2(), model.getDiemThi()
        , model.getTBM(), model.isHocKi());
    }
    
    public ResultSet LoadNewData(String tenLop, String maMon, boolean ki){
        String sql = "select mahocsinh, hoten, ngaysinh from hocsinh where lop= ? and not exists (select * from diem where hocsinh.mahocsinh = diem.mahocsinh and diem.mamon = ? and diem.hocki=?) ";
        try{
            PreparedStatement ps = Jdbc.prepareStatement(sql);
            ps.setString(1, tenLop);
            ps.setString(2, maMon);
            ps.setBoolean(3, ki);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    
    public void insertDG(diemDG model) {
        String sql = "insert into diem(ngay,magiaovien,mahocsinh,mamon,diemTX1,diemTX2,diemTX3,diemDK1,diemDK2,diemHK,diemTBMdanhgia,hocki) values(?,?,?,?,?,?,?,?,?,?,?,?)";
        Jdbc.executeUpdate(sql, model.getNgay(), model.getMaGiaoVien(), model.getMaHocSinh(), model.getMaMon(), model.isDiemTX1(), model.isDiemTX2(), model.isDiemTX3(), model.isDiemDK1(), model.isDiemDK2(), model.isDiemHK(), model.isDiemTBMDanhGia(), model.isHocKi());
    }
    
    
    public void update(Diem model) {
        System.out.println(model.getMaHocSinh()+ model.getMaMon());
        String sql = "UPDATE diem SET diemMieng1=?, diemMieng2=?, diemMieng3=?, diem15phut1=?, diem15phut2=?, diem15phut3=?, diem1Tiet1=?, diem1Tiet2=?, diemthi=?, diemTBM=?  WHERE mahocsinh=? and mamon = ? and hocki = ?";
        Jdbc.executeUpdate(sql,model.getDiemMieng1(), model.getDiemMieng2(), model.getDiemMieng3(), model.getDiem15p1(), model.getDiem15p2(), model.getDiem15p3(),
                model.getDiem1Tiet1(), model.getDiem1Tiet2(), model.getDiemThi(), model.getTBM(), model.getMaHocSinh(), model.getMaMon(), model.isHocKi()); 
        System.out.println("e1");
    }
    
    public void updateDG(diemDG model) {
        String sql = "UPDATE diem SET diemTX1=?, diemTX2=?, diemTX3=?, diemDK1=?, diemDK2=?, diemHK=?, diemTBMdanhgia=?  WHERE mahocsinh=? and mamon = ? and hocki = ?";
        Jdbc.executeUpdate(sql, model.isDiemTX1(), model.isDiemTX2(), model.isDiemTX3(), model.isDiemDK1(), model.isDiemDK2(), model.isDiemHK(), model.isDiemTBMDanhGia(), model.getMaHocSinh(), model.getMaMon(), model.isHocKi()); 
    }

    public List<Diem> select() {
        String sql = "SELECT * FROM diem";
        return select(sql);
    }

    public Diem findByHSId(String maHocSinh, String maMon, boolean ki) {
        String sql = "SELECT * FROM diem WHERE mahocsinh=? and mamon=? and hocki = ?";
        List<Diem> list = select(sql, maHocSinh, maMon, ki);
        return list.size() > 0 ? list.get(0) : null;
    }
    
    public Diem findByClId(String maLop) {
        String sql = "SELECT * FROM diem WHERE malop=?";
        List<Diem> list = select(sql, maLop);
        return list.size() > 0 ? list.get(0) : null;
    }
    
     public Diem find(String maLop, String maMon) {
        String sql = "SELECT mahocsinh, hocsinh.hoten, hocsinh.ngaysinh, diemMieng1, diemMieng2, diemMieng3, diem15phut1, diem15phut2, diem15phut3, diem1Tiet1, diem1Tiet2, diemthi FROM diem inner join hocsinh on hocsinh.mahocsinh = diem.mahocsinh WHERE hocsinh.malop=? and mamon=?";
        List<Diem> list = select(sql, maLop, maMon);
        return list.size() > 0 ? list.get(0) : null;
    }
    
    
    public ResultSet findByEve(String maLop, String maMon, boolean ki){        
        String sql = "SELECT hocsinh.mahocsinh, hocsinh.hoten, hocsinh.ngaysinh, diemMieng1, diemMieng2, diemMieng3, diem15phut1, diem15phut2, diem15phut3, diem1Tiet1, diem1Tiet2, diemthi, diemTBM " +
" FROM diem inner join hocsinh on diem.mahocsinh = hocsinh.mahocsinh inner join lophoc on hocsinh.lop = lophoc.malop inner join mon on diem.mamon = mon.mamon" +
" where lophoc.tenlop = ? and mon.tenmon = ? and diem.hocki = ? and hocsinh.xoa=1";
        try{
            PreparedStatement ps = Jdbc.prepareStatement(sql);
            ps.setString(1, maLop);
            ps.setString(2, maMon);
            ps.setBoolean(3, ki);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    
    
    
    public ResultSet findDG(String maLop, String maMon, boolean ki){
        String sql = "SELECT hocsinh.mahocsinh, hocsinh.hoten, hocsinh.ngaysinh, diemTX1, diemTX2, diemTX3, diemDK1, diemDK2, diemHK, diemTBMdanhgia \n" +
"FROM diem inner join hocsinh on diem.mahocsinh = hocsinh.mahocsinh inner join lophoc on hocsinh.lop = lophoc.malop inner join mon on diem.mamon = mon.mamon \n" +
"where lophoc.tenlop = ? and mon.tenmon = ? and diem.hocki = ?";
        try{
            PreparedStatement ps = Jdbc.prepareStatement(sql);
            ps.setString(1, maLop);
            ps.setString(2, maMon);
            ps.setBoolean(3, ki);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    
    public ResultSet findByClass(String maLop){
        String sqll = "select mahocsinh, hoten, ngaysinh  from hocsinh inner join lophoc on hocsinh.lop = lophoc.malop where lophoc.tenlop = ? and hocsinh.xoa=1";
        try{
            PreparedStatement ps = Jdbc.prepareStatement(sqll);
            ps.setString(1, maLop);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    
    public ResultSet kqHk1(String tenLop){
        String sql = "select hocsinh.mahocsinh, hocsinh.hoten, hocsinh.ngaysinh, convert(float,avg(diemTBM)) as TBhocKi1 FROM diem inner join hocsinh on \n" +
"diem.mahocsinh = hocsinh.mahocsinh inner join lophoc on hocsinh.lop = lophoc.malop inner join \n" +
"mon on diem.mamon = mon.mamon where lophoc.tenlop = ? and hocki = 0 group by hocsinh.hoten, hocsinh.mahocsinh, hocsinh.ngaysinh";
        try{
            PreparedStatement ps = Jdbc.prepareStatement(sql);
            ps.setString(1, tenLop);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    public ResultSet kqHk2(String tenLop){
        String sql = "select hocsinh.mahocsinh, hocsinh.hoten, hocsinh.ngaysinh, convert(float,avg(diemTBM)) as TBhocKi2 FROM diem inner join hocsinh on \n" +
"diem.mahocsinh = hocsinh.mahocsinh inner join lophoc on hocsinh.lop = lophoc.malop inner join \n" +
"mon on diem.mamon = mon.mamon where lophoc.tenlop = ? and hocki = 1 group by hocsinh.hoten, hocsinh.mahocsinh, hocsinh.ngaysinh";
        try{
            PreparedStatement ps = Jdbc.prepareStatement(sql);
            ps.setString(1, tenLop);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    private List<Diem> select(String sql, Object... args) {
        List<Diem> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = Jdbc.executeQuery(sql, args);
                while (rs.next()) {
                    Diem model = readFromResultSet(rs);
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    private Diem readFromResultSet(ResultSet rs) throws SQLException {
        Diem model = new Diem();
        model.setStt(rs.getInt("stt"));
        model.setNgay(rs.getString("ngay"));
        model.setMaGiaoVien(rs.getString("magiaovien"));
        model.setMaHocSinh(rs.getString("mahocsinh"));
        model.setMaMon(rs.getString("mamon"));
        model.setDiemMieng1(rs.getInt("diemMieng1")); 
        model.setDiemMieng2(rs.getInt("diemMieng2")); 
        model.setDiemMieng3(rs.getInt("diemMieng3")); 
	model.setDiem15p1(rs.getInt("diem15phut1"));
	model.setDiem15p2(rs.getInt("diem15phut2"));
	model.setDiem15p3(rs.getInt("diem15phut3"));
	model.setDiem1Tiet1(rs.getFloat("diem1Tiet1"));
	model.setDiem1Tiet2(rs.getFloat("diem1Tiet2"));
	model.setDiemThi(rs.getFloat("diemthi"));
        model.setTBM(rs.getFloat("diemTBM"));
        model.setHocKi(rs.getBoolean("hocki"));
        return model;
    }
}
