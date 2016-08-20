package in.tn.mobilepay.rest.json;

import java.util.List;

import in.tn.mobilepay.entity.PurchaseEntity;
import in.tn.mobilepay.enumeration.DeliveryOptions;
import in.tn.mobilepay.request.model.DiscardJson;
import in.tn.mobilepay.response.model.AddressJson;

public class HistoryPurchaseData extends CommonPurchaseData{
	
	private List<PurchaseItem> purchaseItem;
	private DeliveryOptions userDeliveryOptions;
	private String totalAmount;
	private AddressJson deliveryAddress;
	private DiscardJson discardDetails;
	private AmountDetails amountDetails;
	
	public HistoryPurchaseData(PurchaseEntity purchaseEntity){
		super(purchaseEntity);
		this.totalAmount = String.valueOf(purchaseEntity.getTotalAmount());
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
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
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
	public AmountDetails getAmountDetails() {
		return amountDetails;
	}
	public void setAmountDetails(AmountDetails amountDetails) {
		this.amountDetails = amountDetails;
	}

	@Override
	public String toString() {
		return "HistoryPurchaseData [purchaseItem=" + purchaseItem + ", userDeliveryOptions=" + userDeliveryOptions
				+ ", totalAmount=" + totalAmount + ", deliveryAddress=" + deliveryAddress + ", discardDetails="
				+ discardDetails + ", amountDetails=" + amountDetails + "]";
	}
	
	


}
