package view.QuanLy;

import socket.Request;
import socket.Response;
import entity.Product;
import socket.ClientSocket;
import runapp.Login;
import testbutton.Buttontest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

public class view_QuanLyHangHoa extends JFrame implements ActionListener {
	private String tempId;
	private List<Product> allItems;
	private JPanel contentPane;
	private JButton btnThem, btnXoa, btnSua, btnLamMoi, btntimkiem;
	private JTextField txtId, txtName, txtPrice, txtTimKiem;
	private JTable table;
	private DefaultTableModel tableModel;
	private final Color customColor = new Color(255,255,255,0);
	private final Color whiteColor  = new Color(255,255,255,0);
	private final ObjectMapper mapper = new ObjectMapper();

	public static void main(String[] args) {
		try { for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(info.getName())) { UIManager.setLookAndFeel(info.getClassName()); break; }
		}
		} catch (Exception ex) { ex.printStackTrace(); }
		SwingUtilities.invokeLater(() -> new view_QuanLyHangHoa().setVisible(true));
	}

	public view_QuanLyHangHoa() {
		initUI();
		loadData();
		refresh();
	}

	private void initUI() {
		setResizable(false);
		setTitle("Giao Diện Quản Lý Hàng Hóa");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100,100,1168,650);
		contentPane = new JPanel(null);
		setContentPane(contentPane);

		// Avatar + user
		JLabel lblNvIcon = new JLabel(new ImageIcon(getClass().getResource("/image/avt.png")));
		lblNvIcon.setBounds(760,5,40,40); contentPane.add(lblNvIcon);
		JLabel lblQL = new JLabel("QL:"); lblQL.setForeground(Color.WHITE);
		lblQL.setFont(new Font("Tahoma",Font.BOLD,16)); lblQL.setBounds(801,0,39,50);
		contentPane.add(lblQL);
		JLabel lblTen = new JLabel("Trương Đại Lộc"); lblTen.setForeground(Color.WHITE);
		lblTen.setFont(new Font("Tahoma",Font.BOLD,16)); lblTen.setBounds(832,0,238,50);
		contentPane.add(lblTen);

		// Top toolbar panel
		JPanel topPanel = new JPanel(null) {
			protected void paintComponent(Graphics g) {
				g.setColor(getBackground()); g.fillRect(0,0,getWidth(),getHeight()); super.paintComponent(g);
			}
		};
		topPanel.setOpaque(false); topPanel.setBounds(0,0,1168,50);
		contentPane.add(topPanel);

		// Add toolbars
		addToolbar(topPanel,"Hàng hóa",new Color(255,128,64),e->{ });
		addToolbar(topPanel,"Đặt Hàng",new Color(46,139,87),e->open(view_QuanLyKhachHang.class));
		addToolbar(topPanel,"Nhân Viên",new Color(255,0,128),e->open(view_QuanLyNhanVien.class));
		addToolbar(topPanel,"Tài Khoản",new Color(99,176,28),e->open(view_QuanLyTaiKhoan.class));
		addToolbar(topPanel,"Thống Kê",new Color(100,100,255),e->open(view_ThongKeDoanhThu.class));
		addLogout(topPanel);

		// Title label
		JLabel lblTitle = new JLabel("Quản Lý Hàng Hóa");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Open Sans",Font.BOLD,16));
		lblTitle.setBounds(43,102,170,20);
		contentPane.add(lblTitle);

		// Form fields
		createLabel(contentPane,"Mã HH:",17,139,100,21);
		txtId = new JTextField(); txtId.setEnabled(false);
		txtId.setFont(new Font("Open Sans",Font.PLAIN,16)); txtId.setBounds(17,161,230,30);
		contentPane.add(txtId);

		createLabel(contentPane,"Tên HH:",17,209,100,21);
		txtName = new JTextField(); txtName.setFont(new Font("Open Sans",Font.PLAIN,16));
		txtName.setBounds(17,231,230,30); contentPane.add(txtName);

		createLabel(contentPane,"Giá:",17,279,100,21);
		txtPrice = new JTextField(); txtPrice.setFont(new Font("Open Sans",Font.PLAIN,16));
		txtPrice.setBounds(17,301,230,30); contentPane.add(txtPrice);

		txtTimKiem = new JTextField(); txtTimKiem.setFont(new Font("Open Sans",Font.PLAIN,16));
		txtTimKiem.setBounds(871,99,214,30); contentPane.add(txtTimKiem);
		btntimkiem = new JButton(new ImageIcon(getClass().getResource("/image/search.png")));
		btntimkiem.setBounds(1090,99,40,30); contentPane.add(btntimkiem);

		// Buttons
		btnThem = createButton("Thêm",250,99);
		btnXoa = createButton("Xóa",360,99);
		btnSua = createButton("Sửa",470,99);
		btnLamMoi = createButton("Làm mới",580,99);
		for (JButton b: new JButton[]{btnThem,btnXoa,btnSua,btnLamMoi,btntimkiem}){ b.addActionListener(this); contentPane.add(b);}

		// Table
		tableModel = new DefaultTableModel(new String[]{"Mã HH","Tên HH","Giá"},0);
		table = new JTable(tableModel);
		table.addMouseListener(new MouseAdapter(){ public void mouseClicked(MouseEvent e){
			int r = table.getSelectedRow();
			tempId = tableModel.getValueAt(r,0).toString();
			txtId.setText(tempId);
			txtName.setText(tableModel.getValueAt(r,1).toString());
			txtPrice.setText(tableModel.getValueAt(r,2).toString());
			btnSua.setEnabled(true); btnXoa.setEnabled(true);
		}});
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(250,140,900,469);
		contentPane.add(scroll);

		// Background
		JLabel bg = new JLabel(new ImageIcon(getClass().getResource("/image/bgCF.jpg")));
		bg.setBounds(0,0,1162,613); contentPane.add(bg);
	}

	private void loadData(){
		tableModel.setRowCount(0);
		try {
			Response resp = ClientSocket.sendRequest(new Request("GET_PRODUCTS",null));
			List<Product> list = mapper.convertValue(
					resp.getData(),
					mapper.getTypeFactory().constructCollectionType(List.class,Product.class)
			);
			allItems = list;
			for(Product p:allItems){
				tableModel.addRow(new Object[]{p.getId(),p.getName(),p.getPrice()});
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this,"Lỗi tải dữ liệu: "+ex.getMessage(),"Lỗi",JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e){
		try{
			if(e.getSource()==btnThem && !txtName.getText().trim().isEmpty()){
				Product p = new Product();
				p.setName(txtName.getText().trim());
				p.setPrice(Double.parseDouble(txtPrice.getText().trim()));
				String payload = mapper.writeValueAsString(p);
				ClientSocket.sendRequest(new Request("CREATE_PRODUCT",payload));
				JOptionPane.showMessageDialog(this,"Thêm thành công!"); loadData();
			} else if(e.getSource()==btnSua){
				Product p = new Product();
				p.setId(tempId);
				p.setName(txtName.getText().trim());
				p.setPrice(Double.parseDouble(txtPrice.getText().trim()));
				String payload = mapper.writeValueAsString(p);
				ClientSocket.sendRequest(new Request("UPDATE_PRODUCT",payload));
				JOptionPane.showMessageDialog(this,"Sửa thành công!"); loadData();
			} else if(e.getSource()==btnXoa){
				ClientSocket.sendRequest(new Request("DELETE_PRODUCT",tempId));
				JOptionPane.showMessageDialog(this,"Xóa thành công!"); loadData();
			} else if(e.getSource()==btntimkiem){
				loadData(); // or client-side filter
			} else if(e.getSource()==btnLamMoi){
				loadData();
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this,"Lỗi: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
		refresh();
	}

	private void refresh(){
		txtId.setText(""); txtName.setText(""); txtPrice.setText(""); txtTimKiem.setText("");
		btnSua.setEnabled(false); btnXoa.setEnabled(false); btnThem.setEnabled(true);
	}

	// helpers
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

	private <T extends JFrame> void open(Class<T>cls){ try{T f=cls.getDeclaredConstructor().newInstance();f.setLocationRelativeTo(null);f.setVisible(true);dispose();}catch(Exception ex){ex.printStackTrace();}}

	private JLabel createLabel(JPanel p,String t,int x,int y,int w,int h){JLabel l=new JLabel(t);l.setForeground(Color.WHITE);l.setFont(new Font("Dialog",Font.PLAIN,16));l.setBounds(x,y,w,h);p.add(l);return l;}
	private JButton createButton(String t,int x,int y){JButton b=new JButton(t);b.setFont(new Font("Tahoma",Font.PLAIN,14));b.setBounds(x,y,100,30);return b;}
}
