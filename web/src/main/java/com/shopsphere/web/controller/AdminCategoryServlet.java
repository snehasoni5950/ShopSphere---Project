package com.shopsphere.web.controller;

import com.shopsphere.dao.CategoryDAO;
import com.shopsphere.model.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminCategoryServlet", urlPatterns = {"/admin/categories"})
public class AdminCategoryServlet extends HttpServlet {

    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("delete".equalsIgnoreCase(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                categoryDAO.deleteCategory(id);
                resp.sendRedirect(req.getContextPath() + "/admin/categories?success=Category+deleted");
                return;
            }

            List<Category> categories = categoryDAO.getAllCategories();
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/admin/categories.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Error: " + e.getMessage());
            req.getRequestDispatcher("/admin/categories.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            boolean status = "on".equalsIgnoreCase(req.getParameter("status")) || "true".equalsIgnoreCase(req.getParameter("status"));

            Category c = new Category(0, name, description, status);
            categoryDAO.addCategory(c);
            resp.sendRedirect(req.getContextPath() + "/admin/categories?success=Category+added");
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/admin/categories?error=" + e.getMessage());
        }
    }
}
