package view.QuanLy;

import socket.Request;
import socket.Response;
import entity.Customer;
import socket.ClientSocket;
import runapp.Login;
import testbutton.Buttontest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

public class view_QuanLyKhachHang extends JFrame implements ActionListener {
	private String tempId;
	private List<Customer> allCustomers;
	private JPanel contentPane;
	private JButton btnThem, btnXoa, btnSua, btnLamMoi, btntimkiem;
	private JTextField txtId, txtHoTen, txtDiaChi, txtSDT, txtTimKiem;
	private JTable table;
	private DefaultTableModel tableModel;
	private final Color customColor = new Color(255,255,255,0);
	private final Color whiteColor  = new Color(255,255,255,0);
	private final ObjectMapper mapper = new ObjectMapper();

	public static void main(String[] args) {
		try {
			for(UIManager.LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()){
				if("Nimbus".equals(info.getName())){
					UIManager.setLookAndFeel(info.getClassName()); break;
				}
			}
		}catch(Exception ex){ex.printStackTrace();}
		SwingUtilities.invokeLater(() -> new view_QuanLyKhachHang().setVisible(true));
	}

	public view_QuanLyKhachHang() {
		initUI();
		loadData();
		refresh();
	}

	private void initUI() {
		setResizable(false);
		setTitle("Giao Diện Quản Lý Khách Hàng");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100,100,1168,650);
		contentPane = new JPanel(null);
		setContentPane(contentPane);

		// Avatar và tên người dùng
		JLabel lblNvIcon = new JLabel(new ImageIcon(getClass().getResource("/image/avt.png")));
		lblNvIcon.setBounds(760,5,40,40); contentPane.add(lblNvIcon);
		JLabel lblQL = new JLabel("QL:"); lblQL.setForeground(Color.WHITE);
		lblQL.setFont(new Font("Tahoma",Font.BOLD,16)); lblQL.setBounds(801,0,39,50);
		contentPane.add(lblQL);
		JLabel lblTenNV = new JLabel("Trương Đại Lộc"); lblTenNV.setForeground(Color.WHITE);
		lblTenNV.setFont(new Font("Tahoma",Font.BOLD,16)); lblTenNV.setBounds(832,0,238,50);
		contentPane.add(lblTenNV);

		// Panel toolbar trên
		JPanel topPanel = new JPanel(null) {
			protected void paintComponent(Graphics g) {
				g.setColor(getBackground()); g.fillRect(0,0,getWidth(),getHeight()); super.paintComponent(g);
			}
		};
		topPanel.setOpaque(false); topPanel.setBounds(0,0,1168,50);
		contentPane.add(topPanel);

		// Các nút toolbar
		addToolbar(topPanel, "Hàng hóa", new Color(255,128,64), e->open(view_QuanLyHangHoa.class));
		addToolbar(topPanel, "Đặt Hàng", new Color(46,139,87), e->open(view_QuanLyBan.class));
		addToolbar(topPanel, "Nhân Viên", new Color(255,0,128), e->open(view_QuanLyNhanVien.class));
		addToolbar(topPanel, "Tài Khoản", new Color(99,176,28), e->open(view_QuanLyTaiKhoan.class));
		addToolbar(topPanel, "Thống Kê", new Color(100,100,255), e->open(view_ThongKeDoanhThu.class));
		addLogout(topPanel);

		// Tiêu đề
		JLabel lblTitle = new JLabel("Quản Lý Khách Hàng");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Open Sans",Font.BOLD,16));
		lblTitle.setBounds(43,102,170,20);
		contentPane.add(lblTitle);

		// Form nhập liệu
		createLabel(contentPane, "Mã KH:", 17,139,100,21);
		txtId = new JTextField(); txtId.setEnabled(false);
		txtId.setFont(new Font("Open Sans",Font.PLAIN,16)); txtId.setBounds(17,161,230,30);
		contentPane.add(txtId);

		createLabel(contentPane, "Họ tên:", 17,209,100,21);
		txtHoTen = new JTextField(); txtHoTen.setFont(new Font("Open Sans",Font.PLAIN,16));
		txtHoTen.setBounds(17,231,230,30); contentPane.add(txtHoTen);

		createLabel(contentPane, "Địa chỉ:", 17,279,100,21);
		txtDiaChi = new JTextField(); txtDiaChi.setFont(new Font("Open Sans",Font.PLAIN,16));
		txtDiaChi.setBounds(17,301,230,30); contentPane.add(txtDiaChi);

		createLabel(contentPane, "SĐT:", 17,349,100,21);
		txtSDT = new JTextField(); txtSDT.setFont(new Font("Open Sans",Font.PLAIN,16));
		txtSDT.setBounds(17,371,230,30); contentPane.add(txtSDT);

		txtTimKiem = new JTextField(); txtTimKiem.setFont(new Font("Open Sans",Font.PLAIN,16));
		txtTimKiem.setBounds(871,99,214,30); contentPane.add(txtTimKiem);
		btntimkiem = new JButton(new ImageIcon(getClass().getResource("/image/search.png")));
		btntimkiem.setBounds(1090,99,40,30); contentPane.add(btntimkiem);

		// Các nút thao tác
		btnThem = createButton("Thêm",250,99);
		btnXoa = createButton("Xóa",360,99);
		btnSua = createButton("Sửa",470,99);
		btnLamMoi = createButton("Làm mới",580,99);
		for(JButton b: new JButton[]{btnThem,btnXoa,btnSua,btnLamMoi,btntimkiem}){
			b.addActionListener(this);
			contentPane.add(b);
		}

