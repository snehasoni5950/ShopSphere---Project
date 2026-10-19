package com.shopsphere.dao;

import com.shopsphere.model.Coupon;
import com.shopsphere.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CouponDAO {

    public Coupon findByCode(String code) throws SQLException {
        String sql = "SELECT * FROM coupons WHERE UPPER(code) = UPPER(?) AND status = 1 AND expiry_date >= CURRENT_DATE()";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractCoupon(rs);
                }
            }
        }
        return null;
    }

    public List<Coupon> getAllCoupons() throws SQLException {
        List<Coupon> list = new ArrayList<>();
        String sql = "SELECT * FROM coupons ORDER BY coupon_id DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(extractCoupon(rs));
            }
        }
        return list;
    }

    public boolean addCoupon(Coupon coupon) throws SQLException {
        String sql = "INSERT INTO coupons (code, discount_type, discount_value, minimum_order, maximum_discount, expiry_date, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, coupon.getCode().toUpperCase());
            ps.setString(2, coupon.getDiscountType());
            ps.setDouble(3, coupon.getDiscountValue());
            ps.setDouble(4, coupon.getMinimumOrder());
            ps.setDouble(5, coupon.getMaximumDiscount());
            ps.setDate(6, coupon.getExpiryDate());
            ps.setBoolean(7, coupon.isStatus());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        coupon.setCouponId(rs.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    public void recordCouponUsage(Connection conn, int couponId, int userId, int orderId) throws SQLException {
        String sql = "INSERT INTO coupon_usage (coupon_id, user_id, order_id) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, couponId);
            ps.setInt(2, userId);
            ps.setInt(3, orderId);
            ps.executeUpdate();
        }
    }

    private Coupon extractCoupon(ResultSet rs) throws SQLException {
        return new Coupon(
                rs.getInt("coupon_id"),
                rs.getString("code"),
                rs.getString("discount_type"),
                rs.getDouble("discount_value"),
                rs.getDouble("minimum_order"),
                rs.getDouble("maximum_discount"),
                rs.getDate("expiry_date"),
                rs.getBoolean("status")
        );
    }
}
