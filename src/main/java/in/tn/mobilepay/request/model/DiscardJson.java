package in.tn.mobilepay.request.model;

public class DiscardJson extends TokenJson {
	
	private String userMobile;
	private String purchaseGuid;
	private String reason;

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

	@Override
	public String toString() {
		return "DiscardJson [userMobile=" + userMobile + ", purchaseGuid=" + purchaseGuid + ", reason=" + reason + "]";
	}

}
