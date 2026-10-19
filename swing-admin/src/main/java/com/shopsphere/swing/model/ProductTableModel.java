package com.shopsphere.swing.model;

import com.shopsphere.model.Product;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * RTU Syllabus: Swing MVC - Product Table Model.
 */
public class ProductTableModel extends AbstractTableModel {

    private final String[] columns = {"ID", "Name", "Brand", "Category", "Price (₹)", "Discount (%)", "Final (₹)", "Stock", "Status"};
    private List<Product> products = new ArrayList<>();

    public void setProducts(List<Product> products) {
        this.products = (products != null) ? products : new ArrayList<>();
        fireTableDataChanged();
    }

    public Product getProductAt(int row) {
        if (row >= 0 && row < products.size()) {
            return products.get(row);
        }
        return null;
    }

    @Override
    public int getRowCount() {
        return products.size();
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
        Product p = products.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> p.getProductId();
            case 1 -> p.getName();
            case 2 -> p.getBrand();
            case 3 -> p.getCategoryName() != null ? p.getCategoryName() : p.getCategoryId();
            case 4 -> String.format("%.2f", p.getPrice());
            case 5 -> p.getDiscount() + "%";
            case 6 -> String.format("%.2f", p.getFinalPrice());
            case 7 -> p.getStock();
            case 8 -> p.isStatus() ? "Active" : "Inactive";
            default -> null;
        };
    }
}
