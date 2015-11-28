package in.tn.mobilepay.json;

public class TermsConditions {

	private String discount;
	private String coupon;
	private String redeemed;
	private String purchase;
	private String conditions;
	private String id;
	private String outlet;

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	public String getRedeemed() {
		return redeemed;
	}

	public void setRedeemed(String redeemed) {
		this.redeemed = redeemed;
	}

	public String getPurchase() {
		return purchase;
	}

	public void setPurchase(String purchase) {
		this.purchase = purchase;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOutlet() {
		return outlet;
	}

	public void setOutlet(String outlet) {
		this.outlet = outlet;
	}

	@Override
	public String toString() {
		return "TermsConditions [discount=" + discount + ", coupon=" + coupon
				+ ", redeemed=" + redeemed + ", purchase=" + purchase
				+ ", conditions=" + conditions + ", id=" + id + ", outlet="
				+ outlet + "]";
	}

}
