
package view.NhanVien;

import db.ConnectDB;
import list.*;
import model.*;
import runapp.Login;
import service.Ban_DAO;
import service.HangHoa_DAO;
import service.HoaDon_DAO;
import testbutton.Buttontest;
import view.QuanLy.view_QuanLyNhanVien;

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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class view_hoaDon extends JFrame implements ActionListener {
	private String tempMaBan;
	private Ban_DAO ban_dao;
	private List_Ban list_Ban;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnThem, btnXoa, btnSua, btntimkiem;
	public JLabel lbltennv;
	private JTable tableShowSP;
	private DefaultTableModel tableModel;
	private DefaultTableModel tableModelSanPham;
	private JTextField txtSDT, txtDiaChi, txtTimKiem, txtMaHD;
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	private JPanel panelHangHoa, panelDatHang, panelNhanVien, panelTaiKhoan, panelThongKe;
	Color customColor = new Color(255, 255, 255, 0);
	Color whiteColor = new Color(255, 255, 255, 0);
	private JLabel lblNvIcon; // Thêm biến để lưu đối tượng JLabel chứa ảnh NV
	private List_NhanVien list_nv = new List_NhanVien();
	private JTable table_chonSP;
	private JTextField txtKhachHang;
	private JTextField txtTien;
	private JTextField txtChietKhau;
	private JTextField txtTongTien;
	private JTextField txtSoLuong;
	private HangHoa_DAO hanghoa_dao;
	private List_HangHoa list_hangHoa;
	private JTextField txtMaHangHoa;
	private Buttontest btnTaoMoi;
	private Buttontest btnDatHang;
	private Buttontest btnHuy;
	private view_showKhachHang view_showKH;
	private JButton btnTimKhachHang;
	private HoaDon_DAO daoHD;
	private List_HoaDon listHD;
	private List_ChiTietHoaDon listChiTietHD;
	private view_showBan viewBan;
	public Ban tempBan;
	public KhachHang tempKH;
	public NhanVien tempNV;

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
			java.util.logging.Logger.getLogger(view_QuanLyNhanVien.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(view_QuanLyNhanVien.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(view_QuanLyNhanVien.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(view_QuanLyNhanVien.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		}

		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		view_hoaDon frame = new view_hoaDon();
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	/**
	 * Create the frame.
	 * 
	 * @throws Exception
	 */
	public view_hoaDon() throws Exception {
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		tempNV = new NhanVien();
		tempNV.setMaNV("NV001");
		ban_dao = new Ban_DAO();
		list_Ban = new List_Ban();
		hanghoa_dao = new HangHoa_DAO();
		list_hangHoa = new List_HangHoa();
		view_showKH = new view_showKhachHang();
		listHD = new List_HoaDon();
		listChiTietHD = new List_ChiTietHoaDon();
		initComponents();
		setResizable(false);
		setBackground(Color.WHITE);
		setTitle("Giao Diện Tạo Hóa Đơn");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		tableModelSanPham = new DefaultTableModel();
		this.setLocationRelativeTo(null);
		setBounds(100, 100, 1168, 650);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblNvIcon = new JLabel("");
		lblNvIcon.setIcon(new ImageIcon(view_QuanLyNhanVien.class.getResource("/image/avt.png"))); // Thay đổi đường dẫn
																									// ảnh của bạn
		lblNvIcon.setBounds(760, 5, 40, 40); // Điều chỉnh tọa độ và kích thước của ảnh
		contentPane.add(lblNvIcon);

		JLabel lblnhanvien = new JLabel("NV:");
		lblnhanvien.setForeground(Color.WHITE);
		lblnhanvien.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblnhanvien.setBounds(801, 0, 39, 50);
		lblnhanvien.setForeground(Color.WHITE);
		contentPane.add(lblnhanvien);

		lbltennv = new JLabel("");
		lbltennv.setForeground(Color.WHITE);
		lbltennv.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbltennv.setBounds(832, 0, 238, 50);
		lbltennv.setForeground(Color.WHITE);
//		lbltennv.setText(UserInfo.getTenNhanVien());
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
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Sử dụng FlowLayout
		topPanel.setBackground(customColor); // Thay đổi ở đây
		contentPane.add(topPanel);


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
				if (panelDatHang.isVisible()) {
					panelDatHang.setVisible(false);
				} else {
					panelDatHang.setVisible(true);
				}
			}
		});
		datHangToolbar.add(datHangButton);
		datHangToolbar.setBackground(customColor); // Thay đổi ở đây
		topPanel.add(datHangToolbar);
		
		panelDatHang = new JPanel() {
			protected void paintComponent(Graphics g) {
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};
		panelDatHang.setBounds(0, 49, 1175, 47); // Điều chỉnh tọa độ và kích thước của panel theo ý muốn
		panelDatHang.setLayout(new FlowLayout(FlowLayout.LEFT)); // Thay đổi ở đây
		panelDatHang.setVisible(false); // tắt/ẩn panel
		panelDatHang.setBackground(whiteColor);
		contentPane.add(panelDatHang);

		JButton btnTaoHoaDon = new JButton("Tạo Hóa Đơn");
		btnTaoHoaDon.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnTaoHoaDon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					view_hoaDon gdTaoHD = new view_hoaDon();
					gdTaoHD.lbltennv.setText(lbltennv.getText());
					gdTaoHD.setVisible(true);
					gdTaoHD.setLocationRelativeTo(null);
					dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton btnXemHoaDon = new JButton("Xem Hóa Đơn");
		btnXemHoaDon.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnXemHoaDon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					view_xemHD gdXemHD = new view_xemHD();
					gdXemHD.lbltennv.setText(lbltennv.getText());
					gdXemHD.setVisible(true);
					gdXemHD.setLocationRelativeTo(null);
					dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		panelDatHang.add(btnTaoHoaDon);
		panelDatHang.add(btnXemHoaDon);
		
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
				        if (JOptionPane.showConfirmDialog(null, "Bạn có muốn đăng xuất!", null, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				            Login lg;
							try {
								lg = new Login();
					            lg.setVisible(true);
					            lg.setLocationRelativeTo(null);
					            dispose();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

				        }
					}
				});
		        logoutToolBar.add(logoutButton);
		        logoutToolBar.setBackground(customColor);
		        topPanel.add(logoutToolBar);


		JLabel lblQLKH = new JLabel("Quản Lý Hóa Đơn");
		lblQLKH.setForeground(new Color(255, 255, 255));
		lblQLKH.setFont(new Font("Open Sans", 1, 16));
		lblQLKH.setBounds(43, 102, 170, 20);
		contentPane.add(lblQLKH);

		JLabel lblHoTen = new JLabel("Mã Hóa Đơn:");
		lblHoTen.setForeground(new Color(255, 255, 255));
		lblHoTen.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblHoTen.setBounds(10, 132, 130, 21);
		contentPane.add(lblHoTen);

		JPanel pnlHoTen = new JPanel();
		pnlHoTen.setBackground(new Color(255, 255, 0));
		pnlHoTen.setBounds(10, 161, 230, 37);
		pnlHoTen.setOpaque(false);
		contentPane.add(pnlHoTen);

		// Thêm chúng vào ButtonGroup
		ButtonGroup buttonGroup = new ButtonGroup();

		// Add JTextField below JCheckBox
		txtMaHD = new JTextField();
		txtMaHD.setFont(new Font("Open Sans", 0, 16));
		txtMaHD.setEnabled(false);
		txtMaHD.setColumns(16); // You can adjust the column count based on your requirement
		pnlHoTen.add(txtMaHD);

		txtTimKiem = new JTextField();
		txtTimKiem.setFont(new Font("Dialog", Font.PLAIN, 16));
		txtTimKiem.setColumns(16);
		txtTimKiem.setBounds(886, 128, 214, 30);
		contentPane.add(txtTimKiem);

		btntimkiem = new JButton("");
		btntimkiem.setIcon(new ImageIcon(view_QuanLyNhanVien.class.getResource("/image/search.png")));
		btntimkiem.setBounds(1110, 128, 40, 30);
		contentPane.add(btntimkiem);

		// Khởi tạo các nút
		btnThem = new JButton("Thêm");
		btnThem.setBackground(new Color(50, 205, 50));
		btnXoa = new JButton("Xóa");
		btnXoa.setBackground(new Color(255, 140, 0));
		btnSua = new JButton("Sửa");
		btnSua.setBackground(new Color(0, 255, 255));

		// Đặt vị trí cho các nút
		btnThem.setBounds(610, 305, 100, 30);
		btnXoa.setBounds(732, 305, 100, 30);
		btnSua.setBounds(857, 305, 100, 30);
		// Thêm các nút vào contentPane
		contentPane.add(btnThem);
		contentPane.add(btnXoa);
		contentPane.add(btnSua);

		// Khởi tạo DefaultTableModel với các cột
		String[] columnNames = { "Mã", "Tên", "Giá", "Số Lượng", "Thành Tiền" }; // Thay đổi tên cột tùy ý
		tableModel = new DefaultTableModel(columnNames, 0);

		// Khởi tạo JTable với DefaultTableModel
		tableShowSP = new JTable(tableModel);
		tableShowSP.getColumnModel().getColumn(1).setPreferredWidth(50); // Đặt giá trị 300 làm ví dụ, bạn có thể điều
																			// chỉnh

		tableShowSP.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				int r = table.getSelectedRow();
//				txtHoTen.setText(tableModel.getValueAt(r,1).toString());
//				txtSDT.setText(tableModel.getValueAt(r,3).toString());
//				txtDiaChi.setText(tableModel.getValueAt(r,2).toString());
//				if(tableModel.getValueAt(r,5).toString().equals("Nam")) {
////					rdbtnNam.setSelected(true);
//				}else {
////					rdbtnNu.setSelected(true);
//				}
//				if(tableModel.getValueAt(r,4).toString().equals("Nhân Viên")) {
////					cboxChucVu.setSelectedIndex(0);
//				}else {
////					cboxChucVu.setSelectedIndex(1);
//				}
				int r = tableShowSP.getSelectedRow();
				btnXoa.setEnabled(true);
				btnThem.setEnabled(false);
				btnSua.setEnabled(true);
				txtMaHangHoa.setText(tableModel.getValueAt(r, 0).toString());
				txtSoLuong.setText(tableModel.getValueAt(r, 3).toString());
				btnSua.setEnabled(true);
				btnXoa.setEnabled(true);

			}
		});

		// theo ý muốn

		// Tạo JScrollPane để thêm bảng vào để có thể cuộn
		JScrollPane scrollPane = new JScrollPane(tableShowSP);
		scrollPane.setBounds(250, 347, 900, 169); // Điều chỉnh tọa độ và kích thước của bảng

		// Thêm bảng và JScrollPane vào contentPane
		contentPane.add(scrollPane);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.addMouseListener(new MouseAdapter() {

		});
		scrollPane_1.setBounds(250, 161, 900, 136);
		contentPane.add(scrollPane_1);
		String[] Column2 = { "Mã", "Tên", "Giá" }; // Thay đổi tên cột tùy ý
		tableModelSanPham = new DefaultTableModel(Column2, 0);

		// Khởi tạo JTable với DefaultTableModel
		table_chonSP = new JTable(tableModelSanPham);
		table_chonSP.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int n = table_chonSP.getSelectedRow();
				btnThem.setEnabled(true);
				txtMaHangHoa.setText(tableModelSanPham.getValueAt(n, 0).toString());
			}
		});
		table_chonSP.getColumnModel().getColumn(1).setPreferredWidth(50); // Đặt giá trị 300 làm ví dụ, bạn có thể điều
																			// chỉnh

