package com.shopsphere.dao;

import com.shopsphere.model.Payment;
import com.shopsphere.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PaymentDAO {

    public boolean recordPayment(Connection conn, Payment payment) throws SQLException {
        String sql = "INSERT INTO payments (order_id, payment_method, transaction_reference, amount, payment_status) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, payment.getOrderId());
            ps.setString(2, payment.getPaymentMethod());
            ps.setString(3, payment.getTransactionReference());
            ps.setDouble(4, payment.getAmount());
            ps.setString(5, payment.getPaymentStatus());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        payment.setPaymentId(rs.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    public Payment getPaymentByOrderId(int orderId) throws SQLException {
        String sql = "SELECT * FROM payments WHERE order_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Payment(
                            rs.getInt("payment_id"),
                            rs.getInt("order_id"),
                            rs.getString("payment_method"),
                            rs.getString("transaction_reference"),
                            rs.getDouble("amount"),
                            rs.getString("payment_status"),
                            rs.getTimestamp("created_at")
                    );
                }
            }
        }
        return null;
    }
}
