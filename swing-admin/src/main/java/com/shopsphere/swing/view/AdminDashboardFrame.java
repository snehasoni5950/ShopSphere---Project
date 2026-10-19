package com.shopsphere.swing.view;

import com.shopsphere.model.Category;
import com.shopsphere.model.Order;
import com.shopsphere.model.Product;
import com.shopsphere.model.User;
import com.shopsphere.swing.controller.AdminController;
import com.shopsphere.swing.model.CustomerTableModel;
import com.shopsphere.swing.model.OrderTableModel;
import com.shopsphere.swing.model.ProductTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * RTU Syllabus: Swing Admin Application - JFrame, JPanel, JTable, JScrollPane,
 * JTabbedPane, JComboBox, JTextField, JCheckBox, JButton, JOptionPane.
 */
public class AdminDashboardFrame extends JFrame {

    private final AdminController controller;
    private final User currentAdmin;

    // Table Models
    private final ProductTableModel productTableModel = new ProductTableModel();
    private final OrderTableModel orderTableModel = new OrderTableModel();
    private final CustomerTableModel customerTableModel = new CustomerTableModel();
    private final DefaultTableModel categoryTableModel = new DefaultTableModel(new String[]{"ID", "Category Name", "Description", "Active"}, 0);
    private final ProductTableModel inventoryTableModel = new ProductTableModel();

    // UI Components for Product Form
    private final JTextField txtProductId = new JTextField();
    private final JTextField txtProductName = new JTextField();
    private final JTextField txtBrand = new JTextField();
    private final JComboBox<CategoryItem> cbCategory = new JComboBox<>();
    private final JTextField txtPrice = new JTextField();
    private final JTextField txtDiscount = new JTextField("0.0");
    private final JTextField txtStock = new JTextField("10");
    private final JTextField txtImageUrl = new JTextField();
    private final JCheckBox chkStatus = new JCheckBox("Active / Published", true);

    // UI Components for Order Form
    private final JComboBox<String> cbOrderStatus = new JComboBox<>(new String[]{"PLACED", "CONFIRMED", "SHIPPED", "DELIVERED", "CANCELLED"});

    public AdminDashboardFrame(User admin) {
        this.currentAdmin = admin;
        this.controller = new AdminController();

        setTitle("ShopSphere Desktop Administration Portal - " + admin.getName());
        setSize(1100, 720);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
        refreshAllData();
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Header Bar
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(24, 43, 73));
        headerPanel.setBorder(new EmptyBorder(12, 16, 12, 16));

