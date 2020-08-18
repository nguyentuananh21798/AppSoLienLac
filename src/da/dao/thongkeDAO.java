/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package da.dao;

import da.helper.JdbcHelper;
import da.model.HocSinh;
import da.model.Lop;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class thongkeDAO {

    JdbcHelper Jdbc = new JdbcHelper();

    private Lop readFromResultSet(ResultSet rs) throws SQLException {
        Lop model = new Lop();
        
        model.setMaLop(rs.getString("malop"));
        model.setTenLop(rs.getString("tenlop"));
        model.setMaGiaoVien(rs.getString("magiaovien"));
        model.setNamHoc(rs.getString("namhoc"));
        return model;
    }

    private List<Lop> select(String sql, Object... args) {
        List<Lop> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    Lop model = readFromResultSet(rs);
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

    public List<Lop> selectCBB() {
        String sql = "select * from lophoc";
        return select(sql);
    }
     public List<Lop> selectCBBNH() {
        String sql = " select namhoc from lophoc group by namhoc";
        return select(sql);
    }

    public ResultSet kqHk1(String tenLop){
        String sql = "select hocsinh.mahocsinh, hocsinh.hoten, hocsinh.gioitinh, convert(float,avg(diemTBM)) as TBhocKi1 FROM diem inner join hocsinh on \n" +
"diem.mahocsinh = hocsinh.mahocsinh inner join lophoc on hocsinh.lop = lophoc.malop inner join \n" +
"mon on diem.mamon = mon.mamon where lophoc.tenlop = ? and hocki = 0 group by hocsinh.hoten, hocsinh.mahocsinh, hocsinh.gioitinh ";
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
        String sql = "select hocsinh.mahocsinh, hocsinh.hoten, hocsinh.gioitinh, convert(float,avg(diemTBM)) as TBhocKi2 FROM diem inner join hocsinh on \n" +
"diem.mahocsinh = hocsinh.mahocsinh inner join lophoc on hocsinh.lop = lophoc.malop inner join \n" +
"mon on diem.mamon = mon.mamon where lophoc.tenlop = ? and hocki = 1 group by hocsinh.hoten, hocsinh.mahocsinh, hocsinh.gioitinh";
        try{
            PreparedStatement ps = Jdbc.prepareStatement(sql);
            ps.setString(1, tenLop);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
        
        public ResultSet selectSiSoToanTruong() {
        String sql = "select count(*) as ss from hocsinh ";
        try{
            PreparedStatement ps = Jdbc.prepareStatement(sql);
            
            
            ResultSet rs = ps.executeQuery();
       
            return rs;
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
        
//           public HocSinh selectByKeyword2(String keyword) {
//        String sql = "select * from hocsinh where hoten like ?";
//        List<HocSinh> list = select(sql, "%" + keyword + "%");
//        return list.size() > 0 ? list.get(0) : null;
//    }

}
