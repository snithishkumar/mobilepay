package in.tn.mobilepay.rest.json;

import java.util.List;

import in.tn.mobilepay.entity.PurchaseEntity;
import in.tn.mobilepay.enumeration.DeliveryOptions;
import in.tn.mobilepay.request.model.DiscardJson;
import in.tn.mobilepay.response.model.AddressJson;

public class HistoryPurchaseData extends CommonPurchaseData{
	
	private List<PurchaseItem> purchaseItem;
	private DeliveryOptions userDeliveryOptions;
	private double totalAmount;
	private AddressJson deliveryAddress;
	private DiscardJson discardDetails;
	private CalculatedAmounts calculatedAmounts;
	
	public HistoryPurchaseData(PurchaseEntity purchaseEntity){
		super(purchaseEntity);
		this.totalAmount = purchaseEntity.getTotalAmount();
	}
	
	public List<PurchaseItem> getPurchaseItem() {
		return purchaseItem;
	}
	public void setPurchaseItem(List<PurchaseItem> purchaseItem) {
		this.purchaseItem = purchaseItem;
	}
	public DeliveryOptions getUserDeliveryOptions() {
		return userDeliveryOptions;
	}
	public void setUserDeliveryOptions(DeliveryOptions userDeliveryOptions) {
		this.userDeliveryOptions = userDeliveryOptions;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
	public AddressJson getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(AddressJson deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public DiscardJson getDiscardDetails() {
		return discardDetails;
	}
	public void setDiscardDetails(DiscardJson discardDetails) {
		this.discardDetails = discardDetails;
	}

	public CalculatedAmounts getCalculatedAmounts() {
		return calculatedAmounts;
	}

	public void setCalculatedAmounts(CalculatedAmounts calculatedAmounts) {
		this.calculatedAmounts = calculatedAmounts;
	}

	@Override
	public String toString() {
		return "HistoryPurchaseData [purchaseItem=" + purchaseItem + ", userDeliveryOptions=" + userDeliveryOptions
				+ ", totalAmount=" + totalAmount + ", deliveryAddress=" + deliveryAddress + ", discardDetails="
				+ discardDetails + ", calculatedAmounts=" + calculatedAmounts + "]";
	}
	
	
	


}
