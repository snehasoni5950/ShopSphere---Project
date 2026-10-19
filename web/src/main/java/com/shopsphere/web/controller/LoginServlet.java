package com.shopsphere.web.controller;

import com.shopsphere.dao.CartDAO;
import com.shopsphere.model.User;
import com.shopsphere.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private final CartDAO cartDAO = new CartDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String rememberMe = req.getParameter("rememberMe");

        try {
            User user = userService.authenticate(email, password);
            if (user != null) {
                // Section 15: Session handling
                HttpSession session = req.getSession(true);
                session.setAttribute("user", user);
                session.setAttribute("role", user.getRole());

                // Populate cart count in session
                int cartCount = cartDAO.getCartCount(user.getUserId());
                session.setAttribute("cartCount", cartCount);

                // Section 15: Cookie handling (Remember email safely, never password)
                if ("on".equalsIgnoreCase(rememberMe) || "true".equalsIgnoreCase(rememberMe)) {
                    Cookie emailCookie = new Cookie("remembered_email", user.getEmail());
                    emailCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
                    emailCookie.setPath(req.getContextPath().isEmpty() ? "/" : req.getContextPath());
                    resp.addCookie(emailCookie);
                }

                String redirectUrl = (String) session.getAttribute("redirectAfterLogin");
                if (redirectUrl != null) {
                    session.removeAttribute("redirectAfterLogin");
                    resp.sendRedirect(redirectUrl);
                } else if (user.isAdmin()) {
                    resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/");
                }
            } else {
                req.setAttribute("errorMessage", "Invalid email or password.");
                req.setAttribute("email", email);
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Login failed: " + e.getMessage());
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
