package com.shopsphere.service;

import com.shopsphere.dao.UserDAO;
import com.shopsphere.model.Address;
import com.shopsphere.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public User authenticate(String email, String password) throws SQLException {
        if (email == null || password == null) return null;
        return userDAO.authenticate(email.trim(), password);
    }

    public boolean register(User user) throws SQLException {
        if (user.getEmail() == null || user.getPassword() == null) {
            return false;
        }
        User existing = userDAO.findByEmail(user.getEmail().trim());
        if (existing != null) {
            throw new IllegalArgumentException("An account with this email already exists.");
        }
        return userDAO.register(user);
    }

    public User getUserById(int userId) throws SQLException {
        return userDAO.findById(userId);
    }

    public boolean updateProfile(User user) throws SQLException {
        return userDAO.updateProfile(user);
    }

    public List<Address> getAddresses(int userId) throws SQLException {
        return userDAO.getAddressesByUserId(userId);
    }

    public Address getAddressById(int addressId) throws SQLException {
        return userDAO.getAddressById(addressId);
    }

    public boolean addAddress(Address address) throws SQLException {
        return userDAO.addAddress(address);
    }

    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAllUsers();
    }
}
