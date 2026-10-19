package com.shopsphere.dao;

import com.shopsphere.model.Category;
import com.shopsphere.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    public List<Category> getAllCategories() throws SQLException {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT category_id, name, description, status FROM categories ORDER BY name ASC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Category(
                        rs.getInt("category_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBoolean("status")
                ));
            }
        }
        return list;
    }

    public Category getCategoryById(int categoryId) throws SQLException {
        String sql = "SELECT category_id, name, description, status FROM categories WHERE category_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Category(
                            rs.getInt("category_id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getBoolean("status")
                    );
                }
            }
        }
        return null;
    }

    public boolean addCategory(Category category) throws SQLException {
        String sql = "INSERT INTO categories (name, description, status) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setBoolean(3, category.isStatus());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        category.setCategoryId(rs.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean updateCategory(Category category) throws SQLException {
        String sql = "UPDATE categories SET name = ?, description = ?, status = ? WHERE category_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setBoolean(3, category.isStatus());
            ps.setInt(4, category.getCategoryId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteCategory(int categoryId) throws SQLException {
        String sql = "DELETE FROM categories WHERE category_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            return ps.executeUpdate() > 0;
        }
    }
}
