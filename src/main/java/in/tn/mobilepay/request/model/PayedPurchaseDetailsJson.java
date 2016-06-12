package in.tn.mobilepay.request.model;

import java.util.ArrayList;
import java.util.List;

import in.tn.mobilepay.entity.TransactionalDetailsEntity;
import in.tn.mobilepay.enumeration.DeliveryOptions;
import in.tn.mobilepay.response.model.AddressJson;

public class PayedPurchaseDetailsJson {
	
	private String purchaseId;
	private String productDetails;
	private String amountDetails;
	private DeliveryOptions deliveryOptions;
	private long payemetTime;
	 private AddressJson addressJson;
	 private String addressGuid;
	 private String totalAmount;
	 private List<TransactionalDetailsEntity> transactions = new ArrayList<>();
	    
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
	public DeliveryOptions getDeliveryOptions() {
		return deliveryOptions;
	}
	public void setDeliveryOptions(DeliveryOptions deliveryOptions) {
		this.deliveryOptions = deliveryOptions;
	}
	public long getPayemetTime() {
		return payemetTime;
	}
	public void setPayemetTime(long payemetTime) {
		this.payemetTime = payemetTime;
	}
	
	
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
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
				+ ", amountDetails=" + amountDetails + ", deliveryOptions=" + deliveryOptions + ", payemetTime="
				+ payemetTime + ", addressJson=" + addressJson + ", addressGuid=" + addressGuid + ", transactions="
				+ transactions + "]";
	}
	
	

}
