package com.shopsphere.dao;

import com.shopsphere.model.Product;
import com.shopsphere.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public List<Product> getAllProducts() throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.*, c.name AS category_name FROM products p " +
                     "LEFT JOIN categories c ON p.category_id = c.category_id " +
                     "ORDER BY p.product_id ASC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(extractProduct(rs));
            }
        }
        return list;
    }

    public List<Product> getActiveProducts() throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.*, c.name AS category_name FROM products p " +
                     "LEFT JOIN categories c ON p.category_id = c.category_id " +
                     "WHERE p.status = 1 ORDER BY p.product_id ASC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(extractProduct(rs));
            }
        }
        return list;
    }

    public Product getProductById(int productId) throws SQLException {
        String sql = "SELECT p.*, c.name AS category_name FROM products p " +
                     "LEFT JOIN categories c ON p.category_id = c.category_id " +
                     "WHERE p.product_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractProduct(rs);
                }
            }
        }
        return null;
    }

    public List<Product> getProductsByCategory(int categoryId) throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.*, c.name AS category_name FROM products p " +
                     "LEFT JOIN categories c ON p.category_id = c.category_id " +
                     "WHERE p.category_id = ? AND p.status = 1";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractProduct(rs));
                }
            }
        }
        return list;
    }

    public List<Product> searchProducts(String keyword) throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.*, c.name AS category_name FROM products p " +
                     "LEFT JOIN categories c ON p.category_id = c.category_id " +
                     "WHERE (p.name LIKE ? OR p.brand LIKE ? OR p.description LIKE ?) AND p.status = 1";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setString(3, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractProduct(rs));
                }
            }
        }
        return list;
    }

    public int getStock(int productId) throws SQLException {
        String sql = "SELECT stock FROM products WHERE product_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("stock");
                }
            }
        }
        return 0;
    }

    public boolean updateStock(int productId, int newStock) throws SQLException {
        String sql = "UPDATE products SET stock = ? WHERE product_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newStock);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deductStock(Connection conn, int productId, int quantity) throws SQLException {
        String sql = "UPDATE products SET stock = stock - ? WHERE product_id = ? AND stock >= ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Product> getLowStockProducts(int threshold) throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.*, c.name AS category_name FROM products p " +
                     "LEFT JOIN categories c ON p.category_id = c.category_id " +
                     "WHERE p.stock <= ? ORDER BY p.stock ASC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, threshold);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractProduct(rs));
                }
            }
        }
        return list;
    }

    public boolean addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products (category_id, name, brand, description, price, discount, stock, image_url, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, product.getCategoryId());
            ps.setString(2, product.getName());
            ps.setString(3, product.getBrand());
            ps.setString(4, product.getDescription());
            ps.setDouble(5, product.getPrice());
            ps.setDouble(6, product.getDiscount());
            ps.setInt(7, product.getStock());
            ps.setString(8, product.getImageUrl());
            ps.setBoolean(9, product.isStatus());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        product.setProductId(rs.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean updateProduct(Product product) throws SQLException {
        String sql = "UPDATE products SET category_id = ?, name = ?, brand = ?, description = ?, " +
                     "price = ?, discount = ?, stock = ?, image_url = ?, status = ? WHERE product_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, product.getCategoryId());
            ps.setString(2, product.getName());
            ps.setString(3, product.getBrand());
            ps.setString(4, product.getDescription());
            ps.setDouble(5, product.getPrice());
            ps.setDouble(6, product.getDiscount());
            ps.setInt(7, product.getStock());
            ps.setString(8, product.getImageUrl());
            ps.setBoolean(9, product.isStatus());
            ps.setInt(10, product.getProductId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteProduct(int productId) throws SQLException {
        String sql = "DELETE FROM products WHERE product_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            return ps.executeUpdate() > 0;
        }
    }

    private Product extractProduct(ResultSet rs) throws SQLException {
        Product p = new Product(
                rs.getInt("product_id"),
                rs.getInt("category_id"),
                rs.getString("name"),
                rs.getString("brand"),
                rs.getString("description"),
                rs.getDouble("price"),
                rs.getDouble("discount"),
                rs.getInt("stock"),
                rs.getString("image_url"),
                rs.getBoolean("status"),
                rs.getTimestamp("created_at")
        );
        try {
            p.setCategoryName(rs.getString("category_name"));
        } catch (SQLException ignored) {}
        return p;
    }
}
