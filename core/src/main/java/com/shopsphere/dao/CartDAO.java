package com.shopsphere.dao;

import com.shopsphere.model.CartItem;
import com.shopsphere.model.Product;
import com.shopsphere.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    public List<CartItem> getCartByUser(int userId) throws SQLException {
        List<CartItem> list = new ArrayList<>();
        String sql = "SELECT c.cart_id, c.user_id, c.product_id, c.quantity, " +
                     "p.name, p.brand, p.price, p.discount, p.stock, p.image_url, p.status " +
                     "FROM cart c " +
                     "JOIN products p ON c.product_id = p.product_id " +
                     "WHERE c.user_id = ? ORDER BY c.cart_id DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CartItem item = new CartItem(
                            rs.getInt("cart_id"),
                            rs.getInt("user_id"),
                            rs.getInt("product_id"),
                            rs.getInt("quantity")
                    );
                    Product p = new Product();
                    p.setProductId(rs.getInt("product_id"));
                    p.setName(rs.getString("name"));
                    p.setBrand(rs.getString("brand"));
                    p.setPrice(rs.getDouble("price"));
                    p.setDiscount(rs.getDouble("discount"));
                    p.setStock(rs.getInt("stock"));
                    p.setImageUrl(rs.getString("image_url"));
                    p.setStatus(rs.getBoolean("status"));
                    item.setProduct(p);
                    list.add(item);
                }
            }
        }
        return list;
    }

    public boolean addToCart(int userId, int productId, int quantity) throws SQLException {
        String checkSql = "SELECT cart_id, quantity FROM cart WHERE user_id = ? AND product_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
            checkPs.setInt(1, userId);
            checkPs.setInt(2, productId);
            try (ResultSet rs = checkPs.executeQuery()) {
                if (rs.next()) {
                    int existingId = rs.getInt("cart_id");
                    int existingQty = rs.getInt("quantity");
                    String updateSql = "UPDATE cart SET quantity = ? WHERE cart_id = ?";
                    try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                        updatePs.setInt(1, existingQty + quantity);
                        updatePs.setInt(2, existingId);
                        return updatePs.executeUpdate() > 0;
                    }
                } else {
                    String insertSql = "INSERT INTO cart (user_id, product_id, quantity) VALUES (?, ?, ?)";
                    try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                        insertPs.setInt(1, userId);
                        insertPs.setInt(2, productId);
                        insertPs.setInt(3, quantity);
                        return insertPs.executeUpdate() > 0;
                    }
                }
            }
        }
    }

    public boolean updateQuantity(int cartId, int quantity) throws SQLException {
        if (quantity <= 0) {
            String deleteSql = "DELETE FROM cart WHERE cart_id = ?";
            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement(deleteSql)) {
                ps.setInt(1, cartId);
                return ps.executeUpdate() > 0;
            }
        }
        String sql = "UPDATE cart SET quantity = ? WHERE cart_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, cartId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean removeFromCart(int cartId, int userId) throws SQLException {
        String sql = "DELETE FROM cart WHERE cart_id = ? AND user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        }
    }

    public void clearCart(Connection conn, int userId) throws SQLException {
        String sql = "DELETE FROM cart WHERE user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }

    public void clearCart(int userId) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            clearCart(conn, userId);
        }
    }

    public int getCartCount(int userId) throws SQLException {
        String sql = "SELECT COALESCE(SUM(quantity), 0) AS total_count FROM cart WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total_count");
                }
            }
        }
        return 0;
    }
}
