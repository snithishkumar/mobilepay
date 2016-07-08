package in.tn.mobilepay.rest.json;

import java.util.ArrayList;
import java.util.List;

public class PurchaseDetailsList {

	private List<PurchaseDetails> purchaseDetails = new ArrayList<>();

	public List<PurchaseDetails> getPurchaseDetails() {
		return purchaseDetails;
	}

	public void setPurchaseDetails(List<PurchaseDetails> purchaseDetails) {
		this.purchaseDetails = purchaseDetails;
	}

}
