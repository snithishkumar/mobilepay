package in.tn.mobilepay.rest.json;

import java.util.List;

import in.tn.mobilepay.entity.PurchaseEntity;
import in.tn.mobilepay.enumeration.DeliveryOptions;
import in.tn.mobilepay.response.model.AddressJson;

public class PaiedPurchaseDetails extends CommonPurchaseData{
	private List<PurchaseItem> purchaseItem;
	private double totalAmount;
	private AddressJson deliveryAddress;
	private CalculatedAmounts calculatedAmounts;
	private DeliveryOptions userDeliveryOptions;
	
	public PaiedPurchaseDetails(PurchaseEntity purchaseEntity){
		super(purchaseEntity);
		this.totalAmount = purchaseEntity.getTotalAmount();
		this.userDeliveryOptions = purchaseEntity.getUserDeliveryOptions();
	}
	
	public List<PurchaseItem> getPurchaseItem() {
		return purchaseItem;
	}
	public void setPurchaseItem(List<PurchaseItem> purchaseItem) {
		this.purchaseItem = purchaseItem;
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

	public CalculatedAmounts getCalculatedAmounts() {
		return calculatedAmounts;
	}

	public void setCalculatedAmounts(CalculatedAmounts calculatedAmounts) {
		this.calculatedAmounts = calculatedAmounts;
	}

	public DeliveryOptions getUserDeliveryOptions() {
		return userDeliveryOptions;
	}

	public void setUserDeliveryOptions(DeliveryOptions userDeliveryOptions) {
		this.userDeliveryOptions = userDeliveryOptions;
	}

	@Override
	public String toString() {
		return "PaiedPurchaseDetails [purchaseItem=" + purchaseItem + ", totalAmount=" + totalAmount
				+ ", deliveryAddress=" + deliveryAddress + ", calculatedAmounts=" + calculatedAmounts
				+ ", userDeliveryOptions=" + userDeliveryOptions + "]";
	}
	
	

}
