package com.shopsphere.web.controller;

import com.shopsphere.model.Order;
import com.shopsphere.service.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminOrderServlet", urlPatterns = {"/admin/orders"})
public class AdminOrderServlet extends HttpServlet {

    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Order> orders = orderService.getAllOrdersForAdmin();
            req.setAttribute("orders", orders);
            req.getRequestDispatcher("/admin/orders.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Error: " + e.getMessage());
            req.getRequestDispatcher("/admin/orders.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int orderId = Integer.parseInt(req.getParameter("orderId"));
            String status = req.getParameter("status");

            orderService.updateOrderStatus(orderId, status);
            resp.sendRedirect(req.getContextPath() + "/admin/orders?success=Order+" + orderId + "+status+updated+to+" + status);
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/admin/orders?error=" + e.getMessage());
        }
    }
}
