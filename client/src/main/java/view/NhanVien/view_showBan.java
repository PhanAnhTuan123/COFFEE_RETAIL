package view.NhanVien;

import db.ConnectDB;
import list.List_Ban;
import model.Ban;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class view_showBan extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private List_Ban ban;
	private JTextField txtMaBan;
	private view_hoaDon view;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			view_showBan dialog = new view_showBan();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @throws Exception 
	 */
	public view_showBan() throws Exception {
		try {
			ConnectDB.getInstance().connect();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		ban = new List_Ban();
		setBounds(100, 100, 643, 754);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Chọn Bàn Đem Tới");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 627, 38);
		contentPanel.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 49, 607, 622);
		contentPanel.add(scrollPane);
		String column[] = {
				"Mã Bàn", "Tên Bàn"
		};
		model = new DefaultTableModel(column,0);
		
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int n  = table.getSelectedRow();
				if(n >=0) {
					txtMaBan.setText(model.getValueAt(n, 0).toString());
				}
			}
		});
		table.setModel(model);
		scrollPane.setViewportView(table);
		loadData();
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(!txtMaBan.getText().equalsIgnoreCase("")) {
							try {
								setBan();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else {
							JOptionPane.showMessageDialog(null, "Vui lòng chọn bàn!!");
						}
					}
				});
				
				JLabel lblNewLabel_1 = new JLabel("Mã Bàn");
				lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
				buttonPane.add(lblNewLabel_1);
				
				txtMaBan = new JTextField();
				txtMaBan.setEnabled(false);
				txtMaBan.setFont(new Font("Tahoma", Font.BOLD, 15));
				buttonPane.add(txtMaBan);
				txtMaBan.setColumns(10);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

	}
	private void loadData() throws SQLException {
		model.setRowCount(0);
		for (Ban item : ban.getAll()) {
			model.addRow(new Object[] {
					item.getMaBan(),
					item.getTenBan()
			});
		}
	}

	public view_hoaDon getView() {
		return view;
	}

	public void setView(view_hoaDon view) {
		this.view = view;
	}
	public void setBan() throws Exception {
		Ban ban = new Ban();
		ban.setMaBan(txtMaBan.getText());
		view.setTempBan(ban);
		view.tienHanhDatHang();
		view.refresh();
		view.refreshAfterDatHang();
		dispose();
	}
	
	
}