//		table_1 = new JTable();
		table_chonSP.getColumnModel().getColumn(1).setPreferredWidth(50);
		scrollPane_1.setViewportView(table_chonSP);

		JLabel lblMKhchHng = new JLabel("Mã Khách Hàng");
		lblMKhchHng.setForeground(Color.WHITE);
		lblMKhchHng.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblMKhchHng.setBounds(10, 215, 130, 21);
		contentPane.add(lblMKhchHng);

		txtKhachHang = new JTextField();
		txtKhachHang.setText("");
		txtKhachHang.setEnabled(false);
		txtKhachHang.setFont(new Font("Dialog", Font.PLAIN, 16));
		txtKhachHang.setColumns(16);
		txtKhachHang.setBounds(20, 246, 161, 27);
		contentPane.add(txtKhachHang);

		JLabel lblTng = new JLabel("Tổng Tiền:");
		lblTng.setForeground(Color.WHITE);
		lblTng.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblTng.setBounds(10, 313, 130, 21);
		contentPane.add(lblTng);

		txtTien = new JTextField();
		txtTien.setText("");
		txtTien.setFont(new Font("Dialog", Font.PLAIN, 16));
		txtTien.setColumns(16);
		txtTien.setBounds(20, 344, 220, 27);
		txtTien.setEnabled(false);
		contentPane.add(txtTien);

		JLabel lblTng_1 = new JLabel("Chiết Khấu");
		lblTng_1.setForeground(Color.WHITE);
		lblTng_1.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblTng_1.setBounds(10, 405, 130, 21);
		contentPane.add(lblTng_1);

		txtChietKhau = new JTextField();
		txtChietKhau.setText("");
		txtChietKhau.setFont(new Font("Dialog", Font.PLAIN, 16));
		txtChietKhau.setColumns(16);
		txtChietKhau.setBounds(20, 436, 220, 27);
		txtChietKhau.setEnabled(false);
		contentPane.add(txtChietKhau);

		JLabel lblTng_2 = new JLabel("Tổng Tiền:");
		lblTng_2.setForeground(Color.WHITE);
		lblTng_2.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblTng_2.setBounds(10, 506, 130, 21);
		contentPane.add(lblTng_2);

		txtTongTien = new JTextField();
		txtTongTien.setText("");
		txtTongTien.setFont(new Font("Dialog", Font.PLAIN, 16));
		txtTongTien.setColumns(16);
		txtTongTien.setBounds(20, 539, 220, 27);
		txtTongTien.setEnabled(false);
		contentPane.add(txtTongTien);

		btnDatHang = new Buttontest();
		btnDatHang.setText("Đặt Hàng");
		btnDatHang.setRippleColor(Color.WHITE);
		btnDatHang.setForeground(SystemColor.text);
		btnDatHang.setFont(new Font("Dialog", Font.BOLD, 15));
		btnDatHang.setBackground(new Color(46, 139, 87));
		btnDatHang.setBounds(986, 528, 144, 56);
		contentPane.add(btnDatHang);

		btnHuy = new Buttontest();
		btnHuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnHuy.setText("Hủy Bỏ");
		btnHuy.setRippleColor(Color.WHITE);
		btnHuy.setForeground(SystemColor.text);
		btnHuy.setFont(new Font("Dialog", Font.BOLD, 15));
		btnHuy.setBackground(new Color(255, 0, 0));
		btnHuy.setBounds(696, 528, 144, 56);
		contentPane.add(btnHuy);

		btnTaoMoi = new Buttontest();
		btnTaoMoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnTaoMoi.setText("Tạo Mới");
		btnTaoMoi.setRippleColor(Color.WHITE);
		btnTaoMoi.setForeground(SystemColor.text);
		btnTaoMoi.setFont(new Font("Dialog", Font.BOLD, 15));
		btnTaoMoi.setBackground(new Color(0, 255, 255));
		btnTaoMoi.setBounds(842, 528, 144, 56);
		contentPane.add(btnTaoMoi);

		btnTimKhachHang = new JButton("");

		btnTimKhachHang.setBounds(191, 246, 40, 30);
		contentPane.add(btnTimKhachHang);
		btnTimKhachHang.setIcon(new ImageIcon(view_QuanLyNhanVien.class.getResource("/image/search.png")));

		JLabel lblSoLuong = new JLabel("Số Lượng:");
		lblSoLuong.setForeground(Color.WHITE);
		lblSoLuong.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblSoLuong.setBounds(421, 307, 88, 21);
		contentPane.add(lblSoLuong);

		txtSoLuong = new JTextField();
		txtSoLuong.setText("");
		txtSoLuong.setFont(new Font("Dialog", Font.PLAIN, 16));
		txtSoLuong.setColumns(16);
		txtSoLuong.setBounds(504, 304, 63, 27);
		contentPane.add(txtSoLuong);

		JLabel lblTimKiem = new JLabel("Tìm Kiếm Sản Phẩm");
		lblTimKiem.setForeground(Color.WHITE);
		lblTimKiem.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblTimKiem.setBounds(706, 132, 170, 21);
		contentPane.add(lblTimKiem);

		JLabel lblDanhMc = new JLabel("Danh Mục ");
		lblDanhMc.setForeground(Color.WHITE);
		lblDanhMc.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblDanhMc.setBounds(250, 132, 88, 26);
		contentPane.add(lblDanhMc);

		JComboBox combo_category = new JComboBox();
		combo_category.setBounds(343, 133, 161, 24);
		contentPane.add(combo_category);

		JLabel lblHangHoa = new JLabel("Hàng Hóa:");
		lblHangHoa.setForeground(Color.WHITE);
		lblHangHoa.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblHangHoa.setBounds(250, 307, 88, 21);
		contentPane.add(lblHangHoa);

		txtMaHangHoa = new JTextField();
		txtMaHangHoa.setText("");
		txtMaHangHoa.setEnabled(false);
		txtMaHangHoa.setFont(new Font("Dialog", Font.PLAIN, 16));
		txtMaHangHoa.setColumns(16);
		txtMaHangHoa.setBounds(335, 304, 76, 27);
		contentPane.add(txtMaHangHoa);

		btnThem.addActionListener(this);
		btnXoa.addActionListener(this);
		btnSua.addActionListener(this);
		btntimkiem.addActionListener(this);
		btnTimKhachHang.addActionListener(this);
		btnHuy.addActionListener(this);
		btnDatHang.addActionListener(this);
		btnTaoMoi.addActionListener(this);

		JLabel background = new JLabel("");
		background.setHorizontalAlignment(SwingConstants.CENTER);
		background.setIcon(new ImageIcon(view_QuanLyNhanVien.class.getResource("/image/bgCF.jpg")));
		background.setBounds(0, 5, 1162, 613);
		contentPane.add(background);
