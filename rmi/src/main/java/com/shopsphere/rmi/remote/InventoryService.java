package com.shopsphere.rmi.remote;

import com.shopsphere.model.Product;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * RTU Syllabus: RMI Architecture & Remote Interface.
 * Specified on Page 9 Section 18 of the project guide.
 */
public interface InventoryService extends Remote {

    Product getProduct(int id) throws RemoteException;

    int getStock(int productId) throws RemoteException;

    boolean updateStock(int productId, int quantity) throws RemoteException;

    List<Product> getAllProducts() throws RemoteException;

    List<Product> getLowStockAlerts(int threshold) throws RemoteException;
}
