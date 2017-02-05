package in.tn.mobilepay.request.model;

import in.tn.mobilepay.enumeration.DiscountType;

public class AmountDetailsJson {

	private float taxAmount;
	private String discountAmount;
	private DiscountType discountType;
	private String minimumAmount;
	private String deliveryAmount;
	public float getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(float taxAmount) {
		this.taxAmount = taxAmount;
	}
	public String getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}
	public DiscountType getDiscountType() {
		return discountType;
	}
	public void setDiscountType(DiscountType discountType) {
		this.discountType = discountType;
	}
	public String getMinimumAmount() {
		return minimumAmount;
	}
	public void setMinimumAmount(String minimumAmount) {
		this.minimumAmount = minimumAmount;
	}
	public String getDeliveryAmount() {
		return deliveryAmount;
	}
	public void setDeliveryAmount(String deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}
	@Override
	public String toString() {
		return "AmountDetailsJson [taxAmount=" + taxAmount + ", discountAmount=" + discountAmount + ", discountType="
				+ discountType + ", minimumAmount=" + minimumAmount + ", deliveryAmount=" + deliveryAmount + "]";
	}
	
	

}
