package in.tn.mobilepay.request.model;

import in.tn.mobilepay.enumeration.DiscountType;

public class AmountDetailsJson {

	private float taxAmount;
	private String discount;
	private DiscountType discountType;
	private String discountMiniVal;
	private String deliveryAmount;
	
	

	public String getDeliveryAmount() {
		return deliveryAmount;
	}

	public void setDeliveryAmount(String deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}

	public float getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(float taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public DiscountType getDiscountType() {
		return discountType;
	}

	public void setDiscountType(DiscountType discountType) {
		this.discountType = discountType;
	}

	public String getDiscountMiniVal() {
		return discountMiniVal;
	}

	public void setDiscountMiniVal(String discountMiniVal) {
		this.discountMiniVal = discountMiniVal;
	}

	@Override
	public String toString() {
		return "AmountDetailsJson [taxAmount=" + taxAmount + ", discount=" + discount + ", discountType=" + discountType
				+ ", discountMiniVal=" + discountMiniVal + "]";
	}

}
