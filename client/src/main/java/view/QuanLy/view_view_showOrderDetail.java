package view.QuanLy;

import db.ConnectDB;
import list.List_Order;
import model.Order;
import service.Order_DAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.Properties;

public class view_QuanLyOrder extends JFrame {
    private Order_DAO order_dao;
    private List_Order list_Order;
    private DefaultTableModel tableModel;
    private JTable table;
    private JDatePickerImpl datePicker;
    private JTextField txtTotalPrice;

    public view_QuanLyOrder() throws SQLException {
        order_dao = new Order_DAO();
        list_Order = new List_Order();

        setTitle("Giao Diện Quản Lý Đơn Hàng");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        setLocationRelativeTo(null);

        // Main content pane
        JPanel contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        // Top panel for title
        JLabel lblTitle = new JLabel("Quản Lý Đơn Hàng", JLabel.CENTER);
        lblTitle.setFont(new Font("Open Sans", Font.BOLD, 20));
        contentPane.add(lblTitle, BorderLayout.NORTH);

        // Center panel for form and table
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        contentPane.add(centerPanel, BorderLayout.CENTER);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông Tin Đơn Hàng"));
        centerPanel.add(formPanel, BorderLayout.NORTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Date picker
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Chọn ngày:"), gbc);

        datePicker = new JDatePickerImpl(new JDatePanelImpl(new Properties()));
        gbc.gridx = 1;
        formPanel.add(datePicker, gbc);

        // Total price
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Tổng giá:"), gbc);

        txtTotalPrice = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(txtTotalPrice, gbc);

        // Table panel
        tableModel = new DefaultTableModel(new String[]{"Mã Đơn", "Ngày", "Tổng Giá"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        JButton btnThem = new JButton("Thêm");
        JButton btnXoa = new JButton("Xóa");
        JButton btnSua = new JButton("Sửa");
        JButton btnLamMoi = new JButton("Làm mới");

        buttonPanel.add(btnThem);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnLamMoi);
    }

    public static void main(String[] args) throws SQLException {
        SwingUtilities.invokeLater(() -> {
            try {
                new view_QuanLyOrder().setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}