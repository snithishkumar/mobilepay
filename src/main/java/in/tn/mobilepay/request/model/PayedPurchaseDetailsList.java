package in.tn.mobilepay.request.model;

import java.util.ArrayList;
import java.util.List;

public class PayedPurchaseDetailsList{
	
	private List<PayedPurchaseDetailsJson> purchaseDetailsJsons = new ArrayList<>();

	public List<PayedPurchaseDetailsJson> getPurchaseDetailsJsons() {
		return purchaseDetailsJsons;
	}

	public void setPurchaseDetailsJsons(List<PayedPurchaseDetailsJson> purchaseDetailsJsons) {
		this.purchaseDetailsJsons = purchaseDetailsJsons;
	}

	@Override
	public String toString() {
		return "PayedPurchaseDetailsList [purchaseDetailsJsons=" + purchaseDetailsJsons + "]";
	}
	
	

}
