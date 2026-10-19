package com.shopsphere.swing.model;

import com.shopsphere.model.Order;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * RTU Syllabus: Swing MVC - Order Table Model.
 */
public class OrderTableModel extends AbstractTableModel {

    private final String[] columns = {"Order ID", "Customer", "Total (₹)", "Discount (₹)", "Payment Method", "Payment Status", "Order Status", "Date"};
    private List<Order> orders = new ArrayList<>();

    public void setOrders(List<Order> orders) {
        this.orders = (orders != null) ? orders : new ArrayList<>();
        fireTableDataChanged();
    }

    public Order getOrderAt(int row) {
        if (row >= 0 && row < orders.size()) {
            return orders.get(row);
        }
        return null;
    }

    @Override
    public int getRowCount() {
        return orders.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Order o = orders.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> o.getOrderId();
            case 1 -> o.getUserName() != null ? o.getUserName() : "User #" + o.getUserId();
            case 2 -> String.format("%.2f", o.getTotalAmount());
            case 3 -> String.format("%.2f", o.getDiscount());
            case 4 -> o.getPaymentMethod();
            case 5 -> o.getPaymentStatus();
            case 6 -> o.getOrderStatus();
            case 7 -> o.getCreatedAt() != null ? o.getCreatedAt().toString() : "N/A";
            default -> null;
        };
    }
}
