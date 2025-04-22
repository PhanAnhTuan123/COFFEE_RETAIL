package view.NhanVien;

import list.List_Ban;
import list.List_HangHoa;
import list.List_HoaDon;
import list.List_ChiTietHoaDon;
import entity.Tables;
import entity.Product;
import entity.Order;
import entity.OrderDetail;
import entity.Customer;
import entity.Employee;
import runapp.Login;
import testbutton.Buttontest;
import view.QuanLy.view_QuanLyNhanVien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

public class view_hoaDon extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable tableShowSP;
	private JTable table_chonSP;
	private DefaultTableModel tableModel;
	private DefaultTableModel tableModelSanPham;

	private JTextField txtMaHD;
	private JTextField txtTimKiem;
	private JTextField txtKhachHang;
	private JTextField txtTien;
	private JTextField txtChietKhau;
	private JTextField txtTongTien;
	private JTextField txtSoLuong;
	private JTextField txtMaHangHoa;

	private JButton btnThem;
	private JButton btnXoa;
	private JButton btnSua;
	private JButton btntimkiem;
	private JButton btnTimKhachHang;
	private Buttontest btnDatHang;
	private Buttontest btnHuy;
	private Buttontest btnTaoMoi;

	public JLabel lbltennv;
	private JLabel lblNvIcon;

	private JPanel panelDatHang;

	private Color customColor = new Color(255, 255, 255, 0);
	private Color whiteColor = new Color(255, 255, 255, 0);

	private Employee tempNV;
	private Tables tempBan;
	private Customer tempKH;

	private List_Ban list_Ban = new List_Ban();
	private List_HangHoa list_hangHoa = new List_HangHoa();
	private List_HoaDon listHD = new List_HoaDon();
	private List_ChiTietHoaDon listChiTietHD = new List_ChiTietHoaDon();
	private view_showKhachHang view_showKH;
	private view_showBan viewBan;

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
			try {
				view_hoaDon frame = new view_hoaDon();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public view_hoaDon() throws Exception {
		tempNV = new Employee();
		tempNV.setMaNV("NV001");

		view_showKH = new view_showKhachHang();

		setTitle("Giao Diện Tạo Hóa Đơn");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 1168, 650);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Top bar
		lblNvIcon = new JLabel(new ImageIcon(view_QuanLyNhanVien.class.getResource("/image/avt.png")));
		lblNvIcon.setBounds(760, 5, 40, 40);
		contentPane.add(lblNvIcon);

		JLabel lblNhanVien = new JLabel("NV:");
		lblNhanVien.setForeground(Color.WHITE);
		lblNhanVien.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNhanVien.setBounds(801, 0, 39, 50);
		contentPane.add(lblNhanVien);

		lbltennv = new JLabel();
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

		// Đặt Hàng toolbar
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
		panelDatHang.setBackground(whiteColor);
		panelDatHang.setVisible(false);
		contentPane.add(panelDatHang);

		JButton btnTaoHoaDon = new JButton("Tạo Hóa Đơn");
		btnTaoHoaDon.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnTaoHoaDon.addActionListener(e -> {
			try {
				view_hoaDon f = new view_hoaDon();
				f.lbltennv.setText(lbltennv.getText());
				f.setVisible(true);
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
				view_xemHD f = new view_xemHD();
				f.lbltennv.setText(lbltennv.getText());
				f.setVisible(true);
				dispose();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		panelDatHang.add(btnXemHoaDon);

		// Logout toolbar
		JToolBar logoutToolBar = new JToolBar();
		logoutToolBar.setFloatable(false);
		logoutToolBar.setMargin(new Insets(-5,550,0,0));
		Buttontest logoutButton = new Buttontest();
		logoutButton.setText("Đăng Xuất");
		logoutButton.setFont(new Font("Open Sans", Font.BOLD, 15));
		logoutButton.setForeground(SystemColor.text);
		logoutButton.setRippleColor(Color.WHITE);
		logoutButton.setBackground(new Color(226,110,110));
		logoutButton.addActionListener(e -> {
			if (JOptionPane.showConfirmDialog(null,"Bạn có muốn đăng xuất!",null,JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
				try {
					new Login().setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				dispose();
			}
		});
		logoutToolBar.add(logoutButton);
		logoutToolBar.setBackground(customColor);
		topPanel.add(logoutToolBar);

		// Labels, textfields and buttons
		JLabel lblQLKH = new JLabel("Quản Lý Hóa Đơn");
		lblQLKH.setForeground(Color.WHITE);
		lblQLKH.setFont(new Font("Open Sans", Font.BOLD, 16));
		lblQLKH.setBounds(43,102,170,20);
		contentPane.add(lblQLKH);

		JLabel lblMaHD = new JLabel("Mã Hóa Đơn:");
		lblMaHD.setForeground(Color.WHITE);
		lblMaHD.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblMaHD.setBounds(10,132,130,21);
		contentPane.add(lblMaHD);

		JPanel pnlHoTen = new JPanel();
		pnlHoTen.setOpaque(false);
		pnlHoTen.setBounds(10,161,230,37);
		contentPane.add(pnlHoTen);

		txtMaHD = new JTextField();
		txtMaHD.setFont(new Font("Open Sans", Font.PLAIN, 16));
		txtMaHD.setEnabled(false);
		txtMaHD.setColumns(16);
		pnlHoTen.add(txtMaHD);

		txtTimKiem = new JTextField();
		txtTimKiem.setFont(new Font("Dialog", Font.PLAIN, 16));
		txtTimKiem.setBounds(886,128,214,30);
		contentPane.add(txtTimKiem);

		btntimkiem = new JButton(new ImageIcon(view_QuanLyNhanVien.class.getResource("/image/search.png")));
		btntimkiem.setBounds(1110,128,40,30);
		contentPane.add(btntimkiem);

		btnThem = new JButton("Thêm");
		btnThem.setBackground(new Color(50,205,50));
		btnThem.setBounds(610,305,100,30);
		contentPane.add(btnThem);

		btnXoa = new JButton("Xóa");
		btnXoa.setBackground(new Color(255,140,0));
		btnXoa.setBounds(732,305,100,30);
		contentPane.add(btnXoa);

		btnSua = new JButton("Sửa");
		btnSua.setBackground(new Color(0,255,255));
		btnSua.setBounds(857,305,100,30);
		contentPane.add(btnSua);

		tableModel = new DefaultTableModel(new String[]{"Mã","Tên","Giá","Số Lượng","Thành Tiền"},0);
		tableShowSP = new JTable(tableModel);
		tableShowSP.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableShowSP.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				int r = tableShowSP.getSelectedRow();
				if(r>=0){
					btnXoa.setEnabled(true);
					btnThem.setEnabled(false);
					btnSua.setEnabled(true);
					txtMaHangHoa.setText(tableModel.getValueAt(r,0).toString());
					txtSoLuong.setText(tableModel.getValueAt(r,3).toString());
				}
			}
		});
		JScrollPane scrollPane = new JScrollPane(tableShowSP);
		scrollPane.setBounds(250,347,900,169);
		contentPane.add(scrollPane);

		tableModelSanPham = new DefaultTableModel(new String[]{"Mã","Tên","Giá"},0);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(250,161,900,136);
		table_chonSP = new JTable(tableModelSanPham);
		table_chonSP.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				int n = table_chonSP.getSelectedRow();
				btnThem.setEnabled(true);
				txtMaHangHoa.setText(tableModelSanPham.getValueAt(n,0).toString());
			}
		});
		scrollPane.setViewportView(table_chonSP);
		contentPane.add(scrollPane);

		JLabel lblMKh = new JLabel("Mã Khách Hàng");
		lblMKh.setForeground(Color.WHITE);
		lblMKh.setFont(new Font("Dialog",Font.PLAIN,16));
		lblMKh.setBounds(10,215,130,21);
		contentPane.add(lblMKh);

		txtKhachHang = new JTextField();
		txtKhachHang.setEnabled(false);
		txtKhachHang.setFont(new Font("Dialog",Font.PLAIN,16));
		txtKhachHang.setBounds(20,246,161,27);
		contentPane.add(txtKhachHang);

		JLabel lblTien = new JLabel("Tổng Tiền:");
		lblTien.setForeground(Color.WHITE);
		lblTien.setFont(new Font("Dialog",Font.PLAIN,16));
		lblTien.setBounds(10,313,130,21);
		contentPane.add(lblTien);

		txtTien = new JTextField();
		txtTien.setEnabled(false);
		txtTien.setFont(new Font("Dialog",Font.PLAIN,16));
		txtTien.setBounds(20,344,220,27);
		contentPane.add(txtTien);

		JLabel lblCK = new JLabel("Chiết Khấu");
		lblCK.setForeground(Color.WHITE);
		lblCK.setFont(new Font("Dialog",Font.PLAIN,16));
		lblCK.setBounds(10,405,130,21);
		contentPane.add(lblCK);

		txtChietKhau = new JTextField();
		txtChietKhau.setEnabled(false);
		txtChietKhau.setFont(new Font("Dialog",Font.PLAIN,16));
		txtChietKhau.setBounds(20,436,220,27);
		contentPane.add(txtChietKhau);

		JLabel lblTong = new JLabel("Tổng Tiền:");
		lblTong.setForeground(Color.WHITE);
		lblTong.setFont(new Font("Dialog",Font.PLAIN,16));
		lblTong.setBounds(10,506,130,21);
		contentPane.add(lblTong);

		txtTongTien = new JTextField();
		txtTongTien.setEnabled(false);
		txtTongTien.setFont(new Font("Dialog",Font.PLAIN,16));
		txtTongTien.setBounds(20,539,220,27);
		contentPane.add(txtTongTien);

		btnDatHang = new Buttontest();
		btnDatHang.setText("Đặt Hàng");
		btnDatHang.setRippleColor(Color.WHITE);
		btnDatHang.setForeground(SystemColor.text);
		btnDatHang.setFont(new Font("Dialog",Font.BOLD,15));
		btnDatHang.setBackground(new Color(46,139,87));
		btnDatHang.setBounds(986,528,144,56);
		contentPane.add(btnDatHang);

		btnHuy = new Buttontest();
		btnHuy.setText("Hủy Bỏ");
		btnHuy.setRippleColor(Color.WHITE);
		btnHuy.setForeground(SystemColor.text);
		btnHuy.setFont(new Font("Dialog",Font.BOLD,15));
		btnHuy.setBackground(new Color(255,0,0));
		btnHuy.setBounds(696,528,144,56);
		contentPane.add(btnHuy);

		btnTaoMoi = new Buttontest();
		btnTaoMoi.setText("Tạo Mới");
		btnTaoMoi.setRippleColor(Color.WHITE);
		btnTaoMoi.setForeground(SystemColor.text);
		btnTaoMoi.setFont(new Font("Dialog",Font.BOLD,15));
		btnTaoMoi.setBackground(new Color(0,255,255));
		btnTaoMoi.setBounds(842,528,144,56);
		contentPane.add(btnTaoMoi);

		btnTimKhachHang = new JButton(new ImageIcon(view_QuanLyNhanVien.class.getResource("/image/search.png")));
		btnTimKhachHang.setBounds(191,246,40,30);
		contentPane.add(btnTimKhachHang);

		JLabel lblSoLuong = new JLabel("Số Lượng:");
		lblSoLuong.setForeground(Color.WHITE);
		lblSoLuong.setFont(new Font("Dialog",Font.PLAIN,16));
		lblSoLuong.setBounds(421,307,88,21);
		contentPane.add(lblSoLuong);

		txtSoLuong = new JTextField();
		txtSoLuong.setFont(new Font("Dialog",Font.PLAIN,16));
		txtSoLuong.setBounds(504,304,63,27);
		contentPane.add(txtSoLuong);

		txtMaHangHoa = new JTextField();
		txtMaHangHoa.setEnabled(false);
		txtMaHangHoa.setFont(new Font("Dialog",Font.PLAIN,16));
		txtMaHangHoa.setBounds(335,304,76,27);
		contentPane.add(txtMaHangHoa);

		// Action listeners
		btnThem.addActionListener(this);
		btnXoa.addActionListener(this);
		btnSua.addActionListener(this);
		btnTaoMoi.addActionListener(this);
		btnDatHang.addActionListener(this);
		btnHuy.addActionListener(this);
		btntimkiem.addActionListener(this);
		btnTimKhachHang.addActionListener(this);

		loadData();
		refresh();
		updateTongTien();
		setHD();
	}

	private void loadData() {
		try {
			List<Product> danhSachHang = list_hangHoa.getAll(); // nếu DAO trả về Product
			tableModelSanPham.setRowCount(0);
			for (Product hh : danhSachHang) {
				tableModelSanPham.addRow(new Object[]{
						hh.getId(),
						hh.getName(),
						hh.getPrice()
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu hàng hóa!");
		}
	}


	void refresh() {
		btnThem.setEnabled(false);
		btnSua.setEnabled(false);
		btnXoa.setEnabled(false);
		txtSoLuong.setText("");
		txtMaHangHoa.setText("");
		txtTimKiem.setText("");
	}

	private void setHD() {
		try {
			String ma = listHD.sinhMa();
			txtMaHD.setText(ma);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateTongTien() {
		int n = tableModel.getRowCount();
		double sum = 0;
		for (int i = 0; i < n; i++) {
			sum += (Double) tableModel.getValueAt(i, 4);
		}
		NumberFormat fmt = NumberFormat.getCurrencyInstance(new Locale("vi","VN"));
		txtTien.setText(fmt.format(sum));
		txtChietKhau.setText(fmt.format(sum/100));
		txtTongTien.setText(fmt.format(sum + sum/100));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == btnThem) {
				if (txtSoLuong.getText().isEmpty() || txtMaHangHoa.getText().isEmpty()) {
					JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin sản phẩm!");
					return;
				}
				int qty = Integer.parseInt(txtSoLuong.getText());
				if (qty <= 0) {
					JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
					return;
				}
				Product hh = list_hangHoa.get(txtMaHangHoa.getText());
				if (hh == null) {
					JOptionPane.showMessageDialog(this, "Sản phẩm không tồn tại!");
					return;
				}
				tableModel.addRow(new Object[]{
						hh.getId(), hh.getName(), hh.getPrice(), qty, hh.getPrice() * qty
				});
				refresh();
				updateTongTien();
			}

			if (e.getSource() == btnXoa) {
				int r = tableShowSP.getSelectedRow();
				if (r >= 0) {
					tableModel.removeRow(r);
					refresh();
					updateTongTien();
				} else {
					JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xóa!");
				}
			}

			if (e.getSource() == btnSua) {
				int r = tableShowSP.getSelectedRow();
				if (r >= 0) {
					if (txtSoLuong.getText().isEmpty()) {
						JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng!");
						return;
					}
					int qty = Integer.parseInt(txtSoLuong.getText());
					if (qty <= 0) {
						JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
						return;
					}
					double price = (Double) tableModel.getValueAt(r, 2);
					tableModel.setValueAt(qty, r, 3);
					tableModel.setValueAt(price * qty, r, 4);
					refresh();
					updateTongTien();
				} else {
					JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để sửa!");
				}
			}

			if (e.getSource() == btnTaoMoi) {
				tableModel.setRowCount(0);
				updateTongTien();
			}

			if (e.getSource() == btnHuy) {
				if (JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn hủy bỏ?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					tableModel.setRowCount(0);
					updateTongTien();
				}
			}

			if (e.getSource() == btnDatHang) {
				if (txtKhachHang.getText().isEmpty()) {
					JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng trước!");
					showViewKH();
					return;
				}

				try {
					LocalDateTime currentDateTime = LocalDateTime.now();
					Customer khachHang = tempKH; // Ensure tempKH is set when selecting a customer
					if (khachHang == null) {
						JOptionPane.showMessageDialog(this, "Khách hàng không hợp lệ!");
						return;
					}

					// Create the Order object with the required arguments
					Order hd = new Order(txtMaHD.getText(), currentDateTime, 0.0, khachHang);
					listHD.save(hd);

					for (int i = 0; i < tableModel.getRowCount(); i++) {
						int soLuong = (Integer) tableModel.getValueAt(i, 3);
						Product hh = list_hangHoa.get((String) tableModel.getValueAt(i, 0));
						if (hh == null) {
							JOptionPane.showMessageDialog(this, "Sản phẩm không tồn tại!");
							return;
						}
						double price = (Double) tableModel.getValueAt(i, 2);

						// Create OrderDetail object
						OrderDetail ct = new OrderDetail(
								hd.getId(),                // orderId
								hh.getId(),                // productId
								soLuong,                   // quantity
								price * soLuong            // subtotal
						);
						ct.setOrder(hd);               // Set the Order object
						ct.setProduct(hh);             // Set the Product object

						listChiTietHD.save(ct);
					}

					JOptionPane.showMessageDialog(this, "Tạo hóa đơn thành công!");
					tableModel.setRowCount(0);
					setHD();
					updateTongTien();
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + ex.getMessage());
				}
			}

			if (e.getSource() == btnTimKhachHang) {
				showViewKH();
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ cho số lượng!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + ex.getMessage());
		}
	}

	private void showViewKH() {
		try {
			view_showKH = new view_showKhachHang();
			view_showKH.setView(this);
			view_showKH.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Methods called by view_showKhachHang and view_showBan:
	public void submitMaKH(String maKH) {
		this.tempKH = new Customer();
		tempKH.setMaKH(maKH);
		txtKhachHang.setText(maKH);
	}

	public void setTempBan(Tables b) {
		
	}

	public void setTempKH(Customer kh) {
	}

	public void tienHanhDatHang() {
	}

	public void refreshAfterDatHang() {
	}
}
