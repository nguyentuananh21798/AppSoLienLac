/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package da.helper;

import da.dao.DiemDAO;
import da.dao.DiemDanhDAO;
import da.dao.MonDAO;
import da.model.Diem;
import da.model.Mon;
import da.model.diemDG;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

/**
 *
 * @author admin
 */
public class CsvFile {

    PrintWriter fileWriter1 = null;
    DiemDAO dDao = new DiemDAO();
    DiemDanhDAO dgDAO = new DiemDanhDAO();
    MonDAO mDAO = new MonDAO();

    public void writeForm2Csv(String filePath, String lop) {
        ResultSet rs = dDao.findByClass(lop);
        PrintWriter fileWriter1 = null;
        try {
            FileOutputStream os = new FileOutputStream(filePath);
            os.write(239);
            os.write(187);
            os.write(191);

            fileWriter1 = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));
            fileWriter1.println(" Mã học sinh, Họ tên, Ngày sinh, Điểm miệng 1, Điểm miệng 2, Điểm miệng 3, Điểm 15p 1, Điểm 15p 2, Điểm 15p 3, Điểm 1 tiết 1, Điểm 1 tiết 2, Điểm HK");

            while (rs.next()) {
                fileWriter1.append(rs.getString("mahocsinh"));
                fileWriter1.append(",");
                fileWriter1.append(rs.getString("hoten"));
                fileWriter1.append(",");
                fileWriter1.append(rs.getString("ngaysinh"));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(0));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(0));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(0));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(0));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(0));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(0));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(0));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(0));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(0));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(0));
                fileWriter1.append("\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                fileWriter1.flush();
                fileWriter1.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void writeFormCsv(String filePath, String lop) {
        ResultSet rs = dDao.findByClass(lop);
        PrintWriter fileWriter1 = null;
        try {
            FileOutputStream os = new FileOutputStream(filePath);
            os.write(239);
            os.write(187);
            os.write(191);

            fileWriter1 = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));
            fileWriter1.println(" Mã học sinh, Họ tên, Ngày sinh, Điểm KT thường xuyên 1, Điểm KT thường xuyên 2, Điểm KT thường xuyên 3, Điểm KT định kì 1, Điểm KT định kì 2, Điểm KT học kì");

            while (rs.next()) {
                fileWriter1.append(rs.getString("mahocsinh"));
                fileWriter1.append(",");
                fileWriter1.append(rs.getString("hoten"));
                fileWriter1.append(",");
                fileWriter1.append(rs.getString("ngaysinh"));
                fileWriter1.append(",");
                fileWriter1.append("Chưa Đạt");
                fileWriter1.append(",");
                fileWriter1.append("Chưa Đạt");
                fileWriter1.append(",");
                fileWriter1.append("Chưa Đạt");
                fileWriter1.append(",");
                fileWriter1.append("Chưa Đạt");
                fileWriter1.append(",");
                fileWriter1.append("Chưa Đạt");
                fileWriter1.append(",");
                fileWriter1.append("Chưa Đạt");
                fileWriter1.append("\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                fileWriter1.flush();
                fileWriter1.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeForm1Csv(String filePath) {

        PrintWriter fileWriter1 = null;
        try {
            FileOutputStream os = new FileOutputStream(filePath);
            os.write(239);
            os.write(187);
            os.write(191);

            fileWriter1 = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));
            fileWriter1.println("Mã học sinh, Họ tên, Ngày sinh, Điểm học kì 1, Điểm học kì 2, Tổng kết, Học lực, Danh hiệu, Vắng có phép, Vắng không phép,Hạnh kiểm, Ghi chú");

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                fileWriter1.flush();
                fileWriter1.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void writeCsv(String maLop, String maMon, String filePath, String ki) {
        try {
            ResultSet rs = dDao.findDG(maLop, maMon, sethocki(ki));

            FileOutputStream os = new FileOutputStream(filePath);
            os.write(239);
            os.write(187);
            os.write(191);

            fileWriter1 = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));
            fileWriter1.println(" Mã học sinh, Họ tên, Ngày sinh, Điểm KT thường xuyên 1, Điểm KT thường xuyên 2, Điểm KT thường xuyên 3, Điểm KT định kì 1, Điểm KT định kì 2, Điểm KT học kì");
            while (rs.next()) {
                fileWriter1.append(rs.getString("mahocsinh"));
                fileWriter1.append(",");
                fileWriter1.append(rs.getString("hoten"));
                fileWriter1.append(",");
                fileWriter1.append(rs.getString("ngaysinh"));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(setdat(rs.getBoolean("diemTX1"))));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(setdat(rs.getBoolean("diemTX2"))));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(setdat(rs.getBoolean("diemTX3"))));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(setdat(rs.getBoolean("diemDK1"))));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(setdat(rs.getBoolean("diemDK2"))));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(setdat(rs.getBoolean("diemHK"))));
                fileWriter1.append("\n");
            }System.out.println("đã chạy 3");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                fileWriter1.flush();
                fileWriter1.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void write1Csv(String tenLop, String filePath) {
        try {
            ResultSet rs = dDao.kqHk1(tenLop);
            ResultSet rs2 = dDao.kqHk2(tenLop);

            FileOutputStream os = new FileOutputStream(filePath);
            os.write(239);
            os.write(187);
            os.write(191);

            fileWriter1 = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));
               fileWriter1.println("Mã học sinh, Họ tên, Ngày sinh, Điểm học kì 1, Điểm học kì 2, Tổng kết, Học lực, Danh hiệu, Vắng có phép, Vắng không phép,Hạnh kiểm, Ghi chú");
            while (rs.next() && rs2.next()) {
                fileWriter1.append(rs.getString("mahocsinh"));
                fileWriter1.append(",");
                fileWriter1.append(rs.getString("hoten"));
                fileWriter1.append(",");
                fileWriter1.append(rs.getString("ngaysinh"));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(rs.getFloat("TBhocKi1")));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(rs2.getFloat("TBhocKi2")));
                fileWriter1.append(",");
                Float rate = (rs.getFloat("TBhocKi1") + rs2.getFloat("TBhocKi2")) / 2;
                fileWriter1.append(String.valueOf((double) Math.round(rate * 10) / 10));
                fileWriter1.append(",");
                if (rate >= 8) {
                    fileWriter1.append("Giỏi");
                    fileWriter1.append(",");
                    fileWriter1.append("Học Sinh Giỏi");
                } else if (rate >= 7 && rate < 8) {
                    fileWriter1.append("Khá");
                    fileWriter1.append(",");
                    fileWriter1.append("Học Sinh Khá");
                } else if (rate >= 5 && rate < 7) {
                    fileWriter1.append("Trung Bình");
                    fileWriter1.append(",");
                    fileWriter1.append("Không Có");
                } else if (rate < 5) {
                    fileWriter1.append("Yếu");
                    fileWriter1.append(",");
                    fileWriter1.append("Không Có");
                }
                fileWriter1.append(",");
                int sobuoinghicp = dgDAO.selectNghiCoPhep(rs.getString("mahocsinh"));
                fileWriter1.append(String.valueOf(sobuoinghicp));
                fileWriter1.append(",");
                int sobuoinghikp = dgDAO.selectNghiKoCoPhep(rs.getString("mahocsinh"));
                fileWriter1.append(String.valueOf(sobuoinghikp));
                fileWriter1.append(",");
                int tongNghi = sobuoinghicp + sobuoinghikp;
                if (tongNghi >= 30) {
                    fileWriter1.append("Yếu");
                } else if (tongNghi >= 10 && tongNghi < 30) {
                    fileWriter1.append("Trung Bình");
                } else if (tongNghi >= 5 && tongNghi < 10) {
                    fileWriter1.append("Khá");
                } else if (tongNghi < 5) {
                    fileWriter1.append("Tốt");
                }
                fileWriter1.append(",");
                if (tongNghi > 20 || rate < 5 && rate >= 3) {
                    fileWriter1.append("rèn luyện lại");
                } else if (tongNghi > 20 || rate < 3) {
                    fileWriter1.append("Lưu Ban");
                } else {
                    fileWriter1.append("");
                }
                fileWriter1.append("\n");
            }System.out.println("đã chạy 3");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                fileWriter1.flush();
                fileWriter1.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void write2Csv(String maLop, String maMon, String filePath, String ki) {
        try {
            System.out.println(maLop + "---" + maMon + "----" + sethocki(ki));
            ResultSet rs = dDao.findByEve(maLop, maMon, sethocki(ki));
            FileOutputStream os = new FileOutputStream(filePath);
            os.write(239);
            os.write(187);
            os.write(191);
            fileWriter1 = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));
            fileWriter1.println(" Mã học sinh11, Họ tên, Ngày sinh, Điểm miệng 1, Điểm miệng 2, Điểm miệng 3, Điểm 15p 1, Điểm 15p 2, Điểm 15p 3, Điểm 1 tiết 1, Điểm 1 tiết 2, Điểm HK");
            System.out.println("đã chạy y1");
            int i = 0;
            while (rs.next()) {
                i++;
                System.out.println("đã chạy y");
                fileWriter1.append(rs.getString("mahocsinh"));
                fileWriter1.append(",");
                fileWriter1.append(rs.getString("hoten"));
                fileWriter1.append(",");
                fileWriter1.append(rs.getString("ngaysinh"));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(rs.getInt("diemMieng1")));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(rs.getInt("diemMieng2")));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(rs.getInt("diemMieng3")));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(rs.getInt("diem15phut1")));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(rs.getInt("diem15phut2")));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(rs.getInt("diem15phut3")));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(rs.getFloat("diem1Tiet1")));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(rs.getFloat("diem1Tiet2")));
                fileWriter1.append(",");
                fileWriter1.append(String.valueOf(rs.getFloat("diemthi")));
                fileWriter1.append("\n");
            }
            System.out.println(i);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                fileWriter1.flush();
                fileWriter1.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

        public void readCsv(String mamon, String hocki, String filePath) {
        BufferedReader reader = null;
        try {
            diemDG model = new diemDG();
            String line = "";
            reader = new BufferedReader(new FileReader(filePath));
            reader.readLine();
            System.out.println("đã chạy 1");
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length > 0) {
            model.setMaMon(mDAO.selectByName(mamon).getMaMon());
                    System.out.println(fields[4]);
            model.setDiemTX1(getdat(fields[3]));
            model.setDiemTX2(getdat(fields[4]));
            model.setDiemTX3(getdat(fields[5]));
            model.setDiemDK1(getdat(fields[6]));
            model.setDiemDK2(getdat(fields[7]));
            model.setDiemHK(getdat(fields[8]));
            model.setMaHocSinh(fields[0]);
            model.setHocKi(sethocki(hocki));
            dDao.updateDG(model);
                    System.out.println("đã chạy 3");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public void read2Csv(String mamon, String hocki, String filePath) {
        BufferedReader reader = null;
        try {
            Diem model = new Diem();
            String line = "";
            reader = new BufferedReader(new FileReader(filePath));
            reader.readLine();
            System.out.println("đã chạy 1");
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length > 0) {
            model.setMaMon(mDAO.selectByName(mamon).getMaMon());
            model.setDiemMieng1(Integer.parseInt(fields[3]));
            model.setDiemMieng2(Integer.parseInt(fields[4]));
            model.setDiemMieng3(Integer.parseInt(fields[5]));
            model.setDiem15p1(Integer.parseInt(fields[6]));
            model.setDiem15p2(Integer.parseInt(fields[7]));
            model.setDiem15p3(Integer.parseInt(fields[8]));
            model.setDiem1Tiet1(Float.parseFloat(fields[9]));
            model.setDiem1Tiet2(Float.parseFloat(fields[10]));
            model.setDiemThi(Float.parseFloat(fields[11]));
            model.setTBM(Float.parseFloat("9"));
            model.setMaHocSinh(fields[0]);
            model.setHocKi(sethocki(hocki));
             System.out.println(mDAO.selectByName(mamon).getMaMon()+fields[3]+fields[4]);
            dDao.update(model);
                   
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private boolean sethocki(String hocki) {
        if (hocki.equals("Học Kỳ 1")) {
            return false;
        } else {
            return true;
        }
    }
        private String setdat(boolean diem) {
        if (diem == true) {
            return "Đạt";
        } else {
            return "Chưa Đạt";
        }
    }
        private boolean getdat(String diem) {
        if (diem.equals("Đạt")) {
            return true;
        } else {
            return false;
        }
    }
}
//Xuân Hải: 0988136358
