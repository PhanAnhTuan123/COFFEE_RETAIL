package runapp;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import entity.Account;
import socket.ClientSocket;
import socket.ProtocolUtils;
import socket.Request;
import socket.Response;
import view.NhanVien.Main_form_employee;
import view.QuanLy.Main_form_manager;

public class Login extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUser;
	private JPasswordField passMk;
	private testbutton.Buttontest btnDangNhap;
	private JLabel lblNewLabel_3;

	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Đăng nhập");
		setBounds(100, 100, 734, 505);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel() {
			private static final long serialVersionUID = 1L;
			protected void paintComponent(Graphics g) {
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};
		panel.setForeground(Color.WHITE);
		panel.setBackground(new Color(255, 255, 255, 200));
		panel.setBounds(201, 80, 323, 324);
		panel.setOpaque(false);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblWelcome = new JLabel("Welcome");
		lblWelcome.setBounds(72, 25, 174, 37);
		lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 30));
		panel.add(lblWelcome);

		JLabel lblTitle = new JLabel("Coffee Shop");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitle.setBounds(100, 60, 130, 31);
		panel.add(lblTitle);

		btnDangNhap = new testbutton.Buttontest();
		btnDangNhap.setText("Đăng Nhập");
		btnDangNhap.setBounds(44, 274, 239, 40);
		btnDangNhap.setForeground(Color.WHITE);
		btnDangNhap.setBackground(Color.BLACK);
		btnDangNhap.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnDangNhap.addActionListener(e -> dangNhap());
		panel.add(btnDangNhap);

		lblNewLabel_3 = new JLabel("Quên Mật Khẩu?");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3.setBounds(162, 241, 131, 17);
		panel.add(lblNewLabel_3);

		JLabel lblUsername = new JLabel("Tài Khoản");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(44, 101, 64, 17);
		panel.add(lblUsername);

		JLabel lblPassword = new JLabel("Mật Khẩu");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(44, 167, 64, 17);
		panel.add(lblPassword);

		passMk = new JPasswordField();
		passMk.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passMk.setBounds(44, 190, 240, 40);
		passMk.setText("123");
		passMk.addActionListener(this);
		panel.add(passMk);

		txtUser = new JTextField();
		txtUser.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtUser.setBounds(44, 124, 240, 40);
		txtUser.setText("TK001");
		txtUser.addActionListener(this);
		panel.add(txtUser);

		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon(Login.class.getResource("/image/BG_login.jpg")));
		lblBackground.setBounds(0, 0, 728, 468);
		contentPane.add(lblBackground);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == txtUser || e.getSource() == passMk) {
			dangNhap();
		}
	}

	private void dangNhap() {
		String username = txtUser.getText().trim();
		String password = new String(passMk.getPassword());

		if (username.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập tài khoản và mật khẩu.");
			return;
		}

		Request req = new Request();
		req.setResource("account");
		req.setAction("login");

		Map<String, String> payload = new HashMap<>();
		payload.put("username", username);
		payload.put("password", password);

		try {
			req.setData(ProtocolUtils.toJson(payload));
			Response res = ClientSocket.sendRequest(req);

			if (res.isSuccess()) {
				Account acc = ProtocolUtils.fromJson(res.getData(), Account.class);
				if ("admin".equalsIgnoreCase(acc.getRole())) {
					Main_form_manager manager = new Main_form_manager();
					manager.setVisible(true);
					manager.setLocationRelativeTo(null);
				} else {
					Main_form_employee employee = new Main_form_employee();
					employee.lbltennv.setText(acc.getUsername()); // hoặc acc.getFullname() nếu server có
					employee.setVisible(true);
					employee.setLocationRelativeTo(null);
				}
				dispose();
			} else {
				JOptionPane.showMessageDialog(this, res.getErrorMsg(), "Đăng nhập thất bại", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Không thể kết nối đến server.", "Lỗi", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					if ("Nimbus".equals(info.getName())) {
						UIManager.setLookAndFeel(info.getClassName());
						break;
					}
				}
			} catch (Exception ignored) {}
			new Login().setVisible(true);
		});
	}
}
