package model;

public class Product {
	private int  product_id;
	private String name;
	private double price;
	private int quantity;
	private String description;
	private String farmer_name;
	
	 //  Add this field for buyer's cart to see card quantity
    private int cartQuantity;

	 public int getProductId() {
	        return product_id;
	    }

	    public void setProductId(int productId) {
	        this.product_id = productId;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public double getPrice() {
	        return price;
	    }

	    public void setPrice(double price) {
	        this.price = price;
	    }

	    public int getQuantity() {
	        return quantity;
	    }

	    public void setQuantity(int quantity) {
	        this.quantity = quantity;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    public String getFarmerName() {
	        return farmer_name;
	    }

	    public void setFarmerName(String farmerName) {
	        this.farmer_name = farmerName;
	    }

	    public int getCartQuantity() {
	        return cartQuantity;
	    }

	    public void setCartQuantity(int cartQuantity) {
	        this.cartQuantity = cartQuantity;
	    }
	}


