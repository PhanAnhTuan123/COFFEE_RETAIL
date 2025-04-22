package view.NhanVien;

import entity.OrderDetail;
import entity.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class view_dialogXemHD extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
	private final JTextField txtMaHD = new JTextField();
	private final JTable table = new JTable();
	private final JTextField txtTongTien = new JTextField();
	private DefaultTableModel model;

	public view_dialogXemHD(Frame owner) {
		super(owner, "Xem chi tiết hóa đơn", true);
		initComponents();
		pack();
		setLocationRelativeTo(owner);
	}

	private void initComponents() {
		// ======= CONTENT PANEL =======
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		// --- Top: mã hóa đơn ---
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		JLabel lblMaHD = new JLabel("Mã Hóa Đơn:");
		lblMaHD.setFont(lblMaHD.getFont().deriveFont(Font.BOLD | Font.ITALIC, 14f));
		txtMaHD.setEditable(false);
		txtMaHD.setColumns(15);
		topPanel.add(lblMaHD);
		topPanel.add(txtMaHD);
		contentPanel.add(topPanel, BorderLayout.NORTH);

		// --- Center: bảng chi tiết ---
		String[] cols = {"Mã SP", "Tên SP", "Giá", "Số lượng"};
		model = new DefaultTableModel(cols, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setModel(model);
		JScrollPane scrollPane = new JScrollPane(table);
		contentPanel.add(scrollPane, BorderLayout.CENTER);

		// --- Bottom: tổng tiền và nút đóng ---
		JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));

		JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		JLabel lblTongTien = new JLabel("Tổng Tiền:");
		lblTongTien.setFont(lblTongTien.getFont().deriveFont(Font.BOLD | Font.ITALIC, 14f));
		txtTongTien.setEditable(false);
		txtTongTien.setColumns(15);
		totalPanel.add(lblTongTien);
		totalPanel.add(txtTongTien);

		JButton btnClose = new JButton("Đóng");
		btnClose.addActionListener(e -> dispose());
		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		btnPanel.add(btnClose);

		bottomPanel.add(totalPanel, BorderLayout.WEST);
		bottomPanel.add(btnPanel, BorderLayout.EAST);

		contentPanel.add(bottomPanel, BorderLayout.SOUTH);
	}

	/**
	 * Cập nhật dữ liệu hóa đơn vào dialog.
	 *
	 * @param maHD    mã hóa đơn
	 * @param details danh sách OrderDetail
	 */
	public void refreshData(String maHD, List<OrderDetail> details) {
		txtMaHD.setText(maHD);
		loadTable(details);
		txtTongTien.setText(formatCurrency(calculateTotal(details)));
	}

	private void loadTable(List<OrderDetail> details) {
		model.setRowCount(0);
		for (OrderDetail od : details) {
			Product product = od.getProduct();
			model.addRow(new Object[]{
					product.getId(),
					product.getName(),
					formatCurrency(product.getPrice()),
					od.getQuantity()
			});
		}
	}

	private double calculateTotal(List<OrderDetail> details) {
		return details.stream()
				.mapToDouble(od -> od.getProduct().getPrice() * od.getQuantity())
				.sum();
	}

	private String formatCurrency(double amount) {
		Locale vn = new Locale("vi", "VN");
		return NumberFormat.getCurrencyInstance(vn).format(amount);
	}

	// Ví dụ chạy thử
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			view_dialogXemHD dialog = new view_dialogXemHD(null);
			List<OrderDetail> sample = List.of(
					new OrderDetail(new Product("P01", "Sản phẩm A", 100000.0, null), 2),
					new OrderDetail(new Product("P02", "Sản phẩm B", 150000.0, null), 1)
			);
			dialog.refreshData("HD1001", sample);
			dialog.setVisible(true);
		});
	}
}