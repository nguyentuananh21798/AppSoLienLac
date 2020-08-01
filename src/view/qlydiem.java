/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import da.dao.DiemDAO;
import da.dao.LopDAO;
import da.dao.MonDAO;
import da.helper.DialogHelper;
import da.model.Diem;
import da.model.Lop;
import da.model.Mon;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rahmans
 */
public class qlydiem extends javax.swing.JFrame {

    static boolean maximized = true;
    int xMouse;
    int yMouse;
    String head[] = {"STT", "Mã học sinh", "Họ và tên", "Ngày sinh", "Điểm miệng 1", "Điểm miệng 2", "Điểm miệng 3", "Điểm 15p 1", "Điểm 15p 2", "Điểm 15p 3", "Điểm 1 tiết 1", "Điểm 1 tiết 2", "Điểm HK", "Điểm TB"};
    DefaultTableModel model = new DefaultTableModel(head, 0);
    DiemDAO dDao = new DiemDAO();
    MonDAO mDAO = new MonDAO();
    LopDAO lDAO = new LopDAO();

    public qlydiem() {
        initComponents();
        full();
        this.loadToCboLop();
        this.loadToCboMon();
        model.setRowCount(0);
        tblGridView.setModel(model);
        String tenLop = (String) cbo_Lop.getSelectedItem();
        String tenMon = (String) cbo_Mon.getSelectedItem();
        this.loadToTable(tenLop, tenMon);
    }

    public void loadToCboMon() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbo_Mon.getModel();
        model.removeAllElements();
        try {
            List<Mon> list = mDAO.select();
            for (Mon dv : list) {
                model.addElement(dv.getTenMon());

            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vẫn dữ liệu");
        }
    }

    void loadToCboLop() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbo_Lop.getModel();
        model.removeAllElements();
        try {
            List<Lop> list = lDAO.select();
            for (Lop dv : list) {
                model.addElement(dv.getTenLop());
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vẫn dữ liệu");
        }
    }

