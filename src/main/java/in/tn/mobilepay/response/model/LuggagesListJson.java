package in.tn.mobilepay.response.model;

import java.util.ArrayList;
import java.util.List;

public class LuggagesListJson {

	private List<OrderStatusJson> luggageJsons = new ArrayList<>();
	private List<PurchaseJson> purchaseJsons = new ArrayList<>();

	public List<OrderStatusJson> getLuggageJsons() {
		return luggageJsons;
	}

	public void setLuggageJsons(List<OrderStatusJson> luggageJsons) {
		this.luggageJsons = luggageJsons;
	}

	public List<PurchaseJson> getPurchaseJsons() {
		return purchaseJsons;
	}

	public void setPurchaseJsons(List<PurchaseJson> purchaseJsons) {
		this.purchaseJsons = purchaseJsons;
	}

	@Override
	public String toString() {
		return "LuggagesListJson [luggageJsons=" + luggageJsons + ", purchaseJsons=" + purchaseJsons + "]";
	}

}
