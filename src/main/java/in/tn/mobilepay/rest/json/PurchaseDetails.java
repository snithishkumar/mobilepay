package in.tn.mobilepay.rest.json;

import java.util.List;

import in.tn.mobilepay.entity.PurchaseEntity;
import in.tn.mobilepay.enumeration.DeliveryOptions;
import in.tn.mobilepay.enumeration.OrderStatus;
import in.tn.mobilepay.enumeration.PaymentStatus;
import in.tn.mobilepay.request.model.DiscardJson;
import in.tn.mobilepay.response.model.AddressJson;
import in.tn.mobilepay.response.model.UserJson;
import in.tn.mobilepay.services.ServiceUtil;

public class PurchaseDetails {

	private String purchaseUUID;
	
	private String billNumber;
	private long purchaseDate;
	private List<PurchaseItem> purchaseItem;
	private OrderStatus orderStatus;
	private DeliveryOptions userDeliveryOptions;
	
	private String totalAmount;
	private boolean isEditable;
	private AddressJson deliverAddress;
	private DiscardJson discardDetails;
	private long lastModifiedDate;

	private AmountDetails amountDetails;
	private UserJson userDetails;
	private PaymentStatus paymentStatus;
	private DeliveryOptions merchantDeliveryOptions;

	public PurchaseDetails() {

	}

	public PurchaseDetails(PurchaseEntity purchaseEntity, ServiceUtil serviceUtil) {
		this.purchaseUUID = purchaseEntity.getPurchaseGuid();
		this.billNumber = purchaseEntity.getBillNumber();
		this.purchaseDate = purchaseEntity.getPurchaseDateTime();
		this.orderStatus = purchaseEntity.getOrderStatus();
		this.userDeliveryOptions = purchaseEntity.getUserDeliveryOptions();
		this.totalAmount = String.valueOf(purchaseEntity.getTotalAmount());
		this.isEditable = purchaseEntity.isEditable();
		this.lastModifiedDate = purchaseEntity.getUpdatedDateTime();
		String purchaseData = purchaseEntity.getPurchaseData();
		PurchaseItems purchaseItems = serviceUtil.fromJson(purchaseData, PurchaseItems.class);
		this.purchaseItem = purchaseItems.getPurchaseItems();
		this.paymentStatus = purchaseEntity.getPaymentStatus();
		this.merchantDeliveryOptions = purchaseEntity.getMerchantDeliveryOptions();
		
		
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

	public DeliveryOptions getUserDeliveryOptions() {
		return userDeliveryOptions;
	}

	public void setUserDeliveryOptions(DeliveryOptions userDeliveryOptions) {
		this.userDeliveryOptions = userDeliveryOptions;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public AddressJson getDeliveryAddress() {
		return deliverAddress;
	}

	public void setDeliverAddress(AddressJson deliverAddress) {
		this.deliverAddress = deliverAddress;
	}

	public DiscardJson getDiscardDetails() {
		return discardDetails;
	}

	public void setDiscardDetails(DiscardJson discardDetails) {
		this.discardDetails = discardDetails;
	}

	public long getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(long lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public AmountDetails getAmountDetails() {
		return amountDetails;
	}

	public void setAmountDetails(AmountDetails amountDetails) {
		this.amountDetails = amountDetails;
	}

	public UserJson getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserJson userDetails) {
		this.userDetails = userDetails;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public DeliveryOptions getMerchantDeliveryOptions() {
		return merchantDeliveryOptions;
	}

	public void setMerchantDeliveryOptions(DeliveryOptions merchantDeliveryOptions) {
		this.merchantDeliveryOptions = merchantDeliveryOptions;
	}

	@Override
	public String toString() {
		return "PurchaseDetails [purchaseUUID=" + purchaseUUID + ", billNumber=" + billNumber + ", purchaseDate="
				+ purchaseDate + ", purchaseItem=" + purchaseItem + ", orderStatus=" + orderStatus
				+ ", userDeliveryOptions=" + userDeliveryOptions + ", totalAmount=" + totalAmount + ", isEditable="
				+ isEditable + ", addressDetails=" + deliverAddress + ", discardDetails=" + discardDetails
				+ ", lastModifiedDate=" + lastModifiedDate + ", amountDetails=" + amountDetails + ", userDetails="
				+ userDetails + ", paymentStatus=" + paymentStatus + ", merchantDeliveryOptions="
				+ merchantDeliveryOptions + "]";
	}

	

}
