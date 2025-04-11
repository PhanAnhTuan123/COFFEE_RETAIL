package view.NhanVien;

import db.ConnectDB;
import list.List_KhachHang;
import model.KhachHang;
import runapp.Login;
import testbutton.Buttontest;
import view.QuanLy.Main_form_manager;
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
import java.util.ArrayList;

public class view_QuanLyKhachHang  extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnThem, btnXoa, btnSua, btnLamMoi, btntimkiem;
	private JLabel lbltennv;
//	private JRadioButton rdbtnNam, rdbtnNu;
//	private JComboBox<String> cboxChucVu;
	private JTable table;
	private DefaultTableModel tableModel;
	private JTextField txtSDT, txtDiaChi, txtTimKiem, txtHoTen;
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	private JPanel panelHangHoa, panelDatHang, panelNhanVien, panelTaiKhoan, panelThongKe;
	Color customColor = new Color(255, 255, 255, 0);
	Color whiteColor = new Color(255, 255, 255, 0);
	private JLabel lblNvIcon; // Thêm biến để lưu đối tượng JLabel chứa ảnh NV
	private List_KhachHang list_nv = new List_KhachHang();

	/**
	 * Launch the application.
	 * @throws Exception 
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
			java.util.logging.Logger.getLogger(view_QuanLyKhachHang.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(view_QuanLyKhachHang.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(view_QuanLyKhachHang.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(view_QuanLyKhachHang.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		}
		
		try {
			ConnectDB.getInstance().connect();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		view_QuanLyKhachHang frame = new view_QuanLyKhachHang();
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public view_QuanLyKhachHang() throws Exception {
		initComponents();
		setResizable(false);
		setBackground(Color.WHITE);
		setTitle("Giao Diện Quản Lý Khách Hàng");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		setBounds(100, 100, 1168, 650);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblNvIcon = new JLabel("");
		lblNvIcon.setIcon(new ImageIcon(view_QuanLyKhachHang.class.getResource("/image/avt.png"))); // Thay đổi đường dẫn
																									// ảnh của bạn
		lblNvIcon.setBounds(760, 5, 40, 40); // Điều chỉnh tọa độ và kích thước của ảnh
		contentPane.add(lblNvIcon);

		JLabel lblnhanvien = new JLabel("QL:");
		lblnhanvien.setForeground(Color.WHITE);
		lblnhanvien.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblnhanvien.setBounds(801, 0, 39, 50);
		lblnhanvien.setForeground(Color.WHITE);
		contentPane.add(lblnhanvien);

		lbltennv = new JLabel("Lê Minh Đăng");
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
		qlyHangHoaToolbar.setBackground(customColor); // Thay đổi ở đây
		topPanel.add(qlyHangHoaToolbar);

		panelHangHoa = new JPanel() {
			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g) {
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};
		panelHangHoa.setBounds(0, 49, 1175, 47); // Điều chỉnh tọa độ và kích thước của panel theo ý muốn
		panelHangHoa.setLayout(new FlowLayout(FlowLayout.LEFT)); // Thay đổi ở đây
		panelHangHoa.setVisible(false); // tắt/ẩn panel
		panelHangHoa.setBackground(whiteColor);
		contentPane.add(panelHangHoa);

		JButton btnqlyMonAn = new JButton("Quản Lý Món Ăn");
		btnqlyMonAn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnqlyMonAn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				try {
//					view_QuanLyHangHoa gdqlhh = new view_QuanLyHangHoa();
//					gdqlhh.setLocationRelativeTo(null);
//					gdqlhh.setVisible(true);
//					dispose();
//				} catch (Exception e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
		}
		});

		panelHangHoa.add(btnqlyMonAn);

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
		datHangToolbar.setBackground(customColor); // Thay đổi ở đây
		topPanel.add(datHangToolbar);

		panelDatHang = new JPanel() {
			private static final long serialVersionUID = 1L;

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

		JButton btnBan = new JButton("Quản Lý Bàn");
		btnBan.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBan.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		JButton btnKhachHang = new JButton("Quản Lý Khách Hàng");
		btnKhachHang.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnKhachHang.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		panelDatHang.add(btnKhachHang);
		panelDatHang.add(btnBan);

		// Thêm toolbar "nhân viên"
		JToolBar nhanVienToolbar = new JToolBar();
		nhanVienToolbar.setFloatable(false);
		nhanVienToolbar.setMargin(new java.awt.Insets(-5, -5, 0, -5));
		testbutton.Buttontest nhanVienButton = new Buttontest();
		nhanVienButton.setText("Nhân Viên");
		nhanVienButton.setFont(new Font("Open Sans", Font.BOLD, 15));
		nhanVienButton.setForeground(SystemColor.text);
		nhanVienButton.setRippleColor(new Color(255, 255, 255));
		nhanVienButton.setBackground(new Color(255, 0, 128));
		nhanVienButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (panelHangHoa.isVisible() || panelDatHang.isVisible() || panelNhanVien.isVisible()
						|| panelTaiKhoan.isVisible() || panelThongKe.isVisible()) {
					panelHangHoa.setVisible(false);
					panelDatHang.setVisible(false);
					panelNhanVien.setVisible(false);
					panelTaiKhoan.setVisible(false);
					panelThongKe.setVisible(false);
				} else {
					panelNhanVien.setVisible(true);
				}
			}
		});
		nhanVienToolbar.add(nhanVienButton);
		nhanVienToolbar.setBackground(customColor);
		topPanel.add(nhanVienToolbar);

		panelNhanVien = new JPanel() {
			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g) {
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};
		panelNhanVien.setBounds(0, 49, 1175, 47); // Điều chỉnh tọa độ và kích thước của panel theo ý muốn
		panelNhanVien.setLayout(new FlowLayout(FlowLayout.LEFT)); // Thay đổi ở đây
		panelNhanVien.setVisible(false); // tắt/ẩn panel
		panelNhanVien.setBackground(whiteColor);
		contentPane.add(panelNhanVien);

		JButton btnNhanVien = new JButton("Quản Lý Khách Hàng ");
		btnNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNhanVien.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}

		});
		panelNhanVien.add(btnNhanVien);

		// Thêm toolbar "tài khoản"
		JToolBar taiKhoanToolbar = new JToolBar();
		taiKhoanToolbar.setFloatable(false);
		taiKhoanToolbar.setMargin(new java.awt.Insets(-5, -5, 0, -5));
		testbutton.Buttontest taiKhoanButton = new Buttontest();
		taiKhoanButton.setText("Tài Khoản");
		taiKhoanButton.setFont(new Font("Open Sans", Font.BOLD, 15));
		taiKhoanButton.setForeground(SystemColor.text);
		taiKhoanButton.setRippleColor(new Color(255, 255, 255));
		taiKhoanButton.setBackground(new Color(99, 176, 28));
		taiKhoanButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (panelHangHoa.isVisible() || panelDatHang.isVisible() || panelNhanVien.isVisible()
						|| panelTaiKhoan.isVisible() || panelThongKe.isVisible()) {
					panelHangHoa.setVisible(false);
					panelDatHang.setVisible(false);
					panelNhanVien.setVisible(false);
					panelTaiKhoan.setVisible(false);
					panelThongKe.setVisible(false);
				} else {
					panelTaiKhoan.setVisible(true);
				}
			}
		});
		taiKhoanToolbar.add(taiKhoanButton);
		taiKhoanToolbar.setBackground(customColor);
		topPanel.add(taiKhoanToolbar);

		panelTaiKhoan = new JPanel() {
			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g) {
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};
		panelTaiKhoan.setBounds(0, 49, 1175, 47); // Điều chỉnh tọa độ và kích thước của panel theo ý muốn
		panelTaiKhoan.setLayout(new FlowLayout(FlowLayout.LEFT)); // Thay đổi ở đây
		panelTaiKhoan.setVisible(false); // tắt/ẩn panel
		panelTaiKhoan.setBackground(whiteColor);
		contentPane.add(panelTaiKhoan);

		JButton btnTaiKhoan = new JButton("Quản Lý Tài Khoản");
		btnTaiKhoan.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnTaiKhoan.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				try {
//					view_QuanLyTaiKhoan gdqltk = new view_QuanLyTaiKhoan();
//					gdqltk.setLocationRelativeTo(null);
//					gdqltk.setVisible(true);
//					dispose();
//				} catch (Exception e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
			}

		});
		panelTaiKhoan.add(btnTaiKhoan);

		// thêm toolbar "thống kê"
		JToolBar thongKeToolbar = new JToolBar();
		thongKeToolbar.setFloatable(false);
		thongKeToolbar.setMargin(new java.awt.Insets(-5, -5, 0, -5));
		testbutton.Buttontest thongKeButton = new Buttontest();
		thongKeButton.setText("Thống Kê");
		thongKeButton.setFont(new Font("Open Sans", Font.BOLD, 15));
		thongKeButton.setForeground(SystemColor.text);
		thongKeButton.setRippleColor(new Color(255, 255, 255));
		thongKeButton.setBackground(new Color(100, 100, 255));
		thongKeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (panelHangHoa.isVisible() || panelDatHang.isVisible() || panelNhanVien.isVisible()
						|| panelTaiKhoan.isVisible() || panelThongKe.isVisible()) {
					panelHangHoa.setVisible(false);
					panelDatHang.setVisible(false);
					panelNhanVien.setVisible(false);
					panelTaiKhoan.setVisible(false);
					panelThongKe.setVisible(false);
				} else {
					panelThongKe.setVisible(true);
				}
			}
		});
		thongKeToolbar.add(thongKeButton);
		thongKeToolbar.setBackground(customColor);
		topPanel.add(thongKeToolbar);

		panelThongKe = new JPanel() {
			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g) {
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};
		panelThongKe.setBounds(0, 49, 1175, 47); // Điều chỉnh tọa độ và kích thước của panel theo ý muốn
		panelThongKe.setLayout(new FlowLayout(FlowLayout.LEFT)); // Thay đổi ở đây
		panelThongKe.setVisible(false); // tắt/ẩn panel
		panelThongKe.setBackground(whiteColor);
		contentPane.add(panelThongKe);

		JButton btnThongKeNhanVien = new JButton("Thống Kê Nhân Viên");
		btnThongKeNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnThongKeNhanVien.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		JButton btnThongKeDoanhThu = new JButton("Thống Kê Doanh Thu");
		btnThongKeDoanhThu.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnThongKeDoanhThu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		panelThongKe.add(btnThongKeNhanVien);
		panelThongKe.add(btnThongKeDoanhThu);

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
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
		});
		logoutToolBar.add(logoutButton);
		logoutToolBar.setBackground(customColor);
		topPanel.add(logoutToolBar);
		
		JLabel lblQLKH = new JLabel("Quản Lý Khách Hàng ");
		lblQLKH.setForeground(new Color(255, 255, 255));
		lblQLKH.setFont(new Font("Open Sans", 1, 16));
		lblQLKH.setBounds(43, 102, 170, 20);
		contentPane.add(lblQLKH);

		JLabel lblHoTen = new JLabel("Nhập họ tên :");
		lblHoTen.setForeground(new Color(255, 255, 255));
		lblHoTen.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblHoTen.setBounds(17, 139, 130, 21);
		contentPane.add(lblHoTen);

		JPanel pnlHoTen = new JPanel();
		pnlHoTen.setBackground(new Color(255, 255, 0));
		pnlHoTen.setBounds(10, 161, 230, 37);
		pnlHoTen.setOpaque(false);
		contentPane.add(pnlHoTen);

		JLabel lblSDT = new JLabel("Số điện thoại:");
		lblSDT.setForeground(new Color(255, 255, 255));
		lblSDT.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblSDT.setBounds(17, 209, 130, 21);
		contentPane.add(lblSDT);

		JPanel pnlSDT = new JPanel();
		pnlSDT.setOpaque(false);
		pnlSDT.setBackground(Color.YELLOW);
		pnlSDT.setBounds(10, 241, 230, 37);
		contentPane.add(pnlSDT);

		txtSDT = new JTextField();
		txtSDT.setFont(new Font("Dialog", Font.PLAIN, 16));
		txtSDT.setColumns(16);
		pnlSDT.add(txtSDT);

		JLabel lbldiachi = new JLabel("Địa chỉ:");
		lbldiachi.setForeground(new Color(255, 255, 255));
		lbldiachi.setFont(new Font("Dialog", Font.PLAIN, 16));
		lbldiachi.setBounds(17, 289, 130, 21);
		contentPane.add(lbldiachi);

		JPanel pnlDiaChi = new JPanel();
		pnlDiaChi.setOpaque(false);
		pnlDiaChi.setBackground(Color.YELLOW);
		pnlDiaChi.setBounds(10, 321, 230, 37);
		contentPane.add(pnlDiaChi);

		txtDiaChi = new JTextField();
		txtDiaChi.setFont(new Font("Dialog", Font.PLAIN, 16));
		txtDiaChi.setColumns(16);
		pnlDiaChi.add(txtDiaChi);

//		rdbtnNam = new JRadioButton("Nam");
//		rdbtnNam.setForeground(new Color(255, 255, 255));
//		rdbtnNam.setFont(new Font("Dialog", Font.PLAIN, 16));
//		rdbtnNam.setBounds(16, 390, 103, 21);
//		contentPane.add(rdbtnNam);
//
//		rdbtnNu = new JRadioButton("Nữ");
//		rdbtnNu.setForeground(new Color(255, 255, 255));
//		rdbtnNu.setFont(new Font("Dialog", Font.PLAIN, 16));
//		rdbtnNu.setBounds(140, 390, 103, 21);
//		contentPane.add(rdbtnNu);

		// Thêm chúng vào ButtonGroup
		ButtonGroup buttonGroup = new ButtonGroup();
//		buttonGroup.add(rdbtnNam);
//		buttonGroup.add(rdbtnNu);

		// Add JTextField below JCheckBox
		txtHoTen = new JTextField();
		txtHoTen.setFont(new Font("Open Sans", 0, 16));
		txtHoTen.setColumns(16); // You can adjust the column count based on your requirement
		pnlHoTen.add(txtHoTen);

		txtTimKiem = new JTextField();
		txtTimKiem.setFont(new Font("Dialog", Font.PLAIN, 16));
		txtTimKiem.setColumns(16);
		txtTimKiem.setBounds(871, 99, 214, 30);
		contentPane.add(txtTimKiem);
//
//		JLabel lblChucVu = new JLabel("Chức vụ:");
//		lblChucVu.setForeground(new Color(255, 255, 255));
//		lblChucVu.setFont(new Font("Dialog", Font.PLAIN, 16));
//		lblChucVu.setBounds(17, 440, 70, 21);
//		contentPane.add(lblChucVu);

//		cboxChucVu = new JComboBox<String>();
//		cboxChucVu.setBounds(113, 442, 100, 22);
//		cboxChucVu.addItem("Nhân Viên");
//		cboxChucVu.addItem("Quản Lý");
//		contentPane.add(cboxChucVu);

		btntimkiem = new JButton("");
		btntimkiem.setIcon(new ImageIcon(view_QuanLyNhanVien.class.getResource("/image/search.png")));
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
		String[] columnNames = { "Mã KH", "Tên KH","Địa Chỉ", "SĐT"}; // Thay đổi tên cột tùy ý
		tableModel = new DefaultTableModel(columnNames, 0);

		// Khởi tạo JTable với DefaultTableModel
		table = new JTable(tableModel);
		table.getColumnModel().getColumn(3).setPreferredWidth(50); // Đặt giá trị 300 làm ví dụ, bạn có thể điều chỉnh
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int r = table.getSelectedRow();
				txtHoTen.setText(tableModel.getValueAt(r,1).toString());
				txtSDT.setText(tableModel.getValueAt(r,3).toString());
				txtDiaChi.setText(tableModel.getValueAt(r,2).toString());
//				if(tableModel.getValueAt(r,5).toString().equals("Nam")) {
//					rdbtnNam.setSelected(true);
//				}else {
//					rdbtnNu.setSelected(true);
//				}
////				if(tableModel.getValueAt(r,4).toString().equals("Nhân Viên")) {
//					cboxChucVu.setSelectedIndex(0);
//				}else {
//					cboxChucVu.setSelectedIndex(1);
//				}
				btnSua.setEnabled(true);
				btnXoa.setEnabled(true);
			}
		});
		
		// theo ý muốn

		// Tạo JScrollPane để thêm bảng vào để có thể cuộn
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(250, 140, 900, 469); // Điều chỉnh tọa độ và kích thước của bảng

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
		background.setIcon(new ImageIcon(view_QuanLyNhanVien.class.getResource("/image/bgCF.jpg")));
		background.setBounds(0, 0, 1162, 613);
		contentPane.add(background);
		
		refresh();
		loadData();
	}
	
	private void loadData() throws SQLException {
//		tableModel.setRowCount(0);
//		for(KhachHang nv : list_nv.getAll()) {
//			String gioiTinh = "";
//			if(nv.getGioiTinh() == true) {
//				gioiTinh = "Nam"; 
//			}else {
//				gioiTinh = "Nữ";
//			}
//			tableModel.addRow(new Object[] {nv.getMaKH(),nv.getTenKH(),nv.getDiaChi(),nv.getSdt(),gioiTinh});
//		}
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
		Main_form_manager gdql = new Main_form_manager();
		gdql.setLocationRelativeTo(null);
		gdql.setVisible(true);
	}

	public void loadByName() {
		String ten = txtTimKiem.getText();
		ArrayList<KhachHang> tempList = new ArrayList<KhachHang>();
		tempList = list_nv.findByName(ten);
		tableModel.setRowCount(0);
		for (KhachHang item : tempList) {
			String gioiTinh = "";
			if(item.getGioiTinh() == true) {
				gioiTinh = "Nam"; 
			}else {
				gioiTinh = "Nữ";
			}
			tableModel.addRow(new Object[] {item.getMaKH(),item.getTenKH(),item.getDiaChi(),item.getSdt(),gioiTinh});
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnThem)) {
			if (txtHoTen.getText().isEmpty() || txtSDT.getText().isEmpty() || txtDiaChi.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin đầy đủ");
			} else {
				KhachHang nhanVien = new KhachHang();
				nhanVien.setMaKH(list_nv.sinhMaKH());
				nhanVien.setTenKH(txtHoTen.getText());
				nhanVien.setSdt(txtSDT.getText());
				nhanVien.setDiaChi(txtDiaChi.getText());
//				if(rdbtnNam.isSelected()) {
//					nhanVien.setGioiTinh(true);
//				}else {
//					nhanVien.setGioiTinh(false);
//				}
//				if(cboxChucVu.getSelectedIndex() == 0) {
//					nhanVien.setChucVu("Nhân Viên");
//				}else {
//					nhanVien.setChucVu("Quản lý");
//				}

				try {
					list_nv.save(nhanVien);
					loadData();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		if (e.getSource().equals(btnSua)) {
			int r = table.getSelectedRow();
			KhachHang nhanVien = new KhachHang();
			nhanVien.setMaKH(tableModel.getValueAt(r, 0).toString());
			nhanVien.setTenKH(txtHoTen.getText());
			nhanVien.setSdt(txtSDT.getText());
			nhanVien.setDiaChi(txtDiaChi.getText());
//			if(rdbtnNam.isSelected()) {
//				nhanVien.setGioiTinh(true);
//			}else {
//				nhanVien.setGioiTinh(false);
//			}
//			if(cboxChucVu.getSelectedIndex() == 0) {
//				nhanVien.setChucVu("Nhân Viên");
//			}else {
//				nhanVien.setChucVu("Quản lý");
//			}
			try {
				list_nv.update(nhanVien);
				loadData();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "Sửa thành công!!");
		}
		if (e.getSource().equals(btnXoa)) {
			int r = table.getSelectedRow();
			KhachHang nhanVien = new KhachHang();
			nhanVien.setMaKH(tableModel.getValueAt(r, 0).toString());
			try {
				list_nv.delete(nhanVien);
				loadData();
				refresh();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "Xóa thành công!!");
		}
		if (e.getSource().equals(btntimkiem)) {
//			String ten = txtTimKiem.getText();
			loadByName();

		}
		if (e.getSource().equals(btnLamMoi)) {
			try {
				loadData();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		refresh();
	}

	public void refresh() {
		txtHoTen.setText("");
		txtDiaChi.setText("");
		txtSDT.setText("");
		txtTimKiem.setText("");
		btnXoa.setEnabled(false);
		btnSua.setEnabled(false);
		btnThem.setEnabled(true);

	}
}
