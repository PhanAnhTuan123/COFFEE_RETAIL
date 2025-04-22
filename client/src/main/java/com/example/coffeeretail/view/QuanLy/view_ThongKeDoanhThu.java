package view.QuanLy;

import com.toedter.calendar.JDateChooser;
import socket.ClientSocket;
import socket.Request;
import socket.Response;
import entity.Order;
import entity.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import testbutton.Buttontest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class view_ThongKeDoanhThu extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final ObjectMapper mapper = new ObjectMapper();

	private JTextField txtTuNgay, txtDenNgay;
	private JDateChooser dcTuNgay, dcDenNgay;
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton btnLoc, btnBieuDo;

	public view_ThongKeDoanhThu() {
		super("Thống Kê Doanh Thu");
		initUI();
		loadTableData(); // load mặc định toàn bộ
	}

	private void initUI() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(1168, 650);
		setLocationRelativeTo(null);
		JPanel content = new JPanel(null);
		content.setBackground(Color.WHITE);
		setContentPane(content);

		// Date pickers
		JLabel lblTu = new JLabel("Từ ngày:");
		lblTu.setBounds(20, 100, 70, 25);
		content.add(lblTu);
		dcTuNgay = new JDateChooser();
		dcTuNgay.setBounds(90, 100, 120, 25);
		content.add(dcTuNgay);

		JLabel lblDen = new JLabel("Đến ngày:");
		lblDen.setBounds(20, 135, 70, 25);
		content.add(lblDen);
		dcDenNgay = new JDateChooser();
		dcDenNgay.setBounds(90, 135, 120, 25);
		content.add(dcDenNgay);

		// Filter button
		btnLoc = new JButton("Lọc");
		btnLoc.setBounds(20, 175, 190, 30);
		btnLoc.addActionListener(this);
		content.add(btnLoc);

		// Chart button
		btnBieuDo = new JButton("Biểu đồ");
		btnBieuDo.setBounds(20, 215, 190, 30);
		btnBieuDo.addActionListener(e -> {
			// mở biểu đồ chung
            try {
                new view_showThongKeDoanhThu().setVisible(true);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
		content.add(btnBieuDo);

		// Table
		String[] cols = {"STT", "Mã HH", "Tên Hàng Hóa", "Doanh Thu"};
		tableModel = new DefaultTableModel(cols, 0);
		table = new JTable(tableModel);
		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(230, 100, 900, 480);
		content.add(sp);
	}

	/** Load table (toàn bộ hoặc theo ngày nếu người dùng đã chọn) */
	private void loadTableData() {
		try {
			// 1. Lấy orders và products từ server
			Response r1 = ClientSocket.sendRequest(new Request("GET_ORDERS", null));
			List<Order> orders = mapper.convertValue(
					r1.getData(),
					mapper.getTypeFactory().constructCollectionType(List.class, Order.class)
			);
			Response r2 = ClientSocket.sendRequest(new Request("GET_PRODUCTS", null));
			List<Product> products = mapper.convertValue(
					r2.getData(),
					mapper.getTypeFactory().constructCollectionType(List.class, Product.class)
			);
			Map<String,String> prodNameMap = products.stream()
					.collect(Collectors.toMap(Product::getId, Product::getName));

			// 2. Lọc theo date nếu có chọn
			LocalDate from = toLocalDate(dcTuNgay.getDate());
			LocalDate to   = toLocalDate(dcDenNgay.getDate());
			List<Order> filtered = orders.stream()
					.filter(o -> {
						LocalDate d = o.getDate().toLocalDate();
						if (from!=null && d.isBefore(from)) return false;
						if (to  !=null && d.isAfter(to))   return false;
						return true;
					})
					.collect(Collectors.toList());

			// 3. Tính doanh thu theo sản phẩm
			Map<String, Double> revenue = new LinkedHashMap<>();
			for (Order o : filtered) {
				o.getDetails().forEach(det -> {
					revenue.merge(
							det.getProductId(),
							det.getSubtotal(),
							Double::sum
					);
				});
			}

			// 4. Đưa lên table
			tableModel.setRowCount(0);
			int i = 1;
			for (Map.Entry<String,Double> en : revenue.entrySet()) {
				String id = en.getKey();
				String name = prodNameMap.getOrDefault(id, "[Unknown]");
				tableModel.addRow(new Object[]{ i++, id, name, en.getValue() });
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + ex.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/** Chuyển java.util.Date -> java.time.LocalDate */
	private LocalDate toLocalDate(Date d) {
		if (d == null) return null;
		return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnLoc) {
			loadTableData();
		}
	}
}
