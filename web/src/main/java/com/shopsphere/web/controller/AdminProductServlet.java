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

@WebServlet(name = "AdminProductServlet", urlPatterns = {"/admin/products"})
public class AdminProductServlet extends HttpServlet {

    private final ProductService productService = new ProductService();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String action = req.getParameter("action");
            if ("edit".equalsIgnoreCase(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                Product p = productService.getProductDetails(id);
                req.setAttribute("editProduct", p);
            } else if ("delete".equalsIgnoreCase(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                productService.deleteProduct(id);
                resp.sendRedirect(req.getContextPath() + "/admin/products?success=Product+deleted");
                return;
            }

            List<Product> products = productService.getAllProductsForAdmin();
            List<Category> categories = categoryDAO.getAllCategories();
            req.setAttribute("products", products);
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/admin/products.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Error: " + e.getMessage());
            req.getRequestDispatcher("/admin/products.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String idStr = req.getParameter("productId");
            int productId = (idStr != null && !idStr.trim().isEmpty()) ? Integer.parseInt(idStr.trim()) : 0;

            String name = req.getParameter("name");
            String brand = req.getParameter("brand");
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));
            String description = req.getParameter("description");
            double price = Double.parseDouble(req.getParameter("price"));
            double discount = Double.parseDouble(req.getParameter("discount"));
            int stock = Integer.parseInt(req.getParameter("stock"));
            String imageUrl = req.getParameter("imageUrl");
            boolean status = "on".equalsIgnoreCase(req.getParameter("status")) || "true".equalsIgnoreCase(req.getParameter("status"));

            Product p = new Product();
            p.setProductId(productId);
            p.setName(name);
            p.setBrand(brand);
            p.setCategoryId(categoryId);
            p.setDescription(description);
            p.setPrice(price);
            p.setDiscount(discount);
            p.setStock(stock);
            p.setImageUrl(imageUrl);
            p.setStatus(status);

            productService.saveProduct(p);
            resp.sendRedirect(req.getContextPath() + "/admin/products?success=Product+saved+successfully");
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/admin/products?error=" + e.getMessage());
        }
    }
}
