package view.QuanLy;

import runapp.Login;
import testbutton.Buttontest;

import javax.swing.*;
import java.awt.*;

public class Main_form_manager extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Image img_logo = new ImageIcon(Main_form_manager.class.getResource("/image/logo.png"))
			.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
	private JLabel lbltennv;
	private JPanel panelHangHoa, panelDatHang, panelNhanVien, panelTaiKhoan, panelThongKe;
	private JLabel lblNvIcon;
	Color customColor = new Color(255, 255, 255, 0);
	Color whiteColor = new Color(255, 255, 255, 0);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception ex) {
			java.util.logging.Logger.getLogger(Main_form_manager.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}
		SwingUtilities.invokeLater(() -> {
			Main_form_manager frame = new Main_form_manager();
			frame.setVisible(true);
		});
	}

	/**
	 * Create the frame.
	 */
	public Main_form_manager() {
		setResizable(false);
		setBackground(Color.WHITE);
		setTitle("Giao Diện Quản Lý");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setBounds(100, 100, 1168, 650);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Avatar và tên nhân viên
		lblNvIcon = new JLabel("");
		lblNvIcon.setIcon(new ImageIcon(Main_form_manager.class.getResource("/image/avt.png")));
		lblNvIcon.setBounds(760, 5, 40, 40);
		contentPane.add(lblNvIcon);

		JLabel lblnhanvien = new JLabel("QL:");
		lblnhanvien.setForeground(Color.WHITE);
		lblnhanvien.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblnhanvien.setBounds(801, 0, 39, 50);
		contentPane.add(lblnhanvien);

		lbltennv = new JLabel("Trương Đại Lộc");
		lbltennv.setForeground(Color.WHITE);
		lbltennv.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbltennv.setBounds(832, 0, 238, 50);
		contentPane.add(lbltennv);

		// Thanh toolbar chính
		JPanel topPanel = new JPanel() {
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

		// =====================================================
		// Toolbar Hàng hóa
		// =====================================================
		JToolBar qlyHangHoaToolbar = new JToolBar();
		qlyHangHoaToolbar.setFloatable(false);
		qlyHangHoaToolbar.setMargin(new Insets(-5, -5, 0, -5));
		Buttontest qlyHangHoaButton = new Buttontest();
		qlyHangHoaButton.setText("Hàng hóa");
		qlyHangHoaButton.setFont(new Font("Open Sans", Font.BOLD, 15));
		qlyHangHoaButton.setForeground(SystemColor.text);
		qlyHangHoaButton.setRippleColor(Color.WHITE);
		qlyHangHoaButton.setBackground(new Color(255, 128, 64));
		qlyHangHoaButton.addActionListener(e -> {
            view_QuanLyHangHoa frame = null;
            try {
                frame = new view_QuanLyHangHoa();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			dispose();
		});
		qlyHangHoaToolbar.add(qlyHangHoaButton);
		qlyHangHoaToolbar.setBackground(customColor);
		topPanel.add(qlyHangHoaToolbar);

		panelHangHoa = createSubMenuPanel();
		contentPane.add(panelHangHoa);
		JButton btnqlyMonAn = new JButton("Quản Lý Món Ăn");
		btnqlyMonAn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnqlyMonAn.addActionListener(e -> {
            view_QuanLyHangHoa frame = null;
            try {
                frame = new view_QuanLyHangHoa();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			dispose();
		});
		panelHangHoa.add(btnqlyMonAn);

		// =====================================================
		// Toolbar Đặt hàng
		// =====================================================
		JToolBar datHangToolbar = new JToolBar();
		datHangToolbar.setFloatable(false);
		datHangToolbar.setMargin(new Insets(-5, -5, 0, -5));
		Buttontest datHangButton = new Buttontest();
		datHangButton.setText("Đặt Hàng");
		datHangButton.setFont(new Font("Open Sans", Font.BOLD, 15));
		datHangButton.setForeground(SystemColor.text);
		datHangButton.setRippleColor(Color.WHITE);
		datHangButton.setBackground(new Color(46, 139, 87));
		datHangButton.addActionListener(e -> togglePanel(panelDatHang));
		datHangToolbar.add(datHangButton);
		datHangToolbar.setBackground(customColor);
		topPanel.add(datHangToolbar);

		panelDatHang = createSubMenuPanel();
		contentPane.add(panelDatHang);
		JButton btnKhachHang = new JButton("Quản Lý Khách Hàng");
		btnKhachHang.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnKhachHang.addActionListener(e -> {
            view_QuanLyKhachHang frame = null;
            try {
                frame = new view_QuanLyKhachHang();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			dispose();
		});
		panelDatHang.add(btnKhachHang);
		JButton btnBan = new JButton("Quản Lý Bàn");
		btnBan.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBan.addActionListener(e -> {
            view_QuanLyBan frame = null;
            try {
                frame = new view_QuanLyBan();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			dispose();
		});
		panelDatHang.add(btnBan);

		// =====================================================
		// Toolbar Nhân viên
		// =====================================================
		JToolBar nhanVienToolbar = new JToolBar();
		nhanVienToolbar.setFloatable(false);
		nhanVienToolbar.setMargin(new Insets(-5, -5, 0, -5));
		Buttontest nhanVienButton = new Buttontest();
		nhanVienButton.setText("Nhân Viên");
		nhanVienButton.setFont(new Font("Open Sans", Font.BOLD, 15));
		nhanVienButton.setForeground(SystemColor.text);
		nhanVienButton.setRippleColor(Color.WHITE);
		nhanVienButton.setBackground(new Color(255, 0, 128));
		nhanVienButton.addActionListener(e -> togglePanel(panelNhanVien));
		nhanVienToolbar.add(nhanVienButton);
		nhanVienToolbar.setBackground(customColor);
		topPanel.add(nhanVienToolbar);

		panelNhanVien = createSubMenuPanel();
		contentPane.add(panelNhanVien);
		JButton btnNhanVien = new JButton("Quản Lý Nhân Viên");
		btnNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNhanVien.addActionListener(e -> {
            view_QuanLyNhanVien frame = null;
            try {
                frame = new view_QuanLyNhanVien();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			dispose();
		});
		panelNhanVien.add(btnNhanVien);

		// =====================================================
		// Toolbar Tài khoản
		// =====================================================
		JToolBar taiKhoanToolbar = new JToolBar();
		taiKhoanToolbar.setFloatable(false);
		taiKhoanToolbar.setMargin(new Insets(-5, -5, 0, -5));
		Buttontest taiKhoanButton = new Buttontest();
		taiKhoanButton.setText("Tài Khoản");
		taiKhoanButton.setFont(new Font("Open Sans", Font.BOLD, 15));
		taiKhoanButton.setForeground(SystemColor.text);
		taiKhoanButton.setRippleColor(Color.WHITE);
		taiKhoanButton.setBackground(new Color(99, 176, 28));
		taiKhoanButton.addActionListener(e -> togglePanel(panelTaiKhoan));
		taiKhoanToolbar.add(taiKhoanButton);
		taiKhoanToolbar.setBackground(customColor);
		topPanel.add(taiKhoanToolbar);

		panelTaiKhoan = createSubMenuPanel();
		contentPane.add(panelTaiKhoan);
		JButton btnTaiKhoan = new JButton("Quản Lý Tài Khoản");
		btnTaiKhoan.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnTaiKhoan.addActionListener(e -> {
            view_QuanLyTaiKhoan frame = null;
            try {
                frame = new view_QuanLyTaiKhoan();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			dispose();
		});
		panelTaiKhoan.add(btnTaiKhoan);

		// =====================================================
		// Toolbar Thống Kê
		// =====================================================
		JToolBar thongKeToolbar = new JToolBar();
		thongKeToolbar.setFloatable(false);
		thongKeToolbar.setMargin(new Insets(-5, -5, 0, -5));
		Buttontest thongKeButton = new Buttontest();
		thongKeButton.setText("Thống Kê");
		thongKeButton.setFont(new Font("Open Sans", Font.BOLD, 15));
		thongKeButton.setForeground(SystemColor.text);
		thongKeButton.setRippleColor(Color.WHITE);
		thongKeButton.setBackground(new Color(100, 100, 255));
		thongKeButton.addActionListener(e -> togglePanel(panelThongKe));
		thongKeToolbar.add(thongKeButton);
		thongKeToolbar.setBackground(customColor);
		topPanel.add(thongKeToolbar);

		panelThongKe = createSubMenuPanel();
		contentPane.add(panelThongKe);
		JButton btnThongKeDoanhThu = new JButton("Thống Kê Doanh Thu");
		btnThongKeDoanhThu.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnThongKeDoanhThu.addActionListener(e -> {
            view_ThongKeDoanhThu frame = null;
            try {
                frame = new view_ThongKeDoanhThu();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			dispose();
		});
		panelThongKe.add(btnThongKeDoanhThu);

		// =====================================================
		// Logout button
		// =====================================================
		JToolBar logoutToolBar = new JToolBar();
		logoutToolBar.setFloatable(false);
		logoutToolBar.setMargin(new Insets(-5, 550, 0, 0));
		Buttontest logoutButton = new Buttontest();
		logoutButton.setText("Đăng Xuất");
		logoutButton.setFont(new Font("Open Sans", Font.BOLD, 15));
		logoutButton.setForeground(SystemColor.text);
		logoutButton.setRippleColor(Color.WHITE);
		logoutButton.setBackground(new Color(226, 110, 110));
		logoutButton.addActionListener(e -> {
			if (JOptionPane.showConfirmDialog(null, "Bạn có muốn đăng xuất!", null, JOptionPane.YES_NO_OPTION)
					== JOptionPane.YES_OPTION) {
				Login lg = new Login();
				lg.setVisible(true);
				lg.setLocationRelativeTo(null);
				dispose();
			}
		});
		logoutToolBar.add(logoutButton);
		logoutToolBar.setBackground(customColor);
		topPanel.add(logoutToolBar);

		JLabel background = new JLabel("");
		background.setHorizontalAlignment(SwingConstants.CENTER);
		background.setIcon(new ImageIcon(Main_form_manager.class.getResource("/image/bgCF.jpg")));
		background.setBounds(0, 0, 1162, 613);
		contentPane.add(background);
	}

	private JPanel createSubMenuPanel() {
		JPanel panel = new JPanel() {
			protected void paintComponent(Graphics g) {
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};
		panel.setBounds(0, 49, 1175, 47);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setVisible(false);
		panel.setBackground(whiteColor);
		return panel;
	}

	private void togglePanel(JPanel panel) {
		panelHangHoa.setVisible(false);
		panelDatHang.setVisible(false);
		panelNhanVien.setVisible(false);
		panelTaiKhoan.setVisible(false);
		panelThongKe.setVisible(false);
		panel.setVisible(!panel.isVisible());
	}
}
