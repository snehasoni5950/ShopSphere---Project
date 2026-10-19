package com.shopsphere.network.server;

import com.shopsphere.model.Product;
import com.shopsphere.network.protocol.NetworkCommand;
import com.shopsphere.network.protocol.NetworkResponse;
import com.shopsphere.service.ProductService;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * RTU Syllabus: java.net ServerSocket, Socket, Client-Server architecture, Object Serialization.
 * Demonstrates a remote TCP socket inventory server.
 */
public class ShopSphereInventoryServer {

    public static final int DEFAULT_PORT = 8088;
    private final int port;
    private final ProductService productService;
    private volatile boolean running = true;

    public ShopSphereInventoryServer(int port) {
        this.port = port;
        this.productService = new ProductService();
    }

    public void start() {
        System.out.println("=================================================");
        System.out.println("  ShopSphere java.net Inventory Server");
        System.out.println("  Listening on port: " + port);
        System.out.println("=================================================");

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (running) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("[Server] Accepted connection from: " + clientSocket.getRemoteSocketAddress());
                    new Thread(new ClientHandler(clientSocket)).start();
                } catch (IOException e) {
                    if (!running) break;
                    System.err.println("[Server] Connection accept error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("[Server] Fatal server error: " + e.getMessage());
        }
    }

    public void stop() {
        this.running = false;
    }

    private class ClientHandler implements Runnable {
        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                while (!socket.isClosed()) {
                    Object received;
                    try {
                        received = in.readObject();
                    } catch (EOFException e) {
                        break; // Client disconnected gracefully
                    }

                    if (received instanceof NetworkCommand cmd) {
                        System.out.println("[Server] Received command: " + cmd.getAction() + " (" + cmd.getArgument() + ")");
                        NetworkResponse response = handleCommand(cmd);
                        out.writeObject(response);
                        out.flush();

                        if (NetworkCommand.BYE.equalsIgnoreCase(cmd.getAction())) {
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("[Server] Client handler terminated: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException ignored) {}
                System.out.println("[Server] Connection closed.");
            }
        }

        private NetworkResponse handleCommand(NetworkCommand cmd) {
            try {
                switch (cmd.getAction().toUpperCase()) {
                    case NetworkCommand.GET_PRODUCT:
                        int prodId = Integer.parseInt(cmd.getArgument().trim());
                        Product product = productService.getProductDetails(prodId);
                        if (product != null) {
                            return NetworkResponse.ok("Product found", product);
                        } else {
                            return NetworkResponse.error("Product with ID " + prodId + " not found");
                        }

                    case NetworkCommand.GET_STOCK:
                        int sProdId = Integer.parseInt(cmd.getArgument().trim());
                        int stock = productService.getStock(sProdId);
                        return NetworkResponse.ok("Stock count retrieved", stock);

                    case NetworkCommand.SEARCH_PRODUCT:
                        List<Product> results = productService.searchProducts(cmd.getArgument());
                        return NetworkResponse.ok("Search results retrieved (" + results.size() + ")", results);

                    case NetworkCommand.UPDATE_STOCK:
                        int uProdId = Integer.parseInt(cmd.getArgument().trim());
                        int newStock = cmd.getExtraInt() != null ? cmd.getExtraInt() : 0;
                        boolean updated = productService.updateStock(uProdId, newStock);
                        if (updated) {
                            return NetworkResponse.ok("Stock updated to " + newStock, newStock);
                        } else {
                            return NetworkResponse.error("Failed to update stock for product " + uProdId);
                        }

                    case NetworkCommand.PING:
                        return NetworkResponse.ok("PONG from ShopSphere Inventory Server", System.currentTimeMillis());

                    case NetworkCommand.BYE:
                        return NetworkResponse.ok("Goodbye!", null);

                    default:
                        return NetworkResponse.error("Unknown command action: " + cmd.getAction());
                }
            } catch (Exception e) {
                return NetworkResponse.error("Error executing command: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException ignored) {}
        }
        new ShopSphereInventoryServer(port).start();
    }
}
