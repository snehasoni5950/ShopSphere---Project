package com.shopsphere.web.controller;

import com.shopsphere.dao.CartDAO;
import com.shopsphere.dao.UserDAO;
import com.shopsphere.model.Address;
import com.shopsphere.model.CartItem;
import com.shopsphere.model.Coupon;
import com.shopsphere.model.Order;
import com.shopsphere.model.User;
import com.shopsphere.service.CheckoutService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {

    private final CartDAO cartDAO = new CartDAO();
    private final UserDAO userDAO = new UserDAO();
    private final CheckoutService checkoutService = new CheckoutService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");

        try {
            List<CartItem> cartItems = cartDAO.getCartByUser(user.getUserId());
            if (cartItems.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/cart?error=Your+cart+is+empty.");
                return;
            }

            double subtotal = 0.0;
            for (CartItem ci : cartItems) {
                subtotal += ci.getSubtotal();
            }

            // Coupon handling
            String couponCode = req.getParameter("coupon");
            double discount = 0.0;
            if (couponCode != null && !couponCode.trim().isEmpty()) {
                Coupon coupon = checkoutService.validateCoupon(couponCode, subtotal);
                if (coupon != null) {
                    discount = coupon.calculateDiscount(subtotal);
                    req.setAttribute("appliedCoupon", coupon);
                } else {
                    req.setAttribute("couponError", "Invalid or expired coupon code.");
                }
            }

            double total = Math.max(0.0, Math.round((subtotal - discount) * 100.0) / 100.0);
            List<Address> addresses = userDAO.getAddressesByUserId(user.getUserId());

            req.setAttribute("cartItems", cartItems);
            req.setAttribute("subtotal", subtotal);
            req.setAttribute("discount", discount);
            req.setAttribute("total", total);
            req.setAttribute("addresses", addresses);

            req.getRequestDispatcher("/checkout.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Checkout error: " + e.getMessage());
            req.getRequestDispatcher("/cart.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");

        String addressIdStr = req.getParameter("addressId");
        String couponCode = req.getParameter("couponCode");
        String paymentMethod = req.getParameter("paymentMethod");

        Integer addressId = null;
        if (addressIdStr != null && !addressIdStr.trim().isEmpty()) {
            addressId = Integer.parseInt(addressIdStr.trim());
        }

        try {
            Order order = checkoutService.processCheckout(user.getUserId(), addressId, couponCode, paymentMethod);
            req.getSession().setAttribute("cartCount", 0);
            resp.sendRedirect(req.getContextPath() + "/order-success?orderId=" + order.getOrderId());
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Order placement failed: " + e.getMessage());
            doGet(req, resp);
        }
    }
}
