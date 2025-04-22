package view.NhanVien;

import entity.Tables;
import socket.ClientSocket;
import socket.ProtocolUtils;
import socket.Request;
import socket.Response;
import view.NhanVien.view_hoaDon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;

public class view_showBan extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private JTextField txtMaBan;
	private view_hoaDon view;

	public static void main(String[] args) {
		try {
			view_showBan dialog = new view_showBan();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public view_showBan() {
		setTitle("Chọn Bàn Đem Tới");
		setBounds(100, 100, 643, 754);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5,5,5,5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lbl = new JLabel("Chọn Bàn Đem Tới");
		lbl.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setBounds(0, 0, 627, 38);
		contentPanel.add(lbl);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 49, 607, 622);
		contentPanel.add(scrollPane);

		String[] column = {"Mã Bàn","Tên Bàn"};
		model = new DefaultTableModel(column, 0);

		table = new JTable(model);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int n = table.getSelectedRow();
				if (n >= 0) {
					txtMaBan.setText(model.getValueAt(n, 0).toString());
				}
			}
		});
		scrollPane.setViewportView(table);

		// load from server immediately
		loadData();

		JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		buttonPane.add(okButton);

		buttonPane.add(new JLabel("Mã Bàn"));

		txtMaBan = new JTextField();
		txtMaBan.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtMaBan.setEnabled(false);
		txtMaBan.setColumns(10);
		buttonPane.add(txtMaBan);

		okButton.addActionListener(e -> {
			if (txtMaBan.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn bàn!!");
			} else {
				try {
					setBan();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e -> dispose());
		buttonPane.add(cancelButton);
	}

	private void loadData() {
		model.setRowCount(0);
		try {
			Request req = new Request("GET_TABLES", "");
			Response res = ClientSocket.sendRequest(req);
			if (res.isSuccess()) {
				// Use fromJsonList to deserialize the JSON data
				List<Tables> list = ProtocolUtils.fromJsonList(res.getData(), Tables.class);
				for (Tables b : list) {
					model.addRow(new Object[]{b.getMaBan(), b.getTenBan()});
				}
			} else {
				JOptionPane.showMessageDialog(this, res.getErrorMsg(), "Lỗi tải bàn", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Không thể kết nối tới server.", "Lỗi", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void setView(view_hoaDon view) {
		this.view = view;
	}

	private void setBan() throws Exception {
		Tables b = new Tables();
		b.setMaBan(txtMaBan.getText().trim());
		view.setTempBan(b);
		view.tienHanhDatHang();
		view.refresh();
		view.refreshAfterDatHang();
		dispose();
	}
}
