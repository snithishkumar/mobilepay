package in.tn.mobilepay.rest.json;

import in.tn.mobilepay.exception.ValidationException;

public class PurchaseItem {
	private int itemNo;
	private String name;
	private int quantity;
	private double amount;
	private double totalAmount;
	private float rating;

	public int getItemNo() {
		return itemNo;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}
	
	public boolean validateData()throws ValidationException{
	  if(name == null || name.trim().isEmpty()){
		   throw new ValidationException(400, "Product Name is not found.");
	  }
	  if(quantity < 0){
		  throw new ValidationException(400, "Invalid quantity.");
	  }
	  if(amount < 0){
		  throw new ValidationException(400, "Invalid amount.");
	  }
	  return true;
	}

	@Override
	public String toString() {
		return "PurchaseItem [itemNo=" + itemNo + ", name=" + name + ", quantity=" + quantity + ", totalAmount="
				+ totalAmount + "]";
	}

}
