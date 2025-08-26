package app;

import service.ProductService;
import util.DBConnection;
import service.BuyerService;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		System.out.println(DBConnection.getConnection());

		Scanner sc=new Scanner(System.in);
		ProductService productservice=new ProductService();
		BuyerService buyerservice=new BuyerService();
		while(true) {
			System.out.println("welcome to farmconnect– A Farmer-to-Market Supply Chain");
			System.out.println("select role:\n1.Farmer\n2.Buyer\n0.Exit");
			System.out.println("Enter choice:");
			int role=sc.nextInt();
			switch(role) {
			case 1:
				while(true) {
					System.out.println("\nFarmer Menu");
					System.out.println("1.Add Product");
					System.out.println("2.View Products");
					System.out.println("3.Update Product");
					System.out.println("4.Delete Product");
					System.out.println("0.Back to Main Menu");
					System.out.println("Enter choice");
					int choice=sc.nextInt();
					sc.nextLine();
					
					switch(choice) {
					case 1:productservice.addProduct();break;
					case 2 :productservice.viewProduct();break;
					case 3 :productservice.updateProduct();break;
					case 4:productservice.deleteProduct();break;
					case 0:break;
					default:System.out.println("Invalid Choice");
					}
					if(choice == 0)break;
				}
				break;
				
			case 2:
				  sc.nextLine();
				System.out.println("Enter your name");
				String buyerNAme=sc.nextLine();
				
				while(true) {
				    System.out.println("\n Buyer Menu");
                    System.out.println("1. Search Products");
                    System.out.println("2. Place Order");
                    System.out.println("3. View Order History");
                    System.out.println("0. Back to Main Menu");
                    System.out.print("Enter choice: ");
                    int bchoice=sc.nextInt();
                    sc.nextLine();
                    
                    switch(bchoice) {
                    case 1:buyerservice.searchProduct();break;
                    case 2:buyerservice.placeOrder(buyerNAme);break;
                    case 3 :buyerservice.viewAllProduct();break;
                    case 0:break;
                    default:System.out.println("Invalid choice");
                    }
                    if(bchoice == 0) break;
				}
			case 0:
				System.out.println("Thank you for using Farm Connect. GoodDay!\"");
			System.exit(0);
			default:
				System.out.println("Invalid choice");
			}
		}
		

	}

}
