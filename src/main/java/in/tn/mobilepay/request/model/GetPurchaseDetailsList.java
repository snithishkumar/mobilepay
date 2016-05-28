package in.tn.mobilepay.request.model;

import java.util.ArrayList;
import java.util.List;

public class GetPurchaseDetailsList{
	
	private List<String> purchaseUUIDs = new ArrayList<>();

	public List<String> getPurchaseUUIDs() {
		return purchaseUUIDs;
	}

	public void setPurchaseUUIDs(List<String> purchaseUUIDs) {
		this.purchaseUUIDs = purchaseUUIDs;
	}

	@Override
	public String toString() {
		return "GetPurchaseDetailsList [purchaseUUIDs=" + purchaseUUIDs + "]";
	}
	
	

}
