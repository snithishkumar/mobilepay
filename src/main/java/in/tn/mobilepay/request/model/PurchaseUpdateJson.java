package in.tn.mobilepay.request.model;

import in.tn.mobilepay.response.model.PurchaseJson;

public class PurchaseUpdateJson extends PurchaseJson{
	
	private long updatedDateTime;

	public long getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(long updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}
	
	

}
