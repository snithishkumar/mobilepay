package in.tn.mobilepay.rest.json;

public class UserPurchaseData {

	private String customerName;
	private String customerMobileNo;
	private boolean isHomeDelivery;
	private boolean isRemovable;
	private String purchaseDate;
	private String totalAmount;
	private String billNumber;
	private PurchaseItem purchaseItems;
	private AmountDetails amountDetails;
	private String purchaseUUID;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerMobileNo() {
		return customerMobileNo;
	}

	public void setCustomerMobileNo(String customerMobileNo) {
		this.customerMobileNo = customerMobileNo;
	}

	public boolean isHomeDelivery() {
		return isHomeDelivery;
	}

	public void setHomeDelivery(boolean isHomeDelivery) {
		this.isHomeDelivery = isHomeDelivery;
	}

	public boolean isRemovable() {
		return isRemovable;
	}

	public void setRemovable(boolean isRemovable) {
		this.isRemovable = isRemovable;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public PurchaseItem getPurchaseItems() {
		return purchaseItems;
	}

	public void setPurchaseItems(PurchaseItem purchaseItems) {
		this.purchaseItems = purchaseItems;
	}

	public AmountDetails getAmountDetails() {
		return amountDetails;
	}

	public void setAmountDetails(AmountDetails amountDetails) {
		this.amountDetails = amountDetails;
	}

	public String getPurchaseUUID() {
		return purchaseUUID;
	}

	public void setPurchaseUUID(String purchaseUUID) {
		this.purchaseUUID = purchaseUUID;
	}

	@Override
	public String toString() {
		return "UserPurchaseData [customerName=" + customerName + ", customerMobileNo=" + customerMobileNo
				+ ", isHomeDelivery=" + isHomeDelivery + ", isRemovable=" + isRemovable + ", purchaseDate="
				+ purchaseDate + ", totalAmount=" + totalAmount + ", billNumber=" + billNumber + ", purchaseItems="
				+ purchaseItems + ", amountDetails=" + amountDetails + ", purchaseUUID=" + purchaseUUID + "]";
	}

}
