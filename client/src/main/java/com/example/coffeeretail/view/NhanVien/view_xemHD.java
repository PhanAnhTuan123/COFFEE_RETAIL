package view.NhanVien;

import runapp.Login;
import socket.ClientSocket;
import socket.Request;
import socket.Response;
import socket.ProtocolUtils;
import testbutton.Buttontest;
import view.QuanLy.view_QuanLyNhanVien;
import entity.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class view_xemHD extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnXem, btnLamMoi, btntimkiem;
	public JLabel lbltennv;
	private JTable table;
	private DefaultTableModel tableModel;
	private JTextField txtTimKiem;
	private JPanel panelDatHang;
	Color customColor = new Color(255, 255, 255, 0);
	Color whiteColor  = new Color(255, 255, 255, 0);
	private JLabel lblNvIcon;
	private view_dialogXemHD view;

	public static void main(String[] args) throws Exception {
		// set Nimbus look & feel...
		view_xemHD frame = new view_xemHD();
		frame.setVisible(true);
	}

	public view_xemHD() {
		initComponents();
		setResizable(false);
		setTitle("Giao Diện Xem Hóa Đơn");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setBounds(100, 100, 1168, 650);

		// ---- Top bar & logout button ----
		lblNvIcon = new JLabel(new ImageIcon(view_QuanLyNhanVien.class.getResource("/image/avt.png")));
		lblNvIcon.setBounds(760, 5, 40, 40);
		contentPane.add(lblNvIcon);

		JLabel lblNV = new JLabel("NV:");
		lblNV.setForeground(Color.WHITE);
		lblNV.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNV.setBounds(801, 0, 39, 50);
		contentPane.add(lblNV);

		lbltennv = new JLabel("");
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
		topPanel.setBounds(0, 0, 1168, 50);
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.setBackground(customColor);
		contentPane.add(topPanel);

		JToolBar datHangToolbar = new JToolBar();
		datHangToolbar.setFloatable(false);
		datHangToolbar.setMargin(new Insets(-5,-5,0,-5));
		Buttontest datHangButton = new Buttontest();
		datHangButton.setText("Đặt Hàng");
		datHangButton.setFont(new Font("Open Sans", Font.BOLD, 15));
		datHangButton.setForeground(SystemColor.text);
		datHangButton.setRippleColor(Color.WHITE);
		datHangButton.setBackground(new Color(46,139,87));
		datHangButton.addActionListener(e -> panelDatHang.setVisible(!panelDatHang.isVisible()));
		datHangToolbar.add(datHangButton);
		datHangToolbar.setBackground(customColor);
		topPanel.add(datHangToolbar);

		panelDatHang = new JPanel() {
			protected void paintComponent(Graphics g) {
				g.setColor(getBackground());
				g.fillRect(0,0,getWidth(),getHeight());
				super.paintComponent(g);
			}
		};
		panelDatHang.setBounds(0,49,1175,47);
		panelDatHang.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelDatHang.setVisible(false);
		panelDatHang.setBackground(whiteColor);
		contentPane.add(panelDatHang);

		JButton btnTaoHD = new JButton("Tạo Hóa Đơn");
		btnTaoHD.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnTaoHD.addActionListener(e -> {
			try {
				view_hoaDon gd = new view_hoaDon();
				gd.lbltennv.setText(lbltennv.getText());
				gd.setVisible(true);
				gd.setLocationRelativeTo(null);
				dispose();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		});
		JButton btnXemHD = new JButton("Xem Hóa Đơn");
		btnXemHD.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnXemHD.addActionListener(e -> {
			try {
				view_xemHD gd = new view_xemHD();
				gd.lbltennv.setText(lbltennv.getText());
				gd.setVisible(true);
				gd.setLocationRelativeTo(null);
				dispose();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		});
		panelDatHang.add(btnTaoHD);
		panelDatHang.add(btnXemHD);

		// logout
		JToolBar logoutTB = new JToolBar();
		logoutTB.setFloatable(false);
		logoutTB.setMargin(new Insets(-5,550,0,0));
		Buttontest logoutBtn = new Buttontest();
		logoutBtn.setText("Đăng Xuất");
		logoutBtn.setFont(new Font("Open Sans", Font.BOLD, 15));
		logoutBtn.setForeground(SystemColor.text);
		logoutBtn.setRippleColor(Color.WHITE);
		logoutBtn.setBackground(new Color(226,110,110));
		logoutBtn.addActionListener(e -> {
			if (JOptionPane.showConfirmDialog(null,"Bạn có muốn đăng xuất!",null,
					JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
				try {
					Login lg = new Login();
					lg.setVisible(true);
					lg.setLocationRelativeTo(null);
					dispose();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		logoutTB.add(logoutBtn);
		logoutTB.setBackground(customColor);
		topPanel.add(logoutTB);

		// ---- Search field & buttons ----
		txtTimKiem = new JTextField();
		txtTimKiem.setFont(new Font("Dialog", Font.PLAIN, 16));
		txtTimKiem.setBounds(871,99,214,30);
		contentPane.add(txtTimKiem);

		btntimkiem = new JButton(new ImageIcon(view_QuanLyNhanVien.class.getResource("/image/search.png")));
		btntimkiem.setBounds(1090,99,40,30);
		contentPane.add(btntimkiem);

		btnXem   = new JButton("Xem");
		btnXem.setEnabled(false);
		btnLamMoi= new JButton("Làm Mới");
		btnXem.setBounds(34,101,100,30);
		btnLamMoi.setBounds(174,101,100,30);
		contentPane.add(btnXem);
		contentPane.add(btnLamMoi);

		// ---- Table setup ----
		String[] col = { "Mã Hóa Đơn","Tên Khách Hàng","Tổng Tiền","Ngày" };
		tableModel = new DefaultTableModel(col,0);
		table = new JTable(tableModel);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(table.getSelectedRow()>=0) btnXem.setEnabled(true);
			}
		});
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(0,140,1150,469);
		contentPane.add(scroll);

		// ---- Event wiring ----
		btnXem.addActionListener(this);
		btnLamMoi.addActionListener(this);
		btntimkiem.addActionListener(this);

		loadData();
	}

	private void loadData() {
		tableModel.setRowCount(0);
		Locale localVN = new Locale("vi", "VN");
		NumberFormat fmt = NumberFormat.getCurrencyInstance(localVN);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		try {
			Request req = new Request("GET_ORDERS", "");
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				// Use fromJsonList to deserialize the JSON data
				List<Order> orders = ProtocolUtils.fromJsonList(res.getData(), Order.class);
				for (Order hd : orders) {
					tableModel.addRow(new Object[]{
							hd.getId(),
							hd.getKhachHang().getTenKH(),
							fmt.format(hd.getTotalPrice()),
							sdf.format(hd.getDate())
					});
				}
			} else {
				JOptionPane.showMessageDialog(this,
						"Lỗi khi tải hóa đơn: " + res.getErrorMsg(),
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this,
					"Không thể kết nối server: " + ex.getMessage(),
					"Connection Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void filterData() {
		String kw = txtTimKiem.getText().trim().toLowerCase();
		for(int i=tableModel.getRowCount()-1; i>=0; i--){
			String id   = tableModel.getValueAt(i,0).toString().toLowerCase();
			String name = tableModel.getValueAt(i,1).toString().toLowerCase();
			if(!id.contains(kw) && !name.contains(kw)) {
				tableModel.removeRow(i);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnXem) {
			int i = table.getSelectedRow();
			if (i >= 0) {
				view = new view_dialogXemHD(this); // Pass 'this' as the Frame argument
				String ma = tableModel.getValueAt(i, 0).toString();
				String total = tableModel.getValueAt(i, 2).toString();
				view.refreshData(ma, null); // Pass null or the appropriate data for details
				view.setVisible(true);
			}
		}
		else if (e.getSource()==btnLamMoi) {
			loadData();
			btnXem.setEnabled(false);
			txtTimKiem.setText("");
		}
		else if (e.getSource()==btntimkiem) {
			filterData();
		}
	}

	private void initComponents() {
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
}
