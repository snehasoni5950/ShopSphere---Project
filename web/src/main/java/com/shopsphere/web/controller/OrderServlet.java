package com.shopsphere.web.controller;

import com.shopsphere.model.Order;
import com.shopsphere.model.Payment;
import com.shopsphere.model.User;
import com.shopsphere.service.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderServlet", urlPatterns = {"/orders", "/order-details", "/order-success"})
public class OrderServlet extends HttpServlet {

    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String path = req.getServletPath();

        try {
            if ("/order-success".equals(path)) {
                int orderId = Integer.parseInt(req.getParameter("orderId"));
                Order order = orderService.getOrderDetails(orderId);
                Payment payment = orderService.getOrderPayment(orderId);
                req.setAttribute("order", order);
                req.setAttribute("payment", payment);
                req.getRequestDispatcher("/order-success.jsp").forward(req, resp);

            } else if ("/order-details".equals(path)) {
                int orderId = Integer.parseInt(req.getParameter("id"));
                Order order = orderService.getOrderDetails(orderId);
                if (order == null || (!user.isAdmin() && order.getUserId() != user.getUserId())) {
                    resp.sendRedirect(req.getContextPath() + "/orders?error=Order+not+found");
                    return;
                }
                Payment payment = orderService.getOrderPayment(orderId);
                req.setAttribute("order", order);
                req.setAttribute("payment", payment);
                req.getRequestDispatcher("/order-details.jsp").forward(req, resp);

            } else {
                List<Order> orders = orderService.getUserOrders(user.getUserId());
                req.setAttribute("orders", orders);
                req.getRequestDispatcher("/orders.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Error: " + e.getMessage());
            req.getRequestDispatcher("/orders.jsp").forward(req, resp);
        }
    }
}
