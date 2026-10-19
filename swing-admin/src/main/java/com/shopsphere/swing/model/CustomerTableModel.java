package com.shopsphere.swing.model;

import com.shopsphere.model.User;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * RTU Syllabus: Swing MVC - Customer Table Model.
 */
public class CustomerTableModel extends AbstractTableModel {

    private final String[] columns = {"User ID", "Full Name", "Email Address", "Mobile", "Role", "Status", "Joined On"};
    private List<User> users = new ArrayList<>();

    public void setUsers(List<User> users) {
        this.users = (users != null) ? users : new ArrayList<>();
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return users.size();
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
        User u = users.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> u.getUserId();
            case 1 -> u.getName();
            case 2 -> u.getEmail();
            case 3 -> u.getMobile() != null ? u.getMobile() : "-";
            case 4 -> u.getRole();
            case 5 -> u.getStatus();
            case 6 -> u.getCreatedAt() != null ? u.getCreatedAt().toString() : "-";
            default -> null;
        };
    }
}
