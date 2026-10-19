package com.shopsphere.swing.view;

import com.shopsphere.model.User;
import com.shopsphere.swing.controller.LoginController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * RTU Syllabus: Swing Login Window, Pluggable Look-and-Feel, Event Listeners.
 */
public class LoginFrame extends JFrame {

    private final JTextField txtEmail = new JTextField("admin@shopsphere.com", 20);
    private final JPasswordField txtPassword = new JPasswordField("admin123", 20);
    private final JButton btnLogin = new JButton("Login to Portal");
    private final JButton btnExit = new JButton("Cancel");
    private final LoginController loginController = new LoginController();

    public LoginFrame() {
        setTitle("ShopSphere Desktop Admin - Login");
        setSize(450, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        initUI();
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout(15, 15));
        root.setBorder(new EmptyBorder(20, 25, 20, 25));

        // Header Banner
        JPanel header = new JPanel(new GridLayout(2, 1));
        JLabel title = new JLabel("ShopSphere Admin Portal", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(24, 43, 73));

        JLabel subtitle = new JLabel("RTU Advanced Java / Java Enterprise Project", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(Color.GRAY);

        header.add(title);
        header.add(subtitle);
        root.add(header, BorderLayout.NORTH);

        // Form Panel
        JPanel form = new JPanel(new GridLayout(2, 2, 10, 15));
        form.setBorder(new EmptyBorder(15, 5, 15, 5));

        JLabel lblEmail = new JLabel("Admin Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 12));
        JLabel lblPass = new JLabel("Password:");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 12));

        form.add(lblEmail);
        form.add(txtEmail);
        form.add(lblPass);
        form.add(txtPassword);

        root.add(form, BorderLayout.CENTER);

        // Buttons Panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        btnLogin.setBackground(new Color(24, 43, 73));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 12));

        btnLogin.addActionListener(e -> performLogin());
        txtPassword.addActionListener(e -> performLogin());
        btnExit.addActionListener(e -> System.exit(0));

        btnPanel.add(btnExit);
        btnPanel.add(btnLogin);
        root.add(btnPanel, BorderLayout.SOUTH);

        setContentPane(root);
    }

    private void performLogin() {
        String email = txtEmail.getText().trim();
        String pass = new String(txtPassword.getPassword()).trim();

        try {
            User admin = loginController.authenticateAdmin(email, pass);
            JOptionPane.showMessageDialog(this, "Welcome back, " + admin.getName() + "!\nLaunching Desktop Admin Dashboard...", "Authentication Succeeded", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            SwingUtilities.invokeLater(() -> new AdminDashboardFrame(admin).setVisible(true));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Login Failed: " + ex.getMessage(), "Authentication Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Section 21: Pluggable Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
