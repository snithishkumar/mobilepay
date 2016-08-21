package in.tn.mobilepay.rest.json;

import java.util.List;

import in.tn.mobilepay.entity.PurchaseEntity;
import in.tn.mobilepay.enumeration.DeliveryOptions;
import in.tn.mobilepay.response.model.AddressJson;

public class PaiedPurchaseDetails extends CommonPurchaseData{
	private List<PurchaseItem> purchaseItem;
	private String totalAmount;
	private AddressJson deliveryAddress;
	private AmountDetails amountDetails;
	private DeliveryOptions userDeliveryOptions;
	
	public PaiedPurchaseDetails(PurchaseEntity purchaseEntity){
		super(purchaseEntity);
		this.totalAmount = String.valueOf(purchaseEntity.getTotalAmount());
		this.userDeliveryOptions = purchaseEntity.getUserDeliveryOptions();
	}
	
	public List<PurchaseItem> getPurchaseItem() {
		return purchaseItem;
	}
	public void setPurchaseItem(List<PurchaseItem> purchaseItem) {
		this.purchaseItem = purchaseItem;
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
	public AmountDetails getAmountDetails() {
		return amountDetails;
	}
	public void setAmountDetails(AmountDetails amountDetails) {
		this.amountDetails = amountDetails;
	}
	@Override
	public String toString() {
		return "PaiedPurchaseDetails [purchaseItem=" + purchaseItem + ", totalAmount=" + totalAmount
				+ ", addressDetails=" + deliveryAddress + ", amountDetails=" + amountDetails + "]";
	}
	
	

}
