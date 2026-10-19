package com.shopsphere.service;

import com.shopsphere.dao.OrderDAO;
import com.shopsphere.dao.OrderItemDAO;
import com.shopsphere.dao.PaymentDAO;
import com.shopsphere.dao.ProductDAO;
import com.shopsphere.model.Order;
import com.shopsphere.model.OrderItem;
import com.shopsphere.model.Payment;

import java.sql.SQLException;
import java.util.List;

public class OrderService {

    private final OrderDAO orderDAO = new OrderDAO();
    private final OrderItemDAO orderItemDAO = new OrderItemDAO();
    private final ProductDAO productDAO = new ProductDAO();
    private final PaymentDAO paymentDAO = new PaymentDAO();

    public List<Order> getUserOrders(int userId) throws SQLException {
        List<Order> orders = orderDAO.getOrdersByUser(userId);
        for (Order o : orders) {
            o.setItems(orderItemDAO.getItemsByOrderId(o.getOrderId()));
        }
        return orders;
    }

    public List<Order> getAllOrdersForAdmin() throws SQLException {
        List<Order> orders = orderDAO.getAllOrders();
        for (Order o : orders) {
            o.setItems(orderItemDAO.getItemsByOrderId(o.getOrderId()));
        }
        return orders;
    }

    public Order getOrderDetails(int orderId) throws SQLException {
        Order order = orderDAO.getOrderById(orderId);
        if (order != null) {
            order.setItems(orderItemDAO.getItemsByOrderId(orderId));
        }
        return order;
    }

    public Payment getOrderPayment(int orderId) throws SQLException {
        return paymentDAO.getPaymentByOrderId(orderId);
    }

    public boolean updateOrderStatus(int orderId, String newStatus) throws SQLException {
        return orderDAO.updateOrderStatus(orderId, newStatus);
    }

    public int getTotalOrderCount() throws SQLException {
        return orderDAO.getTotalOrderCount();
    }

    public double getTotalRevenue() throws SQLException {
        return orderDAO.getTotalRevenue();
    }
}
