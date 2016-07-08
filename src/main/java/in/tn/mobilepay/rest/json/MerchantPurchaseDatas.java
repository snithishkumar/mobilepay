package in.tn.mobilepay.rest.json;

import java.util.ArrayList;
import java.util.List;

public class MerchantPurchaseDatas {

	private List<MerchantPurchaseData> merchantPurchaseDatas = new ArrayList<>();

	public List<MerchantPurchaseData> getMerchantPurchaseDatas() {
		return merchantPurchaseDatas;
	}

	public void setMerchantPurchaseDatas(List<MerchantPurchaseData> merchantPurchaseDatas) {
		this.merchantPurchaseDatas = merchantPurchaseDatas;
	}

	@Override
	public String toString() {
		return "MerchantPurchaseDatas [merchantPurchaseDatas=" + merchantPurchaseDatas + "]";
	}

}
