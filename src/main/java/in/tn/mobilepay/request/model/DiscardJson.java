package in.tn.mobilepay.request.model;

import in.tn.mobilepay.entity.DiscardEntity;
import in.tn.mobilepay.enumeration.DiscardBy;

public class DiscardJson extends TokenJson{
	
	private String userMobile;
	private String purchaseGuid;
	private String reason;
	private long createdDateTime;
	
	private DiscardBy discardBy;
	
	
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

	@Override
	public String toString() {
		return "DiscardJson [userMobile=" + userMobile + ", purchaseGuid=" + purchaseGuid + ", reason=" + reason + "]";
	}

}
