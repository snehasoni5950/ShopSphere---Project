package com.shopsphere.web.controller;

import com.shopsphere.model.Review;
import com.shopsphere.model.User;
import com.shopsphere.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ReviewServlet", urlPatterns = {"/review"})
public class ReviewServlet extends HttpServlet {

    private final ProductService productService = new ProductService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        int productId = Integer.parseInt(req.getParameter("productId"));
        int rating = Integer.parseInt(req.getParameter("rating"));
        String reviewText = req.getParameter("reviewText");

        try {
            Review review = new Review();
            review.setUserId(user.getUserId());
            review.setProductId(productId);
            review.setRating(rating);
            review.setReviewText(reviewText);

            productService.addReview(review);
            resp.sendRedirect(req.getContextPath() + "/product-details?id=" + productId + "&success=Review+submitted+successfully!");
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/product-details?id=" + productId + "&error=" + e.getMessage());
        }
    }
}
