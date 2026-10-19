package com.shopsphere.web.controller;

import com.shopsphere.dao.UserDAO;
import com.shopsphere.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminUserServlet", urlPatterns = {"/admin/users"})
public class AdminUserServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<User> users = userDAO.getAllUsers();
            req.setAttribute("users", users);
            req.getRequestDispatcher("/admin/users.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Error loading users: " + e.getMessage());
            req.getRequestDispatcher("/admin/users.jsp").forward(req, resp);
        }
    }
}
