/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package da.dao;

import da.helper.Encryption;
import da.helper.JdbcHelper;
import da.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BNC
 */
public class UserDAO {
    JdbcHelper Jdbc = new JdbcHelper();
    Encryption encrypt = new Encryption();
    public void insert(User model) {
        String passWord = encrypt.encrypt(model.getPassWord(), "DMM");
        String sql = "insert into account values(?,?,?,?)";
        Jdbc.executeUpdate(sql, model.getUserName(), passWord, model.getRole(), model.getID());
    }
        
    public void delete(String MaNV) {
        String sql = "DELETE FROM account WHERE username=?";
        Jdbc.executeUpdate(sql, MaNV);
    }

    public List<User> select() {
        String sql = "SELECT * FROM account";
        return select(sql);
    }

    public User findById(String username) {
        String sql = "SELECT * FROM account WHERE username=?";
        List<User> list = select(sql, username);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<User> select(String sql, Object... args) {
        List<User> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = Jdbc.executeQuery(sql, args);
                while (rs.next()) {
                    User model = readFromResultSet(rs);
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

    private User readFromResultSet(ResultSet rs) throws SQLException {
        User model = new User();
        model.setStt(rs.getInt("stt"));
        model.setUserName(rs.getString("username"));
        model.setPassWord(rs.getString("pass"));
        model.setRole(rs.getString("roles"));
        model.setID(rs.getString("ID"));
        return model;
    }
}