//		

		loadData();
		refresh();
		updateTongTien();
		setHD();
	}

	private void loadData() throws SQLException {

//		for(NhanVien nv : list_nv.getAll()) {
//			String gioiTinh = "";
//			if(nv.getGioiTinh() == true) {
//				gioiTinh = "Nam"; 
//			}else {
//				gioiTinh = "Nữ";
//			}
//			tableModel.addRow(new Object[] {nv.getMaNV(),nv.getTenNV(),nv.getDiaChi(),nv.getSdt(),nv.getChucVu(),gioiTinh});
//		}
		tableModelSanPham.setRowCount(0);
		for (HangHoa item : list_hangHoa.getAll()) {
			tableModelSanPham.addRow(new Object[] { item.getMaHH(), item.getTenHH(), item.getGia() });
		}
	}

	public void loadByName() {
		String ten = txtTimKiem.getText();
		ArrayList<Ban> tempList = new ArrayList<Ban>();
		tempList = list_Ban.findByName(ten);
		tableModel.setRowCount(0);
		for (Ban item : tempList) {
			tableModel.addRow(new Object[] { item.getMaBan(), item.getTenBan() });
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

	private void formWindowClosing(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowClosing
		Main_form_employee gdnv = new Main_form_employee();
		gdnv.lbltennv.setText(lbltennv.getText());
		gdnv.setLocationRelativeTo(null);
		gdnv.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnThem)) {
			System.out.println("Them!!");

			addTableHangHoa();
			updateTongTien();
		}
		if (e.getSource().equals(btnXoa)) {
			removetablehangHoa();
			updateTongTien();
		}
		if (e.getSource().equals(btnSua)) {
			updateTableHangHoa();
			updateTongTien();
		}
		if (e.getSource().equals(btnTaoMoi)) {
			taoMoiTatCa();
			updateTongTien();
		}
		if (e.getSource().equals(btnDatHang)) {
			try {
				datHang();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (e.getSource().equals(btnHuy)) {
			huyBo();
			updateTongTien();
		}
		if (e.getSource().equals(btnTimKhachHang)) {
			try {
				showViewKH();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private void showViewKH() throws Exception {
		view_showKH = new view_showKhachHang();
		view_showKH.setView(this);
		view_showKH.setVisible(true);

	}

	private void taoMoiTatCa() {
		System.out.println("Tao moi");
		tableModel.setRowCount(0);
		txtChietKhau.setText("");
		txtTongTien.setText("");
		txtTien.setText("");
		updateTongTien();
	}

	private void huyBo() {
		System.out.println("Huy bo");
//		JOptionPane.showMessageDialog(btnDatHang, ban_dao);
		int choice = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn hủy bỏ");
//		System.out.println(choice);
		if (choice == 0) {
			taoMoiTatCa();
			refresh();
		}
		updateTongTien();
	}

	private void datHang() throws Exception {
		if (check_maKH()) {
			int choice = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn đặt hàng");
			if (choice == 0) {
				setMaBan();

				refresh();
				updateTongTien();
				setHD();

			} else {

			}
		} else {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn khách hàng trước!!");
			try {
				showViewKH();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void tienHanhDatHang() throws Exception {

		HoaDon hd = new HoaDon(txtMaHD.getText(), new Date(), (double) tongTien(),tempNV,
				tempKH, tempBan);
		listHD.save(hd);
		int n = tableModel.getRowCount();
		for (int i = 0; i < n; i++) {
			ChiTietHoaDon chhd = new ChiTietHoaDon(Integer.parseInt(tableModel.getValueAt(i, 3).toString()),
					list_hangHoa.getHangHoaForID(tableModel.getValueAt(i, 0).toString()), hd,
					Float.parseFloat(tableModel.getValueAt(i, 2).toString()));
			listChiTietHD.save(chhd);
		}
		JOptionPane.showMessageDialog(null, "Tạo hóa đơn thành công!!!");
		refresh();
		setHD();
	}

	private void setMaBan() throws Exception {
		viewBan = new view_showBan();
		viewBan.setVisible(true);
		viewBan.setView(this);

	}

	private void updateTableHangHoa() {
		int n = tableShowSP.getSelectedRow();

		tableModel.setValueAt(txtSoLuong.getText(), n, 3);
		tableModel.setValueAt(
				Integer.parseInt(txtSoLuong.getText()) * Double.parseDouble(tableModel.getValueAt(n, 2).toString()), n,
				4);
		JOptionPane.showMessageDialog(null, "Updated!!");
		refresh();
		updateTongTien();
	}

	private void removetablehangHoa() {
		int n = tableShowSP.getSelectedRow();
		tableModel.removeRow(n);
		JOptionPane.showMessageDialog(null, "Deleted!!");
		refresh();
		updateTongTien();

	}

	public void refresh() {
		btnThem.setEnabled(false);
		btnSua.setEnabled(false);
		btnXoa.setEnabled(false);
		txtSoLuong.setText("");
		txtMaHangHoa.setText("");
		txtTimKiem.setText("");
	}

	public void refreshAfterDatHang() {
		tableModel.setRowCount(0);
		updateTongTien();
		txtKhachHang.setText("");
		setHD();

	}

	public void addTableHangHoa() {
		if (txtSoLuong.getText().equalsIgnoreCase("")) {
			JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng!");
		} else {
			if (valid_selected(txtMaHangHoa.getText())) {

				try {
					HangHoa hh = list_hangHoa.get(txtMaHangHoa.getText());
					int soLuong = Integer.parseInt(txtSoLuong.getText());
					tableModel.addRow(
							new Object[] { hh.getMaHH(), hh.getTenHH(), hh.getGia(), soLuong, hh.getGia() * soLuong });
					JOptionPane.showMessageDialog(null, "Added!!");
					refresh();

				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Hàng hóa đã thêm trước đó!!");
				refresh();
				updateTongTien();
			}
		}

	}

	public void submitMaKH(String maKH) {
		txtKhachHang.setText(maKH);
	}

	public void updateTongTien() {
		int n = tableShowSP.getRowCount();
		float tongTien = 0;
		Locale localVN = new Locale("vi", "VN");
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(localVN);

		for (int i = 0; i < n; i++) {
			tongTien += Double.parseDouble(tableModel.getValueAt(i, 4).toString());
		}
		txtTien.setText(currencyFormat.format(tongTien));
		txtChietKhau.setText(currencyFormat.format(tongTien / 100));
		txtTongTien.setText(currencyFormat.format(tongTien + tongTien / 100));
	}

	public boolean valid_selected(String maSp) {
		int n = tableShowSP.getRowCount();
		for (int i = 0; i < n; i++) {
			if (tableModel.getValueAt(i, 0).toString().equalsIgnoreCase(maSp)) {
				return false;
			}
		}

		return true;
	}

	public void setHD() {
		String ma = listHD.sinhMa();
		txtMaHD.setText(ma);
	}

	private boolean check_maKH() {
		if (txtKhachHang.getText().equalsIgnoreCase("")) {
			return false;
		}
		return true;
	}

	private float tongTien() {
		float tien = 0;
		int n = tableShowSP.getRowCount();
		for (int i = 0; i < n; i++) {

			tien += (Double.parseDouble(tableModel.getValueAt(i, 3).toString())
					* Double.parseDouble(tableModel.getValueAt(i, 2).toString()));
			System.out.println(tien);
		}
		return tien;
	}

	private int chietKhau() {
		return (int) (tongTien() / 100);
	}

	public Ban getTempBan() {
		return tempBan;
	}

	public void setTempBan(Ban tempBan) {
		this.tempBan = tempBan;
	}

	public KhachHang getTempKH() {
		return tempKH;
	}

	public void setTempKH(KhachHang tempKH) {
		this.tempKH = tempKH;
	}

}
