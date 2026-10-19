package com.shopsphere.web.controller;

import com.shopsphere.dao.CategoryDAO;
import com.shopsphere.dao.ProductDAO;
import com.shopsphere.dao.UserDAO;
import com.shopsphere.model.Product;
import com.shopsphere.service.OrderService;
import com.shopsphere.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminDashboardServlet", urlPatterns = {"/admin/dashboard"})
public class AdminDashboardServlet extends HttpServlet {

    private final OrderService orderService = new OrderService();
    private final ProductService productService = new ProductService();
    private final ProductDAO productDAO = new ProductDAO();
    private final UserDAO userDAO = new UserDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int totalOrders = orderService.getTotalOrderCount();
            double totalRevenue = orderService.getTotalRevenue();
            int totalProducts = productDAO.getAllProducts().size();
            int totalUsers = userDAO.getAllUsers().size();
            List<Product> lowStock = productService.getLowStockAlerts(5);

            req.setAttribute("totalOrders", totalOrders);
            req.setAttribute("totalRevenue", totalRevenue);
            req.setAttribute("totalProducts", totalProducts);
            req.setAttribute("totalUsers", totalUsers);
            req.setAttribute("lowStockProducts", lowStock);

            req.getRequestDispatcher("/admin/dashboard.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Error loading admin dashboard: " + e.getMessage());
            req.getRequestDispatcher("/admin/dashboard.jsp").forward(req, resp);
        }
    }
}
