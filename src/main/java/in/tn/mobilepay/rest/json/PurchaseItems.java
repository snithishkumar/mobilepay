package in.tn.mobilepay.rest.json;

import java.util.ArrayList;
import java.util.List;

public class PurchaseItems {
	
	private List<PurchaseItem> purchaseItems = new ArrayList<>();

	public List<PurchaseItem> getPurchaseItems() {
		return purchaseItems;
	}

	public void setPurchaseItems(List<PurchaseItem> purchaseItems) {
		this.purchaseItems = purchaseItems;
	}

	@Override
	public String toString() {
		return "PurchaseItems [purchaseItems=" + purchaseItems + "]";
	}
	
	

}