    public void loadToTable(String maLop, String maMon) {
        model.setRowCount(0);
        try {
            ResultSet rs = dDao.findByEve(maLop, maMon);
            while (rs.next()) {
                Vector row = new Vector();
                row.add("1");
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
            DialogHelper.alert(this, "Lỗi truy vẫn dữ liệu");
            e.printStackTrace();
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
        Double TBM = (((double) Math.round((m1 + m2 + m3 + diem15pl1 + diem15pl2 + diem15pl3 + diem45pl1 * 2 + diem45pl2 * 2 + diemHK * 3) / 13) * 100) / 100);
        lbl_diemTBM.setText(TBM.toString());
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
            dDao.update(model);
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vẫn dữ liệu");
            e.printStackTrace();
        }
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
        String m1 = tblGridView.getValueAt(row, 3).toString();
        String m2 = tblGridView.getValueAt(row, 4).toString();
        String m3 = tblGridView.getValueAt(row, 5).toString();
        String txt15p1 = tblGridView.getValueAt(row, 6).toString();
        String txt15p2 = tblGridView.getValueAt(row, 7).toString();
        String txt15p3 = tblGridView.getValueAt(row, 8).toString();
        String txt45p1 = tblGridView.getValueAt(row, 9).toString();
        String txt45p2 = tblGridView.getValueAt(row, 10).toString();
        String txthk = tblGridView.getValueAt(row, 11).toString();
        String tb = tblGridView.getValueAt(row, 12).toString();
        if (m1.equals("0") && m2.equals("0") && m3.equals("0") && txt15p1.equals("0") && txt15p2.equals("0") && txt15p3.equals("0") && txt45p1.equals("0") && txt45p2.equals("0") && txthk.equals("0") && tb.equals("0")) {
            txt_diemMieng1.setText(null);
            txt_diemMieng2.setText(null);
            txt_diemMieng3.setText(null);
            txt_15p1.setText(null);
            txt_15p2.setText(null);
            txt_15p3.setText(null);
            txt_45p1.setText(null);
            txt_45p2.setText(null);
            txt_hk.setText(null);
            lbl_diemTBM.setText(null);

        } else {

            lbl_mhs.setText(tblGridView.getValueAt(row, 1).toString());
            lbl_hvt.setText(tblGridView.getValueAt(row, 2).toString());
            lbl_ns.setText(tblGridView.getValueAt(row, 3).toString());
            txt_diemMieng1.setText(tblGridView.getValueAt(row, 4).toString());
            txt_diemMieng2.setText(tblGridView.getValueAt(row, 5).toString());
            txt_diemMieng3.setText(tblGridView.getValueAt(row, 6).toString());
            txt_15p1.setText(tblGridView.getValueAt(row, 7).toString());
            txt_15p2.setText(tblGridView.getValueAt(row, 8).toString());
            txt_15p3.setText(tblGridView.getValueAt(row, 9).toString());
            txt_45p1.setText(tblGridView.getValueAt(row, 10).toString());
            txt_45p2.setText(tblGridView.getValueAt(row, 11).toString());
            txt_hk.setText(tblGridView.getValueAt(row, 12).toString());
            lbl_diemTBM.setText(tblGridView.getValueAt(row, 13).toString());

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        btnExit = new javax.swing.JButton();
        btnMaximize = new javax.swing.JButton();
        btnMinimize = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGridView = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbl_mhs = new javax.swing.JLabel();
        lbl_hvt = new javax.swing.JLabel();
        lbl_ns = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txt_hk = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
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
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        lbl_diemTBM = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cbo_Lop = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        cbo_Mon = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cube UI");
        setUndecorated(true);
        setSize(new java.awt.Dimension(1000, 600));

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMaximize, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExit)
                .addContainerGap())
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnExit)
            .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(btnMaximize, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnMinimize))
        );

        tblGridView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "098992323", "Nguyễn Xuân Bách", "04/06/2020", "9", "9", "9", "9", "9", "9", "9", "9", "9", "0"},
                {"2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "0"},
                {"3", "3", "3", "3", "3", "3", "3", "3", "3", "3", "3", "3", "3", "0"},
                {"4", "5", "4", "4", "4", "4", "4", "4", "4", "4", "4", "0", "4", "4"},
                {"5", "5", "5", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
                {"4", "5", "5", null, null, null, null, null, null, null, null, null, null, null},
                {"4", "5", "5", null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã học sinh", "Họ tên", "Ngày sinh", "Điểm miệng 1", "Điểm miệng 2", "Điểm miệng 3", "Điểm 15p 1", "Điểm 15p 2", "Điểm 15p 3", "Điểm 1 tiết 1", "Điểm 1 tiết 2", "Điểm HK", "Điểm TB"
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin học sinh", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel1.setText("Mã học sinh :");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel2.setText("Họ và tên :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel3.setText("Ngày sinh :");

        lbl_mhs.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_mhs.setText("02932823");

        lbl_hvt.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_hvt.setText("Nguyễn Xuân Bách");

        lbl_ns.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_ns.setText("04/06/2000");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_mhs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(32, 32, 32)
                        .addComponent(lbl_ns, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(36, 36, 36)
                        .addComponent(lbl_hvt, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)))
                .addGap(12, 12, 12))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lbl_mhs))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lbl_hvt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lbl_ns))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Điểm hệ số 3", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(28, 28, 28)
                .addComponent(txt_hk, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_hk, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Điểm hệ số 1", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_diemMieng1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_diemMieng2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_diemMieng3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(28, 28, 28)
                        .addComponent(txt_15p1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_15p2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_15p3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_diemMieng2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_diemMieng3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_diemMieng1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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

        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jTextField3.setText("Nguyễn Xuân Bách");

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jButton1.setText("Tìm Kiếm Học Sinh");
        jButton1.setBorder(null);

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

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("Lớp :");

        cbo_Lop.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_Lop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_LopActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setText("Môn :");

        cbo_Mon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_Mon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_MonActionPerformed(evt);
            }
        });

        jButton2.setText("Nhập Excel");

        jButton3.setText("Xuất Excel");

        jButton4.setText("Cập Nhập");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbo_Lop, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(cbo_Mon, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(56, 56, 56)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(40, 40, 40))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1856, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(24, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField3)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbo_Lop, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbo_Mon, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(1898, 833));
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

    private void txt_hkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hkActionPerformed

    private void txt_diemMieng1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_diemMieng1ActionPerformed
       
    }//GEN-LAST:event_txt_diemMieng1ActionPerformed

    private void txt_diemMieng2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_diemMieng2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_diemMieng2ActionPerformed

    private void txt_diemMieng3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_diemMieng3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_diemMieng3ActionPerformed

    private void txt_15p1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_15p1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_15p1ActionPerformed

    private void txt_15p2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_15p2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_15p2ActionPerformed

    private void txt_15p3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_15p3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_15p3ActionPerformed

    private void txt_45p1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_45p1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_45p1ActionPerformed

    private void txt_45p2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_45p2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_45p2ActionPerformed

    private void tblGridViewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblGridViewKeyPressed

    }//GEN-LAST:event_tblGridViewKeyPressed

    private void tblGridViewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGridViewMouseClicked
        check();

    }//GEN-LAST:event_tblGridViewMouseClicked

    private void tblGridViewKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblGridViewKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DOWN || evt.getKeyCode() == KeyEvent.VK_UP) {
            check();

        }
    }//GEN-LAST:event_tblGridViewKeyReleased

    private void txt_diemMieng1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_diemMieng1MouseClicked

    }//GEN-LAST:event_txt_diemMieng1MouseClicked

    private void txt_diemMieng1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_diemMieng1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_diemMieng2.requestFocus();
            tinhDiemTBM();
        }
    }//GEN-LAST:event_txt_diemMieng1KeyPressed

    private void txt_diemMieng2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_diemMieng2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_diemMieng3.requestFocus();
            tinhDiemTBM();
        }
    }//GEN-LAST:event_txt_diemMieng2KeyPressed

    private void txt_diemMieng3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_diemMieng3KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_15p1.requestFocus();
            tinhDiemTBM();
        }
    }//GEN-LAST:event_txt_diemMieng3KeyPressed

    private void txt_15p1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_15p1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_15p2.requestFocus();
            tinhDiemTBM();
        }
    }//GEN-LAST:event_txt_15p1KeyPressed

    private void txt_15p2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_15p2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_15p3.requestFocus();
            tinhDiemTBM();
        }
    }//GEN-LAST:event_txt_15p2KeyPressed

    private void txt_15p3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_15p3KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_45p1.requestFocus();
            tinhDiemTBM();
        }
    }//GEN-LAST:event_txt_15p3KeyPressed

    private void txt_45p1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_45p1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_45p2.requestFocus();
            tinhDiemTBM();
        }
    }//GEN-LAST:event_txt_45p1KeyPressed

    private void txt_45p2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_45p2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_45p2KeyReleased

    private void txt_45p2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_45p2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_hk.requestFocus();
            tinhDiemTBM();
        }
    }//GEN-LAST:event_txt_45p2KeyPressed

    private void txt_hkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_hkKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hkKeyPressed

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

    private void cbo_LopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_LopActionPerformed
        String tenLop = (String) cbo_Lop.getSelectedItem();
        String tenMon = (String) cbo_Mon.getSelectedItem();
        this.loadToTable(tenLop, tenMon);
    }//GEN-LAST:event_cbo_LopActionPerformed

    private void cbo_MonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_MonActionPerformed
        String tenLop = (String) cbo_Lop.getSelectedItem();
        String tenMon = (String) cbo_Mon.getSelectedItem();
        this.loadToTable(tenLop, tenMon);
    }//GEN-LAST:event_cbo_MonActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.tinhDiemTBM();
        this.Update();
        this.loadToTable(cbo_Lop.getSelectedItem().toString(), cbo_Mon.getSelectedItem().toString());
        tuChoiNhap();
    }//GEN-LAST:event_jButton4ActionPerformed

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
    private javax.swing.JComboBox<String> cbo_Lop;
    private javax.swing.JComboBox<String> cbo_Mon;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel lbl_diemTBM;
    private javax.swing.JLabel lbl_hvt;
    private javax.swing.JLabel lbl_mhs;
    private javax.swing.JLabel lbl_ns;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JTable tblGridView;
    private javax.swing.JTextField txt_15p1;
    private javax.swing.JTextField txt_15p2;
    private javax.swing.JTextField txt_15p3;
    private javax.swing.JTextField txt_45p1;
    private javax.swing.JTextField txt_45p2;
    private javax.swing.JTextField txt_diemMieng1;
    private javax.swing.JTextField txt_diemMieng2;
    private javax.swing.JTextField txt_diemMieng3;
    private javax.swing.JTextField txt_hk;
    // End of variables declaration//GEN-END:variables
}
