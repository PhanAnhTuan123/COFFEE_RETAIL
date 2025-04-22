package view.QuanLy;

import socket.ClientSocket;
import socket.Request;
import socket.Response;
import entity.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class view_showThongKeDoanhThu extends JFrame {
	private static final long serialVersionUID = 1L;
	private final ObjectMapper mapper = new ObjectMapper();
	private ChartPanel chartPanel;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				new view_showThongKeDoanhThu().setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Không thể khởi tạo biểu đồ:\n" + e.getMessage(),
						"Lỗi", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	public view_showThongKeDoanhThu() throws Exception {
		super("Thống Kê Doanh Thu");

		// 1. Lấy dữ liệu từ server
		List<Order> orders = fetchOrders();

		// 2. Gom nhóm doanh thu theo tháng-năm
		Map<String, Double> revenueByMonth = aggregateByMonth(orders);

		// 3. Tạo dataset
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		revenueByMonth.forEach((month, total) ->
				dataset.addValue(total, "Doanh thu", month)
		);

		// 4. Vẽ chart
		JFreeChart chart = ChartFactory.createBarChart(
				"Doanh Thu Theo Tháng",      // tiêu đề
				"Tháng/Năm",                 // nhãn trục X
				"Tổng Tiền (VND)",           // nhãn trục Y
				dataset,                     // dữ liệu
				PlotOrientation.VERTICAL,
				false,                       // không hiển thị legend
				true,
				false
		);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinePaint(Color.GRAY);

		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(860, 600));

		// 5. Thiết lập JFrame
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(chartPanel, BorderLayout.CENTER);
		setSize(900, 700);
		setLocationRelativeTo(null);
	}

	/** Gửi request GET_ORDERS và parse về List<Order> */
	private List<Order> fetchOrders() throws Exception {
		Response resp = ClientSocket.sendRequest(new Request("GET_ORDERS", null));
		Object data = resp.getData();
		return mapper.convertValue(
				data,
				mapper.getTypeFactory().constructCollectionType(List.class, Order.class)
		);
	}

	/**
	 * Gom nhóm danh sách đơn hàng theo tháng-năm,
	 * trả về Map<"MM/yyyy", tổng doanh thu>.
	 */
	private Map<String, Double> aggregateByMonth(List<Order> orders) {
		Map<String, Double> map = new TreeMap<>(); // TreeMap để có thứ tự tháng-năm tăng dần
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/yyyy");

		for (Order o : orders) {
			// Order.getDate() là LocalDateTime
			LocalDateTime dt = o.getDate();
			String key = fmt.format(dt);
			map.put(key, map.getOrDefault(key, 0.0) + o.getTotalPrice());
		}
		return map;
	}
}
