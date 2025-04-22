package view.NhanVien;

import runapp.Login;
import view.QuanLy.Main_form_manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main_form_employee extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Image img_logo = new ImageIcon(Main_form_manager.class.getResource("/image/logo.png"))
			.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
	public JLabel lbltennv;
	private JPanel panelDatHang;
	Color customColor = new Color(255, 255, 255, 0);
	Color whiteColor = new Color(255, 255, 255, 0);
	private JLabel lblNvIcon;

	public Main_form_employee() {
		setResizable(false);
		setBackground(Color.WHITE);
		setTitle("Giao Diện Nhân Viên");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		setBounds(100, 100, 1168, 650);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblNvIcon = new JLabel("");
		lblNvIcon.setIcon(new ImageIcon(Main_form_employee.class.getResource("/image/avt.png")));
		lblNvIcon.setBounds(760, 5, 40, 40);
		contentPane.add(lblNvIcon);

		JLabel lblnhanvien = new JLabel("NV:");
		lblnhanvien.setForeground(Color.WHITE);
		lblnhanvien.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblnhanvien.setBounds(801, 0, 39, 50);
		contentPane.add(lblnhanvien);

		lbltennv = new JLabel("");
		lbltennv.setForeground(Color.WHITE);
		lbltennv.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbltennv.setBounds(832, 0, 238, 50);
		contentPane.add(lbltennv);

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

		JToolBar datHangToolbar = new JToolBar();
		datHangToolbar.setFloatable(false);
		datHangToolbar.setMargin(new Insets(-5, -5, 0, -5));
		testbutton.Buttontest datHangButton = new testbutton.Buttontest();
		datHangButton.setText("Đặt Hàng");
		datHangButton.setFont(new Font("Open Sans", Font.BOLD, 15));
		datHangButton.setForeground(SystemColor.text);
		datHangButton.setRippleColor(new Color(255, 255, 255));
		datHangButton.setBackground(new Color(46, 139, 87));
		datHangButton.addActionListener(e -> panelDatHang.setVisible(!panelDatHang.isVisible()));
		datHangToolbar.add(datHangButton);
		datHangToolbar.setBackground(customColor);
		topPanel.add(datHangToolbar);

		panelDatHang = new JPanel() {
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

		JButton btnTaoHoaDon = new JButton("Tạo Hóa Đơn");
		btnTaoHoaDon.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnTaoHoaDon.addActionListener(e -> {
			try {
				view_hoaDon gdTaoHD = new view_hoaDon();
				gdTaoHD.lbltennv.setText(lbltennv.getText());
				gdTaoHD.setVisible(true);
				gdTaoHD.setLocationRelativeTo(null);
				dispose();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		panelDatHang.add(btnTaoHoaDon);

		JButton btnXemHoaDon = new JButton("Xem Hóa Đơn");
		btnXemHoaDon.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnXemHoaDon.addActionListener(e -> {
			try {
				view_xemHD gdXemHD = new view_xemHD();
				gdXemHD.setVisible(true);
				gdXemHD.setLocationRelativeTo(null);
				dispose();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		panelDatHang.add(btnXemHoaDon);

		JToolBar logoutToolBar = new JToolBar();
		logoutToolBar.setFloatable(false);
		logoutToolBar.setMargin(new Insets(-5, 550, 0, 0));
		testbutton.Buttontest logoutButton = new testbutton.Buttontest();
		logoutButton.setText("Đăng Xuất");
		logoutButton.setFont(new Font("Open Sans", Font.BOLD, 15));
		logoutButton.setForeground(SystemColor.text);
		logoutButton.setRippleColor(new Color(255, 255, 255));
		logoutButton.setBackground(new Color(226, 110, 110));
		logoutButton.addActionListener(e -> {
			if (JOptionPane.showConfirmDialog(null, "Bạn có muốn đăng xuất!", null, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				try {
					new Login().setVisible(true);
					dispose();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		logoutToolBar.add(logoutButton);
		logoutToolBar.setBackground(customColor);
		topPanel.add(logoutToolBar);

		JLabel background = new JLabel("");
		background.setHorizontalAlignment(SwingConstants.CENTER);
		background.setIcon(new ImageIcon(Main_form_employee.class.getResource("/image/bgCF.jpg")));
		background.setBounds(0, 0, 1162, 613);
		contentPane.add(background);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Reserved for future use
	}

}
