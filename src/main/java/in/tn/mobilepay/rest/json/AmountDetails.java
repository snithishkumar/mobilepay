package in.tn.mobilepay.rest.json;

import in.tn.mobilepay.enumeration.DiscountType;

/**
 * @author Nithishkumar
 *
 */
public class AmountDetails {

	private String taxAmount;
	private DiscountType discountType;
	private String discountAmount;
	private String minimumAmount;
	private String deliveryAmount;

	public String getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}

	public DiscountType getDiscountType() {
		return discountType;
	}

	public void setDiscountType(DiscountType discountType) {
		this.discountType = discountType;
	}

	public String getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
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
		return "AmountDetails [taxAmount=" + taxAmount + ", discountType=" + discountType + ", discountAmount="
				+ discountAmount + ", minimumAmount=" + minimumAmount + "]";
	}

}
