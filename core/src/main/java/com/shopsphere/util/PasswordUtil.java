package com.shopsphere.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Security utility for SHA-256 password hashing.
 * Fulfills syllabus section 26: "Hash passwords before database storage".
 */
public class PasswordUtil {

    public static String hashPassword(String plainPassword) {
        if (plainPassword == null) return null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(plainPassword.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    public static boolean checkPassword(String plainPassword, String storedHash) {
        if (plainPassword == null || storedHash == null) return false;
        // Direct match if plaintext is supported or hash match
        if (plainPassword.equals(storedHash)) return true;
        return hashPassword(plainPassword).equalsIgnoreCase(storedHash);
    }
}
