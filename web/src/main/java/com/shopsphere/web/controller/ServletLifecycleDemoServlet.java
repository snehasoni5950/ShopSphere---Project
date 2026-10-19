package com.shopsphere.web.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * RTU Syllabus: Servlet Lifecycle, ServletConfig & ServletContext.
 * Page 8 Section 14 of project guide.
 */
@WebServlet(
        name = "ServletLifecycleDemoServlet",
        urlPatterns = {"/demo/servlet-lifecycle"},
        initParams = {
                @WebInitParam(name = "moduleOwner", value = "RTU Student Project Team"),
                @WebInitParam(name = "auditLevel", value = "HIGH")
        }
)
public class ServletLifecycleDemoServlet extends HttpServlet {

    private final AtomicInteger requestCounter = new AtomicInteger(0);
    private String initTimestamp;
    private String moduleOwner;
    private String auditLevel;
    private final List<String> lifecycleEvents = new ArrayList<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.initTimestamp = new Date().toString();
        this.moduleOwner = config.getInitParameter("moduleOwner");
        this.auditLevel = config.getInitParameter("auditLevel");
        lifecycleEvents.add("1. [Loading & Instantiation] Servlet Class instantiated by Tomcat web container.");
        lifecycleEvents.add("2. [init(ServletConfig)] Executed once at: " + initTimestamp);
        System.out.println("[ServletLifecycleDemoServlet] init() invoked successfully.");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int count = requestCounter.incrementAndGet();
        req.setAttribute("serviceHitNumber", count);
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletConfig config = getServletConfig();
        ServletContext context = getServletContext();

        req.setAttribute("servletName", config.getServletName());
        req.setAttribute("moduleOwner", moduleOwner);
        req.setAttribute("auditLevel", auditLevel);
        req.setAttribute("initTime", initTimestamp);
        req.setAttribute("requestCount", requestCounter.get());
        req.setAttribute("serverInfo", context.getServerInfo());
        req.setAttribute("majorVersion", context.getMajorVersion());
        req.setAttribute("minorVersion", context.getMinorVersion());
        req.setAttribute("appName", context.getAttribute("appName"));
        req.setAttribute("lifecycleEvents", lifecycleEvents);

        req.getRequestDispatcher("/demo/servlet-lifecycle.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {
        System.out.println("[ServletLifecycleDemoServlet] destroy() called by container.");
        super.destroy();
    }
}
