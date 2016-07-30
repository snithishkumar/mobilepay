package in.tn.mobilepay.rest.json;

import in.tn.mobilepay.enumeration.DeliveryConditons;
import in.tn.mobilepay.enumeration.DeliveryOptions;

public class HomeDeliveryOptionsJson {

	private DeliveryOptions deliveryOptions;
	private DeliveryConditons deliveryConditons;
	private float minAmount;
	private float maxDistance;
	private float amount;
	
	
	public HomeDeliveryOptionsJson(){
		
	}
	


	public DeliveryOptions getDeliveryOptions() {
		return deliveryOptions;
	}

	public void setDeliveryOptions(DeliveryOptions deliveryOptions) {
		this.deliveryOptions = deliveryOptions;
	}

	public DeliveryConditons getDeliveryConditons() {
		return deliveryConditons;
	}

	public void setDeliveryConditons(DeliveryConditons deliveryConditons) {
		this.deliveryConditons = deliveryConditons;
	}

	public float getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(float minAmount) {
		this.minAmount = minAmount;
	}

	public float getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(float maxDistance) {
		this.maxDistance = maxDistance;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "DeliveryOptionsJson [deliveryOptions=" + deliveryOptions + ", deliveryConditons=" + deliveryConditons
				+ ", minAmount=" + minAmount + ", maxDistance=" + maxDistance + ", amount=" + amount + "]";
	}

}
