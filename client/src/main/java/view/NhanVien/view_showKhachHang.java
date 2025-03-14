package view.NhanVien;

import db.ConnectDB;
import list.List_KhachHang;
import model.KhachHang;
import service.KhachHang_DAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class view_showKhachHang extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField txtMaKH;
	public view_hoaDon view;
	private DefaultTableModel model;
	private List_KhachHang list_kh;
	private KhachHang_DAO kh_dao;
	private JTable table;
	private JButton okButton;
	private JButton cancelButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			view_showKhachHang dialog = new view_showKhachHang();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * 
	 * @throws Exception
	 */
	public view_showKhachHang() throws Exception {

		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		kh_dao = new KhachHang_DAO();
		list_kh = new List_KhachHang();
		setBounds(100, 100, 485, 617);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Nhập Số Điện Thoại");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblNewLabel.setBounds(0, 11, 162, 26);
			contentPanel.add(lblNewLabel);
		}
		{
			textField = new JTextField();
			textField.setBounds(147, 11, 147, 26);
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		{
			JButton btnChon = new JButton("Chọn");
			btnChon.setBounds(299, 10, 77, 26);
			contentPanel.add(btnChon);
		}
		{
			JButton btnLamMoi = new JButton("Làm Mới");
			btnLamMoi.setBounds(384, 9, 77, 26);
			contentPanel.add(btnLamMoi);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 47, 451, 477);
			contentPanel.add(scrollPane);
			{
				table = new JTable();
				table.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int n = table.getSelectedRow();
						if (n >= 0) {
							txtMaKH.setText(model.getValueAt(n, 0).toString());
						}
					}
				});
				scrollPane.setViewportView(table);
			}
		}
		{

			String[] column = { "Mã Khách Hàng", "Tên Khách Hàng", "Số Điện Thoại" };
			model = new DefaultTableModel(column, 0);
			table.setModel(model);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JLabel lblNewLabel_1 = new JLabel("Mã Khách Hàng");
				lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
				buttonPane.add(lblNewLabel_1);
			}
			{
				txtMaKH = new JTextField();
				txtMaKH.setFont(new Font("Tahoma", Font.BOLD, 15));
				buttonPane.add(txtMaKH);
				txtMaKH.setEnabled(false);
				txtMaKH.setColumns(10);
			}
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		loadData();
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
	}

	private void loadData() throws SQLException {
		model.setRowCount(0);
//		System.out.println(list_kh.getAll());
		for (KhachHang kh : list_kh.getAll()) {
			model.addRow(new Object[] { kh.getMaKH(), kh.getTenKH(), kh.getSdt() });
		}
	}

	public view_hoaDon getView() {
		return view;
	}

	public void setView(view_hoaDon view) {
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource().equals(okButton)) {
			System.out.println("OK!");
			onSubmitButtonClick();
		}
		if (arg0.getSource().equals(cancelButton)) {
			System.out.println("Cancel");
		}
	}

	private void onSubmitButtonClick() {
		if (txtMaKH.getText().equalsIgnoreCase("")) {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn khách hàng cần thêm.");
		} else {

			view.submitMaKH(txtMaKH.getText());
			KhachHang kh = new KhachHang();
			kh.setMaKH(txtMaKH.getText());
			view.setTempKH(kh);
			dispose();
		}
	}
}
