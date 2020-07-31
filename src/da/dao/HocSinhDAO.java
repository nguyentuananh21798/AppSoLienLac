/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package da.dao;

import da.helper.JdbcHelper;
import da.model.HocSinh;
import da.model.Lop;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class HocSinhDAO {

    public List<HocSinh> select() {
        String sql = "select * from hocsinh";
        return select(sql);
    }

    public List<HocSinh> selectByKeyword(String keyword) {
        String sql = "select * from hocsinh where hoten like?";
        return select(sql, "%" + keyword + "%");
    }

    public HocSinh selectTenLop(String tenLop) {
        String sql =" select mahocsinh,hoten,ngaysinh,gioitinh,tenlop from hocsinh  join lophoc on hocsinh.lop=lophoc.malop and lophoc.tenlop= ?" ;
        List<HocSinh> list = select(sql, tenLop);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<HocSinh> select(String sql, Object... args) {
        List<HocSinh> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    HocSinh model = readFromResultSet(rs);
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

    private HocSinh readFromResultSet(ResultSet rs) throws SQLException {
        HocSinh model = new HocSinh();
        model.setMaHocSinh(rs.getString("mahocsinh"));
        model.setHoTen(rs.getString("hoten"));
        model.setGioiTinh(rs.getBoolean("gioitinh"));
        model.setNgaySinh(rs.getString("ngaysinh"));
        model.setDiaChi(rs.getString("diachi"));
        model.setDienThoai(rs.getString("dienthoai"));
        model.setEmail(rs.getString("email"));
        model.setLop(rs.getString("lop"));
        model.setDanToc(rs.getString("dantoc"));
        model.setTongiao(rs.getString("tongiao"));
        model.setNgayVaoDoan(rs.getDate("ngayvaodoan"));
        model.setTrangThai(rs.getString("trangthai"));
        return model;
    }

}