        JLabel lblTitle = new JLabel("ShopSphere Enterprise Administration (Swing MVC)");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);

        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightHeader.setOpaque(false);

        JLabel lblAdmin = new JLabel("Logged in as: " + currentAdmin.getName() + " (" + currentAdmin.getEmail() + ")");
        lblAdmin.setForeground(new Color(200, 220, 255));
        lblAdmin.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        rightHeader.add(lblAdmin);
        rightHeader.add(btnLogout);
        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(rightHeader, BorderLayout.EAST);
        root.add(headerPanel, BorderLayout.NORTH);

        // Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        tabbedPane.addTab("Products Management", createProductPanel());
        tabbedPane.addTab("Categories", createCategoryPanel());
        tabbedPane.addTab("Customer Orders", createOrderPanel());
        tabbedPane.addTab("Customers List", createCustomerPanel());
        tabbedPane.addTab("Inventory & Low-Stock Alerts", createInventoryPanel());

        root.add(tabbedPane, BorderLayout.CENTER);

        // Footer Bar
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footer.add(new JLabel("ShopSphere RTU Advanced Java Capstone Project | JDBC + Swing MVC + MySQL"));
        root.add(footer, BorderLayout.SOUTH);

        setContentPane(root);
    }

    // 1. Products Panel
    private JPanel createProductPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JTable table = new JTable(productTableModel);
        table.setRowHeight(24);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);

        // Form Panel
        JPanel form = new JPanel(new GridLayout(9, 2, 8, 8));
        form.setBorder(new TitledBorder("Product Details Form"));

        txtProductId.setEditable(false);
        txtProductId.setBackground(new Color(240, 240, 240));

        form.add(new JLabel("Product ID (Auto):"));
        form.add(txtProductId);
        form.add(new JLabel("Product Name:"));
        form.add(txtProductName);
        form.add(new JLabel("Brand:"));
        form.add(txtBrand);
        form.add(new JLabel("Category:"));
        form.add(cbCategory);
        form.add(new JLabel("Price (₹):"));
        form.add(txtPrice);
        form.add(new JLabel("Discount (%):"));
        form.add(txtDiscount);
        form.add(new JLabel("Stock Quantity:"));
        form.add(txtStock);
        form.add(new JLabel("Image URL:"));
        form.add(txtImageUrl);
        form.add(new JLabel("Status:"));
        form.add(chkStatus);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        JButton btnSave = new JButton("Save / Update Product");
        JButton btnClear = new JButton("Clear Form");
        JButton btnDelete = new JButton("Delete Selected");
        JButton btnRefresh = new JButton("Refresh");

        btnSave.addActionListener(e -> saveProduct());
        btnClear.addActionListener(e -> clearProductForm());
        btnDelete.addActionListener(e -> deleteSelectedProduct(table));
        btnRefresh.addActionListener(e -> loadProducts());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);

        JPanel eastWrapper = new JPanel(new BorderLayout());
        eastWrapper.setPreferredSize(new Dimension(380, 0));
        eastWrapper.add(form, BorderLayout.CENTER);
        eastWrapper.add(buttonPanel, BorderLayout.SOUTH);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                Product p = productTableModel.getProductAt(table.getSelectedRow());
                if (p != null) {
                    populateProductForm(p);
                }
            }
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(eastWrapper, BorderLayout.EAST);
        return panel;
    }

    // 2. Categories Panel
    private JPanel createCategoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JTable table = new JTable(categoryTableModel);
        table.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        form.setBorder(new TitledBorder("Add New Category"));

        JTextField txtCatName = new JTextField();
        JTextField txtCatDesc = new JTextField();
        JCheckBox chkCatActive = new JCheckBox("Active", true);
        JButton btnAddCat = new JButton("Add Category");
        JButton btnDelCat = new JButton("Delete Selected Category");

        form.add(new JLabel("Category Name:"));
        form.add(txtCatName);
        form.add(new JLabel("Description:"));
        form.add(txtCatDesc);
        form.add(new JLabel("Status:"));
        form.add(chkCatActive);
        form.add(new JLabel(""));
        form.add(btnAddCat);

        btnAddCat.addActionListener(e -> {
            String name = txtCatName.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Category name is required", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                Category c = new Category(0, name, txtCatDesc.getText().trim(), chkCatActive.isSelected());
                controller.addCategory(c);
                txtCatName.setText("");
                txtCatDesc.setText("");
                loadCategories();
                JOptionPane.showMessageDialog(this, "Category added successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding category: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnDelCat.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a category to delete");
                return;
            }
            int id = (int) categoryTableModel.getValueAt(row, 0);
            int opt = JOptionPane.showConfirmDialog(this, "Delete category ID " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (opt == JOptionPane.YES_OPTION) {
                try {
                    controller.deleteCategory(id);
                    loadCategories();
                    JOptionPane.showMessageDialog(this, "Category deleted.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel eastWrapper = new JPanel(new BorderLayout());
        eastWrapper.setPreferredSize(new Dimension(340, 0));
        eastWrapper.add(form, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(btnDelCat);
        eastWrapper.add(btnPanel, BorderLayout.SOUTH);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(eastWrapper, BorderLayout.EAST);
        return panel;
    }

    // 3. Orders Panel
    private JPanel createOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JTable table = new JTable(orderTableModel);
        table.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        actionPanel.add(new JLabel("Update Selected Order Status:"));
        actionPanel.add(cbOrderStatus);

        JButton btnUpdateStatus = new JButton("Update Status");
        JButton btnRefreshOrders = new JButton("Refresh Orders");

        btnUpdateStatus.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select an order from the table to update status");
                return;
            }
            Order o = orderTableModel.getOrderAt(row);
            if (o != null) {
                String newStatus = (String) cbOrderStatus.getSelectedItem();
                try {
                    controller.updateOrderStatus(o.getOrderId(), newStatus);
                    loadOrders();
                    JOptionPane.showMessageDialog(this, "Order #" + o.getOrderId() + " updated to " + newStatus);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Update failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnRefreshOrders.addActionListener(e -> loadOrders());

        actionPanel.add(btnUpdateStatus);
        actionPanel.add(btnRefreshOrders);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(actionPanel, BorderLayout.SOUTH);
        return panel;
    }

    // 4. Customers Panel
    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JTable table = new JTable(customerTableModel);
        table.setRowHeight(24);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnRefresh = new JButton("Refresh Customers");
        btnRefresh.addActionListener(e -> loadCustomers());
        bottom.add(btnRefresh);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    // 5. Inventory Panel
    private JPanel createInventoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JTable table = new JTable(inventoryTableModel);
        table.setRowHeight(24);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        controlPanel.setBorder(new TitledBorder("Inventory Restock Controller"));

        JTextField txtThreshold = new JTextField("5", 4);
        JButton btnFilterLowStock = new JButton("Filter Low Stock");
        JTextField txtAddQty = new JTextField("25", 5);
        JButton btnQuickRestock = new JButton("Restock Selected Product (+Qty)");

        btnFilterLowStock.addActionListener(e -> {
            try {
                int threshold = Integer.parseInt(txtThreshold.getText().trim());
                List<Product> low = controller.loadLowStockAlerts(threshold);
                inventoryTableModel.setProducts(low);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid threshold number: " + ex.getMessage());
            }
        });

        btnQuickRestock.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a product to restock.");
                return;
            }
            Product p = inventoryTableModel.getProductAt(row);
            if (p != null) {
                try {
                    int add = Integer.parseInt(txtAddQty.getText().trim());
                    int newStock = p.getStock() + add;
                    controller.updateStock(p.getProductId(), newStock);
                    JOptionPane.showMessageDialog(this, "Product '" + p.getName() + "' restocked! New Stock: " + newStock);
                    refreshAllData();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error restocking: " + ex.getMessage());
                }
            }
        });

        controlPanel.add(new JLabel("Stock Threshold:"));
        controlPanel.add(txtThreshold);
        controlPanel.add(btnFilterLowStock);
        controlPanel.add(new JSeparator(SwingConstants.VERTICAL));
        controlPanel.add(new JLabel("Add Quantity:"));
        controlPanel.add(txtAddQty);
        controlPanel.add(btnQuickRestock);

        panel.add(controlPanel, BorderLayout.SOUTH);
        return panel;
    }

    // Data Loaders
    private void refreshAllData() {
        loadCategories();
        loadProducts();
        loadOrders();
        loadCustomers();
    }

    private void loadProducts() {
        try {
            List<Product> list = controller.loadAllProducts();
            productTableModel.setProducts(list);
            inventoryTableModel.setProducts(list);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading products: " + e.getMessage());
        }
    }

    private void loadCategories() {
        try {
            List<Category> list = controller.loadCategories();
            categoryTableModel.setRowCount(0);
            cbCategory.removeAllItems();
            for (Category c : list) {
                categoryTableModel.addRow(new Object[]{c.getCategoryId(), c.getName(), c.getDescription(), c.isStatus()});
                cbCategory.addItem(new CategoryItem(c.getCategoryId(), c.getName()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading categories: " + e.getMessage());
        }
    }

    private void loadOrders() {
        try {
            List<Order> list = controller.loadAllOrders();
            orderTableModel.setOrders(list);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading orders: " + e.getMessage());
        }
    }

    private void loadCustomers() {
        try {
            List<User> list = controller.loadAllCustomers();
            customerTableModel.setUsers(list);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading customers: " + e.getMessage());
        }
    }

    private void populateProductForm(Product p) {
        txtProductId.setText(String.valueOf(p.getProductId()));
        txtProductName.setText(p.getName());
        txtBrand.setText(p.getBrand());
        txtPrice.setText(String.valueOf(p.getPrice()));
        txtDiscount.setText(String.valueOf(p.getDiscount()));
        txtStock.setText(String.valueOf(p.getStock()));
        txtImageUrl.setText(p.getImageUrl());
        chkStatus.setSelected(p.isStatus());

        for (int i = 0; i < cbCategory.getItemCount(); i++) {
            if (cbCategory.getItemAt(i).id == p.getCategoryId()) {
                cbCategory.setSelectedIndex(i);
                break;
            }
        }
    }

    private void clearProductForm() {
        txtProductId.setText("");
        txtProductName.setText("");
        txtBrand.setText("");
        txtPrice.setText("");
        txtDiscount.setText("0.0");
        txtStock.setText("10");
        txtImageUrl.setText("");
        chkStatus.setSelected(true);
    }

    private void saveProduct() {
        try {
            String name = txtProductName.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Product name cannot be empty.", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            CategoryItem item = (CategoryItem) cbCategory.getSelectedItem();
            int catId = (item != null) ? item.id : 1;

            int id = txtProductId.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtProductId.getText().trim());
            double price = Double.parseDouble(txtPrice.getText().trim());
            double discount = Double.parseDouble(txtDiscount.getText().trim());
            int stock = Integer.parseInt(txtStock.getText().trim());

            Product p = new Product();
            p.setProductId(id);
            p.setName(name);
            p.setBrand(txtBrand.getText().trim());
            p.setCategoryId(catId);
            p.setPrice(price);
            p.setDiscount(discount);
            p.setStock(stock);
            p.setImageUrl(txtImageUrl.getText().trim());
            p.setStatus(chkStatus.isSelected());

            controller.saveProduct(p);
            JOptionPane.showMessageDialog(this, "Product saved successfully!");
            clearProductForm();
            loadProducts();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to save product: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelectedProduct(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product from the table to delete.");
            return;
        }
        Product p = productTableModel.getProductAt(row);
        if (p != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete '" + p.getName() + "'?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    controller.deleteProduct(p.getProductId());
                    clearProductForm();
                    loadProducts();
                    JOptionPane.showMessageDialog(this, "Product deleted.");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Delete failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private static class CategoryItem {
        final int id;
        final String name;

        CategoryItem(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
