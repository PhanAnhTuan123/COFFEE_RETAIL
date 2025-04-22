package view.QuanLy;

import socket.ClientSocket;
import socket.Request;
import socket.Response;
import entity.Account;
import runapp.Login;
import testbutton.Buttontest;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;

public class view_QuanLyTaiKhoan extends JFrame implements ActionListener {
	private String tempUsername;
	private List<Account> allAccounts;
	private final ObjectMapper mapper = new ObjectMapper();

	private JTextField txtUsername, txtPassword, txtSearch;
	private JComboBox<String> cboxRole;
	private JButton btnAdd, btnEdit, btnDelete, btnRefresh, btnSearch;
	private JTable table;
	private DefaultTableModel tableModel;
	private final Color transparent = new Color(255,255,255,0);

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				for (UIManager.LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()) {
					if ("Nimbus".equals(info.getName())) {
						UIManager.setLookAndFeel(info.getClassName());
						break;
					}
				}
			} catch (Exception ignored) {}
			new view_QuanLyTaiKhoan().setVisible(true);
		});
	}

	public view_QuanLyTaiKhoan() {
		initUI();
		loadData();
		refresh();
	}

	private void initUI() {
		setTitle("Quản Lý Tài Khoản");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1168, 650);

		JPanel content = new JPanel(null);
		content.setBackground(Color.WHITE);
		setContentPane(content);

		// Header avatar + name
		JLabel icon = new JLabel(new ImageIcon(getClass().getResource("/image/avt.png")));
		icon.setBounds(760, 5, 40, 40);
		content.add(icon);

		JLabel ql = new JLabel("QL:");
		ql.setForeground(Color.WHITE);
		ql.setFont(new Font("Tahoma", Font.BOLD, 16));
		ql.setBounds(801, 0, 39, 50);
		content.add(ql);

		JLabel name = new JLabel("Trương Đại Lộc");
		name.setForeground(Color.WHITE);
		name.setFont(new Font("Tahoma", Font.BOLD, 16));
		name.setBounds(832, 0, 238, 50);
		content.add(name);

		// Toolbar
		JPanel top = new JPanel(null) {
			protected void paintComponent(Graphics g) {
				g.setColor(getBackground());
				g.fillRect(0,0,getWidth(),getHeight());
				super.paintComponent(g);
			}
		};
		top.setBounds(0, 0, 1168, 50);
		top.setOpaque(false);
		content.add(top);

		addToolbar(top, "Hàng hóa",    new Color(255,128,64),    e->open(view_QuanLyHangHoa.class));
		addToolbar(top, "Đặt Hàng",   new Color(46,139,87),     e->open(view_QuanLyBan.class));
		addToolbar(top, "Khách Hàng", new Color(255,0,128),     e->open(view_QuanLyKhachHang.class));
		addToolbar(top, "Nhân Viên",  new Color(255,0,128),     e->open(view_QuanLyNhanVien.class));
		addToolbar(top, "Thống Kê",   new Color(100,100,255),   e->open(view_ThongKeDoanhThu.class));
		addLogout(top);

		// Title
		JLabel title = new JLabel("Quản Lý Tài Khoản");
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Open Sans", Font.BOLD, 16));
		title.setBounds(43, 102, 170, 20);
		content.add(title);

		// Form fields
		createLabel(content, "Tên Đăng Nhập:", 17,139,130,21);
		txtUsername = new JTextField(); txtUsername.setBounds(17,161,230,30);
		txtUsername.setEnabled(false);
		content.add(txtUsername);

		createLabel(content, "Mật Khẩu:",      17,209,130,21);
		txtPassword = new JTextField(); txtPassword.setBounds(17,231,230,30);
		content.add(txtPassword);

		createLabel(content, "Role:",           17,279,130,21);
		cboxRole = new JComboBox<>(new String[]{"USER","ADMIN"});
		cboxRole.setBounds(17,301,230,30);
		content.add(cboxRole);

		// Search
		txtSearch = new JTextField(); txtSearch.setBounds(871,99,214,30);
		content.add(txtSearch);
		btnSearch = new JButton(new ImageIcon(getClass().getResource("/image/search.png")));
		btnSearch.setBounds(1090,99,40,30);
		btnSearch.addActionListener(this);
		content.add(btnSearch);

		// Action buttons
		btnAdd     = createButton("Thêm",    250, 99);
		btnDelete  = createButton("Xóa",     360, 99);
		btnEdit    = createButton("Sửa",     470, 99);
		btnRefresh = createButton("Làm mới", 580, 99);
		for (JButton b: new JButton[]{btnAdd, btnDelete, btnEdit, btnRefresh}) {
			b.addActionListener(this);
			content.add(b);
		}

		// Table
		tableModel = new DefaultTableModel(
				new String[]{"Tên Đăng Nhập","Mật Khẩu","Role"}, 0
		);
		table = new JTable(tableModel);
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				int r = table.getSelectedRow();
				tempUsername = tableModel.getValueAt(r,0).toString();
				txtUsername.setText(tempUsername);
				txtPassword.setText(tableModel.getValueAt(r,1).toString());
				cboxRole.setSelectedItem(tableModel.getValueAt(r,2).toString());
				btnEdit.setEnabled(true);
				btnDelete.setEnabled(true);
			}
		});
		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(250,140,900,469);
		content.add(sp);

		// Background
		JLabel bg = new JLabel(new ImageIcon(getClass().getResource("/image/bgCF.jpg")));
		bg.setBounds(0,0,1162,613);
		content.add(bg);
	}

	private void loadData() {
		tableModel.setRowCount(0);
		try {
			Response resp = ClientSocket.sendRequest(new Request("GET_ACCOUNTS", null));
			allAccounts = mapper.convertValue(
					resp.getData(),
					mapper.getTypeFactory().constructCollectionType(List.class, Account.class)
			);
			for (Account a: allAccounts) {
				tableModel.addRow(new Object[]{
						a.getUsername(),
						a.getPassWord(),
						a.getRole()
				});
			}
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(this,
					"Lỗi tải dữ liệu: " + ex.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE
			);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource()==btnAdd) {
				if (txtPassword.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu");
				} else {
					Account a = new Account();
					a.setUserName(txtUsername.getText().trim());
					a.setPassWord(txtPassword.getText().trim());
					a.setRole(cboxRole.getSelectedItem().toString());
					String payload = mapper.writeValueAsString(a);
					ClientSocket.sendRequest(new Request("CREATE_ACCOUNT", payload));
					JOptionPane.showMessageDialog(this, "Thêm thành công!");
				}
			}
			else if (e.getSource()==btnEdit) {
				Account a = new Account();
				a.setUserName(tempUsername);
				a.setPassWord(txtPassword.getText().trim());
				a.setRole(cboxRole.getSelectedItem().toString());
				String payload = mapper.writeValueAsString(a);
				ClientSocket.sendRequest(new Request("UPDATE_ACCOUNT", payload));
				JOptionPane.showMessageDialog(this, "Sửa thành công!");
			}
			else if (e.getSource()==btnDelete) {
				ClientSocket.sendRequest(new Request("DELETE_ACCOUNT", tempUsername));
				JOptionPane.showMessageDialog(this, "Xóa thành công!");
			}
			else if (e.getSource()==btnSearch) {
				String key = txtSearch.getText().trim().toLowerCase();
				List<Account> filtered = allAccounts.stream()
						.filter(a -> a.getUsername().toLowerCase().contains(key))
						.collect(Collectors.toList());
				tableModel.setRowCount(0);
				for (Account a: filtered) {
					tableModel.addRow(new Object[]{
							a.getUsername(),
							a.getPassWord(),
							a.getRole()
					});
				}
				return;
			}
			// refresh data for both refresh button and after CRUD
			loadData();
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(this,
					"Lỗi: " + ex.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE
			);
		}
		refresh();
	}

	private void refresh(){
		txtUsername.setText("");
		txtPassword.setText("");
		txtSearch.setText("");
		cboxRole.setSelectedIndex(0);
		btnEdit.setEnabled(false);
		btnDelete.setEnabled(false);
		btnAdd.setEnabled(true);
	}

	// --- helpers below ---

	private void addToolbar(JPanel p, String text, Color bg, ActionListener a){
		JToolBar tb = new JToolBar();
		tb.setFloatable(false);
		tb.setMargin(new Insets(-5,-5,0,-5));
		Buttontest btn = new Buttontest();
		btn.setText(text);
		btn.setFont(new Font("Open Sans", Font.BOLD, 15));
		btn.setForeground(SystemColor.text);
		btn.setRippleColor(Color.WHITE);
		btn.setBackground(bg);
		btn.addActionListener(a);
		tb.add(btn);
		tb.setBackground(transparent);
		p.add(tb);
	}

	private void addLogout(JPanel p){
		JToolBar tb = new JToolBar();
		tb.setFloatable(false);
		tb.setMargin(new Insets(-5,550,0,0));
		Buttontest btn = new Buttontest();
		btn.setText("Đăng Xuất");
		btn.setFont(new Font("Open Sans", Font.BOLD, 15));
		btn.setForeground(SystemColor.text);
		btn.setRippleColor(Color.WHITE);
		btn.setBackground(new Color(226,110,110));
		btn.addActionListener(e -> {
			if (JOptionPane.showConfirmDialog(
					null,
					"Bạn có muốn đăng xuất?",
					null,
					JOptionPane.YES_NO_OPTION
			) == JOptionPane.YES_OPTION) {
				new Login().setVisible(true);
				dispose();
			}
		});
		tb.add(btn);
		tb.setBackground(transparent);
		p.add(tb);
	}

	private <T extends JFrame> void open(Class<T> cls){
		try {
			T f = cls.getDeclaredConstructor().newInstance();
			f.setLocationRelativeTo(null);
			f.setVisible(true);
			dispose();
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private JLabel createLabel(JPanel p, String t, int x, int y, int w, int h){
		JLabel l = new JLabel(t);
		l.setForeground(Color.WHITE);
		l.setFont(new Font("Dialog", Font.PLAIN, 16));
		l.setBounds(x,y,w,h);
		p.add(l);
		return l;
	}

	private JButton createButton(String t, int x, int y){
		JButton b = new JButton(t);
		b.setFont(new Font("Tahoma", Font.PLAIN, 14));
		b.setBounds(x,y,100,30);
		return b;
	}
}
