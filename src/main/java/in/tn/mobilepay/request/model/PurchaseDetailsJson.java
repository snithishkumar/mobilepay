package in.tn.mobilepay.request.model;

public class PurchaseDetailsJson {

	private int itemNo;
	private String description;
	private int quantity;
	private String amount;

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

	@Override
	public String toString() {
		return "PurchaseDetailsJson [itemNo=" + itemNo + ", description=" + description + ", quantity=" + quantity
				+ ", amount=" + amount + "]";
	}

}
