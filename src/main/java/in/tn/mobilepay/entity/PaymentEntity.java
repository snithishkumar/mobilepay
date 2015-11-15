package in.tn.mobilepay.entity;

public class PaymentEntity {

	private int paymentId;
	private double lat;
	private double lng;
	private double paymentAmount;
	private long payedDateTime;

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public long getPayedDateTime() {
		return payedDateTime;
	}

	public void setPayedDateTime(long payedDateTime) {
		this.payedDateTime = payedDateTime;
	}

	@Override
	public String toString() {
		return "PaymentEntity [paymentId=" + paymentId + ", lat=" + lat
				+ ", lng=" + lng + ", paymentAmount=" + paymentAmount
				+ ", payedDateTime=" + payedDateTime + "]";
	}

}
