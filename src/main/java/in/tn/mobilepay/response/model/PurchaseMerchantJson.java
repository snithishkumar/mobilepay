package in.tn.mobilepay.response.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import in.tn.mobilepay.entity.PurchaseEntity;
import in.tn.mobilepay.enumeration.DeliveryOptions;
import in.tn.mobilepay.enumeration.PaymentStatus;
import in.tn.mobilepay.request.model.DiscardJson;

public class PurchaseMerchantJson {
	
	private String purchaseId;
	private long purchaseDate;
	private String billNumber;
	private MerchantJson merchants;
	private UserJson users;
	private JsonElement productDetails;
	private JsonElement amountDetails;
	private String category;
	private boolean isEditable;
	private boolean isDelivered;
	private long lastModifiedDateTime;
	private long serverDateTime;
	private boolean isDiscard;
	private PaymentStatus paymentStatus;
	private String orderStatus;
	private DeliveryOptions deliveryOptions;
	private String totalAmount;
	private DiscardJson discardJson;
	private AddressJson addressDetails;
	
	public PurchaseMerchantJson(){
		
	}
	
	public PurchaseMerchantJson(PurchaseEntity purchaseEntity,Gson gson){
		this.purchaseId = purchaseEntity.getPurchaseGuid();
		this.purchaseDate = purchaseEntity.getPurchaseDateTime();
		this.billNumber = purchaseEntity.getBillNumber();
		this.productDetails = gson.fromJson(purchaseEntity.getPurchaseData(), JsonArray.class);
		this.amountDetails =  gson.fromJson(purchaseEntity.getAmountDetails(), JsonObject.class);
		this.isEditable = purchaseEntity.isEditable();
		//this.isDelivered = purchaseEntity.isDeliverable();
		this.category = purchaseEntity.getMerchantEntity().getCategory();
		this.lastModifiedDateTime = purchaseEntity.getUpdatedDateTime();
		this.serverDateTime = purchaseEntity.getServerDateTime();
		// this.isDiscard = purchaseEntity.isDiscard();
		 this.paymentStatus = purchaseEntity.getPaymentStatus();
		 this.orderStatus = purchaseEntity.getOrderStatus().toString();
		 this.deliveryOptions = purchaseEntity.getDeliveryOptions();
		 this.totalAmount = purchaseEntity.getTotalAmount();
	}

	public String getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}

	public long getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(long purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public MerchantJson getMerchants() {
		return merchants;
	}

	public void setMerchants(MerchantJson merchants) {
		this.merchants = merchants;
	}

	public UserJson getUsers() {
		return users;
	}

	public void setUsers(UserJson users) {
		this.users = users;
	}

	public JsonElement getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(JsonElement productDetails) {
		this.productDetails = productDetails;
	}

	public JsonElement getAmountDetails() {
		return amountDetails;
	}

	public void setAmountDetails(JsonElement amountDetails) {
		this.amountDetails = amountDetails;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public boolean isDelivered() {
		return isDelivered;
	}

	public void setDelivered(boolean isDelivered) {
		this.isDelivered = isDelivered;
	}

	public long getLastModifiedDateTime() {
		return lastModifiedDateTime;
	}

	public void setLastModifiedDateTime(long lastModifiedDateTime) {
		this.lastModifiedDateTime = lastModifiedDateTime;
	}

	public long getServerDateTime() {
		return serverDateTime;
	}

	public void setServerDateTime(long serverDateTime) {
		this.serverDateTime = serverDateTime;
	}

	

	public boolean isDiscard() {
		return isDiscard;
	}

	public void setDiscard(boolean isDiscard) {
		this.isDiscard = isDiscard;
	}

	
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public DeliveryOptions getDeliveryOptions() {
		return deliveryOptions;
	}
	
	

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public DiscardJson getDiscardJson() {
		return discardJson;
	}

	public void setDiscardJson(DiscardJson discardJson) {
		this.discardJson = discardJson;
	}

	public void setDeliveryOptions(DeliveryOptions deliveryOptions) {
		this.deliveryOptions = deliveryOptions;
	}
	
	

	public AddressJson getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(AddressJson addressDetails) {
		this.addressDetails = addressDetails;
	}

	@Override
	public String toString() {
		return "PurchaseMerchantJson [purchaseId=" + purchaseId + ", purchaseDate=" + purchaseDate + ", billNumber="
				+ billNumber + ", merchants=" + merchants + ", users=" + users + ", productDetails=" + productDetails
				+ ", amountDetails=" + amountDetails + ", category=" + category + ", isEditable=" + isEditable
				+ ", isDelivered=" + isDelivered + ", lastModifiedDateTime=" + lastModifiedDateTime
				+ ", serverDateTime=" + serverDateTime + ", isDiscard=" + isDiscard + ", paymentStatus=" + paymentStatus
				+ ", orderStatus=" + orderStatus + ", deliveryOptions=" + deliveryOptions + ", totalAmount="
				+ totalAmount + ", discardJson=" + discardJson + ", addressJson=" + addressDetails + "]";
	}

	

	

	

}
