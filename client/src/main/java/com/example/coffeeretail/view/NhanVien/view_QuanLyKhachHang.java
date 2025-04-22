package view.NhanVien;

import view.QuanLy.Main_form_manager;
import socket.ClientSocket;
import socket.ProtocolUtils;
import socket.Request;
import socket.Response;
import entity.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class view_QuanLyKhachHang extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnThem, btnXoa, btnSua, btnLamMoi, btntimkiem;
	private JLabel lbltennv;
	private JTable table;
	private DefaultTableModel tableModel;
	private JTextField txtSDT, txtDiaChi, txtTimKiem, txtHoTen;
	private JPanel panelHangHoa, panelDatHang, panelNhanVien, panelTaiKhoan, panelThongKe;
	private JLabel lblNvIcon;
	Color customColor = new Color(255,255,255,0);
	Color whiteColor  = new Color(255,255,255,0);

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				view_QuanLyKhachHang frame = new view_QuanLyKhachHang();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public view_QuanLyKhachHang() {
		initComponents();
		setResizable(false);
		setBackground(Color.WHITE);
		setTitle("Giao Diện Quản Lý Khách Hàng");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setBounds(100, 100, 1168, 650);

		// after UI built:
		refresh();
		loadData();
	}

	private void initComponents() {
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// --- top bar with avatar & name ---
		lblNvIcon = new JLabel(new ImageIcon(view_QuanLyKhachHang.class.getResource("/image/avt.png")));
		lblNvIcon.setBounds(760, 5, 40, 40);
		contentPane.add(lblNvIcon);

		JLabel lblMgr = new JLabel("QL:");
		lblMgr.setForeground(Color.WHITE);
		lblMgr.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblMgr.setBounds(801, 0, 39, 50);
		contentPane.add(lblMgr);

		lbltennv = new JLabel("Lê Minh Đăng");
		lbltennv.setForeground(Color.WHITE);
		lbltennv.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbltennv.setBounds(832, 0, 238, 50);
		contentPane.add(lbltennv);

		JPanel topPanel = new JPanel() {
			protected void paintComponent(Graphics g) {
				g.setColor(getBackground());
				g.fillRect(0,0,getWidth(),getHeight());
				super.paintComponent(g);
			}
		};
		topPanel.setOpaque(false);
		topPanel.setBounds(0,0,1168,50);
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.setBackground(customColor);
		contentPane.add(topPanel);

		// --- toolbars omitted for brevity (copy your existing ones here) ---
		// e.g. panelHangHoa, panelDatHang, etc.

		// --- Title ---
		JLabel lblTitle = new JLabel("Quản Lý Khách Hàng");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Open Sans", Font.BOLD, 16));
		lblTitle.setBounds(43, 102, 200, 20);
		contentPane.add(lblTitle);

		// --- Họ tên field ---
		JLabel lblHoTen = new JLabel("Nhập họ tên :");
		lblHoTen.setForeground(Color.WHITE);
		lblHoTen.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblHoTen.setBounds(17, 139, 130, 21);
		contentPane.add(lblHoTen);

		JPanel pnlHoTen = new JPanel();
		pnlHoTen.setOpaque(false);
		pnlHoTen.setBackground(whiteColor);
		pnlHoTen.setBounds(10,161,230,37);
		contentPane.add(pnlHoTen);

		txtHoTen = new JTextField();
		txtHoTen.setFont(new Font("Open Sans", Font.PLAIN, 16));
		txtHoTen.setColumns(16);
		pnlHoTen.add(txtHoTen);

		// --- SĐT field ---
		JLabel lblSDT = new JLabel("Số điện thoại:");
		lblSDT.setForeground(Color.WHITE);
		lblSDT.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblSDT.setBounds(17, 209, 130, 21);
		contentPane.add(lblSDT);

		JPanel pnlSDT = new JPanel();
		pnlSDT.setOpaque(false);
		pnlSDT.setBackground(whiteColor);
		pnlSDT.setBounds(10,241,230,37);
		contentPane.add(pnlSDT);

		txtSDT = new JTextField();
		txtSDT.setFont(new Font("Dialog", Font.PLAIN, 16));
		txtSDT.setColumns(16);
		pnlSDT.add(txtSDT);

		// --- Địa chỉ field ---
		JLabel lblDiaChi = new JLabel("Địa chỉ:");
		lblDiaChi.setForeground(Color.WHITE);
		lblDiaChi.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblDiaChi.setBounds(17, 289, 130, 21);
		contentPane.add(lblDiaChi);

		JPanel pnlDiaChi = new JPanel();
		pnlDiaChi.setOpaque(false);
		pnlDiaChi.setBackground(whiteColor);
		pnlDiaChi.setBounds(10,321,230,37);
		contentPane.add(pnlDiaChi);

		txtDiaChi = new JTextField();
		txtDiaChi.setFont(new Font("Dialog", Font.PLAIN, 16));
		txtDiaChi.setColumns(16);
		pnlDiaChi.add(txtDiaChi);

		// --- Search box ---
		txtTimKiem = new JTextField();
		txtTimKiem.setFont(new Font("Dialog", Font.PLAIN, 16));
		txtTimKiem.setColumns(16);
		txtTimKiem.setBounds(871,99,214,30);
		contentPane.add(txtTimKiem);

		btntimkiem = new JButton(new ImageIcon(view_QuanLyKhachHang.class.getResource("/image/search.png")));
		btntimkiem.setBounds(1090,99,40,30);
		contentPane.add(btntimkiem);

		// --- CRUD buttons ---
		btnThem    = new JButton("Thêm");
		btnXoa     = new JButton("Xóa");
		btnSua     = new JButton("Sửa");
		btnLamMoi  = new JButton("Làm mới");

		btnThem.setBounds(250,99,100,30);
		btnXoa.setBounds(360,99,100,30);
		btnSua.setBounds(470,99,100,30);
		btnLamMoi.setBounds(580,99,100,30);

		contentPane.add(btnThem);
		contentPane.add(btnXoa);
		contentPane.add(btnSua);
		contentPane.add(btnLamMoi);

		// --- Table ---
		String[] columnNames = { "Mã KH", "Tên KH","Địa Chỉ", "SĐT" };
		tableModel = new DefaultTableModel(columnNames,0);
		table = new JTable(tableModel);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				int r = table.getSelectedRow();
				txtHoTen.setText(tableModel.getValueAt(r,1).toString());
				txtDiaChi.setText(tableModel.getValueAt(r,2).toString());
				txtSDT.setText(tableModel.getValueAt(r,3).toString());
				btnSua.setEnabled(true);
				btnXoa.setEnabled(true);
			}
		});
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(250,140,900,469);
		contentPane.add(scrollPane);

		// --- root pane default ---
		getRootPane().setDefaultButton(btnThem);

		// --- wire up actions ---
		btnThem   .addActionListener(this);
		btnXoa    .addActionListener(this);
		btnSua    .addActionListener(this);
		btnLamMoi .addActionListener(this);
		btntimkiem.addActionListener(this);

		// --- background wallpaper ---
		JLabel background = new JLabel(new ImageIcon(view_QuanLyKhachHang.class.getResource("/image/bgCF.jpg")));
		background.setBounds(0,0,1162,613);
		contentPane.add(background);

		// Disable editing until an action is chosen
		refresh();
	}

	private void loadData() {
		tableModel.setRowCount(0);
		try {
			Request req = new Request("GET_CUSTOMERS", "");
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				// Use fromJsonList to deserialize the JSON data
				List<Customer> list = ProtocolUtils.fromJsonList(res.getData(), Customer.class);
				for (Customer kh : list) {
					tableModel.addRow(new Object[]{
							kh.getMaKH(), kh.getTenKH(),
							kh.getDiaChi(), kh.getSdt()
					});
				}
			} else {
				JOptionPane.showMessageDialog(this, res.getErrorMsg(),
						"Lỗi tải khách hàng", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Không thể kết nối tới server.",
					"Lỗi", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		if (src == btnThem) {
			if (txtHoTen.getText().trim().isEmpty()
					|| txtSDT.getText().trim().isEmpty()
					|| txtDiaChi.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin đầy đủ");
				return;
			}
			Customer kh = new Customer();
			kh.setMaKH("KH"+System.currentTimeMillis());
			kh.setTenKH(txtHoTen.getText().trim());
			kh.setDiaChi(txtDiaChi.getText().trim());
			kh.setSdt(txtSDT.getText().trim());
			try {
				String payload = ProtocolUtils.toJson(kh);
				Request req = new Request("CREATE_CUSTOMER", payload);
				Response res = ClientSocket.sendRequest(req);
				if (res.isSuccess()) {
					JOptionPane.showMessageDialog(this, "Thêm thành công!");
					loadData(); refresh();
				} else {
					JOptionPane.showMessageDialog(this, res.getErrorMsg(),
							"Lỗi thêm", JOptionPane.ERROR_MESSAGE);
				}
			} catch(Exception ex){ ex.printStackTrace(); }

		} else if (src == btnSua) {
			int r = table.getSelectedRow();
			if (r<0) return;
			Customer kh = new Customer();
			kh.setMaKH(tableModel.getValueAt(r,0).toString());
			kh.setTenKH(txtHoTen.getText().trim());
			kh.setDiaChi(txtDiaChi.getText().trim());
			kh.setSdt(txtSDT.getText().trim());
			try {
				String payload = ProtocolUtils.toJson(kh);
				Request req = new Request("UPDATE_CUSTOMER", payload);
				Response res = ClientSocket.sendRequest(req);
				if (res.isSuccess()) {
					JOptionPane.showMessageDialog(this,"Sửa thành công!");
					loadData(); refresh();
				} else {
					JOptionPane.showMessageDialog(this, res.getErrorMsg(),
							"Lỗi sửa", JOptionPane.ERROR_MESSAGE);
				}
			} catch(Exception ex){ ex.printStackTrace(); }

		} else if (src == btnXoa) {
			int r = table.getSelectedRow();
			if (r<0) return;
			String id = tableModel.getValueAt(r,0).toString();
			try {
				Request req = new Request("DELETE_CUSTOMER", ProtocolUtils.toJson(id));
				Response res = ClientSocket.sendRequest(req);
				if (res.isSuccess()) {
					JOptionPane.showMessageDialog(this,"Xóa thành công!");
					loadData(); refresh();
				} else {
					JOptionPane.showMessageDialog(this, res.getErrorMsg(),
							"Lỗi xóa", JOptionPane.ERROR_MESSAGE);
				}
			} catch(Exception ex){ ex.printStackTrace(); }

		} else if (src == btntimkiem) {
			String kw = txtTimKiem.getText().trim().toLowerCase();
			tableModel.setRowCount(0);
			try {
				Request req = new Request("GET_CUSTOMERS","");
				Response res = ClientSocket.sendRequest(req);
				if (res.isSuccess()) {
					// Use fromJsonList to deserialize the JSON data
					List<Customer> list = ProtocolUtils.fromJsonList(res.getData(), Customer.class);
					for (Customer kh : list) {
						if (kh.getTenKH().toLowerCase().contains(kw)) {
							tableModel.addRow(new Object[]{
									kh.getMaKH(), kh.getTenKH(),
									kh.getDiaChi(), kh.getSdt()
							});
						}
					}
				}
			} catch(Exception ex){ ex.printStackTrace(); }

		} else if (src == btnLamMoi) {
			loadData(); refresh();
		}
	}

	private void refresh() {
		txtHoTen.setText("");
		txtDiaChi.setText("");
		txtSDT.setText("");
		txtTimKiem.setText("");
		btnXoa.setEnabled(false);
		btnSua.setEnabled(false);
		btnThem.setEnabled(true);
	}

	// make sure to handle window closing back to manager
	@Override
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID()==WindowEvent.WINDOW_CLOSING) {
			Main_form_manager m = new Main_form_manager();
			m.setVisible(true);
			m.setLocationRelativeTo(this);
		}
	}
}
