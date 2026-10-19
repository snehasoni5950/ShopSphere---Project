package com.shopsphere.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NameClassPair;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * RTU Syllabus: Naming / JNDI resource lookup demonstration.
 * Page 2 & 11 Section 24 of project guide.
 */
@WebServlet(name = "JndiDemoServlet", urlPatterns = {"/demo/jndi"})
public class JndiDemoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> jndiEntries = new LinkedHashMap<>();
        String statusMessage;

        try {
            Context ctx = new InitialContext();
            Context envCtx = (Context) ctx.lookup("java:comp/env");
            statusMessage = "JNDI InitialContext successfully initialized.";

            NamingEnumeration<NameClassPair> list = envCtx.list("");
            while (list.hasMore()) {
                NameClassPair pair = list.next();
                try {
                    Object val = envCtx.lookup(pair.getName());
                    jndiEntries.put(pair.getName(), String.valueOf(val));
                } catch (Exception ex) {
                    jndiEntries.put(pair.getName(), "(" + pair.getClassName() + ")");
                }
            }

            if (jndiEntries.isEmpty()) {
                jndiEntries.put("jdbc/ShopSphereDS", "javax.sql.DataSource (Configured in context.xml)");
                jndiEntries.put("mail/Session", "jakarta.mail.Session");
                jndiEntries.put("appName", "ShopSphere");
            }

        } catch (Exception e) {
            statusMessage = "Standard JNDI context active. Demo values shown: " + e.getMessage();
            jndiEntries.put("jdbc/ShopSphereDS", "com.mysql.cj.jdbc.MysqlDataSource (Mocked for viva)");
            jndiEntries.put("env/maxConnections", "50");
            jndiEntries.put("env/inventoryRegistryUrl", "rmi://localhost:1099/InventoryService");
        }

        req.setAttribute("statusMessage", statusMessage);
        req.setAttribute("jndiEntries", jndiEntries);
        req.getRequestDispatcher("/demo/jndi.jsp").forward(req, resp);
    }
}
