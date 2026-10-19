package com.shopsphere.swing.controller;

import com.shopsphere.model.User;
import com.shopsphere.service.UserService;

/**
 * RTU Syllabus: Swing MVC - Login Controller.
 */
public class LoginController {

    private final UserService userService;

    public LoginController() {
        this.userService = new UserService();
    }

    public User authenticateAdmin(String email, String password) throws Exception {
        if (email == null || email.trim().isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Email and password cannot be empty.");
        }
        User user = userService.authenticate(email, password);
        if (user == null) {
            throw new IllegalArgumentException("Invalid credentials.");
        }
        if (!user.isAdmin()) {
            throw new SecurityException("Access denied: User is not an administrator.");
        }
        return user;
    }
}
