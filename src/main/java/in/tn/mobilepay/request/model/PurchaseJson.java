package in.tn.mobilepay.request.model;

import java.util.ArrayList;
import java.util.List;

import in.tn.mobilepay.enumeration.DeliveryOptions;

public class PurchaseJson extends TokenJson {

	private String billNumber;
	private String purchaseUuid;
	private String purchaseDateTime;
	private String userMobile;
	private DeliveryOptions deliveryOptions;
	private Boolean isEditable;
	private List<PurchaseDetailsJson> purchaseDetails = new ArrayList<PurchaseDetailsJson>();
	private AmountDetailsJson amountDetails;
	private String totalAmount;

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public String getPurchaseUuid() {
		return purchaseUuid;
	}

	public void setPurchaseUuid(String purchaseUuid) {
		this.purchaseUuid = purchaseUuid;
	}

	
	public String getPurchaseDateTime() {
		return purchaseDateTime;
	}

	public void setPurchaseDateTime(String purchaseDateTime) {
		this.purchaseDateTime = purchaseDateTime;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	
	public DeliveryOptions getDeliveryOptions() {
		return deliveryOptions;
	}

	public void setDeliveryOptions(DeliveryOptions deliveryOptions) {
		this.deliveryOptions = deliveryOptions;
	}

	public List<PurchaseDetailsJson> getPurchaseDetails() {
		return purchaseDetails;
	}

	public void setPurchaseDetails(List<PurchaseDetailsJson> purchaseDetails) {
		this.purchaseDetails = purchaseDetails;
	}

	public AmountDetailsJson getAmountDetails() {
		return amountDetails;
	}

	public void setAmountDetails(AmountDetailsJson amountDetailsJson) {
		this.amountDetails = amountDetailsJson;
	}
	
	

	public String getTotalAmount() {
		return totalAmount;
	}

	
	
	public Boolean getIsEditable() {
		return isEditable;
	}

	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Override
	public String toString() {
		return "PurchaseJson [billNumber=" + billNumber + ", purchaseUuid=" + purchaseUuid + ", purchaseDateTime="
				+ purchaseDateTime + ", userMobile=" + userMobile + ", deliveryOptions=" + deliveryOptions
				+ ", isEditable=" + isEditable + ", purchaseDetails=" + purchaseDetails + ", amountDetails="
				+ amountDetails + ", totalAmount=" + totalAmount + "]";
	}

	

}
