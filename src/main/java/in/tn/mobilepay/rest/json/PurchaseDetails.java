package in.tn.mobilepay.rest.json;

import java.util.List;

import in.tn.mobilepay.entity.PurchaseEntity;
import in.tn.mobilepay.enumeration.DeliveryOptions;
import in.tn.mobilepay.enumeration.OrderStatus;
import in.tn.mobilepay.request.model.DiscardJson;
import in.tn.mobilepay.response.model.AddressBookJson;
import in.tn.mobilepay.response.model.AddressJson;

public class PurchaseDetails {

	private String purchaseUUID;
	private String billNumber;
	private long purchaseDate;
	private List<PurchaseItem> purchaseItem;
	private OrderStatus orderStatus;
	private DeliveryOptions deliveryOptions;
	private String totalAmount;
	private boolean isDiscard;
	private AddressJson addressDetails;
	private DiscardJson discardDetails;
	//private AmountDetailsJson amountDetails;
	
	public PurchaseDetails(){
		
	}
	
	public PurchaseDetails(PurchaseEntity purchaseEntity){
		this.purchaseUUID = purchaseEntity.getPurchaseGuid();
		this.billNumber = purchaseEntity.getBillNumber();
		this.purchaseDate = purchaseEntity.getPurchaseDateTime();
		this.orderStatus = purchaseEntity.getOrderStatus();
		this.deliveryOptions = purchaseEntity.getDeliveryOptions();
		this.totalAmount = purchaseEntity.getTotalAmount();
		this.isDiscard = purchaseEntity.isDiscard();
		
	}

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

	public long getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(long purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public List<PurchaseItem> getPurchaseItem() {
		return purchaseItem;
	}

	public void setPurchaseItem(List<PurchaseItem> purchaseItem) {
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

	public AddressJson getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(AddressJson addressDetails) {
		this.addressDetails = addressDetails;
	}

	public DiscardJson getDiscardDetails() {
		return discardDetails;
	}

	public void setDiscardDetails(DiscardJson discardDetails) {
		this.discardDetails = discardDetails;
	}
	
	

	/*public AmountDetailsJson getAmountDetails() {
		return amountDetails;
	}

	public void setAmountDetails(AmountDetailsJson amountDetails) {
		this.amountDetails = amountDetails;
	}*/

	@Override
	public String toString() {
		return "PurchaseDetails [purchaseUUID=" + purchaseUUID + ", billNumber=" + billNumber + ", purchaseDate="
				+ purchaseDate + ", purchaseItem=" + purchaseItem + ", orderStatus=" + orderStatus
				+ ", deliveryOptions=" + deliveryOptions + ", totalAmount=" + totalAmount + ", isDiscard=" + isDiscard
				+ ", addressDetails=" + addressDetails + ", discardDetails=" + discardDetails + "]";
	}

}