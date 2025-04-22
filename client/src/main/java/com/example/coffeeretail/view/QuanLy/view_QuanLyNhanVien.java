package view.QuanLy;

import socket.Request;
import socket.Response;
import entity.Employee;
import socket.ClientSocket;
import runapp.Login;
import testbutton.Buttontest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

public class view_QuanLyNhanVien extends JFrame implements ActionListener {
	private String tempId;
	private List<Employee> allEmployees;
	private final ObjectMapper mapper = new ObjectMapper();

	// Fields for form components and action buttons
	private JTextField txtId, txtHoTen, txtDiaChi, txtSDT, txtTimKiem;
	private JRadioButton rdbtnNam, rdbtnNu;
	private JComboBox<String> cboxChucVu;
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton btnThem, btnXoa, btnSua, btnLamMoi, btntimkiem;

	private final Color transparent = new Color(255,255,255,0);

	public static void main(String[] args) {
		try {
			for(UIManager.LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()){
				if("Nimbus".equals(info.getName())){
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch(Exception ignored) {}
		SwingUtilities.invokeLater(() -> new view_QuanLyNhanVien().setVisible(true));
	}

	public view_QuanLyNhanVien() {
		initUI();
		loadData();
		refresh();
	}

	private void initUI() {
		setTitle("Giao Diện Quản Lý Nhân Viên");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100,100,1168,650);
		JPanel content = new JPanel(null);
		setContentPane(content);
		content.setBackground(Color.WHITE);

		// Header
		JLabel icon = new JLabel(new ImageIcon(getClass().getResource("/image/avt.png")));
		icon.setBounds(760,5,40,40);
		content.add(icon);
		JLabel ql = new JLabel("QL:");
		ql.setForeground(Color.WHITE);
		ql.setFont(new Font("Tahoma",Font.BOLD,16));
		ql.setBounds(801,0,39,50);
		content.add(ql);
		JLabel lblName = new JLabel("Trương Đại Lộc");
		lblName.setForeground(Color.WHITE);
		lblName.setFont(new Font("Tahoma",Font.BOLD,16));
		lblName.setBounds(832,0,238,50);
		content.add(lblName);

		// Toolbar
		JPanel top = new JPanel(null) {
			protected void paintComponent(Graphics g) {
				g.setColor(getBackground());
				g.fillRect(0,0,getWidth(),getHeight());
				super.paintComponent(g);
			}
		};
		top.setBounds(0,0,1168,50);
		top.setOpaque(false);
		content.add(top);
		addToolbar(top,"Hàng hóa",new Color(255,128,64), e->open(view_QuanLyHangHoa.class));
		addToolbar(top,"Đặt Hàng",new Color(46,139,87), e->open(view_QuanLyBan.class));
		addToolbar(top,"Khách Hàng",new Color(255,0,128), e->open(view_QuanLyKhachHang.class));
		addToolbar(top,"Tài Khoản",new Color(99,176,28), e->open(view_QuanLyTaiKhoan.class));
		addToolbar(top,"Thống Kê",new Color(100,100,255), e->open(view_ThongKeDoanhThu.class));
		addLogout(top);

		// Title
		JLabel title = new JLabel("Quản Lý Nhân Viên");
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Open Sans",Font.BOLD,16));
		title.setBounds(43,102,170,20);
		content.add(title);

		// Form
		createLabel(content,"Mã NV:",17,139,100,21);
		txtId = new JTextField(); txtId.setEnabled(false);
		txtId.setBounds(17,161,230,30); content.add(txtId);

		createLabel(content,"Họ tên:",17,209,100,21);
		txtHoTen = new JTextField(); txtHoTen.setBounds(17,231,230,30); content.add(txtHoTen);

		createLabel(content,"Địa chỉ:",17,279,100,21);
		txtDiaChi = new JTextField(); txtDiaChi.setBounds(17,301,230,30); content.add(txtDiaChi);

		createLabel(content,"SĐT:",17,329,100,21);
		txtSDT = new JTextField(); txtSDT.setBounds(17,351,230,30); content.add(txtSDT);

		// Gender
		rdbtnNam = new JRadioButton("Nam"); rdbtnNu = new JRadioButton("Nữ");
		rdbtnNam.setBounds(17,390,100,21); rdbtnNu.setBounds(120,390,100,21);
		content.add(rdbtnNam); content.add(rdbtnNu);
		ButtonGroup bg=new ButtonGroup(); bg.add(rdbtnNam); bg.add(rdbtnNu);

		// Role
		createLabel(content,"Chức vụ:",17,430,70,21);
		cboxChucVu = new JComboBox<>(new String[]{"Nhân Viên","Quản Lý"});
		cboxChucVu.setBounds(90,432,150,25); content.add(cboxChucVu);

		// Search
		txtTimKiem = new JTextField(); txtTimKiem.setBounds(871,99,214,30); content.add(txtTimKiem);
		JButton searchBtn = new JButton(new ImageIcon(getClass().getResource("/image/search.png")));
		searchBtn.setBounds(1090,99,40,30); content.add(searchBtn);
		btntimkiem = searchBtn;

		// Action buttons
		btnThem = createButton("Thêm",250,99); content.add(btnThem);
		btnXoa  = createButton("Xóa",360,99); content.add(btnXoa);
		btnSua  = createButton("Sửa",470,99); content.add(btnSua);
		btnLamMoi = createButton("Làm mới",580,99); content.add(btnLamMoi);
		// register listeners
		for(JButton btn: new JButton[]{btnThem,btnXoa,btnSua,btnLamMoi,btntimkiem}) btn.addActionListener(this);

		// Table
		tableModel = new DefaultTableModel(new String[]{"Mã NV","Họ tên","Địa chỉ","SĐT","Chức vụ","Giới tính"},0);
		table = new JTable(tableModel);
		table.addMouseListener(new MouseAdapter(){ public void mouseClicked(MouseEvent e){
			int r = table.getSelectedRow();
			tempId = tableModel.getValueAt(r,0).toString();
			txtId.setText(tempId);
			txtHoTen.setText(tableModel.getValueAt(r,1).toString());
			txtDiaChi.setText(tableModel.getValueAt(r,2).toString());
			txtSDT.setText(tableModel.getValueAt(r,3).toString());
			cboxChucVu.setSelectedItem(tableModel.getValueAt(r,4));
			String gt = tableModel.getValueAt(r,5).toString();
			rdbtnNam.setSelected(gt.equals("Nam")); rdbtnNu.setSelected(gt.equals("Nữ"));
			btnSua.setEnabled(true); btnXoa.setEnabled(true);
		}});
		JScrollPane sp=new JScrollPane(table);
		sp.setBounds(250,140,900,469); content.add(sp);

		// Background
		JLabel bgImg=new JLabel(new ImageIcon(getClass().getResource("/image/bgCF.jpg")));
		bgImg.setBounds(0,0,1162,613); content.add(bgImg);
	}

	private void loadData(){
		tableModel.setRowCount(0);
		try{
			Response resp = ClientSocket.sendRequest(new Request("GET_EMPLOYEES",null));
			allEmployees = mapper.convertValue(
					resp.getData(), mapper.getTypeFactory().constructCollectionType(List.class,Employee.class)
			);
			for(Employee e: allEmployees){
				String gt=e.getGioiTinh()?"Nam":"Nữ";
				tableModel.addRow(new Object[]{e.getMaNV(),e.getTenNV(),e.getDiaChi(),e.getSdt(),e.getChucVu(),gt});
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this,"Lỗi tải dữ liệu: " + ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e){
		try{
			if(e.getSource()==btnThem){
				Employee emp=new Employee();
				emp.setTenNV(txtHoTen.getText().trim());
				emp.setDiaChi(txtDiaChi.getText().trim());
				emp.setSdt(txtSDT.getText().trim());
				emp.setChucVu(cboxChucVu.getSelectedItem().toString());
				emp.setGioiTinh(rdbtnNam.isSelected());
				String payload = mapper.writeValueAsString(emp);
				ClientSocket.sendRequest(new Request("CREATE_EMPLOYEE",payload));
				JOptionPane.showMessageDialog(this,"Thêm thành công!");
			} else if(e.getSource()==btnSua){
				Employee emp=new Employee(); emp.setMaNV(tempId);
				emp.setTenNV(txtHoTen.getText().trim()); emp.setDiaChi(txtDiaChi.getText().trim());
				emp.setSdt(txtSDT.getText().trim()); emp.setChucVu(cboxChucVu.getSelectedItem().toString());
				emp.setGioiTinh(rdbtnNam.isSelected());
				String payload = mapper.writeValueAsString(emp);
				ClientSocket.sendRequest(new Request("UPDATE_EMPLOYEE",payload));
				JOptionPane.showMessageDialog(this,"Sửa thành công!");
			} else if(e.getSource()==btnXoa){
				ClientSocket.sendRequest(new Request("DELETE_EMPLOYEE",tempId));
				JOptionPane.showMessageDialog(this,"Xóa thành công!");
			}
			if(e.getSource()==btnLamMoi || e.getSource()==btntimkiem) loadData();
			else loadData();
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this,"Lỗi: " + ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
		refresh();
	}

	private void refresh(){
		txtId.setText(""); txtHoTen.setText(""); txtDiaChi.setText(""); txtSDT.setText(""); txtTimKiem.setText("");
		rdbtnNam.setSelected(true); cboxChucVu.setSelectedIndex(0);
		btnSua.setEnabled(false); btnXoa.setEnabled(false); btnThem.setEnabled(true);
	}

	// Helpers
	private void addToolbar(JPanel p,String text,Color bg,ActionListener a){
		JToolBar tb=new JToolBar(); tb.setFloatable(false); tb.setMargin(new Insets(-5,-5,0,-5));
		Buttontest btn=new Buttontest(); btn.setText(text); btn.setFont(new Font("Open Sans",Font.BOLD,15));
		btn.setForeground(SystemColor.text); btn.setRippleColor(Color.WHITE); btn.setBackground(bg);
		btn.addActionListener(a); tb.add(btn); tb.setBackground(transparent); p.add(tb);
	}
	private void addLogout(JPanel p){
		JToolBar tb=new JToolBar(); tb.setFloatable(false); tb.setMargin(new Insets(-5,550,0,0));
		Buttontest btn=new Buttontest(); btn.setText("Đăng Xuất"); btn.setFont(new Font("Open Sans",Font.BOLD,15));
		btn.setForeground(SystemColor.text); btn.setRippleColor(Color.WHITE); btn.setBackground(new Color(226,110,110));
		btn.addActionListener(e->{if(JOptionPane.showConfirmDialog(null,"Bạn có muốn đăng xuất?",null,JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){new Login().setVisible(true);dispose();}});
		tb.add(btn); tb.setBackground(transparent); p.add(tb);
	}
	private <T extends JFrame> void open(Class<T>cls){
		try{T f=cls.getDeclaredConstructor().newInstance();f.setLocationRelativeTo(null);f.setVisible(true);dispose();}catch(Exception ex){ex.printStackTrace();}
	}
	private JLabel createLabel(JPanel p,String t,int x,int y,int w,int h){
		JLabel l=new JLabel(t);
		l.setForeground(Color.WHITE);
		l.setFont(new Font("Dialog",Font.PLAIN,16));
		l.setBounds(x,y,w,h);
		p.add(l);
		return l;
	}
	private JButton createButton(String t,int x,int y){
		JButton b=new JButton(t);
		b.setFont(new Font("Tahoma",Font.PLAIN,14));
		b.setBounds(x,y,100,30);
		return b;
	}
}
