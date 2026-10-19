package com.shopsphere.rmi.server;

import com.shopsphere.model.Product;
import com.shopsphere.rmi.remote.InventoryService;
import com.shopsphere.service.ProductService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * RTU Syllabus: RMI Remote Implementation connecting to MySQL via Service/DAO.
 */
public class InventoryServiceImpl extends UnicastRemoteObject implements InventoryService {

    private static final long serialVersionUID = 1L;
    private final transient ProductService productService;

    public InventoryServiceImpl() throws RemoteException {
        super();
        this.productService = new ProductService();
    }

    @Override
    public Product getProduct(int id) throws RemoteException {
        try {
            System.out.println("[RMI Server] Remote call: getProduct(" + id + ")");
            return productService.getProductDetails(id);
        } catch (Exception e) {
            throw new RemoteException("Error fetching product via RMI: " + e.getMessage(), e);
        }
    }

    @Override
    public int getStock(int productId) throws RemoteException {
        try {
            System.out.println("[RMI Server] Remote call: getStock(" + productId + ")");
            return productService.getStock(productId);
        } catch (Exception e) {
            throw new RemoteException("Error fetching stock via RMI: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean updateStock(int productId, int quantity) throws RemoteException {
        try {
            System.out.println("[RMI Server] Remote call: updateStock(" + productId + ", " + quantity + ")");
            return productService.updateStock(productId, quantity);
        } catch (Exception e) {
            throw new RemoteException("Error updating stock via RMI: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Product> getAllProducts() throws RemoteException {
        try {
            System.out.println("[RMI Server] Remote call: getAllProducts()");
            return productService.getAllActiveProducts();
        } catch (Exception e) {
            throw new RemoteException("Error listing products via RMI: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Product> getLowStockAlerts(int threshold) throws RemoteException {
        try {
            System.out.println("[RMI Server] Remote call: getLowStockAlerts(" + threshold + ")");
            return productService.getLowStockAlerts(threshold);
        } catch (Exception e) {
            throw new RemoteException("Error fetching low stock alerts via RMI: " + e.getMessage(), e);
        }
    }
}
