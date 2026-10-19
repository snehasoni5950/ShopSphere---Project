package com.shopsphere.web.controller;

import com.shopsphere.model.User;
import com.shopsphere.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String mobile = req.getParameter("mobile");

        if (name == null || email == null || password == null || name.trim().isEmpty() || email.trim().isEmpty()) {
            req.setAttribute("errorMessage", "All required fields must be completed.");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        if (!password.equals(confirmPassword)) {
            req.setAttribute("errorMessage", "Passwords do not match.");
            req.setAttribute("name", name);
            req.setAttribute("email", email);
            req.setAttribute("mobile", mobile);
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        User user = new User();
        user.setName(name.trim());
        user.setEmail(email.trim());
        user.setPassword(password);
        user.setMobile(mobile != null ? mobile.trim() : "");
        user.setRole("CUSTOMER");
        user.setStatus("ACTIVE");

        try {
            boolean success = userService.register(user);
            if (success) {
                HttpSession session = req.getSession(true);
                session.setAttribute("user", user);
                session.setAttribute("role", user.getRole());
                session.setAttribute("cartCount", 0);
                resp.sendRedirect(req.getContextPath() + "/?success=Account+created+successfully!");
            } else {
                req.setAttribute("errorMessage", "Registration failed. Please try again.");
                req.getRequestDispatcher("/register.jsp").forward(req, resp);
            }
        } catch (IllegalArgumentException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("name", name);
            req.setAttribute("email", email);
            req.setAttribute("mobile", mobile);
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }
}
