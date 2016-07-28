package in.tn.mobilepay.enumeration;

public enum DeliveryOptions {
	BOTH(0), COUNTER_COLLECTION(1),HOME(2),NONE(3);

	private int deliveryOptions;

	private DeliveryOptions(int deliveryOptions) {
		this.deliveryOptions = deliveryOptions;
	}

	public int getDeliveryOptions() {
		return deliveryOptions;
	}

}
