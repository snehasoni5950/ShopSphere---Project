package com.shopsphere.dao;

import com.shopsphere.model.Address;
import com.shopsphere.model.User;
import com.shopsphere.util.DBUtil;
import com.shopsphere.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public User findByEmail(String email) throws SQLException {
        String sql = "SELECT user_id, name, email, password, mobile, role, status, created_at FROM users WHERE email = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractUser(rs);
                }
            }
        }
        return null;
    }

    public User findById(int userId) throws SQLException {
        String sql = "SELECT user_id, name, email, password, mobile, role, status, created_at FROM users WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractUser(rs);
                }
            }
        }
        return null;
    }

    public User authenticate(String email, String plainPassword) throws SQLException {
        User user = findByEmail(email);
        if (user != null && "ACTIVE".equalsIgnoreCase(user.getStatus())) {
            if (PasswordUtil.checkPassword(plainPassword, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public boolean register(User user) throws SQLException {
        String sql = "INSERT INTO users (name, email, password, mobile, role, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, PasswordUtil.hashPassword(user.getPassword()));
            ps.setString(4, user.getMobile());
            ps.setString(5, user.getRole() != null ? user.getRole() : "CUSTOMER");
            ps.setString(6, user.getStatus() != null ? user.getStatus() : "ACTIVE");

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setUserId(rs.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean updateProfile(User user) throws SQLException {
        String sql = "UPDATE users SET name = ?, mobile = ? WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getMobile());
            ps.setInt(3, user.getUserId());
            return ps.executeUpdate() > 0;
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> list = new ArrayList<>();
        String sql = "SELECT user_id, name, email, password, mobile, role, status, created_at FROM users ORDER BY user_id DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(extractUser(rs));
            }
        }
        return list;
    }

    public List<Address> getAddressesByUserId(int userId) throws SQLException {
        List<Address> list = new ArrayList<>();
        String sql = "SELECT address_id, user_id, address_line, city, state, pincode, address_type FROM addresses WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Address(
                            rs.getInt("address_id"),
                            rs.getInt("user_id"),
                            rs.getString("address_line"),
                            rs.getString("city"),
                            rs.getString("state"),
                            rs.getString("pincode"),
                            rs.getString("address_type")
                    ));
                }
            }
        }
        return list;
    }

    public Address getAddressById(int addressId) throws SQLException {
        String sql = "SELECT address_id, user_id, address_line, city, state, pincode, address_type FROM addresses WHERE address_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, addressId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Address(
                            rs.getInt("address_id"),
                            rs.getInt("user_id"),
                            rs.getString("address_line"),
                            rs.getString("city"),
                            rs.getString("state"),
                            rs.getString("pincode"),
                            rs.getString("address_type")
                    );
                }
            }
        }
        return null;
    }

    public boolean addAddress(Address address) throws SQLException {
        String sql = "INSERT INTO addresses (user_id, address_line, city, state, pincode, address_type) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, address.getUserId());
            ps.setString(2, address.getAddressLine());
            ps.setString(3, address.getCity());
            ps.setString(4, address.getState());
            ps.setString(5, address.getPincode());
            ps.setString(6, address.getAddressType() != null ? address.getAddressType() : "HOME");
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        address.setAddressId(rs.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    private User extractUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("user_id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("mobile"),
                rs.getString("role"),
                rs.getString("status"),
                rs.getTimestamp("created_at")
        );
    }
}
