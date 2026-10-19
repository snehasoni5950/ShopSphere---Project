-- ====================================================================
-- ShopSphere E-Commerce Database Schema and Seed Data
-- RTU Advanced Java / Java Enterprise Project Guide Specification
-- ====================================================================

CREATE DATABASE IF NOT EXISTS shopsphere;
USE shopsphere;

-- Disable foreign key checks for clean recreation
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS coupon_usage;
DROP TABLE IF EXISTS coupons;
DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS wishlist;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS addresses;
DROP TABLE IF EXISTS users;

SET FOREIGN_KEY_CHECKS = 1;

-- 1. Users Table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    mobile VARCHAR(20),
    role VARCHAR(20) NOT NULL DEFAULT 'CUSTOMER', -- 'CUSTOMER' or 'ADMIN'
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',  -- 'ACTIVE', 'INACTIVE'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. Addresses Table
CREATE TABLE addresses (
    address_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    address_line VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    pincode VARCHAR(20) NOT NULL,
    address_type VARCHAR(30) DEFAULT 'HOME', -- 'HOME', 'WORK', etc.
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. Categories Table
CREATE TABLE categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    status BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. Products Table
CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT NOT NULL,
    name VARCHAR(200) NOT NULL,
    brand VARCHAR(100),
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    discount DECIMAL(5,2) DEFAULT 0.00,
    stock INT NOT NULL DEFAULT 0,
    image_url VARCHAR(500),
    status BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 5. Cart Table
CREATE TABLE cart (
    cart_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    UNIQUE KEY uk_user_prod (user_id, product_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 6. Wishlist Table
CREATE TABLE wishlist (
    wishlist_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_wishlist (user_id, product_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 7. Orders Table
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    address_id INT,
    total_amount DECIMAL(10,2) NOT NULL,
    discount DECIMAL(10,2) DEFAULT 0.00,
    payment_method VARCHAR(50) NOT NULL, -- 'COD', 'CARD', 'UPI', 'NETBANKING'
    payment_status VARCHAR(50) DEFAULT 'PENDING', -- 'PENDING', 'PAID', 'FAILED'
    order_status VARCHAR(50) DEFAULT 'PLACED', -- 'PLACED', 'CONFIRMED', 'SHIPPED', 'DELIVERED', 'CANCELLED'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE RESTRICT,
    FOREIGN KEY (address_id) REFERENCES addresses(address_id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 8. Order Items Table
CREATE TABLE order_items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 9. Payments Table
CREATE TABLE payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    transaction_reference VARCHAR(100),
    amount DECIMAL(10,2) NOT NULL,
    payment_status VARCHAR(50) NOT NULL, -- 'SUCCESS', 'FAILED', 'PENDING'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 10. Reviews Table
CREATE TABLE reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    review_text TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 11. Coupons Table
CREATE TABLE coupons (
    coupon_id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    discount_type VARCHAR(20) NOT NULL, -- 'PERCENTAGE', 'FLAT'
    discount_value DECIMAL(10,2) NOT NULL,
    minimum_order DECIMAL(10,2) DEFAULT 0.00,
    maximum_discount DECIMAL(10,2) DEFAULT 1000.00,
    expiry_date DATE NOT NULL,
    status BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 12. Coupon Usage Table
CREATE TABLE coupon_usage (
    usage_id INT AUTO_INCREMENT PRIMARY KEY,
    coupon_id INT NOT NULL,
    user_id INT NOT NULL,
    order_id INT NOT NULL,
    used_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (coupon_id) REFERENCES coupons(coupon_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ====================================================================
-- SEED DATA
-- Default passwords:
-- 'admin123'  -> SHA-256: 240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9
-- 'sneha123'  -> SHA-256: 8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918
-- 'rahul123'  -> SHA-256: ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f
-- ====================================================================

-- Users
INSERT INTO users (user_id, name, email, password, mobile, role, status) VALUES
(1, 'Administrator', 'admin@shopsphere.com', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', '9876543210', 'ADMIN', 'ACTIVE'),
(2, 'Sneha Soni', 'sneha@example.com', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '9829012345', 'CUSTOMER', 'ACTIVE'),
(3, 'Rahul Sharma', 'rahul@example.com', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', '9829054321', 'CUSTOMER', 'ACTIVE');

-- Addresses
INSERT INTO addresses (address_id, user_id, address_line, city, state, pincode, address_type) VALUES
(1, 2, 'Flat 402, Royal Residency, Malviya Nagar', 'Jaipur', 'Rajasthan', '302017', 'HOME'),
(2, 2, 'Tech Park, Sitapura Industrial Area', 'Jaipur', 'Rajasthan', '302022', 'WORK'),
(3, 3, '12 Vaishali Circle, Vaishali Nagar', 'Jaipur', 'Rajasthan', '302021', 'HOME');

-- Categories
INSERT INTO categories (category_id, name, description, status) VALUES
(1, 'Electronics', 'Smartphones, Laptops, Audio and Gadgets', TRUE),
(2, 'Fashion', 'Men, Women and Kids Apparel and Footwear', TRUE),
(3, 'Home & Kitchen', 'Home decor, furniture, and kitchen appliances', TRUE),
(4, 'Books', 'Engineering, Computer Science, Fiction and Academics', TRUE),
(5, 'Fitness & Sports', 'Gym equipment, accessories and sportswear', TRUE);

-- Products
INSERT INTO products (product_id, category_id, name, brand, description, price, discount, stock, image_url, status) VALUES
(1, 1, 'Sony WH-1000XM5 Wireless Headphones', 'Sony', 'Industry-leading noise canceling with Auto NC Optimizer and 30-hour battery life.', 26990.00, 10.00, 25, 'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=600&auto=format&fit=crop&q=80', TRUE),
(2, 1, 'Dell XPS 15 OLED Laptop', 'Dell', '13th Gen Intel Core i7, 16GB RAM, 1TB SSD, 3.5K OLED InfinityEdge touch display.', 149999.00, 8.00, 12, 'https://images.unsplash.com/photo-1593642632823-8f785ba67e45?w=600&auto=format&fit=crop&q=80', TRUE),
(3, 1, 'Apple Watch Series 9', 'Apple', 'Smartwatch with Always-On Retina display, S9 SiP, and advanced health sensors.', 41900.00, 5.00, 30, 'https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=600&auto=format&fit=crop&q=80', TRUE),
(4, 2, 'Classic Cotton Oxford Shirt', 'Roadster', 'Slim-fit pure cotton breathable shirt with button-down collar for all seasons.', 1299.00, 25.00, 80, 'https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=600&auto=format&fit=crop&q=80', TRUE),
(5, 2, 'Ultraboost Light Running Shoes', 'Adidas', 'Lightweight boost cushioning with Continental rubber outsole for maximum grip.', 11999.00, 15.00, 40, 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=600&auto=format&fit=crop&q=80', TRUE),
(6, 3, 'Espresso Coffee Machine with Steam Wand', 'Philips', '15-bar Italian pump for rich crema, dual temperature control, stainless steel body.', 8499.00, 20.00, 18, 'https://images.unsplash.com/photo-1517668808822-9ebb02f2a0e6?w=600&auto=format&fit=crop&q=80', TRUE),
(7, 3, 'Ergonomic Mesh Office Chair', 'Green Soul', 'High-back desk chair with breathable mesh, 3D armrests and adjustable lumbar support.', 9999.00, 30.00, 15, 'https://images.unsplash.com/photo-1580481077195-c328a37db719?w=600&auto=format&fit=crop&q=80', TRUE),
(8, 4, 'Core Java: Fundamentals (12th Edition)', 'Prentice Hall', 'Cay S. Horstmann classic guide covering modern Java 17 features, OOP, and APIs.', 899.00, 12.00, 50, 'https://images.unsplash.com/photo-1532012164546-f432f2e3777a?w=600&auto=format&fit=crop&q=80', TRUE),
(9, 4, 'Head First Servlets and JSP', 'OReilly', 'Passing the Sun Certified Web Component Developer Exam by Bryan Basham and Kathy Sierra.', 750.00, 10.00, 35, 'https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=600&auto=format&fit=crop&q=80', TRUE),
(10, 5, 'Adjustable Dumbbell Set (20kg)', 'Kore', 'Rubber encased dumbbell kit with connecting rod to transform into barbell.', 2499.00, 18.00, 22, 'https://images.unsplash.com/photo-1584735935682-2f2b69dff9d2?w=600&auto=format&fit=crop&q=80', TRUE),
(11, 1, 'Mechanical Gaming Keyboard RGB', 'Logitech', 'Tactile Brown switches, aircraft-grade aluminum alloy top, USB passthrough.', 6499.00, 12.00, 3, 'https://images.unsplash.com/photo-1618384887929-16ec33fab9ef?w=600&auto=format&fit=crop&q=80', TRUE),
(12, 1, 'Wireless Ergonomic Optical Mouse', 'Logitech', 'Natural handshake position reduces wrist pressure, 4000 DPI sensor.', 3299.00, 10.00, 2, 'https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=600&auto=format&fit=crop&q=80', TRUE);

-- Coupons
INSERT INTO coupons (coupon_id, code, discount_type, discount_value, minimum_order, maximum_discount, expiry_date, status) VALUES
(1, 'WELCOME50', 'FLAT', 50.00, 500.00, 50.00, '2028-12-31', TRUE),
(2, 'FESTIVE10', 'PERCENTAGE', 10.00, 1000.00, 1000.00, '2028-12-31', TRUE),
(3, 'SUPER20', 'PERCENTAGE', 20.00, 3000.00, 1500.00, '2028-12-31', TRUE);

-- Reviews
INSERT INTO reviews (review_id, user_id, product_id, rating, review_text) VALUES
(1, 2, 1, 5, 'Exceptional active noise cancellation and crystal clear audio fidelity! Worth every rupee.'),
(2, 3, 1, 4, 'Very comfortable for long working hours and zoom meetings.'),
(3, 2, 8, 5, 'Indispensable textbook for mastering Java fundamentals and enterprise concepts.');

-- Cart Items (Sample for demo user 2)
INSERT INTO cart (cart_id, user_id, product_id, quantity) VALUES
(1, 2, 8, 1),
(2, 2, 4, 2);

-- Wishlist Items
INSERT INTO wishlist (wishlist_id, user_id, product_id) VALUES
(1, 2, 1),
(2, 2, 3);

-- Sample Orders
INSERT INTO orders (order_id, user_id, address_id, total_amount, discount, payment_method, payment_status, order_status) VALUES
(1, 2, 1, 24291.00, 2699.00, 'CARD', 'PAID', 'DELIVERED'),
(2, 3, 3, 11999.00, 0.00, 'UPI', 'PAID', 'SHIPPED');

-- Order Items
INSERT INTO order_items (order_item_id, order_id, product_id, quantity, price) VALUES
(1, 1, 1, 1, 24291.00),
(2, 2, 5, 1, 11999.00);

-- Payments
INSERT INTO payments (payment_id, order_id, payment_method, transaction_reference, amount, payment_status) VALUES
(1, 1, 'CARD', 'TXN-CARD-9823411', 24291.00, 'SUCCESS'),
(2, 2, 'UPI', 'TXN-UPI-7741290', 11999.00, 'SUCCESS');
