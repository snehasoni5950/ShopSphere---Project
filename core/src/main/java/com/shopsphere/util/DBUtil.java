package com.shopsphere.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * RTU Syllabus: JDBC, java.sql, DriverManager, Connection, SQLException handling.
 */
public class DBUtil {

    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/shopsphere?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8";
    private static String username = "root";
    private static String password = "root";

    static {
        try (InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in != null) {
                Properties props = new Properties();
                props.load(in);
                driver = props.getProperty("db.driver", driver);
                url = props.getProperty("db.url", url);
                username = props.getProperty("db.username", username);
                password = props.getProperty("db.password", password);
            }
            Class.forName(driver);
        } catch (Exception e) {
            System.err.println("[DBUtil] Warning: Error initializing DB properties: " + e.getMessage());
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException ignored) {}
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public static void setCredentials(String newUrl, String newUsername, String newPassword) {
        url = newUrl;
        username = newUsername;
        password = newPassword;
    }

    public static void close(AutoCloseable... closeables) {
        for (AutoCloseable c : closeables) {
            if (c != null) {
                try {
                    c.close();
                } catch (Exception ignored) {}
            }
        }
    }
}
