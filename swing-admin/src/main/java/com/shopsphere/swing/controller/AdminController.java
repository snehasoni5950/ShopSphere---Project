package com.shopsphere.swing.controller;

import com.shopsphere.dao.CategoryDAO;
import com.shopsphere.model.Category;
import com.shopsphere.model.Order;
import com.shopsphere.model.Product;
import com.shopsphere.model.User;
import com.shopsphere.service.OrderService;
import com.shopsphere.service.ProductService;
import com.shopsphere.service.UserService;

import java.util.List;

/**
 * RTU Syllabus: Swing MVC - Admin Controller orchestrating Services/DAOs.
 */
public class AdminController {

    private final ProductService productService = new ProductService();
    private final OrderService orderService = new OrderService();
    private final UserService userService = new UserService();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    public List<Product> loadAllProducts() throws Exception {
        return productService.getAllProductsForAdmin();
    }

    public List<Category> loadCategories() throws Exception {
        return categoryDAO.getAllCategories();
    }

    public boolean saveProduct(Product product) throws Exception {
        return productService.saveProduct(product);
    }

    public boolean deleteProduct(int productId) throws Exception {
        return productService.deleteProduct(productId);
    }

    public boolean updateStock(int productId, int stock) throws Exception {
        return productService.updateStock(productId, stock);
    }

    public List<Product> loadLowStockAlerts(int threshold) throws Exception {
        return productService.getLowStockAlerts(threshold);
    }

    public List<Order> loadAllOrders() throws Exception {
        return orderService.getAllOrdersForAdmin();
    }

    public boolean updateOrderStatus(int orderId, String newStatus) throws Exception {
        return orderService.updateOrderStatus(orderId, newStatus);
    }

    public List<User> loadAllCustomers() throws Exception {
        return userService.getAllUsers();
    }

    public boolean addCategory(Category category) throws Exception {
        return categoryDAO.addCategory(category);
    }

    public boolean deleteCategory(int categoryId) throws Exception {
        return categoryDAO.deleteCategory(categoryId);
    }
}
