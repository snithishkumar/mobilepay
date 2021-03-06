package in.tn.mobilepay.request.model;

import java.util.ArrayList;
import java.util.List;

import in.tn.mobilepay.entity.DiscardEntity;
import in.tn.mobilepay.entity.TransactionalDetailsEntity;
import in.tn.mobilepay.enumeration.DiscardBy;
import in.tn.mobilepay.rest.json.CalculatedAmounts;
import in.tn.mobilepay.rest.json.CommonPurchaseData;

public class DiscardJson extends CommonPurchaseData{

	private String userMobile;
	private String purchaseGuid;
	private String reason;
	private long createdDateTime;

	private String productDetails;
	private String amountDetails;
	private CalculatedAmounts calculatedAmounts;

	private DiscardBy discardBy;

	private List<TransactionalDetailsEntity> transactions = new ArrayList<>();

	public DiscardJson() {

	}

	public DiscardJson(DiscardEntity discardEntity) {
		this.reason = discardEntity.getReason();
		this.discardBy = discardEntity.getDiscardBy();
		this.createdDateTime = discardEntity.getCreatedDateTime();

	}

	public long getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(long createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getPurchaseGuid() {
		return purchaseGuid;
	}

	public void setPurchaseGuid(String purchaseGuid) {
		this.purchaseGuid = purchaseGuid;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public DiscardBy getDiscardBy() {
		return discardBy;
	}

	public void setDiscardBy(DiscardBy discardBy) {
		this.discardBy = discardBy;
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

	public List<TransactionalDetailsEntity> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionalDetailsEntity> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "DiscardJson [userMobile=" + userMobile + ", purchaseGuid=" + purchaseGuid + ", reason=" + reason
				+ ", createdDateTime=" + createdDateTime + ", productDetails=" + productDetails + ", amountDetails="
				+ amountDetails + ", calculatedAmounts=" + calculatedAmounts + ", discardBy=" + discardBy
				+ ", transactions=" + transactions + "]";
	}


}
