
package service;
import model.Product;
import util.DBConnection;
import java.sql.*;
import java.util.Scanner;

public class ProductService {
	Scanner sc =new Scanner(System.in);
	
	//add new product
	public void addProduct() {
		try(Connection conn = DBConnection.getConnection()){
			System.out.println("Enter product name");
			String name=sc.nextLine();
			
			System.out.println("Enter Price");
			double price=sc.nextDouble();
			
			System.out.println("Enter quantity");
			int quantity=sc.nextInt();
			sc.nextLine(); 
			
			System.out.println("Enter description");
			String desc = sc.nextLine();
			
			System.out.println("Enter Farmer name");
			String farmer=sc.nextLine();
			
			String sql = "INSERT INTO products (name, price, quantity, description, farmer_name) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			  stmt.setString(1, name);
	            stmt.setDouble(2, price);
	            stmt.setInt(3, quantity);
	            stmt.setString(4, desc);
	            stmt.setString(5, farmer);
	            
	            int result = stmt.executeUpdate();
	            if (result > 0) 
	            	System.out.println("Product added succesfully:");
	            
	            else 
	            	System.out.println("Failed to add the product:");
	            
		} catch(Exception e) {
	            	System.out.println("Error"+e.getMessage());
	            }
		
				
	}
	
	//view all product
	public void viewProduct() {
		try(Connection conn = DBConnection.getConnection()){
			String sql="select * from products";
			Statement stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			System.out.println("Product List");
			while(rs.next()) {
				System.out.println("ID:"+rs.getInt("product_id")+
						",Name:"+rs.getString("name")+
						",Price:"+rs.getDouble("price")+
						",qty:"+rs.getInt("quantity")+
						",desc:"+rs.getString("description")+
						",farmer_name"+rs.getString("farmer_name"));
			}
			}catch(Exception e) {
				System.out.println("Error:"+e.getMessage());
		}
	}
	
	
	//update price and quantity
	public void updateProduct() {
		try(Connection conn = DBConnection.getConnection()){
			System.out.println("Enter productid to update");
			int id=sc.nextInt();
			
			System.out.println("Update:\n1.Price\n2.Quantity\nChoice:");
			int choice =sc.nextInt();
			String column;
			if (choice == 1) {
			    column = "price";
			} else {
			    column = "quantity";
			}

			System.out.print("Enter new value: ");
			double newValue = sc.nextDouble();

			// Build the SQL query based on the selected column
			String sql = "UPDATE products SET " + column + " = ? WHERE product_id = ?";

			// Prepare the statement
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, newValue); // Set the new value for price or quantity
			stmt.setInt(2, id);   
			int result=stmt.executeUpdate();
			
			if(result > 0) 
				System.out.println("Product Updated");
				else
					System.out.println("product not found");
			}
			catch(Exception ex) {
				System.out.println("Error:"+ ex.getMessage());
			}
			
		}
		
		//delete a product
	public void deleteProduct() {
		try(Connection conn=DBConnection.getConnection()){
			System.out.println("Enter a product to delete");
			int id=sc.nextInt();
			String sql="DELETE FROM products where product_id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			int result=stmt.executeUpdate();
			if(result > 0)
				System.out.println("Product deleted");
			else
				System.out.println("product not found");
			}
		catch(Exception e) {
			System.out.println("Error:"+e.getMessage());
		}
	}
	
	
		
	}


