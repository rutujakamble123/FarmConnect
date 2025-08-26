
package service;
import model.Product;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import service.OrderService;
import java.util.Scanner;

public class BuyerService {
Scanner sc=new Scanner(System.in);
	
	//1.To search the product
     public void searchProduct()
    {
    try(Connection conn=DBConnection.getConnection()){
    	System.out.println("Enter the product to search");
    	String keyword=sc.nextLine();
    	String sql="SELECT * from products where name LIKE ? OR farmer_name LIKE ?";
    	PreparedStatement stmt = conn.prepareStatement(sql);
    	String search ="%" +keyword +"%";
    	stmt.setString(1, search);
    	stmt.setString(2, search);
    	ResultSet rs=stmt.executeQuery();
    	System.out.println("Result");
    	while(rs.next()) {
    		int id=rs.getInt("product_id");
    		String name=rs.getString("name");
    		double price = rs.getDouble("price");
    		int qty=rs.getInt("quantity");
    		String farmer=rs.getString("farmer_name");
    		   System.out.println("ID: " + id + " | Name: " + name +
    	                " | ₹" + price + " | Qty: " + qty + " | Farmer: " + farmer);
    		   
    	}}
    	catch(Exception e) {
    		System.out.println("Error:"+e.getMessage());
    	}	
	}

 // 2.To View all products
public List<Product> getAllProducts() {
    List<Product> productList = new ArrayList<>();

    try (Connection conn = DBConnection.getConnection()) {    //used this connection because it will automatically close the connection after use.
        String sql = "SELECT * FROM products";                //To view all the product values from product table.
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Product p = new Product();
            p.setProductId(rs.getInt("product_id"));      
            p.setName(rs.getString("name"));             
            p.setPrice(rs.getDouble("price"));
            p.setQuantity(rs.getInt("quantity"));
            p.setDescription(rs.getString("description"));
            p.setFarmerName(rs.getString("farmer_name"));   //this feilds are used as it is from database

            productList.add(p);
        }

    } catch (Exception e) {
        System.out.println("❌ Error: " + e.getMessage());   //used to catch the exception 
    }

    return productList;
}


//3. View order history using stored procedure
public void viewAllProduct() {
	try(Connection conn = DBConnection.getConnection()){
		CallableStatement stmt = conn.prepareCall("{CALL GetBuyerOrders(?)}");
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter your name to view order history: ");
		String buyerName = sc.nextLine();

		stmt.setString(1, buyerName);
		ResultSet rs=stmt.executeQuery();
		System.out.println("Order history for"+buyerName+":");
		while(rs.next()) {
			System.out.println("Order ID: " + rs.getInt("order_id") +
                    " | Product: " + rs.getString("product_name") +
                    " | Quantity: " + rs.getInt("quantity") +
                    " | Status: " + rs.getString("status") +
                    " | Total: ₹" + rs.getDouble("total_amount"));
		}
	}catch(Exception e) {
		System.out.println("Error"+e.getMessage());
	}
}

//place order

public void placeOrder(String buyerName) {
    List<Product> cart = new ArrayList<>();
    List<Product> allProducts = getAllProducts();

    while (true) {
        System.out.print("Enter Product ID to add to cart (or 0 to finish): ");
        int pid = sc.nextInt();
        if (pid == 0) break;

        System.out.print("Enter quantity: ");
        int qty = sc.nextInt();

        boolean found = false;

        for (Product p : allProducts) {
            if (p.getProductId() == pid) {
                found = true; // ✅ Product ID matched
                if (qty <= p.getQuantity()) {
                    p.setCartQuantity(qty);
                    cart.add(p);
                    System.out.println("✅ Added to cart.");
                } else {
                    System.out.println("❌ Not enough stock.");
                }
                break; // stop checking after match
            }
        }

        if (!found) {
            System.out.println("❌ Invalid product ID.");
        }
    }

    // Confirm and place order
    if (!cart.isEmpty()) {
        OrderService os = new OrderService();
        os.placeOrder(buyerName, cart);
    } else {
        System.out.println("❌ Cart is empty. Order not placed.");
    }
}
}