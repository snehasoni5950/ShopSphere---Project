package com.shopsphere.web.controller;

import com.shopsphere.model.Address;
import com.shopsphere.model.User;
import com.shopsphere.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        try {
            User freshUser = userService.getUserById(user.getUserId());
            List<Address> addresses = userService.getAddresses(user.getUserId());
            req.setAttribute("user", freshUser);
            req.setAttribute("addresses", addresses);
            req.getRequestDispatcher("/profile.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Error loading profile: " + e.getMessage());
            req.getRequestDispatcher("/profile.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String action = req.getParameter("action");

        try {
            if ("addAddress".equalsIgnoreCase(action)) {
                String line = req.getParameter("addressLine");
                String city = req.getParameter("city");
                String state = req.getParameter("state");
                String pincode = req.getParameter("pincode");
                String type = req.getParameter("addressType");

                Address addr = new Address(0, user.getUserId(), line, city, state, pincode, type);
                userService.addAddress(addr);
                resp.sendRedirect(req.getContextPath() + "/profile?success=Address+added+successfully!");
            } else if ("updateProfile".equalsIgnoreCase(action)) {
                String name = req.getParameter("name");
                String mobile = req.getParameter("mobile");

                user.setName(name);
                user.setMobile(mobile);
                userService.updateProfile(user);
                req.getSession().setAttribute("user", user);
                resp.sendRedirect(req.getContextPath() + "/profile?success=Profile+updated+successfully!");
            }
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/profile?error=" + e.getMessage());
        }
    }
}
