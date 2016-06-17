package in.tn.mobilepay.rest.json;

public class PurchaseItem {
	private String itemNo;
	private String name;
	private String quantity;
	private String totalAmount;
	private int rating;

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "PurchaseItem [itemNo=" + itemNo + ", name=" + name + ", quantity=" + quantity + ", totalAmount="
				+ totalAmount + "]";
	}

}
