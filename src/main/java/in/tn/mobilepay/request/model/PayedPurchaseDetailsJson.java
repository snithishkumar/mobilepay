package in.tn.mobilepay.request.model;

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
	@Override
	public String toString() {
		return "PayedPurchaseDetailsJson [purchaseId=" + purchaseId + ", productDetails=" + productDetails
				+ ", amountDetails=" + amountDetails + ", deliveryOptions=" + deliveryOptions + ", payemetTime="
				+ payemetTime + ", addressJson=" + addressJson + ", addressGuid=" + addressGuid + "]";
	}
	
	

}
