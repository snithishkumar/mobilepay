package in.tn.mobilepay.response.model;

import in.tn.mobilepay.entity.HomeDeliveryOptionsEntity;
import in.tn.mobilepay.entity.PurchaseEntity;
import in.tn.mobilepay.enumeration.DeliveryOptions;
import in.tn.mobilepay.enumeration.OrderStatus;
import in.tn.mobilepay.enumeration.PaymentStatus;
import in.tn.mobilepay.request.model.DiscardJson;
import in.tn.mobilepay.rest.json.CalculatedAmounts;

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
	private PaymentStatus paymentStatus;
	private OrderStatus orderStatus;
	private DeliveryOptions merchantDeliveryOptions;
	private DeliveryOptions userDeliveryOptions;
	private DiscardJson discardJson;
	private AddressJson addressJson;
	private CounterDetailsJson counterDetails;
	private HomeDeliveryOptionsEntity homeDeliveryOptions;
	private CalculatedAmounts calculatedAmounts;

	private double totalAmount;
	public PurchaseJson() {

	}

	public PurchaseJson(PurchaseEntity purchaseEntity) {
		this.purchaseId = purchaseEntity.getPurchaseGuid();
		this.purchaseDate = purchaseEntity.getPurchaseDateTime();
		this.billNumber = purchaseEntity.getBillNumber();
		this.productDetails = purchaseEntity.getPurchaseData();
		this.amountDetails = purchaseEntity.getAmountDetails();
		this.isEditable = purchaseEntity.isEditable();
		this.category = purchaseEntity.getMerchantEntity().getCategory();
		this.lastModifiedDateTime = purchaseEntity.getUpdatedDateTime();
		this.serverDateTime = purchaseEntity.getServerDateTime();
		this.paymentStatus = purchaseEntity.getPaymentStatus();
		this.orderStatus = purchaseEntity.getOrderStatus();
		this.merchantDeliveryOptions = purchaseEntity.getMerchantDeliveryOptions();
		this.userDeliveryOptions = purchaseEntity.getUserDeliveryOptions();
		this.totalAmount = purchaseEntity.getTotalAmount();
	}
	
	

	public CalculatedAmounts getCalculatedAmounts() {
		return calculatedAmounts;
	}

	public void setCalculatedAmounts(CalculatedAmounts calculatedAmounts) {
		this.calculatedAmounts = calculatedAmounts;
	}

	public DeliveryOptions getUserDeliveryOptions() {
		return userDeliveryOptions;
	}

	public void setUserDeliveryOptions(DeliveryOptions userDeliveryOptions) {
		this.userDeliveryOptions = userDeliveryOptions;
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

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	

	

	public DiscardJson getDiscardJson() {
		return discardJson;
	}

	public void setDiscardJson(DiscardJson discardJson) {
		this.discardJson = discardJson;
	}

	

	public DeliveryOptions getMerchantDeliveryOptions() {
		return merchantDeliveryOptions;
	}

	public void setMerchantDeliveryOptions(DeliveryOptions merchantDeliveryOptions) {
		this.merchantDeliveryOptions = merchantDeliveryOptions;
	}

	public AddressJson getAddressJson() {
		return addressJson;
	}

	public void setAddressJson(AddressJson addressJson) {
		this.addressJson = addressJson;
	}
	
	

	public CounterDetailsJson getCounterDetails() {
		return counterDetails;
	}

	public void setCounterDetails(CounterDetailsJson counterDetails) {
		this.counterDetails = counterDetails;
	}
	
	

	public HomeDeliveryOptionsEntity getHomeDeliveryOptions() {
		return homeDeliveryOptions;
	}

	public void setHomeDeliveryOptions(HomeDeliveryOptionsEntity homeDeliveryOptions) {
		this.homeDeliveryOptions = homeDeliveryOptions;
	}

	@Override
	public String toString() {
		return "PurchaseJson [purchaseId=" + purchaseId + ", purchaseDate=" + purchaseDate + ", billNumber="
				+ billNumber + ", merchants=" + merchants + ", users=" + users + ", productDetails=" + productDetails
				+ ", amountDetails=" + amountDetails + ", category=" + category + ", isEditable=" + isEditable
				+ ", isDelivered=" + isDelivered + ", lastModifiedDateTime=" + lastModifiedDateTime
				+ ", serverDateTime=" + serverDateTime + ", isDiscard=" + isDiscard + ", paymentStatus=" + paymentStatus
				+ ", orderStatus=" + orderStatus + ", merchantDeliveryOptions=" + merchantDeliveryOptions
				+ ", userDeliveryOptions=" + userDeliveryOptions + ", discardJson=" + discardJson + ", addressJson="
				+ addressJson + ", counterDetails=" + counterDetails + ", homeDeliveryOptions=" + homeDeliveryOptions
				+ ", calculatedAmounts=" + calculatedAmounts + "]";
	}

	

}
