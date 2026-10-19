package com.shopsphere.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RTU Syllabus: java.net URL, URLConnection, Protocol and Content Handlers.
 * Page 2 & 11 Section 24 of project guide.
 */
@WebServlet(name = "UrlProtocolDemoServlet", urlPatterns = {"/demo/url-protocol"})
public class UrlProtocolDemoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String testUrlStr = req.getParameter("url");
        if (testUrlStr == null || testUrlStr.trim().isEmpty()) {
            testUrlStr = "https://jsonplaceholder.typicode.com/posts/1";
        }

        Map<String, Object> urlDetails = new HashMap<>();
        try {
            URL url = new URL(testUrlStr);
            urlDetails.put("Protocol", url.getProtocol());
            urlDetails.put("Host", url.getHost());
            urlDetails.put("Port", url.getPort() != -1 ? url.getPort() : url.getDefaultPort());
            urlDetails.put("Path", url.getPath());
            urlDetails.put("Query", url.getQuery() != null ? url.getQuery() : "None");
            urlDetails.put("File", url.getFile());

            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);

            urlDetails.put("ContentType", conn.getContentType());
            urlDetails.put("ContentLength", conn.getContentLengthLong());
            urlDetails.put("ContentEncoding", conn.getContentEncoding());
            urlDetails.put("DateHeader", conn.getDate());

            Map<String, List<String>> headers = conn.getHeaderFields();
            req.setAttribute("headerFields", headers);
            req.setAttribute("success", true);

        } catch (Exception e) {
            urlDetails.put("Error", e.getMessage());
            req.setAttribute("success", false);
        }

        req.setAttribute("urlInput", testUrlStr);
        req.setAttribute("urlDetails", urlDetails);
        req.getRequestDispatcher("/demo/url-protocol.jsp").forward(req, resp);
    }
}
