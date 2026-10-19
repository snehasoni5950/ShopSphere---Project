package com.shopsphere.network.client;

import com.shopsphere.model.Product;
import com.shopsphere.network.protocol.NetworkCommand;
import com.shopsphere.network.protocol.NetworkResponse;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

/**
 * RTU Syllabus: java.net Socket client, command dispatch, response deserialization.
 */
public class ShopSphereInventoryClient {

    private final String host;
    private final int port;

    public ShopSphereInventoryClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public NetworkResponse sendCommand(NetworkCommand cmd) throws Exception {
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(cmd);
            out.flush();

            return (NetworkResponse) in.readObject();
        }
    }

    public static void runAutomatedDemo(String host, int port) {
        System.out.println("\n--- RUNNING AUTOMATED SOCKET PROTOCOL DEMONSTRATION ---");
        ShopSphereInventoryClient client = new ShopSphereInventoryClient(host, port);

        try {
            // 1. PING
            System.out.println("\n[1] Testing PING...");
            NetworkResponse pingResp = client.sendCommand(new NetworkCommand(NetworkCommand.PING, null));
            System.out.println("Result: " + pingResp);

            // 2. GET_PRODUCT
            System.out.println("\n[2] Testing GET_PRODUCT (ID=1)...");
            NetworkResponse prodResp = client.sendCommand(new NetworkCommand(NetworkCommand.GET_PRODUCT, "1"));
            System.out.println("Result: " + prodResp);
            if (prodResp.isSuccess() && prodResp.getPayload() instanceof Product p) {
                System.out.println("-> Deserialized Product Name: " + p.getName());
                System.out.println("-> Deserialized Product Price: ₹" + p.getPrice());
                System.out.println("-> Deserialized Current Stock: " + p.getStock());
            }

            // 3. GET_STOCK
            System.out.println("\n[3] Testing GET_STOCK (ID=1)...");
            NetworkResponse stockResp = client.sendCommand(new NetworkCommand(NetworkCommand.GET_STOCK, "1"));
            System.out.println("Result: " + stockResp);

            // 4. SEARCH_PRODUCT
            System.out.println("\n[4] Testing SEARCH_PRODUCT ('Sony')...");
            NetworkResponse searchResp = client.sendCommand(new NetworkCommand(NetworkCommand.SEARCH_PRODUCT, "Sony"));
            System.out.println("Result: " + searchResp);
            if (searchResp.isSuccess() && searchResp.getPayload() instanceof List<?> list) {
                System.out.println("-> Found " + list.size() + " products matching query.");
            }

            System.out.println("\n--- SOCKET DEMONSTRATION COMPLETE SUCCESSFUL ---");
        } catch (Exception e) {
            System.err.println("Demo encountered error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 8088;

        if (args.length > 0 && ("--demo".equalsIgnoreCase(args[0]) || "-d".equalsIgnoreCase(args[0]))) {
            runAutomatedDemo(host, port);
            return;
        }

        System.out.println("=================================================");
        System.out.println("  ShopSphere java.net Socket Client");
        System.out.println("  Connecting to: " + host + ":" + port);
        System.out.println("=================================================");

        ShopSphereInventoryClient client = new ShopSphereInventoryClient(host, port);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nSelect an action:");
            System.out.println("1. Get Product by ID");
            System.out.println("2. Get Stock by Product ID");
            System.out.println("3. Search Product by Name/Brand");
            System.out.println("4. Update Stock");
            System.out.println("5. Ping Server");
            System.out.println("6. Run Automated Demo Batch");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();
            if ("0".equals(choice)) {
                System.out.println("Exiting client.");
                break;
            }

            try {
                switch (choice) {
                    case "1":
                        System.out.print("Enter Product ID: ");
                        String id = scanner.nextLine().trim();
                        NetworkResponse r1 = client.sendCommand(new NetworkCommand(NetworkCommand.GET_PRODUCT, id));
                        System.out.println("Server Response: " + r1.getMessage());
                        if (r1.isSuccess() && r1.getPayload() != null) {
                            Product p = (Product) r1.getPayload();
                            System.out.println("Product: " + p.getName() + " | Price: ₹" + p.getPrice() + " | Stock: " + p.getStock());
                        }
                        break;

                    case "2":
                        System.out.print("Enter Product ID: ");
                        String sid = scanner.nextLine().trim();
                        NetworkResponse r2 = client.sendCommand(new NetworkCommand(NetworkCommand.GET_STOCK, sid));
                        System.out.println("Server Response: " + r2.getMessage() + " -> Stock: " + r2.getPayload());
                        break;

                    case "3":
                        System.out.print("Enter search keyword: ");
                        String q = scanner.nextLine().trim();
                        NetworkResponse r3 = client.sendCommand(new NetworkCommand(NetworkCommand.SEARCH_PRODUCT, q));
                        System.out.println("Server Response: " + r3.getMessage());
                        if (r3.isSuccess() && r3.getPayload() instanceof List<?> list) {
                            for (Object obj : list) {
                                Product p = (Product) obj;
                                System.out.println(" - [" + p.getProductId() + "] " + p.getName() + " (₹" + p.getPrice() + ")");
                            }
                        }
                        break;

                    case "4":
                        System.out.print("Enter Product ID: ");
                        String uid = scanner.nextLine().trim();
                        System.out.print("Enter New Stock Quantity: ");
                        int newStock = Integer.parseInt(scanner.nextLine().trim());
                        NetworkResponse r4 = client.sendCommand(new NetworkCommand(NetworkCommand.UPDATE_STOCK, uid, newStock));
                        System.out.println("Server Response: " + r4.getMessage());
                        break;

                    case "5":
                        NetworkResponse r5 = client.sendCommand(new NetworkCommand(NetworkCommand.PING, null));
                        System.out.println("Server Response: " + r5.getMessage() + " | Timestamp: " + r5.getPayload());
                        break;

                    case "6":
                        runAutomatedDemo(host, port);
                        break;

                    default:
                        System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.err.println("Communication error: " + e.getMessage());
                System.out.println("Ensure the ShopSphereInventoryServer is running on port " + port);
            }
        }
    }
}
