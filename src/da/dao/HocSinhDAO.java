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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class HocSinhDAO {
     JdbcHelper Jdbc = new JdbcHelper();

    public List<HocSinh> select() {
        String sql = "select * from hocsinh";
        return select(sql);
    }

    public HocSinh select2(String mahocsinh) {
        String sql = "select * from hocsinh join lophoc on hocsinh.lop=lophoc.malop and  hocsinh.mahocsinh= ?";
        List<HocSinh> list = select(sql, mahocsinh);

        return list.size() > 0 ? list.get(0) : null;
    }
     public ResultSet selectSiSoNam(String tenLop) {
        String sql = "select count(*) as ss from hocsinh join lophoc on hocsinh.lop=lophoc.malop and lophoc.tenlop= ? and gioitinh=1 and hocsinh.xoa=1";
        try{
            PreparedStatement ps = Jdbc.prepareStatement(sql);
            
            ps.setString(1, tenLop);
            ResultSet rs = ps.executeQuery();
       
            return rs;
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
     
     
     public ResultSet selectSiSoNu(String tenLop) {
        String sql = "select count(*) as ss from hocsinh join lophoc on hocsinh.lop=lophoc.malop and lophoc.tenlop= ? and gioitinh=0 and hocsinh.xoa=1";
        try{
            PreparedStatement ps = Jdbc.prepareStatement(sql);
            
            ps.setString(1, tenLop);
            ResultSet rs = ps.executeQuery();
       
            return rs;
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
     
     
       public ResultSet selectSiSoTong(String tenLop) {
        String sql = "select count(*) as ss from hocsinh join lophoc on hocsinh.lop=lophoc.malop and lophoc.tenlop= ? and  hocsinh.xoa=1";
        try{
            PreparedStatement ps = Jdbc.prepareStatement(sql);
            
            ps.setString(1, tenLop);
            ResultSet rs = ps.executeQuery();
       
            return rs;
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
     
     
     

     public HocSinh selectByKeyword(String keyword) {
        String sql = "select * from hocsinh where hoten like ?";
        List<HocSinh> list = select(sql, "%" + keyword + "%");
        return list.size() > 0 ? list.get(0) : null;
    }
     
       public HocSinh selectByKeyword2(String keyword) {
        String sql = "select * from hocsinh where hoten like ?";
        List<HocSinh> list = select(sql, "%" + keyword + "%");
        return list.size() > 0 ? list.get(0) : null;
    }

    public List<HocSinh> selectTenLop(String tenLop) {
        String sql = " select * from hocsinh  join lophoc on hocsinh.lop=lophoc.malop and lophoc.tenlop= ? and hocsinh.xoa=1";
        List<HocSinh> list = select(sql, tenLop);
        return list;
    }

    public List<HocSinh> loadTable(String tenLop, String hoten) {
        String sql = "select * from hocsinh  inner join lophoc on hocsinh.lop=lophoc.malop where lophoc.tenlop= ? and hocsinh.hoten=?  ";
        List<HocSinh> list = select(sql, tenLop);
        return list;
    }

    public void insert(HocSinh model) {
        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "insert into hocsinh(hoten,gioitinh,ngaysinh,diachi,dienthoai,email,cmnd,lop,dantoc,tongiao,ngayvaodoan,trangthai,hotenBo,hotenMe,dienthoaiBo,dienthoaiMe,dvCongTacBo,dvCongTacMe,images)"
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        JdbcHelper.executeUpdate(sql, model.getHoTen(), model.getGioiTinh(), sfd.format(model.getNgaySinh()), model.getDiaChi(), model.getDienThoai(), model.getEmail(), model.getCmnd(), model.getLop(), model.getDanToc(), model.getTongiao(), sfd.format(model.getNgayVaoDoan()),
                model.getTrangThai(), model.getHtBO(), model.getHtMe(), model.getSdtBo(), model.getSdtMe(), model.getDvctBo(), model.getDvctMe(),model.getImage());
    }

    public HocSinh findByName(String tl) {
        String sql = " select * from hocsinh  join lophoc on hocsinh.lop=lophoc.malop and lophoc.tenlop= ?";
        List<HocSinh> list = select(sql, tl);
        return list.size() > 0 ? list.get(0) : null;
    }

    public void delete(String m) {
        String sql = "update hocsinh set xoa=0, trangthai=N'Nghỉ học or Bảo lưu' where mahocsinh = ?";
        JdbcHelper.executeUpdate(sql, m);
    }

    public void update(HocSinh model) {
        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "update hocsinh set hoten=?,gioitinh=?,ngaysinh=?,diachi=?,dienthoai=?,email=?,cmnd=?,lop=?,dantoc=?,tongiao=?,ngayvaodoan=?,trangthai=?,hotenBo=?,hotenMe=?,dienthoaiBo=?,dienthoaiMe=?,dvCongTacBo=?,dvCongTacMe=? where mahocsinh=?";
        JdbcHelper.executeUpdate(sql, model.getHoTen(), model.getGioiTinh(),sfd.format(model.getNgaySinh()), model.getDiaChi(), model.getDienThoai(), model.getEmail(), model.getCmnd(), model.getLop(), model.getDanToc(), model.getTongiao(), sfd.format(model.getNgayVaoDoan()),
                model.getTrangThai(), model.getHtBO(), model.getHtMe(), model.getSdtBo(), model.getSdtMe(), model.getDvctBo(), model.getDvctMe(), model.getMaHocSinh());

    }

    private List<HocSinh> select(String sql, Object... args) {
        List<HocSinh> list = new ArrayList<>();
        try {
            ResultSet rs = null;

            rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                HocSinh model = readFromResultSet(rs);
                list.add(model);

            }
            rs.getStatement().getConnection().close();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;

    }

    private HocSinh readFromResultSet(ResultSet rs) throws SQLException {
        HocSinh model = new HocSinh();
        model.setMaHocSinh(rs.getString("mahocsinh"));
        model.setHoTen(rs.getString("hoten"));
        model.setNgaySinh(rs.getDate("ngaysinh"));
        model.setGioiTinh(rs.getBoolean("gioitinh"));

        model.setDiaChi(rs.getString("diachi"));
        model.setDienThoai(rs.getString("dienthoai"));
        model.setEmail(rs.getString("email"));
        model.setCmnd(rs.getString("cmnd"));
        model.setLop(rs.getString("lop"));
        model.setDanToc(rs.getString("dantoc"));
        model.setTongiao(rs.getString("tongiao"));
        model.setNgayVaoDoan(rs.getDate("ngayvaodoan"));
        model.setTrangThai(rs.getString("trangthai"));
        model.setHtBO(rs.getString("hotenBo"));
        model.setHtMe(rs.getString("hotenMe"));
        model.setSdtBo(rs.getString("dienthoaiBo"));
        model.setSdtMe(rs.getString("dienthoaiMe"));
        model.setDvctBo(rs.getString("dvCongTacBo"));
        model.setDvctMe(rs.getString("dvCongTacMe"));
        model.setImage(rs.getBytes("images"));
        return model;
    }

}
