package view.QuanLy;

import db.ConnectDB;
import list.List_HoaDon;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class view_showThongKeDoanhThu extends JFrame {

	private static final long serialVersionUID = 1L;
	private List_HoaDon list_HoaDon = new List_HoaDon();
	private ChartPanel chartPanel;

	public static void main(String[] args) throws Exception {
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		view_showThongKeDoanhThu frame = new view_showThongKeDoanhThu();
//		frame.setVisible(true);
	}

	public view_showThongKeDoanhThu() throws SQLException {
		super("Biểu đồ doanh thu");

		// Tạo dataset
		DefaultCategoryDataset dataset = list_HoaDon.drawThongKeDoanhThuCaoNhatTrongNam();

		JFreeChart chart = ChartFactory.createBarChart("Doanh Thu", // Tiêu đề biểu đồ
				"Tháng/Năm", // Nhãn trục x
				"Doanh Thu", // Nhãn trục y
				dataset, // Dữ liệu
				PlotOrientation.VERTICAL, // Định dạng biểu đồ (dọc)
				true, // Có hiển thị legent không
				true, // Có tạo tooltips không
				false // Có tạo URLs không

		);
		chart.getCategoryPlot().getRenderer().setSeriesOutlinePaint(0, null);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.WHITE); // Màu nền của biểu đồ
		plot.setRangeGridlinePaint(Color.BLACK); // Màu của đường gridlines
		chart.getCategoryPlot().setOutlineVisible(true);
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(860, 670));

		// Create a new frame to display the chart
		JFrame chartFrame = new JFrame("Biểu Đồ Doanh Thu");
		chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		chartFrame.getContentPane().add(chartPanel, BorderLayout.CENTER);
		chartFrame.setSize(900, 700);
		chartFrame.setLocationRelativeTo(null);
		chartFrame.setVisible(true);
	}

}
