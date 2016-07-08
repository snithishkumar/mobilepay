package in.tn.mobilepay.rest.json;

import java.util.ArrayList;
import java.util.List;

public class MerchantPurchaseData {
	private String customerName;
	private String customerMobileNo;
	private Boolean isHomeDelivery;
	private Boolean isRemovable;
	private long purchaseDate;
	private String totalAmount;
	private String billNumber;
	private List<PurchaseItem> purchaseItems = new ArrayList<PurchaseItem>();
	private AmountDetails amountDetails;

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

	public Boolean getIsHomeDelivery() {
		return isHomeDelivery;
	}

	public void setIsHomeDelivery(Boolean isHomeDelivery) {
		this.isHomeDelivery = isHomeDelivery;
	}

	public Boolean getIsRemovable() {
		return isRemovable;
	}

	public void setIsRemovable(Boolean isRemovable) {
		this.isRemovable = isRemovable;
	}

	public long getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(long purchaseDate) {
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

	public List<PurchaseItem> getPurchaseItems() {
		return purchaseItems;
	}

	public void setPurchaseItems(List<PurchaseItem> purchaseItems) {
		this.purchaseItems = purchaseItems;
	}

	public AmountDetails getAmountDetails() {
		return amountDetails;
	}

	public void setAmountDetails(AmountDetails amountDetails) {
		this.amountDetails = amountDetails;
	}

	@Override
	public String toString() {
		return "MerchantPurchaseData [customerName=" + customerName + ", customerMobileNo=" + customerMobileNo
				+ ", isHomeDelivery=" + isHomeDelivery + ", isRemovable=" + isRemovable + ", purchaseDate="
				+ purchaseDate + ", totalAmount=" + totalAmount + ", billNumber=" + billNumber + ", purchaseItems="
				+ purchaseItems + ", amountDetails=" + amountDetails + "]";
	}

}
