/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package da.dao;

import da.helper.JdbcHelper;
import da.model.Diem;
import da.model.Mon;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BNC
 */
public class MonDAO {
     JdbcHelper Jdbc = new JdbcHelper();

    public List<Mon> select() {
        String sql = "SELECT * FROM mon where hinhthucdanhgia ='0' ";
        return select(sql);
    }
    
    public Mon selectByName(String tenMon){
        String sql = "select * from mon where tenmon = ?";
        List<Mon> list = select(sql,tenMon);
        return list.size() > 0 ? list.get(0) : null;
    }
    
    public List<Mon> selectByDG() {
        String sql = "SELECT * FROM mon where hinhthucdanhgia ='1' ";
        return select(sql);
    }
    
   
    private List<Mon> select(String sql, Object... args) {
        List<Mon> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = Jdbc.executeQuery(sql, args);
                while (rs.next()) {
                    Mon model = readFromResultSet(rs);
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

    private Mon readFromResultSet(ResultSet rs) throws SQLException {
        Mon model = new Mon();
        model.setMaMon(rs.getString("mamon"));
        model.setTenMon(rs.getString("tenmon"));
        return model;
    }
}
