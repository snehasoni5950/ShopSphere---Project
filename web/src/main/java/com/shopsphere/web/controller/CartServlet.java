package com.shopsphere.web.controller;

import com.shopsphere.dao.CartDAO;
import com.shopsphere.model.CartItem;
import com.shopsphere.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {

    private final CartDAO cartDAO = new CartDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getCurrentUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            List<CartItem> cartItems = cartDAO.getCartByUser(user.getUserId());
            double subtotal = 0.0;
            for (CartItem item : cartItems) {
                subtotal += item.getSubtotal();
            }

            req.setAttribute("cartItems", cartItems);
            req.setAttribute("subtotal", Math.round(subtotal * 100.0) / 100.0);
            updateSessionCartCount(req, user.getUserId());
            req.getRequestDispatcher("/cart.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Error loading cart: " + e.getMessage());
            req.getRequestDispatcher("/cart.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getCurrentUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String action = req.getParameter("action");
        try {
            if ("add".equalsIgnoreCase(action)) {
                int productId = Integer.parseInt(req.getParameter("productId"));
                int quantity = Integer.parseInt(req.getParameter("quantity") != null ? req.getParameter("quantity") : "1");
                cartDAO.addToCart(user.getUserId(), productId, quantity);
            } else if ("update".equalsIgnoreCase(action)) {
                int cartId = Integer.parseInt(req.getParameter("cartId"));
                int quantity = Integer.parseInt(req.getParameter("quantity"));
                cartDAO.updateQuantity(cartId, quantity);
            } else if ("remove".equalsIgnoreCase(action)) {
                int cartId = Integer.parseInt(req.getParameter("cartId"));
                cartDAO.removeFromCart(cartId, user.getUserId());
            }

            updateSessionCartCount(req, user.getUserId());
            resp.sendRedirect(req.getContextPath() + "/cart");

        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/cart?error=" + e.getMessage());
        }
    }

    private User getCurrentUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return (session != null) ? (User) session.getAttribute("user") : null;
    }

    private void updateSessionCartCount(HttpServletRequest req, int userId) {
        try {
            int count = cartDAO.getCartCount(userId);
            req.getSession().setAttribute("cartCount", count);
        } catch (Exception ignored) {}
    }
}
