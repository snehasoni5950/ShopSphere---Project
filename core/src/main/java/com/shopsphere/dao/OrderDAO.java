package com.shopsphere.dao;

import com.shopsphere.model.Address;
import com.shopsphere.model.Order;
import com.shopsphere.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public int createOrder(Connection conn, Order order) throws SQLException {
        String sql = "INSERT INTO orders (user_id, address_id, total_amount, discount, payment_method, payment_status, order_status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, order.getUserId());
            if (order.getAddressId() != null) {
                ps.setInt(2, order.getAddressId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setDouble(3, order.getTotalAmount());
            ps.setDouble(4, order.getDiscount());
            ps.setString(5, order.getPaymentMethod());
            ps.setString(6, order.getPaymentStatus());
            ps.setString(7, order.getOrderStatus());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    order.setOrderId(id);
                    return id;
                }
            }
        }
        return -1;
    }

    public List<Order> getOrdersByUser(int userId) throws SQLException {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.*, a.address_line, a.city, a.state, a.pincode, a.address_type " +
                     "FROM orders o " +
                     "LEFT JOIN addresses a ON o.address_id = a.address_id " +
                     "WHERE o.user_id = ? ORDER BY o.order_id DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractOrder(rs));
                }
            }
        }
        return list;
    }

    public List<Order> getAllOrders() throws SQLException {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.*, u.name AS user_name, u.email AS user_email, " +
                     "a.address_line, a.city, a.state, a.pincode, a.address_type " +
                     "FROM orders o " +
                     "JOIN users u ON o.user_id = u.user_id " +
                     "LEFT JOIN addresses a ON o.address_id = a.address_id " +
                     "ORDER BY o.order_id DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(extractOrder(rs));
            }
        }
        return list;
    }

    public Order getOrderById(int orderId) throws SQLException {
        String sql = "SELECT o.*, u.name AS user_name, u.email AS user_email, " +
                     "a.address_line, a.city, a.state, a.pincode, a.address_type " +
                     "FROM orders o " +
                     "JOIN users u ON o.user_id = u.user_id " +
                     "LEFT JOIN addresses a ON o.address_id = a.address_id " +
                     "WHERE o.order_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractOrder(rs);
                }
            }
        }
        return null;
    }

    public boolean updateOrderStatus(int orderId, String newStatus) throws SQLException {
        String sql = "UPDATE orders SET order_status = ? WHERE order_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updatePaymentStatus(int orderId, String paymentStatus) throws SQLException {
        String sql = "UPDATE orders SET payment_status = ? WHERE order_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, paymentStatus);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        }
    }

    public int getTotalOrderCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM orders";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public double getTotalRevenue() throws SQLException {
        String sql = "SELECT COALESCE(SUM(total_amount), 0) FROM orders WHERE payment_status = 'PAID'";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        }
        return 0.0;
    }

    private Order extractOrder(ResultSet rs) throws SQLException {
        Order order = new Order(
                rs.getInt("order_id"),
                rs.getInt("user_id"),
                rs.getObject("address_id") != null ? rs.getInt("address_id") : null,
                rs.getDouble("total_amount"),
                rs.getDouble("discount"),
                rs.getString("payment_method"),
                rs.getString("payment_status"),
                rs.getString("order_status"),
                rs.getTimestamp("created_at")
        );
        try {
            order.setUserName(rs.getString("user_name"));
            order.setUserEmail(rs.getString("user_email"));
        } catch (SQLException ignored) {}

        try {
            if (rs.getString("address_line") != null) {
                Address a = new Address();
                a.setAddressId(rs.getInt("address_id"));
                a.setAddressLine(rs.getString("address_line"));
                a.setCity(rs.getString("city"));
                a.setState(rs.getString("state"));
                a.setPincode(rs.getString("pincode"));
                a.setAddressType(rs.getString("address_type"));
                order.setShippingAddress(a);
            }
        } catch (SQLException ignored) {}
        return order;
    }
}
