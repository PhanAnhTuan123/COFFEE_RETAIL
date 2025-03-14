package view.QuanLy;

import db.ConnectDB;
import list.List_DanhMuc;
import model.DanhMuc;
import runapp.Login;
import service.DanhMuc_DAO;
import testbutton.Buttontest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class view_QuanLyDanhMuc extends JFrame implements ActionListener {
    private String tempMaDanhMuc;
    private DanhMuc_DAO danhMuc_dao;
    private List_DanhMuc list_DanhMuc;
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JButton btnThem, btnXoa, btnSua, btnLamMoi, btntimkiem;
    private JLabel lbltennv;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtTimKiem, txtTenDanhMuc, txtMoTa;
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    private JPanel panelHangHoa, panelDatHang, panelNhanVien, panelTaiKhoan, panelThongKe;
    Color customColor = new Color(255, 255, 255, 0);
    Color whiteColor = new Color(255, 255, 255, 0);
    private JLabel lblNvIcon;

    /**
     * Launch the application.
     */
    public static void main(String[] args) throws Exception {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(view_QuanLyDanhMuc.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(view_QuanLyDanhMuc.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(view_QuanLyDanhMuc.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(view_QuanLyDanhMuc.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }

        try {
            ConnectDB.getInstance().connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        view_QuanLyDanhMuc frame = new view_QuanLyDanhMuc();
        frame.setVisible(true);
    }

    /**
     * Create the frame.
     * @throws SQLException
     */
    public view_QuanLyDanhMuc() throws SQLException {
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        danhMuc_dao = new DanhMuc_DAO();
        list_DanhMuc = new List_DanhMuc();

        initComponents();
        setResizable(false);
        setBackground(Color.WHITE);
        setTitle("Giao Diện Quản Lý Danh Mục");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        setBounds(100, 100, 1168, 650);
        contentPane = new JPanel();
        contentPane.setBorder(null);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        lblNvIcon = new JLabel("");
        lblNvIcon.setIcon(new ImageIcon(view_QuanLyDanhMuc.class.getResource("/image/avt.png")));
        lblNvIcon.setBounds(760, 5, 40, 40);
        contentPane.add(lblNvIcon);

        JLabel lblnhanvien = new JLabel("QL:");
        lblnhanvien.setForeground(Color.WHITE);
        lblnhanvien.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblnhanvien.setBounds(801, 0, 39, 50);
        lblnhanvien.setForeground(Color.WHITE);
        contentPane.add(lblnhanvien);

        lbltennv = new JLabel("Trương Đại Lộc");
        lbltennv.setForeground(Color.WHITE);
        lbltennv.setFont(new Font("Tahoma", Font.BOLD, 16));
        lbltennv.setBounds(832, 0, 238, 50);
        lbltennv.setForeground(Color.WHITE);
        contentPane.add(lbltennv);

        // Thêm panel nằm ngang ở trên cùng
        JPanel topPanel = new JPanel() {
            private static final long serialVersionUID = 1L;

            protected void paintComponent(Graphics g) {
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        topPanel.setOpaque(false);
        topPanel.setBounds(0, 0, 1168, 50);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(customColor);
        contentPane.add(topPanel);

        // Thêm toolbar "Hàng hóa"
        JToolBar qlyHangHoaToolbar = new JToolBar();
        qlyHangHoaToolbar.setFloatable(false);
        qlyHangHoaToolbar.setMargin(new java.awt.Insets(-5, -5, 0, -5));
        testbutton.Buttontest qlyHangHoaButton = new Buttontest();
        qlyHangHoaButton.setText("Hàng hóa");
        qlyHangHoaButton.setFont(new Font("Open Sans", Font.BOLD, 15));
        qlyHangHoaButton.setForeground(SystemColor.text);
        qlyHangHoaButton.setRippleColor(new Color(255, 255, 255));
        qlyHangHoaButton.setBackground(new Color(255, 128, 64));
        qlyHangHoaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (panelHangHoa.isVisible() || panelDatHang.isVisible() || panelNhanVien.isVisible()
                        || panelTaiKhoan.isVisible() || panelThongKe.isVisible()) {
                    panelHangHoa.setVisible(false);
                    panelDatHang.setVisible(false);
                    panelNhanVien.setVisible(false);
                    panelTaiKhoan.setVisible(false);
                    panelThongKe.setVisible(false);
                } else {
                    panelHangHoa.setVisible(true);
                }
            }
        });
        qlyHangHoaToolbar.add(qlyHangHoaButton);
        qlyHangHoaToolbar.setBackground(customColor);
        topPanel.add(qlyHangHoaToolbar);

        panelHangHoa = new JPanel() {
            private static final long serialVersionUID = 1L;

            protected void paintComponent(Graphics g) {
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        panelHangHoa.setBounds(0, 49, 1175, 47);
        panelHangHoa.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelHangHoa.setVisible(false);
        panelHangHoa.setBackground(whiteColor);
        contentPane.add(panelHangHoa);

        JButton btnqlyMonAn = new JButton("Quản Lý Món Ăn");
        btnqlyMonAn.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnqlyMonAn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view_QuanLyHangHoa gdqlhh;
                try {
                    gdqlhh = new view_QuanLyHangHoa();
                    gdqlhh.setLocationRelativeTo(null);
                    gdqlhh.setVisible(true);
                    dispose();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        JButton btnqlyDanhMuc = new JButton("Quản Lý Danh Mục");
        btnqlyDanhMuc.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnqlyDanhMuc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Stay on current page
            }
        });

        panelHangHoa.add(btnqlyMonAn);
        panelHangHoa.add(btnqlyDanhMuc);

        // Add other toolbars and panels similar to the original code...
        // Thêm toolbar "dịch vụ"
        JToolBar datHangToolbar = new JToolBar();
        datHangToolbar.setFloatable(false);
        datHangToolbar.setMargin(new java.awt.Insets(-5, -5, 0, -5));
        testbutton.Buttontest datHangButton = new Buttontest();
        datHangButton.setText("Đặt Hàng");
        datHangButton.setFont(new Font("Open Sans", Font.BOLD, 15));
        datHangButton.setForeground(SystemColor.text);
        datHangButton.setRippleColor(new Color(255, 255, 255));
        datHangButton.setBackground(new Color(46, 139, 87));
        datHangButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (panelHangHoa.isVisible() || panelDatHang.isVisible() || panelNhanVien.isVisible()
                        || panelTaiKhoan.isVisible() || panelThongKe.isVisible()) {
                    panelHangHoa.setVisible(false);
                    panelDatHang.setVisible(false);
                    panelNhanVien.setVisible(false);
                    panelTaiKhoan.setVisible(false);
                    panelThongKe.setVisible(false);
                } else {
                    panelDatHang.setVisible(true);
                }
            }
        });
        datHangToolbar.add(datHangButton);
        datHangToolbar.setBackground(customColor);
        topPanel.add(datHangToolbar);

        panelDatHang = new JPanel() {
            private static final long serialVersionUID = 1L;

            protected void paintComponent(Graphics g) {
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        panelDatHang.setBounds(0, 49, 1175, 47);
        panelDatHang.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelDatHang.setVisible(false);
        panelDatHang.setBackground(whiteColor);
        contentPane.add(panelDatHang);

        JButton btnKhachHang = new JButton("Quản Lý Khách Hàng");
        btnKhachHang.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnKhachHang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view_QuanLyKhachHang gdqlkh;
                try {
                    gdqlkh = new view_QuanLyKhachHang();
                    gdqlkh.setLocationRelativeTo(null);
                    gdqlkh.setVisible(true);
                    dispose();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        panelDatHang.add(btnKhachHang);

        // Create other toolbar buttons and panels similar to original code...

        // Create logout button
        JToolBar logoutToolBar = new JToolBar();
        logoutToolBar.setFloatable(false);
        logoutToolBar.setMargin(new java.awt.Insets(-5, 550, 0, 0));
        testbutton.Buttontest logoutButton = new Buttontest();
        logoutButton.setText("Đăng Xuất");
        logoutButton.setFont(new Font("Open Sans", Font.BOLD, 15));
        logoutButton.setForeground(SystemColor.text);
        logoutButton.setRippleColor(new Color(255, 255, 255));
        logoutButton.setBackground(new Color(226, 110, 110));
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Bạn có muốn đăng xuất!", null,
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    Login lg;
                    try {
                        lg = new Login();
                        lg.setVisible(true);
                        lg.setLocationRelativeTo(null);
                        dispose();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        logoutToolBar.add(logoutButton);
        logoutToolBar.setBackground(customColor);
        topPanel.add(logoutToolBar);

        JLabel lblQLDM = new JLabel("Quản Lý Danh Mục");
        lblQLDM.setForeground(new Color(255, 255, 255));
        lblQLDM.setFont(new Font("Open Sans", 1, 16));
        lblQLDM.setBounds(43, 102, 170, 20);
        contentPane.add(lblQLDM);

        JLabel lblTen = new JLabel("Tên danh mục:");
        lblTen.setForeground(new Color(255, 255, 255));
        lblTen.setFont(new Font("Dialog", Font.PLAIN, 16));
        lblTen.setBounds(10, 132, 130, 21);
        contentPane.add(lblTen);

        JPanel pnlTen = new JPanel();
        pnlTen.setBackground(new Color(255, 255, 0));
        pnlTen.setBounds(10, 155, 230, 37);
        pnlTen.setOpaque(false);
        contentPane.add(pnlTen);

        // Add text field for name
        txtTenDanhMuc = new JTextField();
        txtTenDanhMuc.setFont(new Font("Open Sans", 0, 16));
        txtTenDanhMuc.setColumns(16);
        pnlTen.add(txtTenDanhMuc);

        // Add label and panel for description
        JLabel lblMoTa = new JLabel("Mô tả:");
        lblMoTa.setForeground(new Color(255, 255, 255));
        lblMoTa.setFont(new Font("Dialog", Font.PLAIN, 16));
        lblMoTa.setBounds(10, 200, 130, 21);
        contentPane.add(lblMoTa);

        JPanel pnlMoTa = new JPanel();
        pnlMoTa.setBackground(new Color(255, 255, 0));
        pnlMoTa.setBounds(10, 225, 230, 100);
        pnlMoTa.setOpaque(false);
        contentPane.add(pnlMoTa);

        // Add text area for description
        txtMoTa = new JTextField();
        txtMoTa.setFont(new Font("Open Sans", 0, 16));
        txtMoTa.setColumns(16);
        pnlMoTa.add(txtMoTa);

        txtTimKiem = new JTextField();
        txtTimKiem.setFont(new Font("Dialog", Font.PLAIN, 16));
        txtTimKiem.setColumns(16);
        txtTimKiem.setBounds(871, 99, 214, 30);
        contentPane.add(txtTimKiem);

        btntimkiem = new JButton("");
        btntimkiem.setIcon(new ImageIcon(view_QuanLyDanhMuc.class.getResource("/image/search.png")));
        btntimkiem.setBounds(1090, 99, 40, 30);
        contentPane.add(btntimkiem);

        // Khởi tạo các nút
        btnThem = new JButton("Thêm");
        btnXoa = new JButton("Xóa");
        btnSua = new JButton("Sửa");
        btnLamMoi = new JButton("Làm mới");

        // Đặt vị trí cho các nút
        btnThem.setBounds(250, 99, 100, 30);
        btnXoa.setBounds(360, 99, 100, 30);
        btnSua.setBounds(470, 99, 100, 30);
        btnLamMoi.setBounds(580, 99, 100, 30);

        // Thêm các nút vào contentPane
        contentPane.add(btnThem);
        contentPane.add(btnXoa);
        contentPane.add(btnSua);
        contentPane.add(btnLamMoi);

        // Khởi tạo DefaultTableModel với các cột
        String[] columnNames = {"Mã Danh Mục", "Tên Danh Mục", "Mô tả"};
        tableModel = new DefaultTableModel(columnNames, 0);

        // Khởi tạo JTable với DefaultTableModel
        table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(500);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int r = table.getSelectedRow();
                txtTenDanhMuc.setText(tableModel.getValueAt(r, 1).toString());
                txtMoTa.setText(tableModel.getValueAt(r, 2).toString());
                tempMaDanhMuc = tableModel.getValueAt(r, 0).toString();
                btnSua.setEnabled(true);
                btnXoa.setEnabled(true);
            }
        });

        // Tạo JScrollPane để thêm bảng vào để có thể cuộn
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(250, 140, 900, 469);

        // Thêm bảng và JScrollPane vào contentPane
        contentPane.add(scrollPane);

        // add su kien
        btnThem.addActionListener(this);
        btnXoa.addActionListener(this);
        btnSua.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btntimkiem.addActionListener(this);

        JLabel background = new JLabel("");
        background.setHorizontalAlignment(SwingConstants.CENTER);
        background.setIcon(new ImageIcon(view_QuanLyDanhMuc.class.getResource("/image/bgCF.jpg")));
        background.setBounds(0, 0, 1162, 613);
        contentPane.add(background);

        loadData();
        refresh();
    }

    private void loadData() throws SQLException {
        tableModel.setRowCount(0);
        for (DanhMuc item : list_DanhMuc.getAll()) {
            tableModel.addRow(new Object[] {
                    item.getMaDanhMuc(),
                    item.getTenDanhMuc(),
                    item.getMoTa()
            });
        }
    }

    public void loadByName() {
        String ten = txtTimKiem.getText();
        ArrayList<DanhMuc> tempList = new ArrayList<DanhMuc>();
        tempList = list_DanhMuc.findByName(ten);
        tableModel.setRowCount(0);
        for(DanhMuc item : tempList) {
            tableModel.addRow(new Object[] {
                    item.getMaDanhMuc(),
                    item.getTenDanhMuc(),
                    item.getMoTa()
            });
        }
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 300, Short.MAX_VALUE));

        pack();
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        Main_form_manager gdql = new Main_form_manager();
        gdql.setLocationRelativeTo(null);
        gdql.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnThem)) {
            if(validInputs()) {
                DanhMuc danhMuc = new DanhMuc();
                danhMuc.setMaDanhMuc(danhMuc_dao.sinhMaDanhMuc());
                danhMuc.setTenDanhMuc(txtTenDanhMuc.getText());
                danhMuc.setMoTa(txtMoTa.getText());
                try {
                    danhMuc_dao.save(danhMuc);
                    loadData();
                    JOptionPane.showMessageDialog(null, "Thêm danh mục thành công!");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Lỗi: " + e1.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!");
            }
        }

        if(e.getSource().equals(btnSua)) {
            if(validInputs()) {
                DanhMuc danhMuc = new DanhMuc();
                danhMuc.setMaDanhMuc(tempMaDanhMuc);
                danhMuc.setTenDanhMuc(txtTenDanhMuc.getText());
                danhMuc.setMoTa(txtMoTa.getText());
                try {
                    danhMuc_dao.update(danhMuc);
                    loadData();
                    JOptionPane.showMessageDialog(null, "Sửa danh mục thành công!");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Lỗi: " + e1.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!");
            }
        }

        if(e.getSource().equals(btnXoa)) {
            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa danh mục này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION) {
                DanhMuc danhMuc = new DanhMuc();
                danhMuc.setMaDanhMuc(tempMaDanhMuc);

                try {
                    danhMuc_dao.delete(danhMuc);
                    loadData();
                    refresh();
                    JOptionPane.showMessageDialog(null, "Xóa danh mục thành công!");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Lỗi: " + e1.getMessage());
                }
            }
        }

        if(e.getSource().equals(btntimkiem)) {
            loadByName();
        }

        if(e.getSource().equals(btnLamMoi)) {
            try {
                loadData();
                refresh();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void refresh() {
        txtTenDanhMuc.setText("");
        txtMoTa.setText("");
        tempMaDanhMuc = null;
        txtTimKiem.setText("");
        btnXoa.setEnabled(false);
        btnSua.setEnabled(false);
        btnThem.setEnabled(true);
    }

    public boolean validInputs() {
        if(txtTenDanhMuc.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập tên danh mục!");
            return false;
        }
        return true;
    }
}