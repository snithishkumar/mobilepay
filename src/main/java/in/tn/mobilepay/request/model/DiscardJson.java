package in.tn.mobilepay.request.model;

import java.util.ArrayList;
import java.util.List;

import in.tn.mobilepay.entity.DiscardEntity;
import in.tn.mobilepay.entity.TransactionalDetailsEntity;
import in.tn.mobilepay.enumeration.DiscardBy;

public class DiscardJson extends TokenJson{
	
	private String userMobile;
	private String purchaseGuid;
	private String reason;
	private long createdDateTime;
	
	private DiscardBy discardBy;
	
	 private List<TransactionalDetailsEntity> transactions = new ArrayList<>();
	
	public DiscardJson(){
		
	}
	
	public DiscardJson(DiscardEntity discardEntity){
		this.reason = discardEntity.getReason();
		this.discardBy = discardEntity.getDiscardBy();
		
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
	
	

	public List<TransactionalDetailsEntity> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionalDetailsEntity> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "DiscardJson [userMobile=" + userMobile + ", purchaseGuid=" + purchaseGuid + ", reason=" + reason
				+ ", createdDateTime=" + createdDateTime + ", discardBy=" + discardBy + ", transactions=" + transactions
				+ "]";
	}

}
