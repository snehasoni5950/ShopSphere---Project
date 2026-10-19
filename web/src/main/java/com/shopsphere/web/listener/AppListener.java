package com.shopsphere.web.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * RTU Syllabus: Event Handling with ServletContextListener and HttpSessionListener.
 * Page 8 Section 15 of project guide.
 */
@WebListener
public class AppListener implements ServletContextListener, HttpSessionListener {

    private final AtomicInteger activeSessions = new AtomicInteger(0);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.setAttribute("appName", "ShopSphere");
        context.setAttribute("appVersion", "1.0.0 (RTU Capstone)");
        context.setAttribute("activeUserCount", activeSessions);

        System.out.println("==========================================================");
        System.out.println(" [ShopSphere WebApp] Context Initialized on Apache Tomcat");
        System.out.println(" Servlet Specification: Jakarta EE 11 / Servlet 6.1");
        System.out.println(" Application Context Path: " + context.getContextPath());
        System.out.println("==========================================================");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[ShopSphere WebApp] Context Destroyed - shutting down cleanly.");
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        int count = activeSessions.incrementAndGet();
        System.out.println("[AppListener] HttpSession Created: " + se.getSession().getId() + " | Active: " + count);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        int count = activeSessions.decrementAndGet();
        System.out.println("[AppListener] HttpSession Destroyed: " + se.getSession().getId() + " | Active: " + count);
    }
}
