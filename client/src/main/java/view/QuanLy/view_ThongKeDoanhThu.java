package view.QuanLy;

import com.toedter.calendar.JDateChooser;
import db.ConnectDB;
import list.List_HoaDon;
import runapp.Login;
import testbutton.Buttontest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class view_ThongKeDoanhThu extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnThem, btnXoa, btnSua, btnLamMoi, btntimkiem,btnBieuDoThongKe;
	private JLabel lbltennv;
	private JTable table;
	private DefaultTableModel tableModel;
	private JTextField txtTenHH, txtGia, txtTimKiem, txtMaHH,txtDenNgay, txtTuNgay;
	private JDateChooser ChonNgayDateChooser;
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	private JPanel panelHangHoa, panelDatHang, panelNhanVien, panelTaiKhoan, panelThongKe;
	Color customColor = new Color(255, 255, 255, 0);
	Color whiteColor = new Color(255, 255, 255, 0);
	private JLabel lblNvIcon; // Thêm biến để lưu đối tượng JLabel chứa ảnh NV
	private List_HoaDon list_HoaDon = new List_HoaDon();
	private boolean isCalendarVisible = false;

	/**
	 * Launch the application.
	 * 
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
			java.util.logging.Logger.getLogger(view_ThongKeDoanhThu.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(view_ThongKeDoanhThu.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(view_ThongKeDoanhThu.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(view_ThongKeDoanhThu.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		}

		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		view_ThongKeDoanhThu frame = new view_ThongKeDoanhThu();
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 * 
	 * @throws Exception
	 */
	public view_ThongKeDoanhThu() throws Exception {
		initComponents();
		setResizable(false);
		setBackground(Color.WHITE);
		setTitle("Giao Diện Quản Lý Hàng Hóa");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		setBounds(100, 100, 1168, 650);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblNvIcon = new JLabel("");
		lblNvIcon.setIcon(new ImageIcon(view_QuanLyHangHoa.class.getResource("/image/avt.png"))); // Thay đổi đường dẫn
																									// ảnh của bạn
		lblNvIcon.setBounds(760, 5, 40, 40); // Điều chỉnh tọa độ và kích thước của ảnh
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
				view_QuanLyHangHoa gdqlhh;
				try {
					gdqlhh = new view_QuanLyHangHoa();
					gdqlhh.setLocationRelativeTo(null);
					gdqlhh.setVisible(true);
					dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
				view_QuanLyBan gdqlban;
				try {
					gdqlban = new view_QuanLyBan();
					gdqlban.setLocationRelativeTo(null);
					gdqlban.setVisible(true);
					dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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

		JButton btnNhanVien = new JButton("Quản Lý Nhân Viên");
		btnNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNhanVien.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view_QuanLyNhanVien gdqlnv;
				try {
					gdqlnv = new view_QuanLyNhanVien();
					gdqlnv.setLocationRelativeTo(null);
					gdqlnv.setVisible(true);
					dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
				try {
					view_QuanLyTaiKhoan gdqltk = new view_QuanLyTaiKhoan();
					gdqltk.setLocationRelativeTo(null);
					gdqltk.setVisible(true);
					dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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

		JPanel pnlThoiGian = new JPanel();
		pnlThoiGian.setBounds(14, 140, 130, 30);
		pnlThoiGian.setBackground(new Color(255, 255, 0));
		pnlThoiGian.setOpaque(false);
		pnlThoiGian.setLayout(new BoxLayout(pnlThoiGian, BoxLayout.Y_AXIS)); // Use vertical BoxLayout
		contentPane.add(pnlThoiGian);

		JLabel lblTG = new JLabel("Thời gian");
		lblTG.setForeground(new Color(255, 255, 255));
		lblTG.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblTG.setBounds(20, 50, 20, 30);
		pnlThoiGian.add(lblTG);

		JPanel pnlChonNgay_1 = new JPanel();
		pnlChonNgay_1.setBounds(10, 160, 134, 37);
		pnlChonNgay_1.setOpaque(false);
		pnlChonNgay_1.setBackground(Color.YELLOW);
		contentPane.add(pnlChonNgay_1);

		txtTuNgay = new JTextField();
		txtTuNgay.setFont(new Font("Open Sans", Font.PLAIN, 16));
		txtTuNgay.setColumns(9);
		pnlChonNgay_1.add(txtTuNgay);
		// Khởi tạo JDateChooser cho Chon ngày
		ChonNgayDateChooser = new JDateChooser();
		ChonNgayDateChooser.setBounds(85, 167, 100, 29);
		ChonNgayDateChooser.getDateEditor().getUiComponent().setVisible(isCalendarVisible);
		contentPane.add(ChonNgayDateChooser);
		// Thêm sự kiện cho JDateChooser để cập nhật giá trị vào textfield khi người
		// dùng chọn ngày
		ChonNgayDateChooser.addPropertyChangeListener("date", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				Date selectedDate = ChonNgayDateChooser.getDate();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				txtTuNgay.setText(dateFormat.format(selectedDate));
			}
		});

		btnBieuDoThongKe = new JButton("Biểu đồ thống kê");
		btnBieuDoThongKe.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnBieuDoThongKe.setBounds(14, 202, 171, 40);
		btnBieuDoThongKe.setIcon(new ImageIcon(view_ThongKeDoanhThu.class.getResource("/image/thongke.png")));
		contentPane.add(btnBieuDoThongKe);

		JLabel lblTongDoanhThuDV = new JLabel("Dịch vụ có doanh thu cao nhất: " + getHangHoa_CoDoanhThuCaoNhat());
		lblTongDoanhThuDV.setForeground(new Color(216, 107, 80));
		lblTongDoanhThuDV.setFont(new Font("Dialog", Font.BOLD, 16));
		lblTongDoanhThuDV.setBounds(275, 106, 471, 20);
		contentPane.add(lblTongDoanhThuDV);

		// Khởi tạo DefaultTableModel với các cột
		String[] columnNames = { "STT","Ngày", "Tên Hàng Hóa", "Tổng Doanh Thu" }; // Thay đổi tên cột tùy ý
		tableModel = new DefaultTableModel(columnNames, 0);

		// Khởi tạo JTable với DefaultTableModel
		table = new JTable(tableModel);
		// Đặt chiều rộng cho cột "Tên phim"
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(30);
		table.getColumnModel().getColumn(2).setPreferredWidth(30);
		table.getColumnModel().getColumn(3).setPreferredWidth(30);

		// Tạo JScrollPane để thêm bảng vào để có thể cuộn
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(275, 140, 871, 469);

		// Thêm bảng và JScrollPane vào contentPane
		contentPane.add(scrollPane);
		loadDataTable();

		JLabel background = new JLabel("");
		background.setBounds(0, 0, 1162, 613);
		background.setHorizontalAlignment(SwingConstants.CENTER);
		background.setIcon(new ImageIcon(view_ThongKeDoanhThu.class.getResource("/image/bgCF.jpg")));
		contentPane.add(background);
		
		btnBieuDoThongKe.addActionListener(this);
	}
	
	private void loadDataTable() throws SQLException {
		int i = 0;
		for(String[] dsThongKe : list_HoaDon.getThongTinDoanhThu()) {
			tableModel.addRow(new Object[] {++i,dsThongKe[0],dsThongKe[1],dsThongKe[2]});
		}
	}
	
	private String getHangHoa_CoDoanhThuCaoNhat() throws SQLException {
		return list_HoaDon.getThongKeDoanhThuCaoNhatTrongNam();
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnBieuDoThongKe)) {
			try {
				view_showThongKeDoanhThu showDoanhThu = new view_showThongKeDoanhThu();
				showDoanhThu.setResizable(false);
				showDoanhThu.setLocationRelativeTo(null);
				showDoanhThu.setVisible(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
}
