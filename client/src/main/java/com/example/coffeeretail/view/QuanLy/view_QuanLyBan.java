package view.QuanLy;

import socket.Request;
import socket.Response;
import entity.Tables;
import com.fasterxml.jackson.databind.ObjectMapper;
import socket.ClientSocket;
import runapp.Login;
import testbutton.Buttontest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class view_QuanLyBan extends JFrame implements ActionListener {
	private String tempMaBan;
	private List<Tables> allTables;
	private JPanel contentPane;
	private JButton btnThem, btnXoa, btnSua, btnLamMoi, btntimkiem;
	private JTable table;
	private DefaultTableModel tableModel;
	private JTextField txtTimKiem, txtTenBan;
	private final ObjectMapper mapper = new ObjectMapper();
	private final Color customColor = new Color(255, 255, 255, 0);
	private final Color whiteColor  = new Color(255, 255, 255, 0);

	public static void main(String[] args) {
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception ex) {
			java.util.logging.Logger.getLogger(view_QuanLyBan.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}
		SwingUtilities.invokeLater(() -> new view_QuanLyBan().setVisible(true));
	}

	public view_QuanLyBan() {
		initUI();
		loadData();
		refresh();
	}

	private void initUI() {
		setResizable(false);
		setTitle("Giao Diện Quản Lý Bàn");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1168, 650);
		contentPane = new JPanel(null);
		setContentPane(contentPane);

		// Avatar và tên
		JLabel lblNvIcon = new JLabel(new ImageIcon(getClass().getResource("/image/avt.png")));
		lblNvIcon.setBounds(760, 5, 40, 40);
		contentPane.add(lblNvIcon);
		JLabel lblQL = new JLabel("QL:");
		lblQL.setForeground(Color.WHITE);
		lblQL.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblQL.setBounds(801, 0, 39, 50);
		contentPane.add(lblQL);
		JLabel lblTenNV = new JLabel("Trương Đại Lộc");
		lblTenNV.setForeground(Color.WHITE);
		lblTenNV.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTenNV.setBounds(832, 0, 238, 50);
		contentPane.add(lblTenNV);

		// Toolbar
		JPanel topPanel = new JPanel(null) {
			protected void paintComponent(Graphics g) {
				g.setColor(getBackground()); g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};
		topPanel.setOpaque(false);
		topPanel.setBounds(0, 0, 1168, 50);
		contentPane.add(topPanel);

		// Add toolbars
		addToolbar(topPanel, "Hàng hóa", new Color(255,128,64), e -> openFrame(view_QuanLyHangHoa.class));
		addToolbar(topPanel, "Đặt Hàng", new Color(46,139,87), e -> openFrame(view_QuanLyKhachHang.class));
		addToolbar(topPanel, "Nhân Viên", new Color(255,0,128), e -> openFrame(view_QuanLyNhanVien.class));
		addToolbar(topPanel, "Tài Khoản", new Color(99,176,28), e -> openFrame(view_QuanLyTaiKhoan.class));
		addToolbar(topPanel, "Thống Kê", new Color(100,100,255), e -> openFrame(view_ThongKeDoanhThu.class));
		addLogout(topPanel);

		// Title
		JLabel lblTitle = new JLabel("Quản Lý Bàn");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Open Sans", Font.BOLD, 16));
		lblTitle.setBounds(43, 102, 170, 20);
		contentPane.add(lblTitle);

		// Form inputs
		contentPane.add(createLabel("Nhập tên bàn:", 10, 132, 130, 21));
		JPanel pnlHoTen = new JPanel(); pnlHoTen.setBounds(10,161,230,37); pnlHoTen.setOpaque(false);
		contentPane.add(pnlHoTen);
		txtTenBan = new JTextField(); txtTenBan.setFont(new Font("Open Sans", Font.PLAIN, 16)); txtTenBan.setColumns(16);
		pnlHoTen.add(txtTenBan);

		txtTimKiem = new JTextField(); txtTimKiem.setFont(new Font("Dialog", Font.PLAIN, 16)); txtTimKiem.setBounds(871,99,214,30);
		contentPane.add(txtTimKiem);
		btntimkiem = new JButton(new ImageIcon(getClass().getResource("/image/search.png")));
		btntimkiem.setBounds(1090,99,40,30);
		contentPane.add(btntimkiem);

		// Buttons
		btnThem  = createButton("Thêm", 250, 99);
		btnXoa   = createButton("Xóa", 360, 99);
		btnSua   = createButton("Sửa", 470, 99);
		btnLamMoi= createButton("Làm mới",580, 99);

		for (JButton b : new JButton[]{btnThem, btnXoa, btnSua, btnLamMoi, btntimkiem}) {
			b.addActionListener(this);
			contentPane.add(b);
		}

		// Table
		tableModel = new DefaultTableModel(new String[]{"Mã Bàn","Tên Bàn"}, 0);
		table = new JTable(tableModel);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int r = table.getSelectedRow();
				tempMaBan = tableModel.getValueAt(r, 0).toString();
				txtTenBan.setText(tableModel.getValueAt(r, 1).toString());
				btnSua.setEnabled(true); btnXoa.setEnabled(true);
			}
		});
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(250,140,900,469);
		contentPane.add(scroll);

		// Background
		JLabel bg = new JLabel(new ImageIcon(getClass().getResource("/image/bgCF.jpg")));
		bg.setBounds(0,0,1162,613);
		contentPane.add(bg);
	}

	private JLabel createLabel(String text, int x,int y,int w,int h) {
		JLabel lbl = new JLabel(text);
		lbl.setForeground(Color.WHITE);
		lbl.setFont(new Font("Dialog",Font.PLAIN,16));
		lbl.setBounds(x,y,w,h);
		return lbl;
	}

	private JButton createButton(String text,int x,int y) {
		JButton btn = new JButton(text);
		btn.setFont(new Font("Tahoma",Font.PLAIN,14));
		btn.setBounds(x,y,100,30);
		return btn;
	}

	private <T extends JFrame> void openFrame(Class<T> cls) {
		try {
			T frame = cls.getDeclaredConstructor().newInstance();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			dispose();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void addToolbar(JPanel parent, String text, Color bg, ActionListener action) {
		JToolBar tb = new JToolBar(); tb.setFloatable(false); tb.setMargin(new Insets(-5,-5,0,-5));
		Buttontest btn = new Buttontest(); btn.setText(text); btn.setFont(new Font("Open Sans",Font.BOLD,15));
		btn.setForeground(SystemColor.text); btn.setRippleColor(Color.WHITE); btn.setBackground(bg);
		btn.addActionListener(action); tb.add(btn); tb.setBackground(customColor); parent.add(tb);
	}

	private void addLogout(JPanel parent) {
		JToolBar tb = new JToolBar(); tb.setFloatable(false); tb.setMargin(new Insets(-5,550,0,0));
		Buttontest btn = new Buttontest(); btn.setText("Đăng Xuất"); btn.setFont(new Font("Open Sans",Font.BOLD,15));
		btn.setForeground(SystemColor.text); btn.setRippleColor(Color.WHITE); btn.setBackground(new Color(226,110,110));
		btn.addActionListener(e -> {
			if (JOptionPane.showConfirmDialog(null,"Bạn có muốn đăng xuất?",null,JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
				new Login().setVisible(true); dispose();
			}
		}); tb.add(btn); tb.setBackground(customColor); parent.add(tb);
	}

	private void loadData() {
		tableModel.setRowCount(0);
		try {
			// Gửi request lên server
			Response resp = ClientSocket.sendRequest(new Request("GET_TABLES", null));

			// Chuyển Object trả về thành List<Tables>
			List<Tables> list = mapper.convertValue(
					resp.getData(),
					mapper.getTypeFactory().constructCollectionType(List.class, Tables.class)
			);
			allTables = list;

			// Đổ lên bảng
			for (Tables t : allTables) {
				tableModel.addRow(new Object[]{ t.getMaBan(), t.getTenBan() });
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(
					this,
					"Lỗi tải dữ liệu: " + ex.getMessage(),
					"Lỗi",
					JOptionPane.ERROR_MESSAGE
			);
		}
	}

	private void loadByName() {
		String key = txtTimKiem.getText().trim().toLowerCase();
		tableModel.setRowCount(0);
		for (Tables t : allTables) {
			if (t.getTenBan().toLowerCase().contains(key)) {
				tableModel.addRow(new Object[]{t.getMaBan(), t.getTenBan()});
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == btnThem && validTenBan()) {
				Tables t = new Tables();
				t.setTenBan(txtTenBan.getText().trim());
				String payload = mapper.writeValueAsString(t);

				Response r = ClientSocket.sendRequest(
						new Request("CREATE_TABLE", payload)
				);
				// Thông báo thành công
				JOptionPane.showMessageDialog(this, "Thêm thành công!");
				loadData();

			} else if (e.getSource() == btnSua) {
				Tables t = new Tables();
				t.setMaBan(tempMaBan);
				t.setTenBan(txtTenBan.getText().trim());
				String payload = mapper.writeValueAsString(t);

				Response r = ClientSocket.sendRequest(
						new Request("UPDATE_TABLE", payload)
				);
				JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
				loadData();

			} else if (e.getSource() == btnXoa) {
				Response r = ClientSocket.sendRequest(
						new Request("DELETE_TABLE", tempMaBan)
				);
				JOptionPane.showMessageDialog(this, "Xóa thành công!");
				loadData();

			} else if (e.getSource() == btntimkiem) {
				loadByName();

			} else if (e.getSource() == btnLamMoi) {
				loadData();
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(
					this,
					"Lỗi: " + ex.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE
			);
		}
		refresh();
	}


	private void refresh() {
		txtTenBan.setText("");
		txtTimKiem.setText("");
		tempMaBan = null;
		btnSua.setEnabled(false);
		btnXoa.setEnabled(false);
		btnThem.setEnabled(true);
	}

	private boolean validTenBan() {
		return !txtTenBan.getText().trim().isEmpty();
	}
}