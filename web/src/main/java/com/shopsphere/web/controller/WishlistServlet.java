package com.shopsphere.web.controller;

import com.shopsphere.dao.WishlistDAO;
import com.shopsphere.model.User;
import com.shopsphere.model.WishlistItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "WishlistServlet", urlPatterns = {"/wishlist"})
public class WishlistServlet extends HttpServlet {

    private final WishlistDAO wishlistDAO = new WishlistDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        try {
            List<WishlistItem> items = wishlistDAO.getWishlistByUser(user.getUserId());
            req.setAttribute("wishlistItems", items);
            req.getRequestDispatcher("/wishlist.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Error loading wishlist: " + e.getMessage());
            req.getRequestDispatcher("/wishlist.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String action = req.getParameter("action");

        try {
            int productId = Integer.parseInt(req.getParameter("productId"));
            if ("remove".equalsIgnoreCase(action)) {
                wishlistDAO.removeFromWishlist(user.getUserId(), productId);
            } else {
                wishlistDAO.addToWishlist(user.getUserId(), productId);
            }
            resp.sendRedirect(req.getContextPath() + "/wishlist");
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/wishlist?error=" + e.getMessage());
        }
    }
}
