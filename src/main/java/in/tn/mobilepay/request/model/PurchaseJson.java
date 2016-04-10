package in.tn.mobilepay.request.model;

import java.util.ArrayList;
import java.util.List;

public class PurchaseJson extends TokenJson {

	private String billNumber;
	private String purchaseUuid;
	private String dateTime;
	private String userMobile;
	private Boolean isHomeDeliver;
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

	public AmountDetailsJson getAmountDetails() {
		return amountDetails;
	}

	public void setAmountDetails(AmountDetailsJson amountDetailsJson) {
		this.amountDetails = amountDetailsJson;
	}
	
	

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Override
	public String toString() {
		return "PurchaseJson [billNumber=" + billNumber + ", purchaseUuid=" + purchaseUuid + ", dateTime=" + dateTime
				+ ", userMobile=" + userMobile + ", isHomeDeliver=" + isHomeDeliver + ", purchaseDetails="
				+ purchaseDetails + ", amountDetails=" + amountDetails + ", totalAmount=" + totalAmount + "]";
	}

}
