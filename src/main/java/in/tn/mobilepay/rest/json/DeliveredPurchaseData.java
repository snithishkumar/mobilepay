package in.tn.mobilepay.rest.json;

import java.util.List;

import in.tn.mobilepay.entity.PurchaseEntity;
import in.tn.mobilepay.enumeration.DeliveryOptions;
import in.tn.mobilepay.enumeration.PaymentStatus;
import in.tn.mobilepay.response.model.AddressJson;

public class DeliveredPurchaseData extends CommonPurchaseData {

	private List<PurchaseItem> purchaseItem;
	private DeliveryOptions userDeliveryOptions;

	private double totalAmount;
	private AddressJson deliveryAddressDetails;

	private CalculatedAmounts amountDetails;
	private PaymentStatus paymentStatus;
	
	public DeliveredPurchaseData(PurchaseEntity purchaseEntity){
		super(purchaseEntity);
		this.paymentStatus = purchaseEntity.getPaymentStatus();
		this.userDeliveryOptions = purchaseEntity.getUserDeliveryOptions();
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

	
	public AddressJson getDeliveryAddressDetails() {
		return deliveryAddressDetails;
	}
	public void setDeliveryAddressDetails(AddressJson deliveryAddressDetails) {
		this.deliveryAddressDetails = deliveryAddressDetails;
	}
	public CalculatedAmounts getAmountDetails() {
		return amountDetails;
	}
	public void setAmountDetails(CalculatedAmounts amountDetails) {
		this.amountDetails = amountDetails;
	}
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	@Override
	public String toString() {
		return "DeliveredPurchaseData [purchaseItem=" + purchaseItem + ", userDeliveryOptions=" + userDeliveryOptions
				+ ", totalAmount=" + totalAmount + ", deliveryAddressDetails=" + deliveryAddressDetails
				+ ", amountDetails=" + amountDetails + ", paymentStatus=" + paymentStatus + "]";
	}
	
	
	

}
