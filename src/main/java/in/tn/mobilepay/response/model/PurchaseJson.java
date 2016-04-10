package in.tn.mobilepay.response.model;

import in.tn.mobilepay.entity.PurchaseEntity;
import in.tn.mobilepay.enumeration.DeliveryOptions;

public class PurchaseJson {

	private String purchaseId;
	private long purchaseDate;
	private String billNumber;
	private MerchantJson merchants;
	private UserJson users;
	private String productDetails;
	private String amountDetails;
	private String category;
	private boolean isEditable;
	private boolean isDelivered;
	private long lastModifiedDateTime;
	private long serverDateTime;
	private boolean isDiscard;
	private boolean isPayed;
	private String orderStatus;
	private DeliveryOptions deliveryOptions;
	private String totalAmount;
	
	public PurchaseJson(){
		
	}
	
	public PurchaseJson(PurchaseEntity purchaseEntity){
		this.purchaseId = purchaseEntity.getPurchaseGuid();
		this.purchaseDate = purchaseEntity.getPurchaseDateTime();
		this.billNumber = purchaseEntity.getBillNumber();
		this.productDetails = purchaseEntity.getPurchaseData();
		this.amountDetails = purchaseEntity.getAmountDetails();
		this.isEditable = purchaseEntity.isEditable();
		this.isDelivered = purchaseEntity.isDeliverable();
		this.category = purchaseEntity.getMerchantEntity().getCategory();
		this.lastModifiedDateTime = purchaseEntity.getUpdatedDateTime();
		this.serverDateTime = purchaseEntity.getServerDateTime();
		 this.isDiscard = purchaseEntity.isDiscard();
		 this.isPayed = purchaseEntity.isPayed();
		 this.orderStatus = purchaseEntity.getOrderStatus();
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

	public String getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}

	public String getAmountDetails() {
		return amountDetails;
	}

	public void setAmountDetails(String amountDetails) {
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

	public boolean isPayed() {
		return isPayed;
	}

	public void setPayed(boolean isPayed) {
		this.isPayed = isPayed;
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

	public void setDeliveryOptions(DeliveryOptions deliveryOptions) {
		this.deliveryOptions = deliveryOptions;
	}

	@Override
	public String toString() {
		return "PurchaseJson [purchaseId=" + purchaseId + ", purchaseDate=" + purchaseDate + ", billNumber="
				+ billNumber + ", merchants=" + merchants + ", users=" + users + ", productDetails=" + productDetails
				+ ", amountDetails=" + amountDetails + ", category=" + category + ", isEditable=" + isEditable
				+ ", isDelivered=" + isDelivered + ", lastModifiedDateTime=" + lastModifiedDateTime
				+ ", serverDateTime=" + serverDateTime + ", isDiscard=" + isDiscard + ", isPayed=" + isPayed
				+ ", orderStatus=" + orderStatus + ", deliveryOptions=" + deliveryOptions + "]";
	}

	

	
	
	


}
