package in.tn.mobilepay.request.model;

public class PurchaseDetailsJson {

	private int itemNo;
	private String description;
	private int quantity;
	private String amount;
	private String unitPrice;
	private float rating = 0;

	public int getItemNo() {
		return itemNo;
	}

	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "PurchaseDetailsJson [itemNo=" + itemNo + ", description=" + description + ", quantity=" + quantity
				+ ", amount=" + amount + ", unitPrice=" + unitPrice + ", rating=" + rating + "]";
	}

}
