package in.tn.mobilepay.request.model;

import java.util.List;

public class PurchaseJson extends TokenJson {

	private String billNumber;
	private String purchaseUuid;
	private String dateTime;
	private String userMobile;
	private Boolean isHomeDeliver;
	private List<PurchaseDetailsJson> purchaseDetails;
	private List<AmountDetailsJson> amountDetails;
	private String totalAmount;
	private String payableAmount;

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public Boolean getIsHomeDeliver() {
		return isHomeDeliver;
	}

	public void setIsHomeDeliver(Boolean isHomeDeliver) {
		this.isHomeDeliver = isHomeDeliver;
	}

	public List<PurchaseDetailsJson> getPurchaseDetails() {
		return purchaseDetails;
	}

	public void setPurchaseDetails(List<PurchaseDetailsJson> purchaseDetails) {
		this.purchaseDetails = purchaseDetails;
	}

	public List<AmountDetailsJson> getAmountDetails() {
		return amountDetails;
	}

	public void setAmountDetails(List<AmountDetailsJson> amountDetails) {
		this.amountDetails = amountDetails;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(String payableAmount) {
		this.payableAmount = payableAmount;
	}
	
	

	public String getPurchaseUuid() {
		return purchaseUuid;
	}

	public void setPurchaseUuid(String purchaseUuid) {
		this.purchaseUuid = purchaseUuid;
	}

	@Override
	public String toString() {
		return "PurchaseJson [billNumber=" + billNumber + ", purchaseUuid=" + purchaseUuid + ", dateTime=" + dateTime
				+ ", userMobile=" + userMobile + ", isHomeDeliver=" + isHomeDeliver + ", purchaseDetails="
				+ purchaseDetails + ", amountDetails=" + amountDetails + ", totalAmount=" + totalAmount
				+ ", payableAmount=" + payableAmount + "]";
	}

}
