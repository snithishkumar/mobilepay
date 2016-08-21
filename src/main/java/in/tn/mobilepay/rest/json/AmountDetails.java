package in.tn.mobilepay.rest.json;

import in.tn.mobilepay.enumeration.DiscountType;
import in.tn.mobilepay.exception.ValidationException;

/**
 * @author Nithishkumar
 *
 */
public class AmountDetails {

	private double taxAmount;
	private DiscountType discountType;
	private double discountAmount;
	private double minimumAmount;
	private double deliveryAmount;

	public double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public DiscountType getDiscountType() {
		return discountType;
	}

	public void setDiscountType(DiscountType discountType) {
		this.discountType = discountType;
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public double getMinimumAmount() {
		return minimumAmount;
	}

	public void setMinimumAmount(double minimumAmount) {
		this.minimumAmount = minimumAmount;
	}
	
	

	public double getDeliveryAmount() {
		return deliveryAmount;
	}

	public void setDeliveryAmount(double deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}
	
	public  boolean validateData()throws ValidationException{
		  if(discountType == null){
			   throw new ValidationException(400, "Discount Type is not found.");
		  }
		  return true;
		}

	@Override
	public String toString() {
		return "AmountDetails [taxAmount=" + taxAmount + ", discountType=" + discountType + ", discountAmount="
				+ discountAmount + ", minimumAmount=" + minimumAmount + "]";
	}

}
