package in.tn.mobilepay.request.model;

import java.util.ArrayList;
import java.util.List;

import in.tn.mobilepay.entity.TransactionalDetailsEntity;
import in.tn.mobilepay.enumeration.DeliveryOptions;
import in.tn.mobilepay.enumeration.OrderStatus;
import in.tn.mobilepay.response.model.AddressJson;
import in.tn.mobilepay.rest.json.CalculatedAmounts;

public class PayedPurchaseDetailsJson {
	
	private String purchaseId;
	private String productDetails;
	private String amountDetails;
	private DeliveryOptions userDeliveryOptions;
	private long payemetTime;
	 private AddressJson addressJson;
	 private String addressGuid;
	 private OrderStatus orderStatus;
	 private List<TransactionalDetailsEntity> transactions = new ArrayList<>();
	 private CalculatedAmounts calculatedAmounts;
	    
	public String getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
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
	
	
	
	public CalculatedAmounts getCalculatedAmounts() {
		return calculatedAmounts;
	}
	public void setCalculatedAmounts(CalculatedAmounts calculatedAmounts) {
		this.calculatedAmounts = calculatedAmounts;
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
	public long getPayemetTime() {
		return payemetTime;
	}
	public void setPayemetTime(long payemetTime) {
		this.payemetTime = payemetTime;
	}
	
	
	public AddressJson getAddressJson() {
		return addressJson;
	}
	public void setAddressJson(AddressJson addressJson) {
		this.addressJson = addressJson;
	}
	public String getAddressGuid() {
		return addressGuid;
	}
	public void setAddressGuid(String addressGuid) {
		this.addressGuid = addressGuid;
	}
	
	
	public List<TransactionalDetailsEntity> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<TransactionalDetailsEntity> transactions) {
		this.transactions = transactions;
	}
	@Override
	public String toString() {
		return "PayedPurchaseDetailsJson [purchaseId=" + purchaseId + ", productDetails=" + productDetails
				+ ", amountDetails=" + amountDetails + ", userDeliveryOptions=" + userDeliveryOptions + ", payemetTime="
				+ payemetTime + ", addressJson=" + addressJson + ", addressGuid=" + addressGuid + ", orderStatus="
				+ orderStatus + ", transactions=" + transactions + ", calculatedAmounts=" + calculatedAmounts + "]";
	}
	
	
	
	

}
