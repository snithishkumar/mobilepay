package in.tn.mobilepay.rest.json;

import java.util.List;

import in.tn.mobilepay.entity.PurchaseEntity;
import in.tn.mobilepay.response.model.AddressJson;

public class PaiedPurchaseDetails extends CommonPurchaseData{
	private List<PurchaseItem> purchaseItem;
	private String totalAmount;
	private AddressJson addressDetails;
	private AmountDetails amountDetails;
	
	public PaiedPurchaseDetails(PurchaseEntity purchaseEntity){
		super(purchaseEntity);
		this.totalAmount = String.valueOf(purchaseEntity.getTotalAmount());
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
	public AddressJson getAddressDetails() {
		return addressDetails;
	}
	public void setAddressDetails(AddressJson addressDetails) {
		this.addressDetails = addressDetails;
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
				+ ", addressDetails=" + addressDetails + ", amountDetails=" + amountDetails + "]";
	}
	
	

}
