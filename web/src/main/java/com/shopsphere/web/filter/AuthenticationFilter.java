package com.shopsphere.web.filter;

import com.shopsphere.model.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * RTU Syllabus: Filter Lifecycle & AuthenticationFilter.
 * Protects customer workflows like Cart, Checkout, Profile, Orders, Wishlist.
 */
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {
        "/cart/*", "/checkout/*", "/orders/*", "/order-details/*",
        "/order-success/*", "/profile/*", "/wishlist/*", "/review/*"
})
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Filter initialization lifecycle
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            String uri = req.getRequestURI();
            String query = req.getQueryString();
            String redirectUrl = (query != null) ? uri + "?" + query : uri;

            req.getSession(true).setAttribute("redirectAfterLogin", redirectUrl);
            resp.sendRedirect(req.getContextPath() + "/login?error=Please+login+to+access+this+page");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Filter destruction lifecycle
    }
}
