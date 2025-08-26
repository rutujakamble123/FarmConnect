
CREATE DATABASE  farmconnect;
USE farmconnect;

CREATE TABLE farmers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100)
);
select * from farmers;

CREATE TABLE buyers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100)
);
select * from buyers;

CREATE TABLE  products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DOUBLE NOT NULL CHECK (price >= 0),
    quantity INT NOT NULL CHECK (quantity >= 0),
    description TEXT,
    farmer_name VARCHAR(100) NOT NULL
    -- optionally link farmer_id INT REFERENCES farmers(id)
);
select * from products;

CREATE TABLE  orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    buyer_name VARCHAR(100) NOT NULL,
    total_amount DOUBLE NOT NULL CHECK (total_amount >= 0),
    status VARCHAR(50) DEFAULT 'Pending'
    -- optionally link buyer_id INT REFERENCES buyers(id)
);
select * from orders;

CREATE TABLE  order_products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);
select * from order_products;

INSERT INTO products (name, price, quantity, description, farmer_name)
VALUES
('Wheat', 25.5, 100, 'Organic wheat from village', 'Farmer Ramesh'),
('Tomatoes', 15.0, 50, 'Fresh red tomatoes', 'Farmer Sita');

/*Stored Procedure*/

DELIMITER //

CREATE PROCEDURE GetBuyerOrders (IN buyerName VARCHAR(100))
BEGIN
    SELECT o.order_id, o.total_amount, o.status, p.name AS product_name, op.quantity
    FROM orders o
    JOIN order_products op ON o.order_id = op.order_id
    JOIN products p ON op.product_id = p.product_id
    WHERE o.buyer_name = buyerName;
END //

DELIMITER ;

CALL GetBuyerOrders('Rutuja');

/*Trigger*/

DELIMITER //

CREATE TRIGGER update_stock_after_order
AFTER INSERT ON order_products
FOR EACH ROW
BEGIN
    UPDATE products
    SET quantity = quantity - NEW.quantity
    WHERE product_id = NEW.product_id;
END //

DELIMITER ;

/*Indexes*/

CREATE INDEX idx_product_id ON products(product_id);
CREATE INDEX idx_order_id ON orders(order_id);
CREATE INDEX idx_buyer_name ON orders(buyer_name);
