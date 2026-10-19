package com.shopsphere.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * RTU Syllabus: Internationalization language switcher servlet.
 */
@WebServlet(name = "I18nServlet", urlPatterns = {"/i18n"})
public class I18nServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lang = req.getParameter("lang");
        if (lang == null || (!"hi".equalsIgnoreCase(lang) && !"en".equalsIgnoreCase(lang))) {
            lang = "en";
        }

        HttpSession session = req.getSession(true);
        session.setAttribute("lang", lang.toLowerCase());

        String referer = req.getHeader("Referer");
        if (referer != null && !referer.isEmpty()) {
            resp.sendRedirect(referer);
        } else {
            resp.sendRedirect(req.getContextPath() + "/demo/i18n-demo.jsp");
        }
    }
}
