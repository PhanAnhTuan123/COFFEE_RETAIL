package view.NhanVien;

import db.ConnectDB;
import list.List_ChiTietHoaDon;
import model.ChiTietHoaDon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;

public class view_dialogXemHD extends JDialog {

	private static final long serialVersionUID = 1L;
	private static String maHD;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtmaHD;
	private JTable table;
	private JTextField txtTongTien;
	private List_ChiTietHoaDon listHD;
	private DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			view_dialogXemHD dialog = new view_dialogXemHD();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public view_dialogXemHD() {
		
			try {
				ConnectDB.getInstance().connect();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		listHD = new List_ChiTietHoaDon();
		setBounds(100, 100, 558, 571);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Mã Hóa Đơn");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
			lblNewLabel.setBounds(10, 0, 99, 31);
			contentPanel.add(lblNewLabel);
		}
		{
			txtmaHD = new JTextField();
			txtmaHD.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
			txtmaHD.setBounds(110, 0, 125, 31);
			contentPanel.add(txtmaHD);
			txtmaHD.setColumns(10);
			txtmaHD.setEnabled(false);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 42, 522, 400);
			contentPanel.add(scrollPane);
			{
				String[] columns = {
					"Mã","Tên","Giá","Số Lượng"	
				};
				model = new DefaultTableModel(columns,0);
				table = new JTable(model);
				scrollPane.setViewportView(table);
			}
		}
		{
			JLabel lblTngTin = new JLabel("Tổng Tiền");
			lblTngTin.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
			lblTngTin.setBounds(10, 448, 99, 31);
			contentPanel.add(lblTngTin);
		}
		{
			txtTongTien = new JTextField();
			txtTongTien.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
			txtTongTien.setEnabled(false);
			txtTongTien.setColumns(10);
			txtTongTien.setBounds(97, 448, 125, 31);
			contentPanel.add(txtTongTien);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
	}

	public static String getMaHD() {
		return maHD;
	}

	public static void setMaHD(String maHD) {
		view_dialogXemHD.maHD = maHD;
	}

	public void refresh(String maHd,String tongTien) {
		
		try {
			txtmaHD.setText(maHd);
			txtTongTien.setText(tongTien);
			loadData(maHd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void loadData(String maHD) throws SQLException {
		model.setRowCount(0);
		Locale localVN = new Locale("vi","VN");
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(localVN);
		
		for (ChiTietHoaDon item : listHD.getForCondition(maHD)) {
			model.addRow(new Object[] {
					item.getHangHoa().getMaHH(),
					item.getHangHoa().getTenHH(),
					currencyFormat.format(item.getHangHoa().getGia()),
					item.getSoLuong()
			});
		}
	}
	

}
