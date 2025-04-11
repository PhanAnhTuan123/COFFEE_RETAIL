package runapp;

import db.ConnectDB;
import list.List_NhanVien;
import list.List_TaiKhoan;
import model.TaiKhoan;
import view.NhanVien.Main_form_employee;
import view.QuanLy.Main_form_manager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUser;
	private JPasswordField passMk;
	private testbutton.Buttontest btnDangNhap;
	private JLabel lblNewLabel_3;
	public String user;
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	String quanly;
	private List_TaiKhoan list_tk = new List_TaiKhoan();
	private List_NhanVien list_nv = new List_NhanVien();

	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 * 
	 * @throws Exception
	 */

	public Login() throws Exception {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Đăng nhập");
		setBounds(100, 100, 734, 505);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g) {
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};
		panel.setForeground(new Color(255, 255, 255));
		panel.setBackground(new Color(255, 255, 255, 200));
		panel.setBounds(201, 80, 323, 324);
		panel.setOpaque(false);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Welcome");
		lblNewLabel_1.setBounds(72, 25, 174, 37);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 30));
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Coffee Shop");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(100, 60, 130, 31);
		panel.add(lblNewLabel_2);

		btnDangNhap = new testbutton.Buttontest();
		btnDangNhap.setText("Đăng Nhập");
		btnDangNhap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					try {
						ConnectDB.getInstance().connect();
					} catch (SQLException e2) {
						e2.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					dangNhap();

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace(); // Gọi phương thức đăng nhập khi Enter được ấn trên các trường nhập liệu
				}
			}
		});

		btnDangNhap.setBounds(44, 274, 239, 40);
		panel.add(btnDangNhap);
		btnDangNhap.setForeground(Color.WHITE);
		btnDangNhap.setBackground(Color.BLACK);
		btnDangNhap.setFont(new Font("Tahoma", Font.PLAIN, 20));

		lblNewLabel_3 = new JLabel("Quên Mật Khẩu?");
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
//				SendEmail1 sc = new SendEmail1();
//				sc.setVisible(true);
//				dispose();
			}
		});
		lblNewLabel_3.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
//				lblNewLabel_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3.setBounds(162, 241, 131, 17);
		panel.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Tài Khoản");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_4.setBounds(44, 101, 64, 17);
		panel.add(lblNewLabel_4);

		JLabel lblNewLabel_4_1 = new JLabel("Mật Khẩu");
		lblNewLabel_4_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_4_1.setBounds(44, 167, 64, 17);
		panel.add(lblNewLabel_4_1);

		passMk = new JPasswordField();
		passMk.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passMk.setBounds(44, 190, 240, 40);
		panel.add(passMk);
		passMk.addActionListener(this);
		passMk.setText("123");

		txtUser = new JTextField();
		txtUser.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtUser.setBounds(44, 124, 240, 40);
		panel.add(txtUser);
		txtUser.setColumns(10);
		txtUser.addActionListener(this);
		txtUser.setText("TK001");

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Login.class.getResource("/image/BG_login.jpg")));
		lblNewLabel.setBounds(0, 0, 728, 468);
		contentPane.add(lblNewLabel);

	}

	public static void main(String[] args) throws Exception {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Login run = new Login();
		run.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == txtUser || e.getSource() == passMk) {
			try {
				dangNhap();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace(); // Gọi phương thức đăng nhập khi Enter được ấn trên các trường nhập liệu
			}
		}

	}

	private void dangNhap() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
//		if (txtUser.getText().trim().isEmpty() || passMk.getPassword().length == 0) {
//			JOptionPane.showMessageDialog(null, "Vui lòng nhập tài khoản và mật khẩu.");
//			return; // Exit the method if fields are empty
//		}
//		if (txtUser.getText().equals("TK001")) {
//			Main_form_manager gdqly = new Main_form_manager();
//			gdqly.setVisible(true);
//			gdqly.setLocationRelativeTo(null);
//			dispose();
//		}else {
//			TaiKhoan tk = list_tk.get(txtUser.getText());
//			if (tk.getPassWord().equals(new String(passMk.getPassword()))) {
//				String tenNhanVien = list_nv.getTenNV(txtUser.getText());
//				Main_form_employee gdnv = new Main_form_employee();
//				gdnv.lbltennv.setText(tenNhanVien);
//				gdnv.setVisible(true);
//				gdnv.setLocationRelativeTo(null);
//				dispose();
//			}else {
//				JOptionPane.showMessageDialog(this, "Tài khoản hoặc mật khẩu sai");
//			}
//		}
		// Kiểm tra xem các trường nhập liệu có bị để trống không
		if (txtUser.getText().trim().isEmpty() || passMk.getPassword().length == 0) {
		    JOptionPane.showMessageDialog(null, "Vui lòng nhập tài khoản và mật khẩu.");
		    return; // Thoát khỏi phương thức nếu các trường còn trống
		}

		String username = txtUser.getText().trim();
		char[] passwordChar = passMk.getPassword();
		String password = new String(passwordChar);

		// Kiểm tra đăng nhập cho tài khoản 'TK001'
		if (username.equals("TK001") && password.equals("123")) {
		    // Đăng nhập thành công, mở Main_form_manager
		    Main_form_manager gdqly = new Main_form_manager();
		    gdqly.setVisible(true);
		    gdqly.setLocationRelativeTo(null);
		    dispose(); // Đóng cửa sổ hiện tại
		} else {
		    // Kiểm tra đăng nhập cho tài khoản khác
		    TaiKhoan tk = list_tk.get(username);
		    if (tk != null && tk.getPassWord().equals(password)) {
		        // Mật khẩu đúng, đăng nhập thành công, mở Main_form_employee
		        String tenNhanVien = list_nv.getTenNV(username);
		        Main_form_employee gdnv = new Main_form_employee();
		        gdnv.lbltennv.setText(tenNhanVien);
		        gdnv.setVisible(true);
		        gdnv.setLocationRelativeTo(null);
		        dispose(); // Đóng cửa sổ hiện tại
		    } else {
		        // Thông báo lỗi nếu tài khoản hoặc mật khẩu không đúng
		        JOptionPane.showMessageDialog(null, "Tên tài khoản hoặc mật khẩu sai");
		    }
		}

	}
}
