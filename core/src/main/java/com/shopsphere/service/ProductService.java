package com.shopsphere.service;

import com.shopsphere.dao.CategoryDAO;
import com.shopsphere.dao.ProductDAO;
import com.shopsphere.dao.ReviewDAO;
import com.shopsphere.model.Category;
import com.shopsphere.model.Product;
import com.shopsphere.model.Review;

import java.sql.SQLException;
import java.util.List;

public class ProductService {

    private final ProductDAO productDAO = new ProductDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final ReviewDAO reviewDAO = new ReviewDAO();

    public List<Product> getAllActiveProducts() throws SQLException {
        return productDAO.getActiveProducts();
    }

    public List<Product> getAllProductsForAdmin() throws SQLException {
        return productDAO.getAllProducts();
    }

    public Product getProductDetails(int productId) throws SQLException {
        return productDAO.getProductById(productId);
    }

    public List<Product> getProductsByCategory(int categoryId) throws SQLException {
        return productDAO.getProductsByCategory(categoryId);
    }

    public List<Product> searchProducts(String query) throws SQLException {
        if (query == null || query.trim().isEmpty()) {
            return getAllActiveProducts();
        }
        return productDAO.searchProducts(query.trim());
    }

    public List<Category> getCategories() throws SQLException {
        return categoryDAO.getAllCategories();
    }

    public boolean saveProduct(Product product) throws SQLException {
        if (product.getProductId() > 0) {
            return productDAO.updateProduct(product);
        } else {
            return productDAO.addProduct(product);
        }
    }

    public boolean deleteProduct(int productId) throws SQLException {
        return productDAO.deleteProduct(productId);
    }

    public int getStock(int productId) throws SQLException {
        return productDAO.getStock(productId);
    }

    public boolean updateStock(int productId, int stock) throws SQLException {
        return productDAO.updateStock(productId, stock);
    }

    public List<Product> getLowStockAlerts(int threshold) throws SQLException {
        return productDAO.getLowStockProducts(threshold);
    }

    public List<Review> getReviews(int productId) throws SQLException {
        return reviewDAO.getReviewsByProduct(productId);
    }

    public boolean addReview(Review review) throws SQLException {
        return reviewDAO.addReview(review);
    }
}