		// Bảng dữ liệu
		tableModel = new DefaultTableModel(new String[]{"Mã KH","Họ tên","Địa chỉ","SĐT"},0);
		table = new JTable(tableModel);
		table.addMouseListener(new MouseAdapter(){ public void mouseClicked(MouseEvent e){
			int r = table.getSelectedRow();
			tempId = tableModel.getValueAt(r,0).toString();
			txtId.setText(tempId);
			txtHoTen.setText(tableModel.getValueAt(r,1).toString());
			txtDiaChi.setText(tableModel.getValueAt(r,2).toString());
			txtSDT.setText(tableModel.getValueAt(r,3).toString());
			btnSua.setEnabled(true); btnXoa.setEnabled(true);
		}});
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(250,140,900,469);
		contentPane.add(scroll);

		// Nền
		JLabel bg = new JLabel(new ImageIcon(getClass().getResource("/image/bgCF.jpg")));
		bg.setBounds(0,0,1162,613);
		contentPane.add(bg);
	}

	private void loadData(){
		tableModel.setRowCount(0);
		try{
			Response resp = ClientSocket.sendRequest(new Request("GET_CUSTOMERS",null));
			List<Customer> list = mapper.convertValue(
					resp.getData(),
					mapper.getTypeFactory().constructCollectionType(List.class,Customer.class)
			);
			allCustomers = list;
			for(Customer c: allCustomers){
				tableModel.addRow(new Object[]{c.getMaKH(),c.getTenKH(),c.getDiaChi(),c.getSdt()});
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this,"Lỗi tải dữ liệu: "+ex.getMessage(),"Lỗi",JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e){
		try{
			if(e.getSource()==btnThem && !txtHoTen.getText().trim().isEmpty()){
				Customer c = new Customer();
				c.setTenKH(txtHoTen.getText().trim());
				c.setDiaChi(txtDiaChi.getText().trim());
				c.setSdt(txtSDT.getText().trim());
				String payload = mapper.writeValueAsString(c);
				ClientSocket.sendRequest(new Request("CREATE_CUSTOMER",payload));
				JOptionPane.showMessageDialog(this,"Thêm thành công!"); loadData();
			} else if(e.getSource()==btnSua){
				Customer c = new Customer();
				c.setMaKH(tempId);
				c.setTenKH(txtHoTen.getText().trim());
				c.setDiaChi(txtDiaChi.getText().trim());
				c.setSdt(txtSDT.getText().trim());
				String payload = mapper.writeValueAsString(c);
				ClientSocket.sendRequest(new Request("UPDATE_CUSTOMER",payload));
				JOptionPane.showMessageDialog(this,"Sửa thành công!"); loadData();
			} else if(e.getSource()==btnXoa){
				ClientSocket.sendRequest(new Request("DELETE_CUSTOMER",tempId));
				JOptionPane.showMessageDialog(this,"Xóa thành công!"); loadData();
			} else if(e.getSource()==btntimkiem){
				loadData(); // hoặc client-side filter
			} else if(e.getSource()==btnLamMoi){
				loadData();
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this,"Lỗi: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
		refresh();
	}

	private void refresh(){
		txtId.setText(""); txtHoTen.setText(""); txtDiaChi.setText(""); txtSDT.setText(""); txtTimKiem.setText("");
		btnSua.setEnabled(false); btnXoa.setEnabled(false); btnThem.setEnabled(true);
	}

	// helper
	private void addToolbar(JPanel p,String t,Color bg,ActionListener a){
		JToolBar tb=new JToolBar(); tb.setFloatable(false); tb.setMargin(new Insets(-5,-5,0,-5));
		Buttontest btn=new Buttontest(); btn.setText(t); btn.setFont(new Font("Open Sans",Font.BOLD,15));
		btn.setForeground(SystemColor.text); btn.setRippleColor(Color.WHITE); btn.setBackground(bg);
		btn.addActionListener(a); tb.add(btn); tb.setBackground(customColor); p.add(tb);
	}

	private void addLogout(JPanel p){
		JToolBar tb=new JToolBar(); tb.setFloatable(false); tb.setMargin(new Insets(-5,550,0,0));
		Buttontest btn=new Buttontest(); btn.setText("Đăng Xuất"); btn.setFont(new Font("Open Sans",Font.BOLD,15));
		btn.setForeground(SystemColor.text); btn.setRippleColor(Color.WHITE); btn.setBackground(new Color(226,110,110));
		btn.addActionListener(e->{ if(JOptionPane.showConfirmDialog(null,"Bạn có muốn đăng xuất?",null,JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){ new Login().setVisible(true); dispose(); }});
		tb.add(btn); tb.setBackground(customColor); p.add(tb);
	}

	private <T extends JFrame> void open(Class<T>cls){ try{ T f=cls.getDeclaredConstructor().newInstance(); f.setLocationRelativeTo(null); f.setVisible(true); dispose(); }catch(Exception ex){ ex.printStackTrace(); }}

	private JLabel createLabel(JPanel p,String t,int x,int y,int w,int h){ JLabel l=new JLabel(t); l.setForeground(Color.WHITE); l.setFont(new Font("Dialog",Font.PLAIN,16)); l.setBounds(x,y,w,h); p.add(l); return l; }
	private JButton createButton(String t,int x,int y){ JButton b=new JButton(t); b.setFont(new Font("Tahoma",Font.PLAIN,14)); b.setBounds(x,y,100,30); return b; }
}
