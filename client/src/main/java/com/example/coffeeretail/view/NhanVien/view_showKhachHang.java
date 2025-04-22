package view.NhanVien;

import entity.Customer;
import socket.ClientSocket;
import socket.ProtocolUtils;
import socket.Request;
import socket.Response;
import testbutton.Buttontest;
import view.NhanVien.view_hoaDon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class view_showKhachHang extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField txtMaKH;
	private view_hoaDon view;
	private DefaultTableModel model;
	private JTable table;
	private JButton okButton;
	private JButton cancelButton;

	public static void main(String[] args) {
		try {
			view_showKhachHang dialog = new view_showKhachHang();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public view_showKhachHang() {
		setTitle("Chọn Khách Hàng");
		setBounds(100, 100, 485, 617);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5,5,5,5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

//		public void handleResponse(Response res) {
//			try {
//				// Deserialize JSON data into a List<Customer>
//				List<Customer> list = ProtocolUtils.fromJsonList(res.getData(), Customer.class);
//				// Use the list as needed
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}

		// --- UI stayed exactly the same ---
		JLabel lblNewLabel = new JLabel("Nhập Số Điện Thoại");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(0, 11, 162, 26);
		contentPanel.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(147, 11, 147, 26);
		contentPanel.add(textField);
		textField.setColumns(10);

		JButton btnChon = new JButton("Chọn");
		btnChon.setBounds(299, 10, 77, 26);
		contentPanel.add(btnChon);
		btnChon.addActionListener(e -> filterByPhone());

		JButton btnLamMoi = new JButton("Làm Mới");
		btnLamMoi.setBounds(384, 9, 77, 26);
		contentPanel.add(btnLamMoi);
		btnLamMoi.addActionListener(e -> loadData());

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 47, 451, 477);
		contentPanel.add(scrollPane);

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

		String[] columns = { "Mã Khách Hàng", "Tên Khách Hàng", "Số Điện Thoại" };
		model = new DefaultTableModel(columns, 0);
		table.setModel(model);

		JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JLabel lblMa = new JLabel("Mã Khách Hàng");
		lblMa.setFont(new Font("Tahoma", Font.BOLD, 15));
		buttonPane.add(lblMa);

		txtMaKH = new JTextField();
		txtMaKH.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtMaKH.setEnabled(false);
		txtMaKH.setColumns(10);
		buttonPane.add(txtMaKH);

		okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);

		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		// load data from server
		loadData();
	}

	private void loadData() {
		model.setRowCount(0);
		try {
			Request req = new Request("GET_CUSTOMERS", "");
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				// Use fromJsonList to deserialize the JSON data
				List<Customer> list = ProtocolUtils.fromJsonList(res.getData(), Customer.class);
				for (Customer kh : list) {
					model.addRow(new Object[]{
							kh.getMaKH(),
							kh.getTenKH(),
							kh.getSdt()
					});
				}
			} else {
				JOptionPane.showMessageDialog(
						this,
						res.getErrorMsg(),
						"Lỗi tải khách hàng",
						JOptionPane.ERROR_MESSAGE
				);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(
					this,
					"Không thể kết nối tới server.",
					"Lỗi",
					JOptionPane.ERROR_MESSAGE
			);
		}
	}

	private void filterByPhone() {
		String phone = textField.getText().trim();
		if (phone.isEmpty()) {
			loadData();
			return;
		}
		model.setRowCount(0);
		try {
			Request req = new Request("SEARCH_CUSTOMERS_BY_PHONE", ProtocolUtils.toJson(phone));
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				// Use fromJsonList to deserialize the JSON data
				List<Customer> list = ProtocolUtils.fromJsonList(res.getData(), Customer.class);
				for (Customer kh : list) {
					model.addRow(new Object[]{
							kh.getMaKH(),
							kh.getTenKH(),
							kh.getSdt()
					});
				}
			} else {
				JOptionPane.showMessageDialog(this, res.getErrorMsg());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Không thể kết nối tới server.");
		}
	}

	public void setView(view_hoaDon view) {
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okButton) {
			String ma = txtMaKH.getText().trim();
			if (ma.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng.");
				return;
			}
			view.submitMaKH(ma);
			Customer kh = new Customer();
			kh.setMaKH(ma);
			view.setTempKH(kh);
			dispose();
		} else if (e.getSource() == cancelButton) {
			dispose();
		}
	}
}
