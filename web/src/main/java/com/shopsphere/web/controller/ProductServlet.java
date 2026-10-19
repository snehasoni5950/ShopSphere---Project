package com.shopsphere.web.controller;

import com.shopsphere.dao.CategoryDAO;
import com.shopsphere.model.Category;
import com.shopsphere.model.Product;
import com.shopsphere.model.Review;
import com.shopsphere.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductServlet", urlPatterns = {"/products", "/product-details"})
public class ProductServlet extends HttpServlet {

    private final ProductService productService = new ProductService();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        try {
            if ("/product-details".equals(path)) {
                handleProductDetails(req, resp);
            } else {
                handleProductListing(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Error: " + e.getMessage());
            req.getRequestDispatcher("/products.jsp").forward(req, resp);
        }
    }

    private void handleProductListing(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String query = req.getParameter("q");
        String catParam = req.getParameter("category");

        List<Product> products;
        if (query != null && !query.trim().isEmpty()) {
            products = productService.searchProducts(query);
            req.setAttribute("searchQuery", query);
        } else if (catParam != null && !catParam.trim().isEmpty()) {
            int categoryId = Integer.parseInt(catParam.trim());
            products = productService.getProductsByCategory(categoryId);
            req.setAttribute("selectedCategory", categoryId);
        } else {
            products = productService.getAllActiveProducts();
        }

        List<Category> categories = categoryDAO.getAllCategories();
        req.setAttribute("products", products);
        req.setAttribute("categories", categories);
        req.getRequestDispatcher("/products.jsp").forward(req, resp);
    }

    private void handleProductDetails(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String idParam = req.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/products");
            return;
        }

        int productId = Integer.parseInt(idParam.trim());
        Product product = productService.getProductDetails(productId);
        if (product == null) {
            resp.sendRedirect(req.getContextPath() + "/products?error=Product+not+found");
            return;
        }

        List<Review> reviews = productService.getReviews(productId);
        req.setAttribute("product", product);
        req.setAttribute("reviews", reviews);
        req.getRequestDispatcher("/product-details.jsp").forward(req, resp);
    }
}
