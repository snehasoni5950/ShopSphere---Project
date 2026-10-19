package com.shopsphere.rmi.server;

import com.shopsphere.rmi.remote.InventoryService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * RTU Syllabus: RMI Registry startup and Remote Object binding.
 */
public class RmiServerMain {

    public static final int RMI_PORT = 1099;
    public static final String SERVICE_NAME = "InventoryService";

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("  ShopSphere RMI Registry & Server");
        System.out.println("=================================================");

        try {
            Registry registry;
            try {
                registry = LocateRegistry.createRegistry(RMI_PORT);
                System.out.println("[RMI Server] Created new RMI Registry on port " + RMI_PORT);
            } catch (Exception e) {
                System.out.println("[RMI Server] Using existing RMI Registry on port " + RMI_PORT);
                registry = LocateRegistry.getRegistry(RMI_PORT);
            }

            InventoryService service = new InventoryServiceImpl();
            registry.rebind(SERVICE_NAME, service);

            System.out.println("[RMI Server] '" + SERVICE_NAME + "' bound successfully.");
            System.out.println("[RMI Server] Ready to receive remote client invocations...");

        } catch (Exception e) {
            System.err.println("[RMI Server] Startup error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
