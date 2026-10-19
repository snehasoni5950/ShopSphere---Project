package com.shopsphere.web.controller;

import com.shopsphere.dao.CategoryDAO;
import com.shopsphere.model.Category;
import com.shopsphere.model.Product;
import com.shopsphere.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeServlet", urlPatterns = {"", "/home", "/index"})
public class HomeServlet extends HttpServlet {

    private final ProductService productService = new ProductService();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Product> products = productService.getAllActiveProducts();
            List<Category> categories = categoryDAO.getAllCategories();

            req.setAttribute("products", products);
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Error loading home catalog: " + e.getMessage());
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}
