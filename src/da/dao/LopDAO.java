/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package da.dao;

import da.helper.JdbcHelper;
import da.model.Lop;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class LopDAO {

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
    
      public List<Lop> select(){
        String sql= "select * from lophoc";
        return select(sql);
    }
     
      public Lop findByName(String tenLop) {
        String sql = "SELECT * FROM lophoc WHERE tenlop=?";
        List<Lop> list = select(sql, tenLop);
        return list.size() > 0 ? list.get(0) : null;
    }
    
}
