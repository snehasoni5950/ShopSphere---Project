package com.shopsphere.rmi.client;

import com.shopsphere.model.Product;
import com.shopsphere.rmi.remote.InventoryService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

/**
 * RTU Syllabus: RMI Client lookup & Remote Method Calls.
 */
public class RmiClientMain {

    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 1099;
    public static final String SERVICE_NAME = "InventoryService";

    public static InventoryService getService(String host, int port) throws Exception {
        Registry registry = LocateRegistry.getRegistry(host, port);
        return (InventoryService) registry.lookup(SERVICE_NAME);
    }

    public static void runAutomatedDemo(String host, int port) {
        System.out.println("\n--- RUNNING AUTOMATED RMI DEMONSTRATION ---");
        try {
            InventoryService service = getService(host, port);
            System.out.println("[1] Connected to Remote InventoryService via RMI Registry.");

            // 1. Get Product
            System.out.println("\n[2] Invoking remote getProduct(1)...");
            Product p = service.getProduct(1);
            if (p != null) {
                System.out.println("-> Remote Product: " + p.getName() + " | Brand: " + p.getBrand() + " | Price: ₹" + p.getPrice());
            }

            // 2. Get Stock
            System.out.println("\n[3] Invoking remote getStock(1)...");
            int stock = service.getStock(1);
            System.out.println("-> Current remote stock: " + stock);

            // 3. Low stock alerts
            System.out.println("\n[4] Invoking remote getLowStockAlerts(5)...");
            List<Product> lowStock = service.getLowStockAlerts(5);
            System.out.println("-> Products with stock <= 5: " + lowStock.size());
            for (Product lp : lowStock) {
                System.out.println("   * [" + lp.getProductId() + "] " + lp.getName() + " (Stock: " + lp.getStock() + ")");
            }

            System.out.println("\n--- RMI DEMONSTRATION COMPLETE SUCCESSFUL ---");

        } catch (Exception e) {
            System.err.println("RMI Demo failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String host = DEFAULT_HOST;
        int port = DEFAULT_PORT;

        if (args.length > 0 && ("--demo".equalsIgnoreCase(args[0]) || "-d".equalsIgnoreCase(args[0]))) {
            runAutomatedDemo(host, port);
            return;
        }

        System.out.println("=================================================");
        System.out.println("  ShopSphere RMI Client");
        System.out.println("  Connecting to RMI Registry at " + host + ":" + port);
        System.out.println("=================================================");

        try {
            InventoryService service = getService(host, port);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\nSelect an RMI action:");
                System.out.println("1. Remote getProduct(id)");
                System.out.println("2. Remote getStock(productId)");
                System.out.println("3. Remote updateStock(productId, newQuantity)");
                System.out.println("4. Remote listAllProducts()");
                System.out.println("5. Remote getLowStockAlerts(threshold)");
                System.out.println("6. Run Automated RMI Batch Demo");
                System.out.println("0. Exit");
                System.out.print("Choice: ");

                String choice = scanner.nextLine().trim();
                if ("0".equals(choice)) {
                    System.out.println("Exiting RMI Client.");
                    break;
                }

                try {
                    switch (choice) {
                        case "1":
                            System.out.print("Enter Product ID: ");
                            int id = Integer.parseInt(scanner.nextLine().trim());
                            Product p = service.getProduct(id);
                            if (p != null) {
                                System.out.println("Result: " + p.getName() + " | Price: ₹" + p.getPrice() + " | Stock: " + p.getStock());
                            } else {
                                System.out.println("No product found with ID " + id);
                            }
                            break;

                        case "2":
                            System.out.print("Enter Product ID: ");
                            int sid = Integer.parseInt(scanner.nextLine().trim());
                            int stock = service.getStock(sid);
                            System.out.println("Remote Stock Result: " + stock);
                            break;

                        case "3":
                            System.out.print("Enter Product ID: ");
                            int uid = Integer.parseInt(scanner.nextLine().trim());
                            System.out.print("Enter New Quantity: ");
                            int qty = Integer.parseInt(scanner.nextLine().trim());
                            boolean ok = service.updateStock(uid, qty);
                            System.out.println("Remote Update Result: " + (ok ? "SUCCESS" : "FAILED"));
                            break;

                        case "4":
                            List<Product> list = service.getAllProducts();
                            System.out.println("Total Remote Products: " + list.size());
                            for (Product prod : list) {
                                System.out.println(" - [" + prod.getProductId() + "] " + prod.getName() + " (₹" + prod.getPrice() + ", Stock: " + prod.getStock() + ")");
                            }
                            break;

                        case "5":
                            System.out.print("Enter Stock Threshold (e.g. 5): ");
                            int thresh = Integer.parseInt(scanner.nextLine().trim());
                            List<Product> low = service.getLowStockAlerts(thresh);
                            System.out.println("Low Stock Products: " + low.size());
                            for (Product prod : low) {
                                System.out.println(" * " + prod.getName() + " - Stock: " + prod.getStock());
                            }
                            break;

                        case "6":
                            runAutomatedDemo(host, port);
                            break;

                        default:
                            System.out.println("Invalid selection.");
                    }
                } catch (Exception ex) {
                    System.err.println("Remote execution error: " + ex.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("[RMI Client] Could not connect to RMI Registry: " + e.getMessage());
            System.out.println("Ensure RmiServerMain is running first.");
        }
    }
}
