package com.shopsphere.service;

import com.shopsphere.dao.CartDAO;
import com.shopsphere.dao.CouponDAO;
import com.shopsphere.dao.OrderDAO;
import com.shopsphere.dao.OrderItemDAO;
import com.shopsphere.dao.PaymentDAO;
import com.shopsphere.dao.ProductDAO;
import com.shopsphere.model.CartItem;
import com.shopsphere.model.Coupon;
import com.shopsphere.model.Order;
import com.shopsphere.model.OrderItem;
import com.shopsphere.model.Payment;
import com.shopsphere.util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * CheckoutService fulfills Section 12 & 26:
 * Server-side order total calculation, stock deduction, and transactional integrity.
 */
public class CheckoutService {

    private final CartDAO cartDAO = new CartDAO();
    private final CouponDAO couponDAO = new CouponDAO();
    private final OrderDAO orderDAO = new OrderDAO();
    private final OrderItemDAO orderItemDAO = new OrderItemDAO();
    private final ProductDAO productDAO = new ProductDAO();
    private final PaymentDAO paymentDAO = new PaymentDAO();

    public Coupon validateCoupon(String code, double subtotal) throws SQLException {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }
        Coupon coupon = couponDAO.findByCode(code.trim());
        if (coupon != null && coupon.calculateDiscount(subtotal) > 0) {
            return coupon;
        }
        return null;
    }

    public Order processCheckout(int userId, Integer addressId, String couponCode, String paymentMethod) throws SQLException {
        List<CartItem> cartItems = cartDAO.getCartByUser(userId);
        if (cartItems == null || cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty.");
        }

        // 1. Calculate server-side subtotal and verify stock
        double subtotal = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem ci : cartItems) {
            if (ci.getProduct() == null || !ci.getProduct().isStatus()) {
                throw new IllegalStateException("Product " + ci.getProductId() + " is currently unavailable.");
            }
            if (ci.getProduct().getStock() < ci.getQuantity()) {
                throw new IllegalStateException("Insufficient stock for product: " + ci.getProduct().getName());
            }
            double itemPrice = ci.getProduct().getFinalPrice();
            subtotal += itemPrice * ci.getQuantity();

            OrderItem item = new OrderItem();
            item.setProductId(ci.getProductId());
            item.setQuantity(ci.getQuantity());
            item.setPrice(itemPrice);
            orderItems.add(item);
        }

        // 2. Validate coupon on server
        double discount = 0.0;
        Coupon validCoupon = null;
        if (couponCode != null && !couponCode.trim().isEmpty()) {
            validCoupon = validateCoupon(couponCode, subtotal);
            if (validCoupon != null) {
                discount = validCoupon.calculateDiscount(subtotal);
            }
        }

        double totalPayable = Math.max(0.0, Math.round((subtotal - discount) * 100.0) / 100.0);

        // 3. Transactional execution
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            // Deduct stock
            for (OrderItem oi : orderItems) {
                boolean deducted = productDAO.deductStock(conn, oi.getProductId(), oi.getQuantity());
                if (!deducted) {
                    throw new IllegalStateException("Failed to reserve stock for product id " + oi.getProductId());
                }
            }

            // Create Order
            Order order = new Order();
            order.setUserId(userId);
            order.setAddressId(addressId);
            order.setTotalAmount(totalPayable);
            order.setDiscount(discount);
            order.setPaymentMethod(paymentMethod != null ? paymentMethod : "COD");
            order.setPaymentStatus("COD".equalsIgnoreCase(paymentMethod) ? "PENDING" : "PAID");
            order.setOrderStatus("CONFIRMED");

            int orderId = orderDAO.createOrder(conn, order);
            if (orderId <= 0) {
                throw new SQLException("Failed to create order record.");
            }

            // Insert Order Items
            orderItemDAO.addOrderItems(conn, orderId, orderItems);

            // Record Payment
            Payment payment = new Payment();
            payment.setOrderId(orderId);
            payment.setPaymentMethod(order.getPaymentMethod());
            payment.setTransactionReference("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            payment.setAmount(totalPayable);
            payment.setPaymentStatus(order.getPaymentStatus().equals("PAID") ? "SUCCESS" : "PENDING");
            paymentDAO.recordPayment(conn, payment);

            // Record Coupon Usage
            if (validCoupon != null) {
                couponDAO.recordCouponUsage(conn, validCoupon.getCouponId(), userId, orderId);
            }

            // Clear Cart
            cartDAO.clearCart(conn, userId);

            // Commit transaction
            conn.commit();
            order.setOrderId(orderId);
            return order;

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Rollback failed: " + rollbackEx.getMessage());
                }
            }
            throw new RuntimeException("Checkout failed: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ignored) {}
            }
        }
    }
}
