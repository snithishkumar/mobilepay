package in.tn.mobilepay.rest.json;

import in.tn.mobilepay.enumeration.DeliveryOptions;
import in.tn.mobilepay.enumeration.OrderStatus;
import in.tn.mobilepay.request.model.DiscardJson;
import in.tn.mobilepay.response.model.AddressBookJson;

public class PurchaseDetails {

	private String purchaseUUID;
	private String billNumber;
	private String purchaseDate;
	private PurchaseItem purchaseItem;
	private OrderStatus orderStatus;
	private DeliveryOptions deliveryOptions;
	private String totalAmount;
	private boolean isDiscard;
	private AddressBookJson addressDetails;
	private DiscardJson discardDetails;

	public String getPurchaseUUID() {
		return purchaseUUID;
	}

	public void setPurchaseUUID(String purchaseUUID) {
		this.purchaseUUID = purchaseUUID;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public PurchaseItem getPurchaseItem() {
		return purchaseItem;
	}

	public void setPurchaseItem(PurchaseItem purchaseItem) {
		this.purchaseItem = purchaseItem;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public DeliveryOptions getDeliveryOptions() {
		return deliveryOptions;
	}

	public void setDeliveryOptions(DeliveryOptions deliveryOptions) {
		this.deliveryOptions = deliveryOptions;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public boolean isDiscard() {
		return isDiscard;
	}

	public void setDiscard(boolean isDiscard) {
		this.isDiscard = isDiscard;
	}

	public AddressBookJson getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(AddressBookJson addressDetails) {
		this.addressDetails = addressDetails;
	}

	public DiscardJson getDiscardDetails() {
		return discardDetails;
	}

	public void setDiscardDetails(DiscardJson discardDetails) {
		this.discardDetails = discardDetails;
	}

	@Override
	public String toString() {
		return "PurchaseDetails [purchaseUUID=" + purchaseUUID + ", billNumber=" + billNumber + ", purchaseDate="
				+ purchaseDate + ", purchaseItem=" + purchaseItem + ", orderStatus=" + orderStatus
				+ ", deliveryOptions=" + deliveryOptions + ", totalAmount=" + totalAmount + ", isDiscard=" + isDiscard
				+ ", addressDetails=" + addressDetails + ", discardDetails=" + discardDetails + "]";
	}

}
