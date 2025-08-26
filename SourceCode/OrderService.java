
package service;
import  model.Product;
import util.DBConnection;
import java.sql.*;
import java.util.List;

public class OrderService {
	
public void placeOrder(String buyerName,List<Product> Cart) {
	
	double totalAmount = 0;
	Connection conn=null;

    try { conn = DBConnection.getConnection();
        conn.setAutoCommit(false); // start transaction

        // 1. Calculate total
        for (Product p : Cart) {
            totalAmount += p.getPrice() * p.getCartQuantity();
        }

        // 2. Insert into orders table
        String orderSQL = "INSERT INTO orders (buyer_name, total_amount, status) VALUES (?, ?, ?)";
        PreparedStatement orderStmt = conn.prepareStatement(orderSQL, Statement.RETURN_GENERATED_KEYS);
        orderStmt.setString(1, buyerName);
        orderStmt.setDouble(2, totalAmount);
        orderStmt.setString(3, "Pending");
        orderStmt.executeUpdate();

        // Get generated order_id
        ResultSet keys = orderStmt.getGeneratedKeys();
        int orderId = 0;
        if (keys.next()) {
            orderId = keys.getInt(1);
        }

        // 3. Insert into order_products table
        String opSQL = "INSERT INTO order_products (order_id, product_id, quantity) VALUES (?, ?, ?)";
        PreparedStatement opStmt = conn.prepareStatement(opSQL);

        for (Product p : Cart) {
            opStmt.setInt(1, orderId);
            opStmt.setInt(2, p.getProductId());
            opStmt.setInt(3, p.getCartQuantity());
            opStmt.addBatch();
        }
        opStmt.executeBatch();

        // 4. Update product stock
        String updateStockSQL = "UPDATE products SET quantity = quantity - ? WHERE product_id = ?";
        PreparedStatement updateStmt = conn.prepareStatement(updateStockSQL);

        for (Product p : Cart) {
            updateStmt.setInt(1, p.getCartQuantity());
            updateStmt.setInt(2, p.getProductId());
            updateStmt.addBatch();
        }
        updateStmt.executeBatch();

        conn.commit();
        System.out.println("✅ Order placed successfully! Order ID: " + orderId);

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("❌ Order failed. Rolling back.");
        try {
        	conn.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
	
}

